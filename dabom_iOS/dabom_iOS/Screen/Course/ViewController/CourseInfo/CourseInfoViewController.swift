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
    
    let memberId: Int = UserDefaults.standard.integer(forKey: "memberId")
    let loginType: String? = UserDefaults.standard.string(forKey: "loginType")
    
    var courseId: Int?
    
    var courseInfoData: CourseInfoDataModel?
    
    var courseTitle: String?
    var courseDescription: String?
    var instructor: String?

    var isWish: Bool?
    var isEnroll: Bool?
    var isCharge: Bool?

    let maxUpper: CGFloat = 400.0
    let minUpper: CGFloat = 0.0
    
    var reviewData: [CourseReviewDataModel] = []
    var inquiryData: [CourseInquiryDataModel] = []
    var ticketData: [TicketDataModel] = []
    
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
        setAudio()
            
        getCourseInfo(id: self.courseId!)
        getTicket(courseId: self.courseId!)
        
        // 로그인 중일 때만 wish, enroll 체크
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
    
    
    // MARK: - 핸드폰 무음 시에도 소리 실행하게 설정
    
    private func setAudio() {
        do {
            try AVAudioSession.sharedInstance().setCategory(AVAudioSession.Category.playback)
            try AVAudioSession.sharedInstance().setActive(true)
        } catch {
            print(error)
        }
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

        // rightBarButtonItem에 찜하기 버튼, 온오프 버튼 존재 -> 현재 온오프 버튼은 hidden
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
            
            // 찜 버튼 토글
            if sender.isSelected {
                sender.isSelected = false
            } else {
                sender.isSelected = true
            }
        } else {
            // 로그인하지 않은 상태에서 또는 로그인이 만료된 상태에서 찜하기 눌렀을 때 로그인 화면으로 유도
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
        
        switch sender.selectedSegmentIndex {
        case 0:
            // 클래스 소개로 스크롤 이동
            self.mainTV.scrollToRow(at: NSIndexPath(row: 2, section: 0) as IndexPath, at: UITableView.ScrollPosition.top, animated: true)
        case 1:
            // 커리큘럼으로 스크롤 이동
            self.mainTV.scrollToRow(at: NSIndexPath(row: descriptionCnt + 2, section: 0) as IndexPath, at: UITableView.ScrollPosition.top, animated: true)
        case 2:
            // 강좌 후기로 스크롤 이동
            self.mainTV.scrollToRow(at: NSIndexPath(row: descriptionCnt + 3, section: 0) as IndexPath, at: UITableView.ScrollPosition.top, animated: true)
        case 3:
            // 문의사항으로 스크롤 이동
            self.mainTV.scrollToRow(at: NSIndexPath(row: descriptionCnt + 4, section: 0) as IndexPath, at: UITableView.ScrollPosition.top, animated: true)
        default:
            break
        }
    }
    
    
    // MARK: - 강좌 상세 정보 가져오기
    
    private func getCourseInfo(id: Int) {
        CourseInfoDataService.shared.getCourseInfo(id: id) { response in
            switch (response) {
            case .success(let data):
                if let data = data as? CourseInfoDataModel {
                    self.courseInfoData = data
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
    
    
    // MARK: - 강좌 리뷰 정보 가져오기
    
    private func getCourseReview() {
        guard let courseId = courseId else { return }
    
        GetReviewDataService.shared.getReview(courseId: courseId) { response in
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
    
    
    // MARK: - 강좌 문의 정보 가져오기
    
    private func getCourseInquiry() {
        guard let courseId = courseId else { return }
        
        GetInquiryDataService.shared.getInquiry(courseId: courseId) { response in
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
    
    
    // MARK: - 수강권 정보 가져오기
    
    private func getTicket(courseId: Int) {
        TicketDataService.shared.getTickets(courseId: courseId) { response in
            switch response {
            case .success(let data):
                if let data = data as? [TicketDataModel] {
                    self.ticketData = data
                    
                    // 유료 강좌인지 무료 강좌인지 확인
                    if self.ticketData[0].costPrice > 0 {
                        self.isCharge = true
                    } else {
                        self.isCharge = false
                    }
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
            return 400
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
            
            cell.setStatus(isEnroll: self.isEnroll ?? false, isCharge: self.isCharge ?? true)
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
            // 클래스 소개 이미지
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
            // 커리큘럼
            guard let cell = mainTV.dequeueReusableCell(withIdentifier: Const.Xib.Identifier.courseCurriculumTVC, for: indexPath) as? CourseCurriculumTVC else { return UITableViewCell() }
            cell.delegate = self
            
            return cell
            
        case descriptionCnt+3:
            // 강좌 리뷰
            guard let cell = mainTV.dequeueReusableCell(withIdentifier: Const.Xib.Identifier.courseReviewTVC, for: indexPath) as? CourseReviewTVC else { return UITableViewCell() }
            cell.setData(self.reviewData)
            cell.delegate = self
            
            return cell
            
        case descriptionCnt+4:
            // 강좌 문의사항
            guard let cell = mainTV.dequeueReusableCell(withIdentifier: Const.Xib.Identifier.courseInquiryTVC, for: indexPath) as? CourseInquiryTVC else { return UITableViewCell() }
            cell.setData(self.inquiryData)
            cell.delegate = self
            
            return cell
            
        default:
            return UITableViewCell()
        }
    }
    
    
    // MARK: - Sticky view 조절
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if scrollView.contentOffset.y > 0 {
            upperConstraint.constant = max((maxUpper - scrollView.contentOffset.y), minUpper)
        } else {
            upperConstraint.constant = maxUpper - scrollView.contentOffset.y
        }
    }
}


// MARK: - 문의사항 모두 보기 버튼 delegate 패턴

extension CourseInfoViewController: allInquiryBtnDelegate {
    func allInquiryBtnPressed() {
        guard let nextVC = UIStoryboard(name: Const.Storyboard.Name.courseInfoView, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.courseInquiryAllVC) as? CourseInquiryAllVC else { return }
        
        nextVC.inquiryData = self.inquiryData
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
    }    
    
}


// MARK: - 리뷰 모두 모기 버튼 delegate 패턴

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
                TicketDataService.shared.postPurchase(itemId: self.ticketData[0].id) { response in
                    switch response {
                    case .success:
                        let complete = UIAlertController(title: "신청 되었습니다", message: nil, preferredStyle: .alert)
                        let done = UIAlertAction(title: "확인", style: .default)
                        complete.addAction(done)
                        self.present(complete, animated: true)
                                                
                        self.isEnroll = true
                        self.mainTV.reloadRows(at: [IndexPath(row: 0, section: 0)], with: .none)
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
    
    // MARK: - 강좌 상세보기 최상단의 강좌 샘플 강의
    
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
