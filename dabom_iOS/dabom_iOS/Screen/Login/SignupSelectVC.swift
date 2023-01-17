//
//  SignupSelectVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/16.
//

import UIKit

class SignupSelectVC: UIViewController {
    
    var rootView: LoginSignupVC?

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    
    @IBAction func emailSignupBtnPressed(_ sender: Any) {
        self.dismiss(animated: true) {
            let nextVC = UIStoryboard(name: "LoginSignup", bundle: nil).instantiateViewController(withIdentifier: "SignupVC") as! SignupVC

            self.rootView?.navigationController?.pushViewController(nextVC, animated: true)
        }
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
