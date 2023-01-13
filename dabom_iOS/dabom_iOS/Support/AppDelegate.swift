//
//  AppDelegate.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/04.
//

import UIKit

@main
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?
    
    var isLogin: Bool = false

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        
        window = UIWindow()
        
        if isLogin {
            guard let mainVC = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "TabBarViewController") as? TabBarViewController else {return false}
            window?.rootViewController = mainVC
        } else {
            print("22")
            guard let loginVC = UIStoryboard(name: "LoginSignup", bundle: nil).instantiateViewController(withIdentifier: "LoginSignupNC") as? LoginSignupNC else {return false}
            window?.rootViewController = loginVC
        }
        
        
        return true
    }

    func changeRootVC(_ vc: UIViewController, animated: Bool) {
        print("here")
        
        guard let window = self.window else { return }
        window.rootViewController = vc
        
        UIView.transition(with: window, duration: 0.2, options: [.transitionCrossDissolve], animations: nil, completion: nil)
    }

}

