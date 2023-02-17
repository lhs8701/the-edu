//
//  HomeViewController.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/04.
//

import UIKit

class HomeViewController: UIViewController {
    
    // MARK: - IBOutlet
    
    @IBOutlet weak var homeTableView: UITableView!
    
    
    // MARK: - let, var
    
    private var courseTableList: [CourseTableDataModel] = []
    private var courseRankingList: [CourseRankingDataModel] = []
    private var bannerList: [BannerDataModel] = []
    
    var autoStart: Bool = false
    
    
    // MARK: - Life Cycle
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        self.navigationController?.navigationBar.topItem?.backButtonTitle = ""
        self.navigationController?.navigationBar.tintColor = .black
        
        setTV()
        setRanking()
        setBanner()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(true, animated: true)
    }

    
    // MARK: - TableView Setting
    
    private func setTV() {
        homeTableView.register(UINib(nibName: Const.Xib.Name.courseTVC, bundle: nil), forCellReuseIdentifier: Const.Xib.Identifier.courseTVC)
        homeTableView.register(UINib(nibName: Const.Xib.Name.bannerTVC, bundle: nil), forCellReuseIdentifier: Const.Xib.Identifier.bannerTVC)
        
        homeTableView.delegate = self
        homeTableView.dataSource = self
        homeTableView.separatorStyle = .none
        
    }
    
    
    // MARK: - 강좌 랭킹 정보 가져오기
    
    private func setRanking() {
        CourseRankingDataService.shared.getCourseRanking { response in
            switch response {
            case .success(let data):
                if let data = data as? [CourseRankingDataModel] {
                    self.courseRankingList = data
                    self.homeTableView.reloadData()
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
    
    
    // MARK: - 진행 중인 이벤트 배너 가져오기
    
    private func setBanner() {
        BannerDataService.shared.getOngoingBanner { response in
            switch response {
            case .success(let data):
                if let data = data as? [BannerDataModel] {
                    self.bannerList = data
                    self.homeTableView.reloadData()
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
    
    
    // MARK: - 카테고리 햄버거 버튼 눌렀을 때
    
    @IBAction func categoryBtnPressed(_ sender: Any) {
        guard let categoryVC = UIStoryboard(name: Const.Storyboard.Name.homeTab, bundle: nil).instantiateViewController(withIdentifier: "CategorySelectVC") as? CategorySelectVC else {return}
        
        self.navigationController?.pushViewController(categoryVC, animated: true)
    }
    
}


// MARK: - UITableViewDelegate

extension HomeViewController: UITableViewDelegate {
    
    // tableView 높이 자동 지정
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return UITableView.automaticDimension
    }
    
    func tableView(_ tableView: UITableView, estimatedHeightForRowAt indexPath: IndexPath) -> CGFloat {
        return 200
    }
}


// MARK: - UITableViewDataSource

extension HomeViewController: UITableViewDataSource {
    // 행 개수
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 1 + courseRankingList.count
    }
    
    // 행 번호마다 셀 설정
    // 0 -> 배너
    // 1 ~ -> 클래스 랭킹
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        // 0 -> 배너 셀
        if indexPath.row == 0 {
            guard let cell = tableView.dequeueReusableCell(withIdentifier: BannerTableViewCell.identifier, for: indexPath) as? BannerTableViewCell else { return UITableViewCell() }
            if self.bannerList.count != 0 {
                if !autoStart {
                    cell.autoStart = true
                    self.autoStart = true
                }
                
                cell.setData(bannerTableData: self.bannerList)
            }
            cell.delegate = self
            
            return cell
        } else {
            // 배너 셀 이후로 강좌 랭킹 셀
            guard let cell = tableView.dequeueReusableCell(withIdentifier: CourseTableViewCell.identifier, for: indexPath) as? CourseTableViewCell else { return UITableViewCell() }
            cell.setData(courseRankingData: self.courseRankingList[indexPath.row - 1])
            
            cell.delegate = self
            
            return cell
        }
        
    }
    
}


// MARK: - 클래스 랭킹에 표시된 강좌 썸네일 클릭 시 강좌 상세보기로 이동

extension HomeViewController: CourseCVCellDelegate {
    
    func CourseSelectedCVCell(courseId: Int, courseName: String) {
        guard let nextVC = UIStoryboard(name: Const.Storyboard.Name.courseInfoView, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.courseInfo) as? CourseInfoViewController else { return }

        nextVC.courseId = courseId
        nextVC.courseTitle = courseName
        
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
    }
    
}


// MARK: - 배너 셀 클릭 시 이벤트 페이지로 이동

extension HomeViewController: BannerCVCellDelegate {
    
    func BannerSelectedCVCell(eventId: Int, bannerName: String) {
        guard let nextVC = UIStoryboard(name: Const.Storyboard.Name.homeTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.bannerInfo) as? BannerInfoViewController else { return }
        
        nextVC.eventId = eventId
        nextVC.bannerTitle = bannerName
        
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
    }
    
}
