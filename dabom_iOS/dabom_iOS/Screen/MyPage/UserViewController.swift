//
//  UserViewController.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/04.
//

import UIKit
import Kingfisher

class UserViewController: UIViewController {

    // MARK: - IBOutlet
    @IBOutlet weak var profileImageView: UIImageView!
    @IBOutlet weak var profileView: UIView!
    @IBOutlet weak var userNameLabel: UILabel!
    @IBOutlet weak var myCouponBtn: UIButton!
    @IBOutlet weak var eventBtn: UIButton!
    @IBOutlet weak var informationBtn: UIButton!
    @IBOutlet weak var settingBtn: UIButton!
    
    
    // MARK: - let, var
    var userNickname: String = ""
    var userEmail: String = ""
    var userProfile: String = ""
    var userProfileImage: ImageDataModel = ImageDataModel()
    
    // MARK: - Life Cycle
    override func viewDidLoad() {
        super.viewDidLoad()

        
        profileImageView.layer.cornerRadius = 40
        myCouponBtn.layer.drawLineAt(edges: [.bottom], color: UIColor.lightGray, width: 1.0)
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
    
    
    // MARK: - 내 쿠폰함 버튼 눌렀을 때
    @IBAction func myCouponBtnPressed(_ sender: Any) {
        guard let couponVC = UIStoryboard(name: Const.Storyboard.Name.userTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.myCoupon) as? MyCouponVC else {return}
        
        couponVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(couponVC, animated: true)
        
    }

    
    // MARK: - 계정 설정 버튼 눌렀을 때
    @IBAction func accountBtnPressed(_ sender: Any) {
        guard let accountVC = UIStoryboard(name: Const.Storyboard.Name.userTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.account) as? AccountVC else {return}

        accountVC.userNickname = self.userNickname
        accountVC.userEmail = self.userEmail
        accountVC.userProfile = self.userProfile
        accountVC.profileImage = self.profileImageView.image
        
        accountVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(accountVC, animated: true)
        
    }
    
    
    // MARK: - 이벤트 버튼 눌렀을 때
    @IBAction func eventBtnPressed(_ sender: Any) {
        guard let eventVC = UIStoryboard(name: Const.Storyboard.Name.userTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.event) as? EventVC else {return}
        
        eventVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(eventVC, animated: true)
        
    }
    
    
    // MARK: - 서비스 문의 버튼 눌렀을 때
    @IBAction func contactBtnPressed(_ sender: Any) {
        guard let contactVC = UIStoryboard(name: Const.Storyboard.Name.userTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.contact) as? ContactVC else {return}
        
        contactVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(contactVC, animated: true)
        
    }
    
    
    // MARK: - 환경 설정 버튼 눌렀을 때
    @IBAction func settingBtnPressed(_ sender: Any) {
        guard let settingVC = UIStoryboard(name: Const.Storyboard.Name.userTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.setting) as? SettingVC else {return}
        
        settingVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(settingVC, animated: true)
    }
    
    // MARK: - 유저 프로필 정보 가져오기 (프로필 사진, 닉네임)
    private func getProfile() {
        UserProfileService.shared.getProfile { response in
            switch response {
            case .success(let userData):
                if let profile = userData as? UserProfileDataModel {
                    self.userEmail = profile.email ?? ""
                    self.userNickname = profile.nickname ?? ""
                    self.userProfileImage = profile.profileImage
                    self.userProfile = profile.profileImage.originalFilePath
                    
                    self.userNameLabel.text = self.userNickname

                    self.profileImageView.kf.indicatorType = .activity
                    self.profileImageView.noCacheImage(with: self.userProfileImage.mediumFilePath)
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
