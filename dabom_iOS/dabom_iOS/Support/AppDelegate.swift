//
//  AppDelegate.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/04.
//

import UIKit

@main
class AppDelegate: UIResponder, UIApplicationDelegate {



    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        
//        if #available(iOS 15, *) {
//            let naviAppearance = UINavigationBarAppearance()
//            let tabAppearance = UITabBarAppearance()
//
//            naviAppearance.configureWithOpaqueBackground()
//            //appearance.titleTextAttributes = [.foregroundColor: UIColor.black]
//            naviAppearance.backgroundColor = UIColor(red: 255/255.0, green: 255/255.0, blue: 255/255.0, alpha: 1.0)
//            UINavigationBar.appearance().standardAppearance = naviAppearance
//
//            tabAppearance.configureWithOpaqueBackground()
//            tabAppearance.backgroundColor = UIColor(red: 255/255.0, green: 255/255.0, blue: 255/255.0, alpha: 1.0)
//            UITabBar.appearance().standardAppearance = tabAppearance
//            UITabBar.appearance().scrollEdgeAppearance = tabAppearance
//        }
        
        return true
    }

    // MARK: UISceneSession Lifecycle

    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        // Called when a new scene session is being created.
        // Use this method to select a configuration to create the new scene with.
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }

    func application(_ application: UIApplication, didDiscardSceneSessions sceneSessions: Set<UISceneSession>) {
        // Called when the user discards a scene session.
        // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
        // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
    }


}

