//
//  GetInquiryDataService.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/30.
//

import Foundation
import Alamofire

struct GetInquiryDataService {
    static let shared = GetInquiryDataService()
    
    func getInquiry(courseId: Int, completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.getCourseInquiries)/\(courseId)/inquiries"
        print(URL)
        
        let request = AF.request(URL, method: .get, encoding: JSONEncoding.default)
        
        request.responseData { dataResponse in
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
    
    private func isValidData(data: Data) -> NetworkResult<Any> {
        let decoder = JSONDecoder()
        
        guard let decodedData = try? decoder.decode([CourseInquiryDataModel].self, from: data) else {
            print("decode fail")
            return .pathErr }
        
        return .success(decodedData)
    }
}
