//
//  LoginVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/13.
//

import UIKit
import KakaoSDKAuth
import KakaoSDKUser
import AuthenticationServices

class LoginVC: UIViewController {
    
    // MARK: - IBOutlet
    
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var loginBtn: UIButton!
    @IBOutlet weak var loginLabel: UILabel!
    
    
    // MARK: - let, var
    
    var User = UserDataModel()
    
    
    // MARK: - Life Cycle
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setNavi()
        configureView()
        textFieldSetting()
        hideKeyboardWhenTappedAround()
    }
    
    
    // MARK: - configure
    
    private func configureView() {
        self.loginBtn.layer.cornerRadius = 10
        self.loginLabel.layer.drawLineAt(edges: [.bottom], color: UIColor(named: "mainColor") ?? .yellow, width: 5.0)
    }
    
    private func setNavi() {
        self.navigationController?.navigationBar.topItem?.backButtonTitle = ""
    }

    
    // MARK: - func
    
    func textFieldSetting() {
        // TextField 아래 선
        self.emailTextField.layer.drawLineAt(edges: [.bottom], color: UIColor.darkGray, width: 1.0)
        self.passwordTextField.layer.drawLineAt(edges: [.bottom], color: UIColor.darkGray, width: 1.0)
        
        self.emailTextField.placeholder = "sample@gmail.com"
        self.passwordTextField.placeholder = "영문, 숫자, 특수문자 포함 8 ~ 16자"
        
        // delegate 설정
        self.emailTextField.delegate = self
        self.passwordTextField.delegate = self
    }
    
    // 이메일 유효성 검사
    func isValidEmail(id: String) -> Bool {
        let emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}"
        let emailTest = NSPredicate(format: "SELF MATCHES %@", emailRegEx)
        return emailTest.evaluate(with: id)
    }
    
    // 비밀번호 유효성 검사
    func isValidPassword(pwd: String) -> Bool {
        let passwordRegEx = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,16}$"
        let passwordTest = NSPredicate(format: "SELF MATCHES %@", passwordRegEx)
        return passwordTest.evaluate(with: pwd)
    }
    
    // 유효성 체크
    func validCheck(id: String, pwd: String) -> Bool {
        // 아이디 유효성 검사
        if !isValidEmail(id: id) {
            shakeTextField(textField: emailTextField)
            emailTextField.text = ""
            emailTextField.placeholder = "이메일 형식을 확인해주세요"
            
            return false
        }
        
        // 비밀번호 유효성 검사
        if !isValidPassword(pwd: pwd) {
            shakeTextField(textField: passwordTextField)
            passwordTextField.text = ""
            passwordTextField.placeholder = "비밀번호 형식을 확인해주세요"
            
            return false
        }
        
        return true
    }
    
    
    // MARK: - IBAction
    
    // 로그인 버튼 눌렀을 때
    @IBAction func loginBtnPressed(_ sender: Any) {
        // 키보드 내리기
        view.endEditing(true)
        
        // 아이디, 비밀번호 입력 여부 확인
        guard let email = emailTextField.text, !email.isEmpty else {
            emailTextField.placeholder = "아이디를 입력해주세요"
            passwordTextField.placeholder = "비밀번호를 입력해주세요"
            emailTextField.becomeFirstResponder()
            return
        }
        guard let password = passwordTextField.text, !password.isEmpty else {
            passwordTextField.placeholder = "비밀번호를 입력해주세요"
            passwordTextField.becomeFirstResponder()
            return
        }
        
        // 아이디, 비밀번호 유효성 검사
        if validCheck(id: email, pwd: password) {
            User.account = email
            User.password = password
            
            // 이메일 로그인 api 호출
            AuthenticationService.shared.emailLogin(user: User) { response in
                switch (response) {
                case .success:
                    // 로그인 성공했을 때
                    UserDefaults.standard.setValue("email", forKey: "loginType")
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
                    // 아이디 또는 비밀번호가 잘못됐을 때
                    self.passwordTextField.text = ""
                    let alert = UIAlertController(title: "", message: "아이디 또는 비밀번호를 확인해주세요", preferredStyle: .alert)
                    let confirm = UIAlertAction(title: "확인", style: .default)
                    alert.addAction(confirm)
                    self.present(alert, animated: true)
                }
            }
            
        }
    }
 
    // 카카오 로그인 버튼 눌렀을 때
    @IBAction func kakaoLoginBtnPressed(_ sender: Any) {
        if (UserApi.isKakaoTalkLoginAvailable()) {
            UserApi.shared.loginWithKakaoTalk { (oauthToken, error) in
                if let error = error {
                    print(error)
                } else {
                    let accessToken = String(oauthToken?.accessToken ?? "")
                    
                    // 카카오 로그인 api 호출
                    AuthenticationService.shared.kakaoLogin(accessToken: accessToken) { response in
                        switch (response) {
                        case .success:
                            // 로그인 성공했을 때 토큰, loginType 저장
                            UserDefaults.standard.setValue("kakao", forKey: "loginType")
                            UserDefaults.standard.setValue(accessToken, forKey: "kakaoToken")
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
                    
                    // 카카오 로그인 api 호출
                    AuthenticationService.shared.kakaoLogin(accessToken: accessToken) { response in
                        switch (response) {
                        case .success:
                            // 로그인 성공했을 때 토큰, loginType 저장
                            UserDefaults.standard.setValue("kakao", forKey: "loginType")
                            UserDefaults.standard.setValue(accessToken, forKey: "kakaoToken")
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
    
    // 애플 로그인 버튼 눌렀을 때
    @IBAction func appleLoginBtnPressed(_ sender: Any) {
        let appleIDProvider = ASAuthorizationAppleIDProvider()
        let request = appleIDProvider.createRequest()
        request.requestedScopes = [.fullName, .email]
        
        let authorizationController = ASAuthorizationController(authorizationRequests: [request])
        authorizationController.delegate = self
        authorizationController.presentationContextProvider = self
        authorizationController.performRequests()
    }
    
    // 비밀번호 찾기 버튼 눌렀을 때
    @IBAction func findPasswordBtnPressed(_ sender: Any) {
        guard let findPwVC = UIStoryboard(name: Const.Storyboard.Name.loginSignup, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.findPasswordVC) as? FindPasswordVC else { return }
        
        findPwVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(findPwVC, animated: true)
    }
    
}


// MARK: - extension

extension LoginVC: UITextFieldDelegate {
    // 유효성 검사 틀렸을 때 TextField 흔들기
    func shakeTextField(textField: UITextField) -> Void{
        UIView.animate(withDuration: 0.1, animations: {
            textField.frame.origin.x -= 8
        }, completion: { _ in
            UIView.animate(withDuration: 0.1, animations: {
                textField.frame.origin.x += 8
             }, completion: { _ in
                 UIView.animate(withDuration: 0.1, animations: {
                    textField.frame.origin.x -= 8
                })
            })
        })
    }
    
    // 이메일 입력 이후 비밀번호로 커서 이동
    @objc func didEndOnExit(_ sender: UITextField) {
        if emailTextField.isFirstResponder {
            passwordTextField.becomeFirstResponder()
        }
    }
    
    // 비밀번호까지 입력 후 리턴 시 로그인 액션
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        if textField == self.emailTextField {
            self.passwordTextField.becomeFirstResponder()
        } else if textField == self.passwordTextField {
            self.loginBtnPressed(self.loginBtn!)
        }
        return true
    }
}


// MARK: - Apple Login extension

extension LoginVC: ASAuthorizationControllerPresentationContextProviding {
    func presentationAnchor(for controller: ASAuthorizationController) -> ASPresentationAnchor {
        return self.view.window!
    }
}

extension LoginVC: ASAuthorizationControllerDelegate {
    // MARK: - Apple 인증 성공 시
    func authorizationController(controller: ASAuthorizationController, didCompleteWithAuthorization authorization: ASAuthorization) {
        if let appleIDCredential = authorization.credential as? ASAuthorizationAppleIDCredential {
            let userIdentifier = appleIDCredential.user
            let userName = (appleIDCredential.fullName?.familyName ?? "") + (appleIDCredential.fullName?.givenName ?? "")
            let email = appleIDCredential.email ?? ""
            
            // 애플 로그인 api 호출
            AuthenticationService.shared.appleLogin(socialToken: userIdentifier) { response in
                switch (response) {
                case .success:
                    // 로그인 성공했을 때
                    UserDefaults.standard.setValue("apple", forKey: "loginType")
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
    
    // MARK: - Apple 인증 실패 시
    func authorizationController(controller: ASAuthorizationController, didCompleteWithError error: Error) {
        print("authorization Error: \(error)")
        let alert = UIAlertController(title: "알림", message: "Apple 로그인 실패", preferredStyle: UIAlertController.Style.alert)
        let confirm = UIAlertAction(title: "확인", style: UIAlertAction.Style.cancel, handler: nil)
        
        alert.addAction(confirm)
        self.present(alert, animated: true)
    }
}
