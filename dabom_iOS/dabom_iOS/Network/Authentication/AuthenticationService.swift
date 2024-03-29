//
//  LoginSignupService.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/18.
//

import Foundation
import Alamofire

struct AuthenticationService {
    static let shared = AuthenticationService()
    
    // MARK: - email Signup
    func emailSignup(user: UserDataModel, completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.signup)"
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json"
        ]
        
        let bodyData : Parameters = [
            "account" : user.account,
            "password" : user.password,
            "nickname" : user.nickname,
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

    
    // MARK: - Logout
    func logout(completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.logout)"
        
        let accessToken = UserDefaults.standard.string(forKey: "accessToken") ?? ""
        let refreshToken = UserDefaults.standard.string(forKey: "refreshToken") ?? ""
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json",
            "ACCESS" : accessToken,
            "REFRESH" : refreshToken
        ]
        
        let request = AF.request(URL, method: .post, encoding: JSONEncoding.default, headers: header)
        
        request.responseData(emptyResponseCodes: [200, 204, 205]) { dataResponse in
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
    
    // MARK: - Withdraw
    func withdraw(completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.withdraw)"
        
        let accessToken = UserDefaults.standard.string(forKey: "accessToken") ?? ""
        let refreshToken = UserDefaults.standard.string(forKey: "refreshToken") ?? ""
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json",
            "ACCESS" : accessToken,
            "REFRESH" : refreshToken
        ]
        
        let request = AF.request(URL, method: .post, encoding: JSONEncoding.default, headers: header)
        
        request.responseData(emptyResponseCodes: [200, 204, 205]) { dataResponse in
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
    
    // MARK: - 비밀번호 변경
    func changePassword(currentPW: String, changePW: String, completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.changePassword)"
        
        let accessToken = UserDefaults.standard.string(forKey: "accessToken") ?? ""
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json",
            "ACCESS" : accessToken
        ]
        
        let bodyData: Parameters = [
            "currentPassword": currentPW,
            "newPassword" : changePW
        ] as Dictionary
        
        let request = AF.request(URL, method: .post, parameters: bodyData, encoding: JSONEncoding.default, headers: header)
        
        request.responseData(emptyResponseCodes: [200, 201, 204, 205]) { dataResponse in
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
    
    // MARK: - 비밀번호 초기화 (이메일 임시 비밀번호 전송)
    func resetPassword(email: String, completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.resetPassword)"
        
        let bodyData: Parameters = [
            "Content-Type" : "application/json",
            "account" : email
        ] as Dictionary
        
        let request = AF.request(URL, method: .post, parameters: bodyData, encoding: JSONEncoding.default)
        
        request.responseData { dataResponse in
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
    
    
    // MARK: - kakao Login
    func kakaoLogin(accessToken: String, completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.kakaoLogin)"
        
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
    
    // MARK: - kakao Logout
    func kakaoLogout(completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.kakaoLogout)"
        
        let accessToken = UserDefaults.standard.string(forKey: "accessToken") ?? ""
        let refreshToken = UserDefaults.standard.string(forKey: "refreshToken") ?? ""
        let socialToken = UserDefaults.standard.string(forKey: "kakaoToken") ?? ""
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json",
            "ACCESS" : accessToken,
            "REFRESH" : refreshToken
        ]
        
        let bodyData: Parameters = [
            "socialToken" : socialToken,
        ] as Dictionary
        
        let request = AF.request(URL, method: .post, parameters: bodyData, encoding: JSONEncoding.default, headers: header)
        
        request.responseData(emptyResponseCodes: [200, 204, 205]) { dataResponse in
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
    
    // MARK: - kakao Withdraw
    func kakaoWithdraw(completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.kakaoWithdraw)"
        
        let accessToken = UserDefaults.standard.string(forKey: "accessToken") ?? ""
        let refreshToken = UserDefaults.standard.string(forKey: "refreshToken") ?? ""
        let socialToken = UserDefaults.standard.string(forKey: "kakaoToken") ?? ""
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json",
            "ACCESS" : accessToken,
            "REFRESH" : refreshToken
        ]
        
        let bodyData: Parameters = [
            "socialToken" : socialToken,
        ] as Dictionary
        
        let request = AF.request(URL, method: .post, parameters: bodyData, encoding: JSONEncoding.default, headers: header)
        
        request.responseData(emptyResponseCodes: [200, 204, 205]) { dataResponse in
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
    
    // MARK: - apple signup
    func appleSignup(socialToken: String, email: String, nickname: String, completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.appleSignup)"
        
        let bodyData: Parameters = [
            "socialToken" : socialToken,
            "email" : email,
            "nickname" : nickname
        ] as Dictionary
        
        let request = AF.request(URL, method: .post, parameters: bodyData, encoding: JSONEncoding.default)
        
        request.responseData(emptyResponseCodes: [200, 201, 204, 205]) { dataResponse in
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
    
    // MARK: - apple login
    func appleLogin(socialToken: String, completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.appleLogin)"
        
        let bodyData : Parameters = [
            "socialToken" : socialToken
        ] as Dictionary
        
        let request = AF.request(URL, method: .post, parameters: bodyData, encoding: JSONEncoding.default)
        
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
    
    // MARK: - apple logout
    func appleLogout(completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.appleLogout)"
        
        let accessToken = UserDefaults.standard.string(forKey: "accessToken") ?? ""
        let refreshToken = UserDefaults.standard.string(forKey: "refreshToken") ?? ""
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json",
            "ACCESS" : accessToken,
            "REFRESH" : refreshToken
        ]
        
        let request = AF.request(URL, method: .post, encoding: JSONEncoding.default, headers: header)
        
        request.responseData(emptyResponseCodes: [200, 204, 205]) { dataResponse in
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
    
    // MARK: - apple withdraw
    func appleWithdraw(completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.appleWithdraw)"
        
        let accessToken = UserDefaults.standard.string(forKey: "accessToken") ?? ""
        let refreshToken = UserDefaults.standard.string(forKey: "refreshToken") ?? ""
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json",
            "ACCESS" : accessToken,
            "REFRESH" : refreshToken
        ]
        
        let request = AF.request(URL, method: .post,encoding: JSONEncoding.default, headers: header)
        
        request.responseData(emptyResponseCodes: [200, 204, 205]) { dataResponse in
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
    
    
    // MARK: - Reissue
    func reissue(completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.reissue)"
        
        let accessToken = UserDefaults.standard.string(forKey: "accessToken") ?? ""
        let refreshToken = UserDefaults.standard.string(forKey: "refreshToken") ?? ""
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json"
        ]
        
        let bodyData: Parameters = [
            "accessToken" : accessToken,
            "refreshToken" : refreshToken
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
            // response 데이터가 있을 때 -> 로그인, reissue 시
            switch statusCode {
            case 200, 201:
                return setUserGrant(data: data)
            case 400:
                return .pathErr
            case 401, 404:
                return .resourceErr
            case 500:
                return .serverErr
            default:
                return .networkFail
            }
        } else {
            // response 데이터가 없을 때 -> 회원가입, 로그아웃, 회원탈퇴 시
            switch statusCode {
            case 200, 201:
                return .success(true)
            case 400:
                return .pathErr
            case 401, 404:
                return .resourceErr
            case 500:
                return .serverErr
            default:
                print(statusCode)
                return .networkFail
            }
        }
    }
    
    // MARK: - 로그인 시에 JSON parsing -> 회원 권한, 토큰 Setting
    private func setUserGrant(data: Data) -> NetworkResult<Any> {
        let decoder = JSONDecoder()
        
        guard let decodedData = try? decoder.decode(UserGrantInfoDataModel.self, from: data) else {
            print("userGrant Data decode fail")
            return .pathErr
        }
        print(decodedData.tokenForm.accessToken)
        print(decodedData.tokenForm.refreshToken)
        
        UserDefaults.standard.setValue(decodedData.tokenForm.accessToken, forKey: "accessToken")
        UserDefaults.standard.setValue(decodedData.tokenForm.refreshToken, forKey: "refreshToken")
        
        return .success(true)
    }
    
    // MARK: - 로그아웃, 회원탈퇴 시에 회원 권한, 토큰, 최근 검색어 Reset
    func resetUserGrant() {
        UserDefaults.standard.removeObject(forKey: "loginType")
        UserDefaults.standard.removeObject(forKey: "memberId")
        UserDefaults.standard.removeObject(forKey: "accessToken")
        UserDefaults.standard.removeObject(forKey: "refreshToken")
        UserDefaults.standard.removeObject(forKey: "recentSearch")
    }
    
    // MARK: - 로그인 성공 시 main 화면으로 이동
    func goToMain() {
        guard let mainVC = UIStoryboard(name: Const.Storyboard.Name.main, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.mainTabBar) as? TabBarViewController else {return}
        
        (UIApplication.shared.delegate as! AppDelegate).changeRootVC(mainVC, animated: false)
    }
    
    // MARK: - 로그아웃, 회원탈퇴 시 로그인, 회원가입 선택 화면으로 이동
    func goToLoginSignup() {
        guard let loginSignupVC = UIStoryboard(name: Const.Storyboard.Name.loginSignup, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.loginSignupNavi) as? LoginSignupNC else {return}
        
        (UIApplication.shared.delegate as! AppDelegate).changeRootVC(loginSignupVC, animated: false)
    }
}
