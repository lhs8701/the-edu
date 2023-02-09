//
//  CourseInfoViewController.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/09.
//

import UIKit
import AVFoundation
import AVKit
import SnapKit

class CourseInfoViewController: UIViewController {
        
    // MARK: - IBOutlet

    @IBOutlet weak var mainTV: UITableView!
    
    @IBOutlet weak var stickyView: UIView!
    
    @IBOutlet weak var upperConstraint: NSLayoutConstraint! {
        didSet {
            upperConstraint.constant = maxUpper
        }
    }
    
    @IBOutlet weak var segmentCtrl: UISegmentedControl!
    
    
    // MARK: - 변수, 상수
    var courseTitle: String?
    var courseDescription: String?
    var instructor: String?
    
    var courseInfoData: CourseInfoDataModel?
    
    var courseId: Int?
    
    var isWish: Bool?
    var isEnroll: Bool?

    let maxUpper: CGFloat = 450.0
    let minUpper: CGFloat = 0.0
    
    let memberId: Int = UserDefaults.standard.integer(forKey: "memberId")
    let loginType: String? = UserDefaults.standard.string(forKey: "loginType")
    
    var reviewData: [CourseReviewDataModel] = []
    var inquiryData: [CourseInquiryDataModel] = []
    
    
    var onOffButton: UIButton!
    var heartButton: UIButton!
    
    var avPlayer = AVPlayer()
    var avController = AVPlayerViewController()
    
    
    // MARK: - Life Cycle
    
