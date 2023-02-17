//
//  CurriculumDataService.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/07.
//

import Foundation
import Alamofire

struct UserCurriculumDataService {
    static let shared = UserCurriculumDataService()
    
    // MARK: - Curriculum 정보 가져오기
    func getUserCurriculum(courseId: Int, completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.getUserCurriculum)/\(courseId)/curriculum/status"
        print(URL)
        
        let accessToken = UserDefaults.standard.string(forKey: "accessToken") ?? ""
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json",
            "ACCESS" : accessToken
        ]
        
        let request = AF.request(URL, method: .get, encoding: JSONEncoding.default, headers: header)
        
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
    
    // MARK: - Status Code 분기
    private func judgeStatus(by statusCode: Int, _ data: Data) -> NetworkResult<Any> {
        switch statusCode {
        case 200:
            return isValidData(data: data)
        case 400:
            print("statusCode 400")
            return .pathErr
        case 401, 404:
            print("잘못된 리소스")
            return .resourceErr
        case 500:
            return .serverErr
        default:
            return .networkFail
        }
        
    }
    
    // MARK: - JSON Parsing
    private func isValidData(data: Data) -> NetworkResult<Any> {
        let decoder = JSONDecoder()
        
        guard let decodedData = try? decoder.decode(UserCurriculumDataModel.self, from: data) else {
            print("decode fail")
            return .pathErr }
        return .success(decodedData)
    }
    
    
}
