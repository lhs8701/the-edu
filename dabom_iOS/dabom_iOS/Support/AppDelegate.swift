//
//  AppDelegate.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/04.
//

import UIKit
import KakaoSDKCommon
import KakaoSDKAuth

@main
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?
    
//    var isLogin: Bool = false
    var loginType: String? = UserDefaults.standard.string(forKey: "loginType")

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        // MARK: - 카카오톡 로그인 설정
        KakaoSDK.initSDK(appKey: "9d5a7db9c37d4b17c44b843e6c4fa727")
        
        NetworkMonitor.shared.startMonitoring()
        
        // MARK: - 로그인 분기
        window = UIWindow()
        
//        for key in UserDefaults.standard.dictionaryRepresentation().keys {
//            UserDefaults.standard.removeObject(forKey: key.description)
//        }
        
//        if isLogin {
//            guard let mainVC = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "TabBarViewController") as? TabBarViewController else {return false}
//            window?.rootViewController = mainVC
//        } else {
//            print("22")
//            guard let loginVC = UIStoryboard(name: "LoginSignup", bundle: nil).instantiateViewController(withIdentifier: "LoginSignupNC") as? LoginSignupNC else {return false}
//            window?.rootViewController = loginVC
//        }

        if loginType == nil {
            guard let loginVC = UIStoryboard(name: Const.Storyboard.Name.loginSignup, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.loginSignupNavi) as? LoginSignupNC else {return false}
            window?.rootViewController = loginVC
        } else {
            guard let mainVC = UIStoryboard(name: Const.Storyboard.Name.main, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.mainTabBar) as? TabBarViewController else {return false}
            window?.rootViewController = mainVC
        }
        
        
        return true
    }
    
    func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
        if (AuthApi.isKakaoTalkLoginUrl(url)) {
            return AuthController.handleOpenUrl(url: url)
        }
        
        return false
    }
    

    func changeRootVC(_ vc: UIViewController, animated: Bool) {
        print("here")
        
        guard let window = self.window else { return }
        window.rootViewController = vc
        
        UIView.transition(with: window, duration: 0.2, options: [.transitionCrossDissolve], animations: nil, completion: nil)
    }

}

