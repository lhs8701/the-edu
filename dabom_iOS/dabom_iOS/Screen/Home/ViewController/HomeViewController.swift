//
//  HomeViewController.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/04.
//

import UIKit

class HomeViewController: UIViewController {

    @IBOutlet weak var homeTableView: UITableView!
    
    private var courseTableList: [CourseTableDataModel] = []
    private var courseRankingList: [CourseRankingDataModel] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        self.navigationController?.navigationBar.topItem?.backButtonTitle = ""
        self.navigationController?.navigationBar.tintColor = .black
        
        setTV()
        setRanking()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(true, animated: true)
    }

    private func setTV() {
        homeTableView.register(UINib(nibName: Const.Xib.Name.courseTVC, bundle: nil), forCellReuseIdentifier: Const.Xib.Identifier.courseTVC)
        homeTableView.register(UINib(nibName: Const.Xib.Name.bannerTVC, bundle: nil), forCellReuseIdentifier: Const.Xib.Identifier.bannerTVC)
        
        homeTableView.delegate = self
        homeTableView.dataSource = self
        homeTableView.separatorStyle = .none
        
    }
    
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
    
    
    @IBAction func categoryBtnPressed(_ sender: Any) {
        guard let categoryVC = UIStoryboard(name: Const.Storyboard.Name.homeTab, bundle: nil).instantiateViewController(withIdentifier: "CategorySelectVC") as? CategorySelectVC else {return}
        
        self.navigationController?.pushViewController(categoryVC, animated: true)
    }
    
}


// MARK: - UITableViewDelegate
extension HomeViewController: UITableViewDelegate {
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
    // 마지막 -> 로드맵 (아직 구현 안됨)
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
//        switch indexPath.row {
//        case 0:
//            guard let cell = tableView.dequeueReusableCell(withIdentifier: BannerTableViewCell.identifier, for: indexPath) as? BannerTableViewCell else { return UITableViewCell() }
//            cell.setData(BannerDataModel.sampleData)
//            cell.delegate = self
//
//            return cell
//        case 1, 2, 3, 4, 5:
//            guard let cell = tableView.dequeueReusableCell(withIdentifier: CourseTableViewCell.identifier, for: indexPath) as? CourseTableViewCell else { return UITableViewCell() }
//
//    //        cell.setData(courseTableList[indexPath.row])
//            cell.setData(CourseTableDataModel.sampleData[indexPath.row - 1])
//            cell.delegate = self
//
//            return cell
//        default:
//            return UITableViewCell()
//        }
        
        if indexPath.row == 0 {
            guard let cell = tableView.dequeueReusableCell(withIdentifier: BannerTableViewCell.identifier, for: indexPath) as? BannerTableViewCell else { return UITableViewCell() }
            cell.setData(BannerDataModel.sampleData)
            cell.delegate = self
            
            return cell
        } else {
            guard let cell = tableView.dequeueReusableCell(withIdentifier: CourseTableViewCell.identifier, for: indexPath) as? CourseTableViewCell else { return UITableViewCell() }

    //        cell.setData(courseTableList[indexPath.row])
//            cell.setData(CourseTableDataModel.sampleData[indexPath.row - 1])
            cell.setData(courseRankingData: self.courseRankingList[indexPath.row - 1])
            
            cell.delegate = self
            
            return cell
        }
        
        
    }
    
    
}

extension HomeViewController: CourseCVCellDelegate {
    func CourseSelectedCVCell(index: Int, courseName: String) {
        guard let nextVC = UIStoryboard(name: Const.Storyboard.Name.courseInfoView, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.courseInfo) as? CourseInfoViewController else { return }
        
        nextVC.courseId = 1
        nextVC.courseTitle = courseName
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
    }
    
}

extension HomeViewController: BannerCVCellDelegate {
    func BannerSelectedCVCell(index: Int, bannerName: String) {
        guard let nextVC = UIStoryboard(name: Const.Storyboard.Name.homeTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.bannerInfo) as? BannerInfoViewController else { return }
        
        nextVC.bannerImageName = bannerName
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
    }
}
