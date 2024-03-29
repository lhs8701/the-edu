//
//  NetworkVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/10.
//

import UIKit

class NetworkVC: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
        let alert = UIAlertController(title: "네트워크에 접속할 수 없습니다.", message: "네트워크 연결 상태를 확인해주세요.", preferredStyle: UIAlertController.Style.alert)
        let end = UIAlertAction(title: "종료", style: .destructive) { _ in
            UIApplication.shared.perform(#selector(NSXPCConnection.suspend))
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                exit(0)
            }
        }
        let confirm = UIAlertAction(title: "설정", style: .default) { _ in
            guard let url = URL(string: UIApplication.openSettingsURLString) else {return}
            if UIApplication.shared.canOpenURL(url) {
                UIApplication.shared.open(url)
            }
        }
        
        alert.addAction(end)
        alert.addAction(confirm)
        
        DispatchQueue.main.async {
            self.present(alert, animated: true, completion: nil)
        }
        
    }
}
