//
//  ShowVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/10.
//

import Foundation
import UIKit

let story = UIStoryboard(name: "NetworkView", bundle: nil)

func showNetworkVCOnRoot() {
    DispatchQueue.main.async {
        guard let networkViewController = story.instantiateViewController(withIdentifier: "NetworkVC") as? NetworkVC else {print("storyboard error")
            return}
        networkViewController.modalPresentationStyle = .fullScreen
        
        let scenes = UIApplication.shared.connectedScenes
        let windowScene = scenes.first as? UIWindowScene
        let window = windowScene?.windows.first
        
        window?.rootViewController?.show(networkViewController, sender: nil)
    }
}
