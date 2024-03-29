//
//  LoginSignupVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/13.
//

import UIKit
import SnapKit

class LoginSignupVC: UIViewController {

    // MARK: - IBOutlet
    
    @IBOutlet weak var signupSelectBtn: UIButton!
    @IBOutlet weak var loginSelectBtn: UIButton!
    
    
    // MARK: - Life Cycle
    
    override func viewDidLoad() {
        super.viewDidLoad()

        setNavi()
        configureView()
    }
    
    
    // MARK: - func
    
    private func setNavi() {
        self.navigationController?.navigationBar.topItem?.backButtonTitle = ""
        self.navigationController?.navigationBar.tintColor = .black
    }
    
    private func configureView() {
        [signupSelectBtn, loginSelectBtn].forEach {
            $0?.layer.cornerRadius = 10
        }
    }
    
    
    // MARK: - 신규 회원 가입 버튼 클릭 시
    
    @available(iOS 15.0, *)
    @IBAction func signupBtnPressed(_ sender: Any) {
        guard let selectVC = UIStoryboard(name: Const.Storyboard.Name.loginSignup, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.signupSelect) as? SignupSelectVC else {return}
        
        selectVC.modalPresentationStyle = .pageSheet
        
        if let sheet = selectVC.sheetPresentationController {
            sheet.detents = [.medium()]
            sheet.prefersGrabberVisible = true
        }
        
        selectVC.rootView = self
        present(selectVC, animated: true, completion: nil)
    }
    
    
    // MARK: - 기존 회원 로그인 버튼 클릭 시
    
    @IBAction func loginBtnPressed(_ sender: Any) {
        guard let loginVC = UIStoryboard(name: Const.Storyboard.Name.loginSignup, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.login) as? LoginVC else {return}
        
        self.navigationController?.pushViewController(loginVC, animated: true)
    }
    
}
