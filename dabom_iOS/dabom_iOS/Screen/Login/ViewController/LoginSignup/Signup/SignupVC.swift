//
//  SignupVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/16.
//

import UIKit

class SignupVC: UIViewController {
    // MARK: - IBOutlet
    @IBOutlet var defaultHidden: [UILabel]!
    
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var emailTextFieldDesc: UILabel!
    
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var passwordTextFieldDesc: UILabel!
    
    @IBOutlet weak var passwordConfirmTextField: UITextField!
    @IBOutlet weak var passwordConfirmDesc: UILabel!
    
    @IBOutlet weak var nameTextField: UITextField!
        
    @IBOutlet weak var signupBtn: UIButton!
    
    
    
    // MARK: - let, var
    let datePicker = UIDatePicker()
    
    var User = UserDataModel()
    
    
    // MARK: - Life Cycle
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        for label in defaultHidden {
            label.isHidden = true
        }
        
        self.textFieldSetting()
        self.hideKeyboardWhenTappedAround()
        self.signupBtn.layer.cornerRadius = 10
    }
    
    
    // MARK: - IBAction
    @IBAction func signupBtnPressed(_ sender: Any) {
        // 이메일, 비밀번호 입력 여부
        guard let email = emailTextField.text, !email.isEmpty else {
            emailTextField.placeholder = "아이디를 입력해주세요"
            emailTextFieldDesc.isHidden = false
            emailTextField.becomeFirstResponder()
            return
        }
        guard let password = passwordTextField.text, !password.isEmpty else {
            passwordTextField.placeholder = "비밀번호를 입력해주세요"
            passwordTextField.becomeFirstResponder()
            return
        }
        guard let nickname = nameTextField.text, !nickname.isEmpty else {
            nameTextField.placeholder = "닉네임을 입력해주세요"
            nameTextField.becomeFirstResponder()
            return
        }
            
        // 이메일, 비밀번호 유효성 검사
        if !isValidEmail(id: email) {
            emailTextField.text = ""
            emailTextField.placeholder = "잘못된 이메일 형식입니다"
        }
        if !isValidPassword(pwd: password) {
            passwordTextField.text = ""
            passwordConfirmTextField.text = ""
            passwordTextField.placeholder = "잘못된 비밀번호 형식입니다"
        }
        if !isValidNickname(nickname: nickname) {
            nameTextField.text = ""
            nameTextField.placeholder = "닉네임은 2 ~ 16자여야 합니다"
        }
        
        // 키보드 내리기
        view.endEditing(true)
        
        User.account = email
        User.password = password
        User.nickname = nickname
        
        AuthenticationService.shared.emailSignup(user: User) { response in
            switch (response) {
            case .success:
                print("signup Success")
                self.goToLogin()
            case .requestErr(let message):
                print("requestErr", message)
            case .pathErr:
                print("networkResult pathErr")
                print("pathErr")
            case .serverErr:
                print("serverErr")
            case .networkFail:
                print("networkFail")
            case .resourceErr:
                print("resourceErr")
                let alert = UIAlertController(title: "", message: "이미 가입된 이메일입니다", preferredStyle: .alert)
                let confirm = UIAlertAction(title: "확인", style: .default)
                alert.addAction(confirm)
                self.present(alert, animated: true)
            }
        }
        
        print(email)
        print(password)
        
        
    }
    
    // MARK: - setting
    func textFieldSetting() {
        // delegate 설정
        self.emailTextField.delegate = self
        self.passwordTextField.delegate = self
        self.passwordConfirmTextField.delegate = self
        self.nameTextField.delegate = self
        
        self.emailTextField.placeholder = "sample@gmail.com"
        self.passwordTextField.placeholder = "영문, 숫자, 특수문자 포함 8 ~ 16자"
        self.nameTextField.placeholder = "2 ~ 16자 닉네임"
        
    }
    
    
    
    // MARK: - 유효성 검사
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
    
    func isValidNickname(nickname: String) -> Bool {
        if nickname.count >= 2 && nickname.count <= 16 {
            return true
        } else {
            return false
        }
    }
    
    
    func goToLogin() {
        guard let nextVC = UIStoryboard(name: Const.Storyboard.Name.loginSignup, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.login) as? LoginVC else {return}
        
        self.navigationController?.pushViewController(nextVC, animated: true)
    }
    
}

// MARK: - extension
extension SignupVC: UITextFieldDelegate {
    // 리턴 시에 유효성 검사
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        if textField == self.emailTextField {
            if !isValidEmail(id: textField.text ?? "") {
                textField.text = ""
                textField.placeholder = "sample@gmail.com"
                emailTextFieldDesc.text = "잘못된 이메일 형식입니다."
                emailTextFieldDesc.isHidden = false
            } else {
                self.emailTextFieldDesc.isHidden = true
                self.passwordTextField.becomeFirstResponder()
            }
        }
        
        if textField == self.passwordTextField {
            if !isValidPassword(pwd: textField.text ?? "") {
                textField.text = ""
                textField.placeholder = "영문, 숫자, 특수문자 포함 8 ~ 16자"
                passwordTextFieldDesc.text = "잘못된 비밀번호 형식입니다."
                passwordTextFieldDesc.isHidden = false
            } else {
                self.passwordTextFieldDesc.isHidden = true
                self.passwordConfirmTextField.becomeFirstResponder()
            }
        }
        
        if textField == self.passwordConfirmTextField {
            if !isValidPassword(pwd: passwordTextField.text ?? "") {
                passwordTextField.text = ""
                passwordTextField.placeholder = "영문, 숫자, 특수문자 포함 8 ~ 16자"
                passwordTextFieldDesc.text = "잘못된 비밀번호 형식입니다."
                passwordTextFieldDesc.isHidden = false
            } else {
                if textField.text != passwordTextField.text {
                    textField.text = ""
                    textField.placeholder = "비밀번호를 한 번 더 입력해주세요"
                    passwordConfirmDesc.text = "비밀번호가 일치하지 않습니다."
                    passwordConfirmDesc.isHidden = false
                } else {
                    passwordConfirmDesc.isHidden = true
                    self.nameTextField.becomeFirstResponder()
                }
            }
        }
          
        return true
    }
}
