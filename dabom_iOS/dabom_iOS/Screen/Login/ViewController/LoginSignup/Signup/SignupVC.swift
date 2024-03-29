//
//  SignupVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/16.
//

import UIKit
import SnapKit

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
    
    var User = UserDataModel()
    
    // 로딩 중 activityIndicator
    lazy var activityIndicator: UIActivityIndicatorView = {
        let activityIndicator = UIActivityIndicatorView()
        
        activityIndicator.backgroundColor = .lightGray
        activityIndicator.layer.opacity = 0.4
        activityIndicator.color = .black
        activityIndicator.hidesWhenStopped = true
        activityIndicator.style = .medium
            
        activityIndicator.stopAnimating()
            
        return activityIndicator
    }()
    
    // MARK: - Life Cycle
    
    override func viewDidLoad() {
        super.viewDidLoad()

        configureView()
        textFieldSetting()
        hideKeyboardWhenTappedAround()
    }
    
    
    // MARK: - configure
    
    private func configureView() {
        view.addSubview(activityIndicator)

        activityIndicator.snp.makeConstraints {
            $0.edges.equalToSuperview()
        }
        
        for label in defaultHidden {
            label.isHidden = true
        }
        
        self.signupBtn.layer.cornerRadius = 10
    }
    
    
    // MARK: - setting
    
    private func textFieldSetting() {
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
    
    
    // MARK: - 회원 가입 이후에 로그인 화면으로 이동
    
    func goToLogin() {
        guard let nextVC = UIStoryboard(name: Const.Storyboard.Name.loginSignup, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.login) as? LoginVC else {return}
        
        self.navigationController?.pushViewController(nextVC, animated: true)
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
            
        // 이메일, 비밀번호가 입력되었으면, 유효성 검사
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
        
        // 이메일, 비밀번호가 입력되었는지, 유효성 검사 이후 회원가입 호출
        signup()
    }

    func signup() {
        // 회원가입 시에 인증 이메일 발송해야하기 때문에 이메일이 발송될 때까지 로딩 중 activityIndicator 노출
        activityIndicator.startAnimating()
        
        // 이메일 회원가입 api 호출
        AuthenticationService.shared.emailSignup(user: User) { response in
            switch (response) {
            case .success:
                // 인증 이메일이 발송되었을 때
                let alert = UIAlertController(title: "이메일 인증", message: "인증 메일이 전송되었습니다.\n메일을 확인하고 회원가입을 완료해주세요.", preferredStyle: .alert)
                let confirm = UIAlertAction(title: "확인", style: .default) { _ in
                    // 로그인 화면으로 이동
                    self.goToLogin()
                }
                alert.addAction(confirm)
                self.present(alert, animated: true)
                self.activityIndicator.stopAnimating()
            case .requestErr(let message):
                print("requestErr", message)
            case .pathErr:
                // 이메일 회원가입 호출 시 이미 그 이메일이 인증 진행 중인 이메일일 때
                let alert = UIAlertController(title: "", message: "이미 인증 중인 이메일입니다.", preferredStyle: .alert)
                let confirm = UIAlertAction(title: "확인", style: .default)
                alert.addAction(confirm)
                self.present(alert, animated: true)
                self.activityIndicator.stopAnimating()
            case .serverErr:
                print("serverErr")
            case .networkFail:
                print("networkFail")
            case .resourceErr:
                // 이메일 회원가입 호출 시 이미 그 이메일이 가입된 이메일일 때
                let alert = UIAlertController(title: "", message: "이미 가입된 이메일입니다.", preferredStyle: .alert)
                let confirm = UIAlertAction(title: "확인", style: .default)
                alert.addAction(confirm)
                self.present(alert, animated: true)
                self.activityIndicator.stopAnimating()
            }
        }
    }
}


// MARK: - extension

extension SignupVC: UITextFieldDelegate {
    // 키보드 리턴 시에 유효성 검사
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
