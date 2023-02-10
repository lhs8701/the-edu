//
//  ContactVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/10.
//

import UIKit

class ContactVC: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(false, animated: true)
        self.navigationController?.navigationBar.topItem?.title = "서비스 문의"
        self.navigationController?.navigationBar.topItem?.backButtonTitle = ""
    }


    
    

}
