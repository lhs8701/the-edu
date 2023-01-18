//
//  LoginSignupService.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/18.
//

import Foundation
import Alamofire

struct LoginSignupService {
    static let shared = LoginSignupService()
    
    func signup(user: UserDataModel) {
        let URL = "\(Const.Url.signup)"
        print(URL)
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json"
        ]
        
        let bodyData : Parameters = [
            "account" : user.account,
            "password" : user.password,
            "name" : user.name,
            "nickname" : user.nickname,
            "mobile" : user.mobile,
            "birthDate" : user.birthDate
        ] as Dictionary
        
        let request = AF.request(URL, method: .post, parameters: bodyData, encoding: JSONEncoding.default, headers: header)
        
        request.responseData(emptyResponseCodes: [200, 204, 205]) { dataResponse in
            debugPrint(dataResponse)
            switch dataResponse.result {
            case .success:
                guard let statusCode = dataResponse.response?.statusCode else {return}

                let networkResult = self.judgeStatus(by: statusCode, nil)

                switch networkResult {
                case .success(let data):
                    print("signup Success")
                case .requestErr(let message):
                    print("requestErr", message)
                case .pathErr:
                    print("networkResult pathErr")
                    print("pathErr")
                case .serverErr:
                    print("serverErr")
                case .networkFail:
                    print("networkFail")
                }
            case .failure:
                print("request failure")
            }
        }
    }
    
    
    private func judgeStatus(by statusCode: Int, _ data: Data?) -> NetworkResult<Any> {
        if let data = data {
//            switch statusCode {
//            case 200:
//
//            }
        } else {
            switch statusCode {
            case 200:
                return .success(true)
            case 400:
                print("Code 400")
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
        return .networkFail
    }
}
