//
//  LoginVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/13.
//

import UIKit

class LoginVC: UIViewController {

    @IBOutlet weak var emailTextField: UITextField!
    
    @IBOutlet weak var passwordTextField: UITextField!
    
    @IBOutlet weak var loginBtn: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        self.emailTextField.layer.drawLineAt(edges: [.bottom], color: UIColor.black, width: 1.0)
        self.passwordTextField.layer.drawLineAt(edges: [.bottom], color: UIColor.black, width: 1.0)
        
//        self.emailTextField.addTarget(self, action: #selector(didEndOnExit), for: UIControl.Event.editingDidEndOnExit)
//        self.passwordTextField.addTarget(self, action: #selector(didEndOnExit), for: UIControl.Event.editingDidEndOnExit)
//        self.loginBtn.addTarget(self, action: #selector(didEndOnExit), for: UIControl.Event.editingDidEndOnExit)
        self.emailTextField.delegate = self
        self.passwordTextField.delegate = self
        self.hideKeyboardWhenTappedAround()
    }
    
    
    
    @IBAction func loginBtnPressed(_ sender: Any) {
        guard let email = emailTextField.text, !email.isEmpty else {
            emailTextField.placeholder = "아이디를 입력해주세요"
            emailTextField.becomeFirstResponder()
            return
        }
        guard let password = passwordTextField.text, !password.isEmpty else {
            passwordTextField.placeholder = "비밀번호를 입력해주세요"
            passwordTextField.becomeFirstResponder()
            return
        }
        
//        if isValidEmail(id: email) {
//            if let removable = self.view.viewWithTag(100) {
//                removable.removeFromSuperview()
//            }
//        } else {
//            shakeTextField(textField: emailTextField)
//            emailTextField.text = ""
//            emailTextField.placeholder = "이메일 형식을 확인해주세요"
//        }
//
//        if isValidPassword(pwd: password){
//            if let removable = self.view.viewWithTag(101) {
//                    removable.removeFromSuperview()
//                }
//        } else {
//            shakeTextField(textField: passwordTextField)
//
//            passwordTextField.text = ""
//            passwordTextField.placeholder = "비밀번호 형식을 확인해주세요"
//        }
        
        
        if !isValidEmail(id: email) {
            shakeTextField(textField: emailTextField)
            emailTextField.text = ""
            emailTextField.placeholder = "이메일 형식을 확인해주세요"
        }
        
        if !isValidPassword(pwd: password) {
            shakeTextField(textField: passwordTextField)
            passwordTextField.text = ""
            passwordTextField.placeholder = "비밀번호 형식을 확인해주세요"
        }
        
        view.endEditing(true)
        
        print(email)
        print(password)
        
    }
    
    
    
    @IBAction func goToMain(_ sender: Any) {
        guard let mainVC = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "TabBarViewController") as? TabBarViewController else {return}
        
        (UIApplication.shared.delegate as! AppDelegate).changeRootVC(mainVC, animated: false)
        
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
    
    
    
}

extension LoginVC: UITextFieldDelegate {
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
    
    @objc func didEndOnExit(_ sender: UITextField) {
        if emailTextField.isFirstResponder {
            passwordTextField.becomeFirstResponder()
        }
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        if textField == self.emailTextField {
            self.passwordTextField.becomeFirstResponder()
        } else if textField == self.passwordTextField {
            self.loginBtnPressed(self.loginBtn!)
        }
        return true
    }
}
