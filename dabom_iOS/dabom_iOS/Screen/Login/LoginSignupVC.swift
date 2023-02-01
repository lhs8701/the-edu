//
//  LoginSignupVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/13.
//

import UIKit

class LoginSignupVC: UIViewController {

    @IBOutlet weak var signupSelectBtn: UIButton!
    
    @IBOutlet weak var loginSelectBtn: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        self.navigationController?.navigationBar.topItem?.backButtonTitle = ""
        self.navigationController?.navigationBar.tintColor = .black
        
        [signupSelectBtn, loginSelectBtn].forEach {
            $0?.layer.cornerRadius = 10
        }
    }
    

    @available(iOS 15.0, *)
    @IBAction func signupBtnPressed(_ sender: Any) {
        guard let selectVC = UIStoryboard(name: Const.Storyboard.Name.loginSignup, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.signupSelect) as? SignupSelectVC else {return}
        
        selectVC.modalPresentationStyle = .pageSheet
        
        if let sheet = selectVC.sheetPresentationController {
            sheet.detents = [.medium()]
            
//            sheet.delegate = self
            
            sheet.prefersGrabberVisible = true
        }
        
        selectVC.rootView = self
        present(selectVC, animated: true, completion: nil)
    }
    
}