    override func viewDidLoad() {
        super.viewDidLoad()

        setRightBarButton()
        setTableView()
        setSegmentController()
            
        // courseId 기본값 설정 (임시)
//        self.courseId = 2
        getCourseInfo(id: self.courseId!)
        
        if self.loginType != nil {
            checkWish()
            checkEnroll()
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        setNavigationBar()
    }
    
    
    // MARK: - navigationBar 설정
    
    private func setNavigationBar() {
        self.navigationController?.setNavigationBarHidden(false, animated: true)
        self.navigationController?.navigationBar.topItem?.backButtonTitle = ""
    }
    
    // MARK: - rightBarButtonItem 설정
    private func setRightBarButton() {
        let onOffImage = UIImage(named: "onoff")?.withRenderingMode(.alwaysOriginal)
        self.onOffButton = UIButton.init(frame: CGRect.init(x: 0, y: 0, width: 60, height: 30))
        self.onOffButton.setImage(onOffImage, for: .normal)
        self.onOffButton.addTarget(self, action: #selector(onOffBtnPressed(_:)), for: .touchUpInside)
        let onOff = UIBarButtonItem(customView: onOffButton)
        onOffButton.layer.isHidden = true
        
        let unselectedHeart = UIImage(named: Const.Image.unselectedHeart)
        let selectedHeart = UIImage(named: Const.Image.selectedHeart)
        self.heartButton = UIButton.init(frame: CGRect.init(x: 0, y: 0, width: 30, height: 30))
        self.heartButton.setImage(unselectedHeart, for: .normal)
        self.heartButton.setImage(selectedHeart, for: .selected)
        self.heartButton.addTarget(self, action: #selector(wishBtnPressed(_:)), for: .touchUpInside)
        let heart = UIBarButtonItem(customView: heartButton)

        navigationItem.rightBarButtonItems = [heart, onOff]
    }
    
    // MARK: - 찜한 강좌인지 확인
    func checkWish() {
        CourseInfoDataService.shared.isWishCourse(courseId: self.courseId!) { check in
            switch check {
            case true:
                self.heartButton.isSelected = true
            case false:
                self.heartButton.isSelected = false
            }
        }
    }
    
    // MARK: - 신청한 강좌인지 확인
    func checkEnroll() {
        CourseInfoDataService.shared.isEnrollCourse(courseId: self.courseId!) { check in
            self.isEnroll = check
        }
    }
    
    // MARK: - 찜하기 버튼 눌렀을 때
    @objc func wishBtnPressed(_ sender: UIButton) {
        if self.loginType != nil {
            CourseInfoDataService.shared.changeWishCourse(courseId: self.courseId!) { response in
                switch (response) {
                case .success:
                    print("change Success")
                case .requestErr(let message):
                    print("requestErr", message)
                case .pathErr:
                    print("networkResult pathErr")
                    print("pathErr")
                case .serverErr:
                    print("serverErr")
                case .networkFail:
                    print("networkFail")
                case .resourceErr:
                    print("resourceErr")
                }
            }
            
            if sender.isSelected {
                sender.isSelected = false
            } else {
                sender.isSelected = true
            }
        } else {
            let alert = UIAlertController(title: "로그인이 필요한 서비스입니다", message: "로그인 하시겠습니까?", preferredStyle: .alert)
            let cancel = UIAlertAction(title: "취소", style: .cancel)
            let login = UIAlertAction(title: "확인", style: .default) { _ in
                AuthenticationService.shared.goToLoginSignup()
            }
            
            alert.addAction(cancel)
            alert.addAction(login)
            
            present(alert, animated: true)
        }
        
    }
    
    // MARK: - OnOff 설명 버튼
    @objc func onOffBtnPressed(_ sender: UIButton) {
        guard let descVC = UIStoryboard(name: Const.Storyboard.Name.courseInfoView, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.onOffDescription) as? OnOffDescriptionVC else {return}
        
        present(descVC, animated: true)
    }
    
    // MARK: - tableView 설정
    private func setTableView() {
        self.mainTV.delegate = self
        self.mainTV.dataSource = self
        self.mainTV.register(UINib(nibName: Const.Xib.Name.infoImageTVC, bundle: nil), forCellReuseIdentifier: Const.Xib.Identifier.infoImageTVC)
        self.mainTV.register(UINib(nibName: Const.Xib.Name.courseInfoTVC, bundle: nil), forCellReuseIdentifier: Const.Xib.Identifier.courseInfoTVC)
        self.mainTV.register(UINib(nibName: Const.Xib.Name.segmentTVC, bundle: nil), forCellReuseIdentifier: Const.Xib.Identifier.segmentTVC)
        self.mainTV.register(UINib(nibName: Const.Xib.Name.courseReviewTVC, bundle: nil), forCellReuseIdentifier: Const.Xib.Identifier.courseReviewTVC)
        self.mainTV.register(UINib(nibName: Const.Xib.Name.courseInquiryTVC, bundle: nil), forCellReuseIdentifier: Const.Xib.Identifier.courseInquiryTVC)
        self.mainTV.register(UINib(nibName: Const.Xib.Name.courseCurriculumTVC, bundle: nil), forCellReuseIdentifier: Const.Xib.Identifier.courseCurriculumTVC)
    }

    
    // MARK: - segmentController 설정
    private func setSegmentController() {
        let backgroundImg = UIImage(named: "segment")
        let Img = UIImage()
        self.segmentCtrl.setBackgroundImage(Img, for: .normal, barMetrics: .default)
        self.segmentCtrl.setBackgroundImage(backgroundImg, for: .selected, barMetrics: .default)
        self.segmentCtrl.setBackgroundImage(Img, for: .highlighted, barMetrics: .default)
        
        
        self.segmentCtrl.setDividerImage(Img, forLeftSegmentState: .selected, rightSegmentState: .normal, barMetrics: .default)
        self.segmentCtrl.setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.gray, .font: UIFont.systemFont(ofSize: 17)], for: .normal)
        self.segmentCtrl.setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.black, .font: UIFont.systemFont(ofSize: 17, weight: .bold)], for: .selected)
                
        self.segmentCtrl.addTarget(self, action: #selector(segCtrlValChanged(_:)), for: .valueChanged)
    }
    
    @objc func segCtrlValChanged(_ sender: UISegmentedControl) {
        let descriptionCnt = self.getDescriptionCnt()
        print(descriptionCnt)
        
        switch sender.selectedSegmentIndex {
        case 0:
            self.mainTV.scrollToRow(at: NSIndexPath(row: 2, section: 0) as IndexPath, at: UITableView.ScrollPosition.top, animated: true)
        case 1:
            self.mainTV.scrollToRow(at: NSIndexPath(row: descriptionCnt + 2, section: 0) as IndexPath, at: UITableView.ScrollPosition.top, animated: true)
        case 2:
            self.mainTV.scrollToRow(at: NSIndexPath(row: descriptionCnt + 3, section: 0) as IndexPath, at: UITableView.ScrollPosition.top, animated: true)
        case 3:
            self.mainTV.scrollToRow(at: NSIndexPath(row: descriptionCnt + 4, section: 0) as IndexPath, at: UITableView.ScrollPosition.top, animated: true)
        default:
            break
        }
    }
    
