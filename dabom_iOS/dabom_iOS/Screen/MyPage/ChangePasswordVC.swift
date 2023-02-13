//
//  ChangePasswordVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/13.
//

import UIKit

class ChangePasswordVC: UIViewController {

    @IBOutlet weak var currentPasswordField: UITextField!
    
    @IBOutlet weak var changePasswordField: UITextField!
    
    @IBOutlet weak var confirmPasswordField: UITextField!
    
    @IBOutlet weak var changeBtn: UIButton!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        hideKeyboardWhenTappedAround()
    }
    
    private func configure() {
        changeBtn.layer.cornerRadius = 10
    }
    
    private func textFieldSetting() {
        self.currentPasswordField.delegate = self
        self.changePasswordField.delegate = self
        self.confirmPasswordField.delegate = self
        
        self.currentPasswordField.placeholder = "현재 비밀번호를 입력해주세요"
        self.changePasswordField.placeholder = "영문, 숫자, 특수문자 포함 8 ~ 16자"
        
    }

    private func isValidPassword(pwd: String) -> Bool {
        let passwordRegEx = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,16}$"
        let passwordTest = NSPredicate(format: "SELF MATCHES %@", passwordRegEx)
        return passwordTest.evaluate(with: pwd)
    }
    
    @IBAction func changeBtnPressed(_ sender: Any) {
        guard let currentPW = currentPasswordField.text, !currentPW.isEmpty else {
            currentPasswordField.placeholder = "현재 비밀번호를 입력해주세요"
            currentPasswordField.becomeFirstResponder()
            return
        }
        
        guard let changePW = changePasswordField.text, !changePW.isEmpty else {
            changePasswordField.placeholder = "변경할 비밀번호를 입력해주세요"
            changePasswordField.becomeFirstResponder()
            return
        }
        
        guard let confirmPW = confirmPasswordField.text, !confirmPW.isEmpty else {
            confirmPasswordField.placeholder = "비밀번호를 한 번 더 입력해주세요"
            confirmPasswordField.becomeFirstResponder()
            return
        }
        
        if !isValidPassword(pwd: currentPW) {
            currentPasswordField.text = ""
            currentPasswordField.placeholder = "잘못된 비밀번호 형식입니다"
            currentPasswordField.becomeFirstResponder()
            return
        }
        
        if !isValidPassword(pwd: changePW) {
            changePasswordField.text = ""
            changePasswordField.placeholder = "잘못된 비밀번호 형식입니다"
            changePasswordField.becomeFirstResponder()
            confirmPasswordField.text = ""
            return
        }
        
        if changePW != confirmPW {
            confirmPasswordField.text = ""
            confirmPasswordField.placeholder = "비밀번호가 일치하지 않습니다"
            confirmPasswordField.becomeFirstResponder()
            return
        }
        
        AuthenticationService.shared.changePassword(currentPW: currentPW, changePW: changePW) { response in
            switch (response) {
            case .success:
                print("change Success")
                self.navigationController?.popViewController(animated: true)
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
            }
        }
        
        
    }
    
}

extension ChangePasswordVC: UITextFieldDelegate {
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        if textField == self.currentPasswordField {
            if !isValidPassword(pwd: textField.text ?? "") {
                textField.text = ""
                textField.placeholder = "잘못된 비밀번호 형식입니다"
                textField.becomeFirstResponder()
            } else {
                self.changePasswordField.becomeFirstResponder()
            }
        }
        
        if textField == self.changePasswordField {
            if !isValidPassword(pwd: textField.text ?? "") {
                textField.text = ""
                textField.placeholder = "잘못된 비밀번호 형식입니다"
                textField.becomeFirstResponder()
            } else {
                self.confirmPasswordField.becomeFirstResponder()
            }
        }
            
        
        if textField == self.confirmPasswordField {
            if !isValidPassword(pwd: textField.text ?? "") {
                textField.text = ""
                textField.placeholder = "잘못된 비밀번호 형식입니다"
                textField.becomeFirstResponder()
            } else {
                if textField.text != changePasswordField.text {
                    textField.text = ""
                    textField.placeholder = "비밀번호가 일치하지 않습니다"
                    textField.becomeFirstResponder()
                } else {
                    self.changeBtnPressed(self.changeBtn!)
                }
            }
        }
        
        return true
    }
}
