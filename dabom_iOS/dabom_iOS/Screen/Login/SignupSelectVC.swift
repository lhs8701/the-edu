//
//  SignupSelectVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/16.
//

import UIKit
import KakaoSDKAuth
import KakaoSDKUser

class SignupSelectVC: UIViewController {
    
    var rootView: LoginSignupVC?

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    
    @IBAction func emailSignupBtnPressed(_ sender: Any) {
        self.dismiss(animated: true) {
            let nextVC = UIStoryboard(name: Const.Storyboard.Name.loginSignup, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.signup) as! SignupVC

            self.rootView?.navigationController?.pushViewController(nextVC, animated: true)
        }
    }
    
    @IBAction func kakaoSignup(_ sender: Any) {
        // 카카오톡 앱으로 로그인 가능하면
        if (UserApi.isKakaoTalkLoginAvailable()) {
            UserApi.shared.loginWithKakaoTalk { (oauthToken, error) in
                if let error = error {
                    print(error)
                } else {
                    print("loginWithKakaoTalk() success")
                    
                    let accessToken = oauthToken?.accessToken
                    let refreshToken = oauthToken?.refreshToken
                    print(String(accessToken ?? ""))
                    print(String(refreshToken ?? ""))
                }
            }
        } else { // 카카오톡 앱으로 로그인 안되면 account로 로그인
            UserApi.shared.loginWithKakaoAccount { (oauthToken, error) in
                if let error = error {
                    print(error)
                } else {
                    print("loginWithKakaoAccount() success")
                    
                    let accessToken = oauthToken?.accessToken
                    let refreshToken = oauthToken?.refreshToken
                    print(String(accessToken ?? ""))
                    print(String(refreshToken ?? ""))
                }
            }
        }
        
    }
    
    
}
