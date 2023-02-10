//
//  MyCouponVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/10.
//

import UIKit

class MyCouponVC: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(false, animated: true)
//        self.navigationController?.navigationBar.topItem?.title = "환경설정"
        self.navigationController?.navigationBar.topItem?.backButtonTitle = ""
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
