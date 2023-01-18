//
//  UserViewController.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/04.
//

import UIKit

class UserViewController: UIViewController {

    @IBOutlet weak var profileImageView: UIImageView!
    
    @IBOutlet weak var profileView: UIView!
    @IBOutlet weak var userNameLabel: UILabel!
    
    @IBOutlet weak var myCouponBtn: UIButton!
    
    @IBOutlet weak var noticeBtn: UIButton!
    
    @IBOutlet weak var eventBtn: UIButton!
    
    @IBOutlet weak var informationBtn: UIButton!
    
    @IBOutlet weak var settingBtn: UIButton!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        

        // Do any additional setup after loading the view.
        profileImageView.image = UIImage(named: "testProfile")
        myCouponBtn.layer.drawLineAt(edges: [.bottom], color: UIColor.lightGray, width: 1.0)
        noticeBtn.layer.drawLineAt(edges: [.bottom], color: UIColor.lightGray, width: 1.0)
        eventBtn.layer.drawLineAt(edges: [.bottom], color: UIColor.lightGray, width: 1.0)
        informationBtn.layer.drawLineAt(edges: [.bottom], color: UIColor.lightGray, width: 1.0)
        settingBtn.layer.drawLineAt(edges: [.bottom], color: UIColor.lightGray, width: 1.0)
    }
    
    
    @IBAction func accountBtnPressed(_ sender: Any) {
//        guard let loginSignupVC = UIStoryboard(name: "LoginSignup", bundle: nil).instantiateViewController(withIdentifier: "LoginSignupVC") as? LoginSignupVC else {return}
//        self.present(loginSignupVC, animated: true)
        
        // 로그인 안된 상태이면
        guard let loginSignupVC = UIStoryboard(name: Const.Storyboard.Name.loginSignup, bundle: nil).instantiateViewController(withIdentifier: "LoginSignupNC") as? LoginSignupNC else {return}
        
        (UIApplication.shared.delegate as! AppDelegate).changeRootVC(loginSignupVC, animated: false)
        
    }
    

}
