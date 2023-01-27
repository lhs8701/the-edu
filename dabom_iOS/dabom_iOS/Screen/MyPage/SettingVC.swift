//
//  SettingVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/27.
//

import UIKit

class SettingVC: UIViewController {

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
        
        alert.addAction(cancel)
        alert.addAction(logout)
        
        present(alert, animated: true)
        
    }
    
    // MARK: - Withdraw
    @IBAction func withdrawBtnPressed(_ sender: Any) {
        let alert = UIAlertController(title: nil, message: "정말로 탈퇴 하시겠습니까?", preferredStyle: .actionSheet)
        
        let cancel = UIAlertAction(title: "취소", style: .cancel, handler: nil)
        
        let withdraw = UIAlertAction(title: "확인", style: .destructive) { _ in
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
        
        alert.addAction(cancel)
        alert.addAction(withdraw)
        
        present(alert, animated: true)
        
    }
    
    
    
}
