//
//  UploadImageDataService.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/10.
//

import Foundation
import Alamofire

struct UploadImageDataService {
    static let shared = UploadImageDataService()
    
    func uploadImage(nickname: String, image: UIImage, completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.uploadImage)"
        print(URL)
        
        let accessToken = UserDefaults.standard.string(forKey: "accessToken") ?? ""
        
        let header: HTTPHeaders = [
            "Content-Type" : "multipart/form-data",
            "ACCESS" : accessToken
        ]
        
        let imageData = image.jpegData(compressionQuality: 0.5)
        
        AF.upload(multipartFormData: { multipartFormData in
            multipartFormData.append(imageData!, withName: "multipartFile", fileName: "profile.jpeg", mimeType: "image/jpeg")
        }, to: URL, method: .post, headers: header)
        .responseData { dataResponse in
            debugPrint(dataResponse)
            switch dataResponse.result {
            case .success:
                guard let statusCode = dataResponse.response?.statusCode else {return}
                guard let value = dataResponse.value else {return}
                
                let networkResult = self.judgeStatus(by: statusCode, value)
                completion(networkResult)
            case .failure:
                completion(.pathErr)
            }
        }
    }
    
    // MARK: - Status Code 분기
    private func judgeStatus(by statusCode: Int, _ data: Data) -> NetworkResult<Any> {
        
        switch statusCode {
        case 200:
            return isValidData(data: data)
        case 400:
            print("Status 400")
            return .pathErr
        case 500:
            print("Status 500")
            return .serverErr
        default:
            return .networkFail
        }
        
    }
    
    // MARK: - JSON Parsing
    private func isValidData(data: Data) -> NetworkResult<Any> {
        let decoder = JSONDecoder()
        
        guard let decodedData = try? decoder.decode(ImageDataModel.self, from: data) else {
            print("decode fail")
            return .pathErr }
        
        return .success(decodedData)
    }
}
