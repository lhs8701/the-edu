//
//  SignupSelectVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/16.
//

import UIKit
import KakaoSDKAuth
import KakaoSDKUser
import AuthenticationServices

class SignupSelectVC: UIViewController {
    
    // MARK: - IBOutlet
    
    @IBOutlet weak var emailSignupBtn: UIButton!
    
    
    // MARK: - let, var
    
    var rootView: LoginSignupVC?
    
    
    // MARK: - Life Cycle
    
    override func viewDidLoad() {
        super.viewDidLoad()

        configureView()
    }
    
    
    // MARK: - configure
    
    private func configureView() {
        self.emailSignupBtn.layer.cornerRadius = 5
    }
    
    
    // MARK: - 이메일 회원가입
    
    @IBAction func emailSignupBtnPressed(_ sender: Any) {
        self.dismiss(animated: true) {
            let nextVC = UIStoryboard(name: Const.Storyboard.Name.loginSignup, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.signup) as! SignupVC

            self.rootView?.navigationController?.pushViewController(nextVC, animated: true)
        }
    }
    
    
    // MARK: - 카카오톡 회원가입
    
    @IBAction func kakaoSignup(_ sender: Any) {
        // 카카오톡 앱으로 로그인 가능하면
        if (UserApi.isKakaoTalkLoginAvailable()) {
            UserApi.shared.loginWithKakaoTalk { (oauthToken, error) in
                if let error = error {
                    print(error)
                } else {
                    let accessToken = String(oauthToken?.accessToken ?? "")
                    
                    AuthenticationService.shared.kakaoLogin(accessToken: accessToken) { response in
                        switch (response) {
                        case .success:
                            // 카카오 로그인이 성공했으면 UserDefaults에 토큰, loginType 저장
                            UserDefaults.standard.setValue(accessToken, forKey: "kakaoToken")
                            UserDefaults.standard.setValue("kakao", forKey: "loginType")
                            
                            // 저장(로그인) 후 메인 화면으로 이동
                            AuthenticationService.shared.goToMain()
                        case .requestErr(let message):
                            print("requestErr", message)
                        case .pathErr:
                            print("pathErr")
                        case .serverErr:
                            print("serverErr")
                        case .networkFail:
                            print("networkFail")
                        case .resourceErr:
                            print("resourceErr")
                        }
                    }
                }
            }
        } else { // 카카오톡 앱으로 로그인 안되면 account로 로그인
            UserApi.shared.loginWithKakaoAccount { (oauthToken, error) in
                if let error = error {
                    print(error)
                } else {
                    let accessToken = String(oauthToken?.accessToken ?? "")
                    
                    AuthenticationService.shared.kakaoLogin(accessToken: accessToken) { response in
                        switch (response) {
                        case .success:
                            // 카카오 로그인이 성공했으면 UserDefaults에 토큰, loginType 저장
                            UserDefaults.standard.setValue(accessToken, forKey: "kakaoToken")
                            UserDefaults.standard.setValue("kakao", forKey: "loginType")
                            
                            // 저장(로그인) 후 메인 화면으로 이동
                            AuthenticationService.shared.goToMain()
                        case .requestErr(let message):
                            print("requestErr", message)
                        case .pathErr:
                            print("pathErr")
                        case .serverErr:
                            print("serverErr")
                        case .networkFail:
                            print("networkFail")
                        case .resourceErr:
                            print("resourceErr")
                        }
                    }
                }
            }
        }
    }
    
    
    // MARK: - Apple 회원가입
    
    @IBAction func appleSignup(_ sender: Any) {
        let appleIDProvider = ASAuthorizationAppleIDProvider()
        let request = appleIDProvider.createRequest()
        request.requestedScopes = [.fullName, .email]
        
        let authorizationController = ASAuthorizationController(authorizationRequests: [request])
        authorizationController.delegate = self
        authorizationController.presentationContextProvider = self
        authorizationController.performRequests()
        
    }
    
    // 이미 가입된 회원일 때 dismiss 후 로그인 화면으로 이동
    func goToLogin() {
        self.dismiss(animated: true) {
            let nextVC = UIStoryboard(name: Const.Storyboard.Name.loginSignup, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.login) as! LoginVC

            self.rootView?.navigationController?.pushViewController(nextVC, animated: true)
        }
    }

}


extension SignupSelectVC: ASAuthorizationControllerPresentationContextProviding {
    func presentationAnchor(for controller: ASAuthorizationController) -> ASPresentationAnchor {
        return self.view.window!
    }
}

extension SignupSelectVC: ASAuthorizationControllerDelegate {
    // MARK: - Apple 인증 성공 시
    func authorizationController(controller: ASAuthorizationController, didCompleteWithAuthorization authorization: ASAuthorization) {
        if let appleIDCredential = authorization.credential as? ASAuthorizationAppleIDCredential {
            let userIdentifier = appleIDCredential.user
            let userName = (appleIDCredential.fullName?.familyName ?? "") + (appleIDCredential.fullName?.givenName ?? "")
            let email = appleIDCredential.email ?? ""
            
            
            // 애플 회원가입 api 호출
            AuthenticationService.shared.appleSignup(socialToken: userIdentifier, email: email, nickname: userName) { response in
                switch (response) {
                case .success:
                    // 회원가입 성공하면 바로 login 호출
                    AuthenticationService.shared.appleLogin(socialToken: userIdentifier) { response in
                        switch (response) {
                        case .success:
                            // 로그인까지 성공하면 메인화면으로 이동
                            AuthenticationService.shared.goToMain()
                        case .requestErr(let message):
                            print("requestErr", message)
                        case .pathErr:
                            print("pathErr")
                        case .serverErr:
                            print("serverErr")
                        case .networkFail:
                            print("networkFail")
                        case .resourceErr:
                            print("resourceErr")
                        }
                    }
                case .requestErr(let message):
                    print("requestErr", message)
                case .pathErr:
                    print("pathErr")
                case .serverErr:
                    print("serverErr")
                case .networkFail:
                    print("networkFail")
                case .resourceErr:
                    // 회원가입 호출 시 이미 가입 된 계정일 때
                    let alert = UIAlertController(title: "", message: "이미 가입된 계정입니다", preferredStyle: .alert)
                    let confirm = UIAlertAction(title: "확인", style: .default) { _ in
                        self.goToLogin()
                    }
                    alert.addAction(confirm)
                    self.present(alert, animated: true)
                }
            }
        }
    }
    
    
    // MARK: - Apple 인증 실패 시
    func authorizationController(controller: ASAuthorizationController, didCompleteWithError error: Error) {
        print("authorization Error: \(error)")
        let alert = UIAlertController(title: "알림", message: "Apple 로그인 실패", preferredStyle: UIAlertController.Style.alert)
        let confirm = UIAlertAction(title: "확인", style: UIAlertAction.Style.cancel, handler: nil)
        
        alert.addAction(confirm)
        self.present(alert, animated: true)
    }
    
}
