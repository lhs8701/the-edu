//
//  SignupVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/16.
//

import UIKit

class SignupVC: UIViewController {
    // MARK: - IBOutlet
    @IBOutlet weak var birthdayTextField: UITextField!
    
    @IBOutlet var defaultHidden: [UILabel]!
    
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var emailTextFieldDesc: UILabel!
    
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var passwordTextFieldDesc: UILabel!
    
    @IBOutlet weak var passwordConfirmTextField: UITextField!
    @IBOutlet weak var passwordConfirmDesc: UILabel!
    
    @IBOutlet weak var nameTextField: UITextField!
    
    @IBOutlet weak var mobileTextField: UITextField!
    
    
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
        
        self.showDatePicker()
        self.textFieldSetting()
        self.hideKeyboardWhenTappedAround()
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
        
        // 키보드 내리기
        view.endEditing(true)
        
        User.account = email
        User.password = password
        User.name = nameTextField.text!
        User.nickname = "임시"
        User.mobile = mobileTextField.text!
        User.birthDate = birthdayTextField.text!
        
        LoginSignupService.shared.signup(user: User) { response in
            switch (response) {
            case .success:
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
    }
    
    // MARK: - datePicker 설정
    func showDatePicker() {
        datePicker.datePickerMode = .date
        datePicker.preferredDatePickerStyle = .wheels
        
        let toolbar = UIToolbar();
        toolbar.sizeToFit()
        let doneBtn = UIBarButtonItem(title: "Done", style: .plain, target: self, action: #selector(doneDatePicker))
        let cancelBtn = UIBarButtonItem(title: "Cancel", style: .plain, target: self, action: #selector(cancelDatePicker))
        
        toolbar.setItems([doneBtn, cancelBtn], animated: false)
        birthdayTextField.inputAccessoryView = toolbar
        birthdayTextField.inputView = datePicker
    }
    
    @objc func doneDatePicker() {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy.MM.dd"
        formatter.locale = Locale(identifier: "ko_KR")
        birthdayTextField.text = formatter.string(from: datePicker.date)
        self.view.endEditing(true)
    }
    
    @objc func cancelDatePicker() {
        self.view.endEditing(true)
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
    

    
    
    
}

// MARK: - extension
extension SignupVC: UITextFieldDelegate {
    // 리턴 시에 유효성 검사
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        if textField == self.emailTextField {
            if !isValidEmail(id: textField.text ?? "") {
                textField.text = ""
                textField.placeholder = "이메일 형식 ~~"
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
                textField.placeholder = "비밀번호 형식 ~~"
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
                passwordTextField.placeholder = "비밀번호 형식 ~~"
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
