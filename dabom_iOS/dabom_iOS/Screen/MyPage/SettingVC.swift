//
//  SettingVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/27.
//

import UIKit

class SettingVC: UIViewController {
    
    let loginType: String? = UserDefaults.standard.string(forKey: "loginType")

    // MARK: - Life Cycle
    override func viewDidLoad() {
        super.viewDidLoad()

    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(false, animated: true)
        self.navigationController?.navigationBar.topItem?.title = "환경설정"
    }

    // MARK: - Logout
    @IBAction func logoutBtnPressed(_ sender: Any) {
        let alert = UIAlertController(title: nil, message: "로그아웃 하시겠습니까?", preferredStyle: .actionSheet)
        
        let cancel = UIAlertAction(title: "취소", style: .cancel, handler: nil)
        
        let logout = UIAlertAction(title: "확인", style: .destructive) { _ in
            if self.loginType == "email" {
                self.emailLogout()
                
            } else if self.loginType == "kakao" {
                self.kakaoLogout()
                
            } else if self.loginType == "apple" {
                
            } else {
                
            }
            
        }
        
        alert.addAction(cancel)
        alert.addAction(logout)
        
        present(alert, animated: true)
        
    }
    
    private func emailLogout() {
        AuthenticationService.shared.logout { response in
            switch response {
            case .success:
                print("logout Success")
                AuthenticationService.shared.goToLoginSignup()
                AuthenticationService.shared.resetUserGrant()
            case .requestErr(let message):
                print("requestErr", message)
            case .pathErr:
                print("networkResult pathErr")
            case .serverErr:
                print("serverErr")
            case .networkFail:
                print("networkFail")
            case .resourceErr:
                print("resourceErr")
            }
        }
    }
    
    private func kakaoLogout() {
        AuthenticationService.shared.kakaoLogout { response in
            switch response {
            case .success:
                print("kakaoLogout Success")
                AuthenticationService.shared.goToLoginSignup()
                AuthenticationService.shared.resetUserGrant()
            case .requestErr(let message):
                print("requestErr", message)
            case .pathErr:
                print("networkResult pathErr")
            case .serverErr:
                print("serverErr")
            case .networkFail:
                print("networkFail")
            case .resourceErr:
                print("resourceErr")
            }
        }
    }
    
    // MARK: - Withdraw
    @IBAction func withdrawBtnPressed(_ sender: Any) {
        let alert = UIAlertController(title: nil, message: "정말로 탈퇴 하시겠습니까?", preferredStyle: .actionSheet)
        
        let cancel = UIAlertAction(title: "취소", style: .cancel, handler: nil)
        
        let withdraw = UIAlertAction(title: "확인", style: .destructive) { _ in
            if self.loginType == "email" {
                self.emailWithdraw()
                
            } else if self.loginType == "kakao" {
                self.kakaoWithdraw()
                
            } else if self.loginType == "apple" {
                
            } else {
                
            }
        }
        
        alert.addAction(cancel)
        alert.addAction(withdraw)
        
        present(alert, animated: true)
        
    }
    
    private func emailWithdraw() {
        AuthenticationService.shared.withdraw { response in
            switch response {
            case .success:
                print("withdraw Success")
                AuthenticationService.shared.goToLoginSignup()
                AuthenticationService.shared.resetUserGrant()
            case .requestErr(let message):
                print("requestErr", message)
            case .pathErr:
                print("networkResult pathErr")
            case .serverErr:
                print("serverErr")
            case .networkFail:
                print("networkFail")
            case .resourceErr:
                print("resourceErr")
            }
        }
    }
    
    private func kakaoWithdraw() {
        AuthenticationService.shared.kakaoWithdraw { response in
            switch response {
            case .success:
                print("kakaoWithdraw Success")
                AuthenticationService.shared.goToLoginSignup()
                AuthenticationService.shared.resetUserGrant()
            case .requestErr(let message):
                print("requestErr", message)
            case .pathErr:
                print("networkResult pathErr")
            case .serverErr:
                print("serverErr")
            case .networkFail:
                print("networkFail")
            case .resourceErr:
                print("resourceErr")
            }
        }
    }
    
    // MARK: - Reissue Test
    @IBAction func reissueTest(_ sender: Any) {
        
        AuthenticationService.shared.reissue { response in
            switch response {
            case .success:
                print("reissue Success")
            case .requestErr(let message):
                print("requestErr", message)
            case .pathErr:
                print("networkResult pathErr")
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
