//
//  AccountVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/02.
//

import UIKit

class AccountVC: UIViewController {
    
    @IBOutlet weak var profileImageView: UIImageView!
    @IBOutlet weak var userNameLabel: UITextField!
    @IBOutlet weak var userEmailLabel: UITextField!
    @IBOutlet weak var identificationbtn: UIButton!
    @IBOutlet weak var saveBtn: UIButton!
    
    @IBOutlet weak var loginTypeLabel: UILabel!
    
    let loginType: String? = UserDefaults.standard.string(forKey: "loginType")
    
    override func viewDidLoad() {
        super.viewDidLoad()


        setProfile()
        hideKeyboardWhenTappedAround()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(false, animated: true)
        self.navigationController?.navigationBar.topItem?.title = "계정 정보"
    }
    
    private func setProfile() {
        self.profileImageView.image = UIImage(named: "testProfile")
        self.userNameLabel.text = "유저 닉네임"
        self.userEmailLabel.text = "sample@gmail.com"
        
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
    
    
    
    
}
