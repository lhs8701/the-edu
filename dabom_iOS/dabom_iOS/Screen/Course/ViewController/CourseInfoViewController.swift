//
//  CourseInfoViewController.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/09.
//

import UIKit

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
    
    var courseId: Int?
    
    var isWish: Bool?

    let maxUpper: CGFloat = 450.0
    let minUpper: CGFloat = 0.0
    
    let memberId: Int = UserDefaults.standard.integer(forKey: "memberId")
    
    var reviewData: [CourseReviewDataModel]?
    
    
    var onOffButton: UIButton!
    var heartButton: UIButton!
    
    
    // MARK: - Life Cycle
    
    override func viewDidLoad() {
        super.viewDidLoad()

        setRightBarButton()
        setTableView()
        setSegmentController()
            
        // courseId 기본값 설정 (임시)
        self.courseId = 1
        getCourseInfo(id: self.courseId!)
        checkWish()

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
        
        let unselectedHeart = UIImage(named: Const.Image.unselectedHeart)
        let selectedHeart = UIImage(named: Const.Image.selectedHeart)
        self.heartButton = UIButton.init(frame: CGRect.init(x: 0, y: 0, width: 30, height: 30))
        self.heartButton.setImage(unselectedHeart, for: .normal)
        self.heartButton.setImage(selectedHeart, for: .selected)
        self.heartButton.addTarget(self, action: #selector(wishBtnPressed(_:)), for: .touchUpInside)
        let heart = UIBarButtonItem(customView: heartButton)

        navigationItem.rightBarButtonItems = [heart, onOff]
    }
    
    func checkWish() {
        CourseInfoDataService.shared.isWishCourse(memberId: self.memberId, courseId: self.courseId!) { check in
            switch check {
            case true:
                print("true!!")
                self.heartButton.isSelected = true
            case false:
                print("false!!")
                self.heartButton.isSelected = false
            }
        }
    }
    
    @objc func wishBtnPressed(_ sender: UIButton) {
        CourseInfoDataService.shared.changeWishCourse(memberId: self.memberId, courseId: self.courseId!) { response in
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
    }
    
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
        switch sender.selectedSegmentIndex {
        case 0:
            self.mainTV.scrollToRow(at: NSIndexPath(row: 2, section: 0) as IndexPath, at: UITableView.ScrollPosition.top, animated: true)
        case 1:
            self.mainTV.scrollToRow(at: NSIndexPath(row: 3, section: 0) as IndexPath, at: UITableView.ScrollPosition.top, animated: true)
        case 2:
            self.mainTV.scrollToRow(at: NSIndexPath(row: 4, section: 0) as IndexPath, at: UITableView.ScrollPosition.top, animated: true)
        case 3:
            self.mainTV.scrollToRow(at: NSIndexPath(row: 5, section: 0) as IndexPath, at: UITableView.ScrollPosition.top, animated: true)
        default:
            break
        }
    }
    
    // MARK: - getCourse
    private func getCourseInfo(id: Int) {
        CourseInfoDataService.shared.getCourseInfo(id: id) { response in
            switch (response) {
            case .success(let courseInfoData):
                if let data = courseInfoData as? CourseInfoDataModel {
                    self.courseTitle = data.title
                    self.courseDescription = data.description
                    self.instructor = data.instructor
                    
                    self.getCourseReview()
//                    self.mainTV.reloadData()
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
}


// MARK: - UITableViewDelegate, UITableViewDataSource
extension CourseInfoViewController: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        switch indexPath.row {
        case 0:
            return 450
        case 1:
            return 50
        case 2, 3:
            return 800
        case 4, 5:
            return 575
        default:
            return 0
        }
//        return UITableView.automaticDimension
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 7
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        switch indexPath.row {
        case 0:
            // 메인 정보 자리
            guard let cell = mainTV.dequeueReusableCell(withIdentifier: Const.Xib.Identifier.courseInfoTVC, for: indexPath) as? CourseInfoTVC else { return UITableViewCell() }
            cell.classTitle.text = self.courseTitle
            cell.courseDescription.text = self.courseDescription
            cell.instructor.text = self.instructor
            return cell
        case 1:
            // Sticky View 자리
            guard let cell = mainTV.dequeueReusableCell(withIdentifier: Const.Xib.Identifier.segmentTVC, for: indexPath) as? SegmentTVC else { return UITableViewCell() }
            return cell
        case 2:
            guard let cell = mainTV.dequeueReusableCell(withIdentifier: Const.Xib.Identifier.infoImageTVC, for: indexPath) as? InfoImageTVC else { return UITableViewCell() }
            cell.infoImageView.image = UIImage(named: "testIntro01")
            return cell
        case 3:
            guard let cell = mainTV.dequeueReusableCell(withIdentifier: Const.Xib.Identifier.infoImageTVC, for: indexPath) as? InfoImageTVC else { return UITableViewCell() }
            cell.infoImageView.image = UIImage(named: "testIntro02")
            return cell
        case 4:
            guard let cell = mainTV.dequeueReusableCell(withIdentifier: Const.Xib.Identifier.courseReviewTVC, for: indexPath) as? CourseReviewTVC else { return UITableViewCell() }
//            cell.reviewData = self.reviewData
            cell.setData(self.reviewData)
            
            return cell
        case 5:
            guard let cell = mainTV.dequeueReusableCell(withIdentifier: Const.Xib.Identifier.courseReviewTVC, for: indexPath) as? CourseReviewTVC else { return UITableViewCell() }
            
            cell.reviewTitle.text = "문의 사항"
            cell.allReviewBtn.setTitle("문의 모두 보기", for: .normal)
            
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
