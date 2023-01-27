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
    
    // MARK: - Logout
    func logout(completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.logout)"
        print(URL)
        
        let accessToken = UserDefaults.standard.string(forKey: "accessToken") ?? ""
        let refreshToken = UserDefaults.standard.string(forKey: "refreshToken") ?? ""
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json",
            "X-AUTH-TOKEN" : accessToken
        ]
        
        let bodyData: Parameters = [
            "refreshToken" : refreshToken
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
    
    // MARK: - Withdraw
    func withdraw(completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.withdraw)"
        print(URL)
        
        let accessToken = UserDefaults.standard.string(forKey: "accessToken") ?? ""
        let refreshToken = UserDefaults.standard.string(forKey: "refreshToken") ?? ""
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json",
            "X-AUTH-TOKEN" : accessToken
        ]
        
        let bodyData: Parameters = [
            "refreshToken" : refreshToken
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
    
    
    // MARK: - HTTP StatusCode 분기
    private func judgeStatus(by statusCode: Int, _ data: Data?) -> NetworkResult<Any> {
        if let data = data {
            // response 데이터가 있을 때 -> 로그인 시
            print("heheheheheheh")
            switch statusCode {
            case 200:
                return setUserGrant(data: data)
            case 400:
                print("statusCode 400")
                return .pathErr
            case 404:
                print("잘못된 리소스")
                return .resourceErr
            case 500:
                return .serverErr
            default:
                return .networkFail
            }
        } else {
            // response 데이터가 없을 때 -> 회원가입, 로그아웃, 회원탈퇴 시
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
    
    // MARK: - 로그인 시에 JSON parsing -> 회원 권한, 토큰 Setting
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
        
        UserDefaults.standard.setValue(true, forKey: "isLogin")
        UserDefaults.standard.setValue(decodedData.memberId, forKey: "memberId")
        UserDefaults.standard.setValue(decodedData.grantType, forKey: "grantType")
        UserDefaults.standard.setValue(decodedData.accessToken, forKey: "accessToken")
        UserDefaults.standard.setValue(decodedData.refreshToken, forKey: "refreshToken")
        
        return .success(true)
    }
    
    // MARK: - 로그아웃, 회원탈퇴 시에 회원 권한, 토큰, 최근 검색어 Reset
    func resetUserGrant() {
        UserDefaults.standard.setValue(false, forKey: "isLogin")
        UserDefaults.standard.removeObject(forKey: "memberId")
        UserDefaults.standard.removeObject(forKey: "grantType")
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
