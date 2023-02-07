//
//  UserDataService.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/02.
//

import Foundation
import Alamofire

struct UserProfileService {
    static let shared = UserProfileService()
    
    
    func getProfile(completion: @escaping (NetworkResult<Any>) -> Void) {
        let accessToken = UserDefaults.standard.string(forKey: "accessToken") ?? ""
        
        let URL = "\(Const.Url.getProfile)/me/profile"
        print(URL)
        
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
    
    func patchProfile(nickname: String, email: String, completion: @escaping (NetworkResult<Any>) -> Void) {
        let accessToken = UserDefaults.standard.string(forKey: "accessToken") ?? ""
        
        let URL = "\(Const.Url.patchProfile)/me/profile"
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json",
            "ACCESS" : accessToken
        ]
        
        let bodyData: Parameters = [
            "nickname" : nickname,
            "email" : email
        ] as Dictionary

        
        let request = AF.request(URL, method: .patch, parameters: bodyData, encoding: JSONEncoding.default, headers: header)
        
        request.responseData { dataResponse in
            debugPrint(dataResponse)
            switch dataResponse.result {
            case .success:
                guard let statusCode = dataResponse.response?.statusCode else {return}

                let networkResult = self.judgeStatus(by: statusCode, nil)
                completion(networkResult)
            case .failure:
                completion(.pathErr)
            }
        }
    }
    
    // MARK: - Status Code 분기
    private func judgeStatus(by statusCode: Int, _ data: Data?) -> NetworkResult<Any> {
        
        if let data = data {
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
        } else {
            switch statusCode {
            case 200:
                return .success(true)
            case 400:
                print("statusCode 400")
                return .pathErr
            case 401:
                print("이미 계정이 존재합니다")
                return .pathErr
            case 500:
                return .serverErr
            default:
                print(statusCode)
                return .networkFail
            }
        }
        
    }
    
    // MARK: - JSON Parsing
    private func isValidData(data: Data) -> NetworkResult<Any> {
        let decoder = JSONDecoder()
        
        guard let decodedData = try? decoder.decode(UserProfileDataModel.self, from: data) else {
            print("decode fail")
            return .pathErr }
        
        return .success(decodedData)
    }
}
