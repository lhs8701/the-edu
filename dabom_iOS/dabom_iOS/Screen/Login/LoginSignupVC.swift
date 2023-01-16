//
//  LoginSignupVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/13.
//

import UIKit

class LoginSignupVC: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */
    @available(iOS 15.0, *)
    @IBAction func signupBtnPressed(_ sender: Any) {
        guard let selectVC = UIStoryboard(name: "LoginSignup", bundle: nil).instantiateViewController(withIdentifier: "SignupSelectVC") as? SignupSelectVC else {return}
        
        selectVC.view.backgroundColor = .systemBlue
        selectVC.modalPresentationStyle = .pageSheet
        
        if let sheet = selectVC.sheetPresentationController {
            sheet.detents = [.medium()]
            
//            sheet.delegate = self
            
            sheet.prefersGrabberVisible = true
        }
        
        present(selectVC, animated: true, completion: nil)
    }
    
}
