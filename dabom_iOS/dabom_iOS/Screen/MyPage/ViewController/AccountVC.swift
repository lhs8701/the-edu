//
//  AccountVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/02.
//

import UIKit
import AVFoundation
import Photos
import SnapKit


class AccountVC: UIViewController {
    
    // MARK: - IBOutlet
    
    @IBOutlet weak var profileImageView: UIImageView!
    @IBOutlet weak var userNameLabel: UITextField!
    @IBOutlet weak var userEmailLabel: UITextField!
    @IBOutlet weak var saveBtn: UIButton!
    @IBOutlet weak var changePasswordBtn: UIButton!
    @IBOutlet weak var loginTypeLabel: UILabel!
    
    
    // MARK: - let, var
    
    let imagePicker = UIImagePickerController()
    
    let loginType: String? = UserDefaults.standard.string(forKey: "loginType")
    
    var userNickname: String = ""
    var userEmail: String = ""
    var userProfile: String = ""
    
    var profileImage: UIImage?
    
    
    // MARK: - Life Cycle
    
    override func viewDidLoad() {
        super.viewDidLoad()


        setProfile()
        hideKeyboardWhenTappedAround()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        setNavi()
    }
    
    
    // MARK: - NavigationBar Setting
    
    private func setNavi() {
        self.navigationController?.setNavigationBarHidden(false, animated: true)

    }
    
    
    // MARK: - setProfile
    
    private func setProfile() {
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(photoPressed))
        self.profileImageView.addGestureRecognizer(tapGesture)
        self.profileImageView.isUserInteractionEnabled = true
        
        self.profileImageView.image = self.profileImage
        self.userNameLabel.text = self.userNickname
        self.userEmailLabel.text = self.userEmail
        
        self.profileImageView.layer.cornerRadius = 45
        self.saveBtn.layer.cornerRadius = 10
        self.changePasswordBtn.layer.cornerRadius = 10
        
        if loginType == "kakao" {
            self.loginTypeLabel.isHidden = false
            self.loginTypeLabel.text = "카카오 로그인 회원입니다"
            self.userEmailLabel.isEnabled = false
            self.changePasswordBtn.isEnabled = false
        } else if loginType == "apple" {
            self.loginTypeLabel.isHidden = false
            self.loginTypeLabel.text = "애플 로그인 회원입니다"
            self.userEmailLabel.isEnabled = false
            self.changePasswordBtn.isEnabled = false
        } else {
            self.loginTypeLabel.isHidden = true
            self.userEmailLabel.isEnabled = true
            self.changePasswordBtn.isEnabled = true
        }
        
        imagePicker.delegate = self
    }
    
    
    // MARK: - 이메일, 닉네임 유효성 검사
    
    func isValidEmail(id: String) -> Bool {
        let emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}"
        let emailTest = NSPredicate(format: "SELF MATCHES %@", emailRegEx)
        return emailTest.evaluate(with: id)
    }
    
    func isValidNickname(nickname: String) -> Bool {
        if nickname.count >= 2 && nickname.count <= 16 {
            return true
        } else {
            return false
        }
    }
    
    
    // MARK: - 저장하기 버튼 눌렀을 때 동작
    
    @IBAction func saveBtnPressed(_ sender: Any) {
        
        guard let email = userEmailLabel.text, !email.isEmpty else {
            userEmailLabel.placeholder = "이메일 아이디를 입력해주세요"
            userEmailLabel.becomeFirstResponder()
            return
        }
        
        guard let nickname = userNameLabel.text, !nickname.isEmpty else {
            userNameLabel.placeholder = "닉네임을 입력해주세요"
            userNameLabel.becomeFirstResponder()
            return
        }
        
        if !isValidEmail(id: email) {
            userEmailLabel.text = ""
            userEmailLabel.placeholder = "잘못된 이메일 형식입니다"
        }
        if !isValidNickname(nickname: nickname) {
            userNameLabel.text = ""
            userNameLabel.placeholder = "닉네임은 2 ~ 16자여야 합니다"
        }
        
        view.endEditing(true)
        
        self.userEmail = email
        self.userNickname = nickname
        
        self.patchProfile()
        
    }
    
    
    // MARK: - 수정한 프로필 서버로 전송
    
    private func patchProfile() {
        
        UserProfileService.shared.patchProfile(nickname: self.userNickname, email: self.userEmail, profileImage: self.userProfile) { response in
            switch response {
            case .success:
                self.navigationController?.popViewController(animated: true)
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
    
    // MARK: - 프로필 사진 변경하기 위해서 사진 눌렀을 때
    
    @objc func photoPressed() {
        
        if photoPermission() {
            print("permitted")
        } else {
            print("false")
            openSetting()
        }
        
    }
    
    
    // MARK: - 비밀번호 바꾸기 버튼 눌렀을 때
    
    @IBAction func changePasswordBtnPressed(_ sender: Any) {
        guard let nextVC = UIStoryboard(name: Const.Storyboard.Name.userTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.changePasswordVC) as? ChangePasswordVC else {return}
        
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
    }
    
    
    // MARK: - 이미지 리사이징
    
    func resizeImage(image: UIImage, newWidth: CGFloat) -> UIImage {
        let scale = newWidth / image.size.width
        let newHeight = image.size.height * scale
        UIGraphicsBeginImageContext(CGSizeMake(newWidth, newHeight))
        image.draw(in: CGRectMake(0, 0, newWidth, newHeight))
        let newImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return newImage ?? image
    }
    
}


// MARK: - extension

extension AccountVC: UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        if let image = info[UIImagePickerController.InfoKey.originalImage] as? UIImage {
            let resizeImage = self.resizeImage(image: image, newWidth: 300)
            self.profileImageView.image = resizeImage
            
            self.saveBtn.isEnabled = false
            
            // 유저가 앨범에서 사진 선택하면 서버로 업로드
            UploadImageDataService.shared.uploadImage(nickname: self.userNickname, image: resizeImage) { response in
                switch response {
                case .success(let data):
                    if let data = data as? ImageDataModel {
                        self.userProfile = data.originalFilePath
                    }
                    self.saveBtn.isEnabled = true

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
        dismiss(animated: true)
    }
    
    
    // MARK: - 사진 눌렀을 때 권한 확인
    
    func photoPermission() -> Bool {
        let status = PHPhotoLibrary.authorizationStatus()
        
        switch status {
        case .authorized:
            imagePicker.sourceType = .photoLibrary
            present(imagePicker, animated: true)
            return true
        case .denied, .limited, .restricted: return false
        case .notDetermined:
            PHPhotoLibrary.requestAuthorization(for: .readWrite) { state in
                if state == .authorized {
                    DispatchQueue.main.async {
                        self.imagePicker.sourceType = .photoLibrary
                        self.present(self.imagePicker, animated: true)
                    }
                }
            }
            return true
        default:
            print("default")
            return false
        }
    }
    
    
    // MARK: - 권한이 거부되어 있을 때 설정 화면으로 유도
    
    func openSetting() {
        let alert = UIAlertController(title: "설정", message: "앨범 접근이 허용되어 있지 않습니다. \r\n 설정화면으로 이동하시겠습니까?", preferredStyle: .alert)
        let cancel = UIAlertAction(title: "취소", style: .cancel)
        let confirm = UIAlertAction(title: "이동", style: .default) { _ in
            UIApplication.shared.open(URL(string: UIApplication.openSettingsURLString)!)
        }
        
        alert.addAction(cancel)
        alert.addAction(confirm)
        
        self.present(alert, animated: true, completion: nil)
    }
    
}
