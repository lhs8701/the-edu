//
//  ContactVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/10.
//

import UIKit

class ContactVC: UIViewController {
    
    // MARK: - Life Cycle
    
    override func viewDidLoad() {
        super.viewDidLoad()

    }
    
    override func viewWillAppear(_ animated: Bool) {
        setNavi()
    }

    
    // MARK: - NavigationBar Setting
    
    private func setNavi() {
        self.navigationController?.setNavigationBarHidden(false, animated: true)
        self.navigationController?.navigationBar.topItem?.title = "서비스 문의"
        self.navigationController?.navigationBar.topItem?.backButtonTitle = ""
    }
    
}