    // MARK: - getCourse
    private func getCourseInfo(id: Int) {
        CourseInfoDataService.shared.getCourseInfo(id: id) { response in
            switch (response) {
            case .success(let data):
                if let data = data as? CourseInfoDataModel {

                    self.courseInfoData = data
                    print(data)
                    self.getCourseReview()
                    self.getCourseInquiry()
                    self.mainTV.reloadData()
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
    
    // MARK: - getCourseReview
    private func getCourseReview() {
        GetReviewDataService.shared.getReview(courseId: self.courseId ?? 1) { response in
            switch response {
            case .success(let reviewData):
                if let data = reviewData as? [CourseReviewDataModel] {
                    self.reviewData = data
                    
                    self.mainTV.reloadData()
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
    
    // MARK: - getCourseInquiry
    private func getCourseInquiry() {
        GetInquiryDataService.shared.getInquiry(courseId: self.courseId ?? 1) { response in
            switch response {
            case .success(let inquiryData):
                if let data = inquiryData as? [CourseInquiryDataModel] {
                    self.inquiryData = data
                    
                    self.mainTV.reloadData()
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
    
    // MARK: - Description Image 갯수 반환
    private func getDescriptionCnt() -> Int {
        var descriptionCnt = self.courseInfoData?.descriptionImages.count ?? 0
        
        // 이미지 갯수는 최소 1개로 설정 (default 이미지 존재)
        if descriptionCnt < 1 {
            descriptionCnt = 1
        }
        
        return descriptionCnt
    }
    
    
}


// MARK: - UITableViewDelegate, UITableViewDataSource
extension CourseInfoViewController: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if indexPath.row == 0 {
            return 450
        } else {
            return UITableView.automaticDimension
        }
        
    }
    
    func tableView(_ tableView: UITableView, estimatedHeightForRowAt indexPath: IndexPath) -> CGFloat {
        return 50
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if let courseInfoData = courseInfoData {
            return 6 + courseInfoData.descriptionImages.count
        } else {
            return 6
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let descriptionCnt = self.getDescriptionCnt()
        
        switch indexPath.row {
        case 0:
            // 메인 정보 자리
            guard let cell = mainTV.dequeueReusableCell(withIdentifier: Const.Xib.Identifier.courseInfoTVC, for: indexPath) as? CourseInfoTVC else { return UITableViewCell() }

            cell.delegate = self
            
            cell.setEnroll(self.isEnroll ?? false)
            cell.courseThumbnailImageView.setImage(with: self.courseInfoData?.thumbnailImage.originalFilePath ?? "")
            cell.classTitle.text = self.courseInfoData?.title
            cell.courseDescription.text = self.courseInfoData?.description
            cell.instructor.text = self.courseInfoData?.instructor
            
            return cell
        case 1:
            // Sticky View 자리
            guard let cell = mainTV.dequeueReusableCell(withIdentifier: Const.Xib.Identifier.segmentTVC, for: indexPath) as? SegmentTVC else { return UITableViewCell() }
            return cell
        case 2...descriptionCnt+1:
            // description Image
            guard let cell = mainTV.dequeueReusableCell(withIdentifier: Const.Xib.Identifier.infoImageTVC, for: indexPath) as? InfoImageTVC else { return UITableViewCell() }
            
            cell.contentView.snp.makeConstraints {
                $0.height.equalTo(self.view.frame.width * 2)
                $0.width.equalTo(self.view.frame.width)
            }
            
            if let courseInfoData = courseInfoData {
                if courseInfoData.descriptionImages.count != 0 {
                    cell.infoImageView.setImage(with: courseInfoData.descriptionImages[indexPath.row-2].originalFilePath)
                }
            }
            
            return cell
        case descriptionCnt + 2:
            guard let cell = mainTV.dequeueReusableCell(withIdentifier: Const.Xib.Identifier.courseCurriculumTVC, for: indexPath) as? CourseCurriculumTVC else { return UITableViewCell() }
            cell.delegate = self
            
            return cell
        case descriptionCnt+3:
            // Review
            guard let cell = mainTV.dequeueReusableCell(withIdentifier: Const.Xib.Identifier.courseReviewTVC, for: indexPath) as? CourseReviewTVC else { return UITableViewCell() }
            cell.setData(self.reviewData)
            cell.delegate = self
            
            return cell
        case descriptionCnt+4:
            // Inquiry
            guard let cell = mainTV.dequeueReusableCell(withIdentifier: Const.Xib.Identifier.courseInquiryTVC, for: indexPath) as? CourseInquiryTVC else { return UITableViewCell() }
            cell.setData(self.inquiryData)
            cell.delegate = self
            
            return cell
        default:
            return UITableViewCell()
        }
    }
    
    // StickyView 조절
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if scrollView.contentOffset.y > 0 {
            upperConstraint.constant = max((maxUpper - scrollView.contentOffset.y), minUpper)
        } else {
            upperConstraint.constant = maxUpper - scrollView.contentOffset.y
        }
    }
}

// MARK: - allInquiryBtnDelegate
extension CourseInfoViewController: allInquiryBtnDelegate {
    func allInquiryBtnPressed() {
        guard let nextVC = UIStoryboard(name: Const.Storyboard.Name.courseInfoView, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.courseInquiryAllVC) as? CourseInquiryAllVC else { return }
        
        nextVC.inquiryData = self.inquiryData
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
    }    
    
}

// MARK: - allReviewBtnDelegate
extension CourseInfoViewController: allReviewBtnDelegate {
    func allReviewBtnPressed() {
        guard let nextVC = UIStoryboard(name: Const.Storyboard.Name.courseInfoView, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.courseReviewAllVC) as? CourseReviewAllVC else { return }
        
        nextVC.reviewData = self.reviewData
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
    }
}

// MARK: - CourseEnrollBtnDelegate 강좌 신청 버튼 눌렀을 때
extension CourseInfoViewController: CourseEnrollBtnDelegate {
    func CourseEnroll() {
        if self.loginType != nil {
            let alert = UIAlertController(title: "수강 신청", message: "해당 강좌에 수강 신청하시겠습니까?", preferredStyle: .alert)
            let cancel = UIAlertAction(title: "취소", style: .cancel)
            let confirm = UIAlertAction(title: "신청", style: .default) { _ in
                CourseInfoDataService.shared.enrollCourse(courseId: self.courseId!) { response in
                    switch (response) {
                    case .success:
                        let complete = UIAlertController(title: "신청 되었습니다", message: nil, preferredStyle: .alert)
                        let done = UIAlertAction(title: "확인", style: .default)
                        complete.addAction(done)
                        self.present(complete, animated: true)
                        
                        self.isEnroll = true
                        self.mainTV.reloadRows(at: [IndexPath(row: 0, section: 0)], with: .none)
                        print("enroll Success")
                    case .requestErr(let message):
                        print("requestErr", message)
                    case .pathErr:
                        print("networkResult pathErr")
                        print("pathErr")
                    case .serverErr:
                        print("serverErr")
                    case .networkFail:
                        print("networkFail")
                    case .resourceErr:
                        let err = UIAlertController(title: "이미 신청한 강좌입니다", message: nil, preferredStyle: .alert)
                        let done = UIAlertAction(title: "확인", style: .default)
                        err.addAction(done)
                        self.present(err, animated: true)
                        print("resourceErr")
                    }
                }
            }
            
            alert.addAction(cancel)
            alert.addAction(confirm)
            
            present(alert, animated: true)
        } else {
            let alert = UIAlertController(title: "로그인이 필요한 서비스입니다", message: "로그인 하시겠습니까?", preferredStyle: .alert)
            let cancel = UIAlertAction(title: "취소", style: .cancel)
            let login = UIAlertAction(title: "확인", style: .default) { _ in
                AuthenticationService.shared.goToLoginSignup()
            }
            
            alert.addAction(cancel)
            alert.addAction(login)
            
            present(alert, animated: true)
        }
    }
    
    func CourseSamplePlay() {
        guard let courseInfoData = courseInfoData else {return}
        guard let videoUrl = URL(string: "\(Const.Url.baseUrl)\(courseInfoData.sample.videoInfo.filePath)") else {return}
        self.avPlayer = AVPlayer(url: videoUrl)
        self.avController.player = self.avPlayer
        self.avController.view.frame = self.view.frame
        self.present(avController, animated: true, completion: nil)
        avPlayer.play()
    }
}

// MARK: - 강좌 커리큘럼 보기 버튼 눌렀을 때
extension CourseInfoViewController: allCurriculumBtnDelegate {
    func allCurriculumBtnPressed() {
        guard let nextVC = UIStoryboard(name: Const.Storyboard.Name.courseInfoView, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.courseCurriculumAllVC) as? CourseCurriculumAllVC else {return}
        if let courseInfoData = courseInfoData {
            nextVC.courseId = courseInfoData.id
        }
        
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
    }
}
