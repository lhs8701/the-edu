//
//  AccountVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/02.
//

import UIKit

class AccountVC: UIViewController {
    // MARK: - IBOutlet
    @IBOutlet weak var profileImageView: UIImageView!
    @IBOutlet weak var userNameLabel: UITextField!
    @IBOutlet weak var userEmailLabel: UITextField!
    @IBOutlet weak var identificationbtn: UIButton!
    @IBOutlet weak var saveBtn: UIButton!
    @IBOutlet weak var loginTypeLabel: UILabel!
    
    // MARK: - let, var
    let loginType: String? = UserDefaults.standard.string(forKey: "loginType")
    
    var userNickname: String = ""
    var userEmail: String = ""
    var profileImage: UIImage?
    
    // MARK: - Life Cycle
    override func viewDidLoad() {
        super.viewDidLoad()


        setProfile()
        hideKeyboardWhenTappedAround()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(false, animated: true)
        self.navigationController?.navigationBar.topItem?.title = "계정 정보"
    }
    
    // MARK: - setProfile
    private func setProfile() {
        self.profileImageView.image = self.profileImage
        self.userNameLabel.text = self.userNickname
        self.userEmailLabel.text = self.userEmail
        
        self.profileImageView.layer.cornerRadius = 45
        self.identificationbtn.layer.cornerRadius = 10
        self.saveBtn.layer.cornerRadius = 10
        
        if loginType == "kakao" {
            self.loginTypeLabel.isHidden = false
            self.loginTypeLabel.text = "카카오 로그인 회원입니다"
            self.userEmailLabel.isEnabled = false
        } else if loginType == "apple" {
            self.loginTypeLabel.isHidden = false
            self.loginTypeLabel.text = "애플 로그인 회원입니다"
            self.userEmailLabel.isEnabled = false
        } else {
            self.loginTypeLabel.isHidden = true
            self.userEmailLabel.isEnabled = true
        }
        
    }
    
    // MARK: - 이메일, 닉네임 유효성 검사
    func isValidEmail(id: String) -> Bool {
        let emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}"
        let emailTest = NSPredicate(format: "SELF MATCHES %@", emailRegEx)
        return emailTest.evaluate(with: id)
    }
    
    func isValidNickname(nickname: String) -> Bool {
        if nickname.count >= 2 && nickname.count <= 16 {
            return true
        } else {
            return false
        }
    }
    
    
    // MARK: - 저장하기 버튼 눌렀을 때 동작
    @IBAction func saveBtnPressed(_ sender: Any) {
        
        guard let email = userEmailLabel.text, !email.isEmpty else {
            userEmailLabel.placeholder = "이메일 아이디를 입력해주세요"
            userEmailLabel.becomeFirstResponder()
            return
        }
        
        guard let nickname = userNameLabel.text, !nickname.isEmpty else {
            userNameLabel.placeholder = "닉네임을 입력해주세요"
            userNameLabel.becomeFirstResponder()
            return
        }
        
        if !isValidEmail(id: email) {
            userEmailLabel.text = ""
            userEmailLabel.placeholder = "잘못된 이메일 형식입니다"
        }
        if !isValidNickname(nickname: nickname) {
            userNameLabel.text = ""
            userNameLabel.placeholder = "닉네임은 2 ~ 16자여야 합니다"
        }
        
        view.endEditing(true)
        
        self.userEmail = email
        self.userNickname = nickname
        
        self.patchProfile()
        
    }
    
    
    // MARK: - 수정한 프로필 서버로 전송
    private func patchProfile() {
        
        UserProfileService.shared.patchProfile(nickname: self.userNickname, email: self.userEmail) { response in
            switch response {
            case .success:
                print("patch Profile Success")
                self.navigationController?.popViewController(animated: true)
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
