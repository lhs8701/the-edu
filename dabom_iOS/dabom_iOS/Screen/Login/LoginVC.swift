//
//  LoginVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/13.
//

import UIKit

class LoginVC: UIViewController {
    
    // MARK: - IBOutlet
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var loginBtn: UIButton!
    
    // MARK: - let, var
    var User = UserDataModel()
    
    // MARK: - Life Cycle
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        self.textFieldSetting()
        self.hideKeyboardWhenTappedAround()
    }
    
    
    // MARK: - IBAction
    @IBAction func loginBtnPressed(_ sender: Any) {
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
        validCheck(id: email, pwd: password)
        User.account = email
        User.password = password
        // 키보드 내리기
        view.endEditing(true)
        
//        LoginSignupService.shared.login(user: User)
        LoginSignupService.shared.login(user: User) { response in
            switch (response) {
            case .success:
                print("login Success")
            case .requestErr(let message):
                print("requestErr", message)
            case .pathErr:
                print("pathErr")
            case .serverErr:
                print("serverErr")
            case .networkFail:
                print("networkFail")
            }
        }
        
        print(email)
        print(password)
        
    }
    
    
    @IBAction func goToMain(_ sender: Any) {
        guard let mainVC = UIStoryboard(name: Const.Storyboard.Name.main, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.mainTabBar) as? TabBarViewController else {return}
        
        (UIApplication.shared.delegate as! AppDelegate).changeRootVC(mainVC, animated: false)
    }
    
    
    // MARK: - func
    func textFieldSetting() {
        // TextField 아래 선
        self.emailTextField.layer.drawLineAt(edges: [.bottom], color: UIColor.black, width: 1.0)
        self.passwordTextField.layer.drawLineAt(edges: [.bottom], color: UIColor.black, width: 1.0)
        
        // delegate 설정
        self.emailTextField.delegate = self
        self.passwordTextField.delegate = self
    }
    
    func isValidEmail(id: String) -> Bool {
        let emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}"
        let emailTest = NSPredicate(format: "SELF MATCHES %@", emailRegEx)
        return emailTest.evaluate(with: id)
    }
    
    func isValidPassword(pwd: String) -> Bool {
        let passwordRegEx = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,16}$"
        let passwordTest = NSPredicate(format: "SELF MATCHES %@", passwordRegEx)
        return passwordTest.evaluate(with: pwd)
    }
    
    func validCheck(id: String, pwd: String) {
        // 아이디 유효성 검사
        if !isValidEmail(id: id) {
            shakeTextField(textField: emailTextField)
            emailTextField.text = ""
            emailTextField.placeholder = "이메일 형식을 확인해주세요"
        }
        
        // 비밀번호 유효성 검사
        if !isValidPassword(pwd: pwd) {
            shakeTextField(textField: passwordTextField)
            passwordTextField.text = ""
            passwordTextField.placeholder = "비밀번호 형식을 확인해주세요"
        }
    }
    
}

// MARK: - extension
extension LoginVC: UITextFieldDelegate {
    // 유효성 검사 틀렸을 때 TextField 흔들기
    func shakeTextField(textField: UITextField) -> Void{
        UIView.animate(withDuration: 0.2, animations: {
            textField.frame.origin.x -= 10
        }, completion: { _ in
            UIView.animate(withDuration: 0.2, animations: {
                textField.frame.origin.x += 20
             }, completion: { _ in
                 UIView.animate(withDuration: 0.2, animations: {
                    textField.frame.origin.x -= 20
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
