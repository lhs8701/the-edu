//
//  UserViewController.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/04.
//

import UIKit

class UserViewController: UIViewController {

    // MARK: - IBOutlet
    @IBOutlet weak var profileImageView: UIImageView!
    
    @IBOutlet weak var profileView: UIView!
    @IBOutlet weak var userNameLabel: UILabel!
    
    @IBOutlet weak var myCouponBtn: UIButton!
    
    @IBOutlet weak var noticeBtn: UIButton!
    
    @IBOutlet weak var eventBtn: UIButton!
    
    @IBOutlet weak var informationBtn: UIButton!
    
    @IBOutlet weak var settingBtn: UIButton!
    
    // MARK: - Life Cycle
    override func viewDidLoad() {
        super.viewDidLoad()

        
        

        profileImageView.image = UIImage(named: "testProfile")
        myCouponBtn.layer.drawLineAt(edges: [.bottom], color: UIColor.lightGray, width: 1.0)
        noticeBtn.layer.drawLineAt(edges: [.bottom], color: UIColor.lightGray, width: 1.0)
        eventBtn.layer.drawLineAt(edges: [.bottom], color: UIColor.lightGray, width: 1.0)
        informationBtn.layer.drawLineAt(edges: [.bottom], color: UIColor.lightGray, width: 1.0)
        settingBtn.layer.drawLineAt(edges: [.bottom], color: UIColor.lightGray, width: 1.0)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.navigationBar.topItem?.backButtonTitle = ""
        self.navigationController?.navigationBar.tintColor = .black
        self.navigationController?.setNavigationBarHidden(true, animated: true)
    }
    
    // MARK: - func
    @IBAction func accountBtnPressed(_ sender: Any) {
        // 로그인 안된 상태이면
        guard let loginSignupVC = UIStoryboard(name: Const.Storyboard.Name.loginSignup, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.loginSignupNavi) as? LoginSignupNC else {return}
        
        (UIApplication.shared.delegate as! AppDelegate).changeRootVC(loginSignupVC, animated: false)
        
    }
    
    
    @IBAction func settingBtnPressed(_ sender: Any) {
        guard let settingVC = UIStoryboard(name: Const.Storyboard.Name.userTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.setting) as? SettingVC else {return}
        
        settingVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(settingVC, animated: true)
    }
    
}
