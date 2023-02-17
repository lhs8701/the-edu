//
//  FindPasswordVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/14.
//

import UIKit
import SnapKit

class FindPasswordVC: UIViewController {

    // MARK: - IBOutlet
    
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var sendBtn: UIButton!
    
    
    // MARK: - let, var
    
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

        hideKeyboardWhenTappedAround()
        setNavi()
        configureView()
    }
    
    
    // MARK: - configure
    
    private func setNavi() {
        self.navigationController?.navigationBar.topItem?.backButtonTitle = ""
    }
    
    private func configureView() {
        view.addSubview(activityIndicator)
        
        activityIndicator.snp.makeConstraints {
            $0.edges.equalToSuperview()
        }
        
        emailTextField.placeholder = "sample@gmail.com"
        sendBtn.layer.cornerRadius = 10
    }
    
    
    // MARK: - Email 유효성 검증
    func isValidEmail(email: String) -> Bool {
        let emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}"
        let emailTest = NSPredicate(format: "SELF MATCHES %@", emailRegEx)
        return emailTest.evaluate(with: email)
    }
    
    
    // MARK: - 전송하기 버튼 눌렀을 때
    @IBAction func sendBtnPressed(_ sender: Any) {
        view.endEditing(true)
        
        guard let email = emailTextField.text, !email.isEmpty else {
            emailTextField.placeholder = "이메일을 입력해주세요"
            emailTextField.becomeFirstResponder()
            return
        }
        
        if isValidEmail(email: email) {
            activityIndicator.startAnimating()
            AuthenticationService.shared.resetPassword(email: email) { response in
                switch response {
                case .success:
                    let alert = UIAlertController(title: "", message: "전송 되었습니다", preferredStyle: .alert)
                    let confirm = UIAlertAction(title: "확인", style: .default) {_ in
                        self.navigationController?.popViewController(animated: true)
                    }
                    
                    alert.addAction(confirm)
                    
                    self.present(alert, animated: true)
                    self.activityIndicator.stopAnimating()
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
                    let alert = UIAlertController(title: "", message: "등록되지 않은 이메일입니다", preferredStyle: .alert)
                    let confirm = UIAlertAction(title: "확인", style: .default)
                    alert.addAction(confirm)
                    self.present(alert, animated: true)
                }
            }
        }
    }
    
}
