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
    
    // MARK: - email Signup
    func emailSignup(user: UserDataModel, completion: @escaping (NetworkResult<Any>) -> Void) {
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
                completion(networkResult)
            case .failure:
                completion(.pathErr)
            }
        }
    }
    
    // MARK: - email Login
    func emailLogin(user: UserDataModel, completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.login)"
        print(URL)
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json"
        ]
        
        let bodyData : Parameters = [
            "account" : user.account,
            "password" : user.password
        ] as Dictionary
        
        let request = AF.request(URL, method: .post, parameters: bodyData, encoding: JSONEncoding.default, headers: header)
        
        request.responseData { dataResponse in
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
    
    // MARK: - kakao Login
    func kakaoLogin(accessToken: String, completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.kakaoLogin)"
        print(URL)
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json"
        ]
        
        let bodyData: Parameters = [
            "socialToken" : accessToken
        ] as Dictionary
        
        let request = AF.request(URL, method: .post, parameters: bodyData, encoding: JSONEncoding.default, headers: header)
        
        request.responseData { dataResponse in
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
    
    
    // MARK: - HTTP StatusCode 분기
    private func judgeStatus(by statusCode: Int, _ data: Data?) -> NetworkResult<Any> {
        if let data = data {
            // response 데이터가 있을 때 -> 로그인 시
            switch statusCode {
            case 200:
                return setUserGrant(data: data)
            case 400:
                print("statusCode 400")
                return .pathErr
            case 500:
                return .serverErr
            default:
                return .networkFail
            }
        } else {
            // response 데이터가 없을 때 -> 회원가입 시
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
    
    // MARK: - 로그인 시에 회원 권한, 토큰 Setting
    private func setUserGrant(data: Data) -> NetworkResult<Any> {
        let decoder = JSONDecoder()
        
        guard let decodedData = try? decoder.decode(UserGrantInfoDataModel.self, from: data) else {
            print("userGrant Data decode fail")
            return .pathErr
        }
        print(decodedData.memberId)
        print(decodedData.grantType)
        print(decodedData.accessToken)
        print(decodedData.refreshToken)
        
        UserDefaults.standard.setValue(decodedData.memberId, forKey: "memberId")
        UserDefaults.standard.setValue(decodedData.grantType, forKey: "grantType")
        UserDefaults.standard.setValue(decodedData.accessToken, forKey: "accessToken")
        UserDefaults.standard.setValue(decodedData.refreshToken, forKey: "refreshToken")
        
        return .success(true)
    }
    
    // MARK: - 로그인 성공 시 main 화면으로 이동
    func goToMain() {
        guard let mainVC = UIStoryboard(name: Const.Storyboard.Name.main, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.mainTabBar) as? TabBarViewController else {return}
        
        (UIApplication.shared.delegate as! AppDelegate).changeRootVC(mainVC, animated: false)
    }
}
