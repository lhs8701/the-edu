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
    
    
    // MARK: - let, var
    var userProfile = UserProfileDataModel()
    
    
    
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
        
        self.getProfile()
    }
    
    // MARK: - func
    @IBAction func accountBtnPressed(_ sender: Any) {
        guard let accountVC = UIStoryboard(name: Const.Storyboard.Name.userTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.account) as? AccountVC else {return}
        accountVC.userProfile = self.userProfile
        
        accountVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(accountVC, animated: true)
        
    }
    
    
    @IBAction func settingBtnPressed(_ sender: Any) {
        guard let settingVC = UIStoryboard(name: Const.Storyboard.Name.userTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.setting) as? SettingVC else {return}
        
        settingVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(settingVC, animated: true)
    }
    
    private func getProfile() {
        UserProfileService.shared.getProfile { response in
            switch response {
            case .success(let userData):
                if let profile = userData as? UserProfileDataModel {
                    self.userProfile = profile
                    self.userNameLabel.text = profile.nickname
                    
                }
            case .requestErr(let message):
                print("requestErr", message)
            case .pathErr:
                print("pathErr")
            case .serverErr:
                print("serverErr")
            case .networkFail:
                print("networkFail")
            case .resourceErr:
                print("resourceErr")
            }
        }
    }

}
