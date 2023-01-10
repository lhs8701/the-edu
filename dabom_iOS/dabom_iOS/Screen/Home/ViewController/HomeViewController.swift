//
//  HomeViewController.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/04.
//

import UIKit

class HomeViewController: UIViewController {

    @IBOutlet weak var homeTableView: UITableView!
    
    private var courseTableList: Array<CourseTableDataModel> = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        self.navigationController?.navigationBar.topItem?.backButtonTitle = ""
        self.navigationController?.navigationBar.tintColor = .black
        
        
        setTV()
        print("HOMEVIEWDIDLOAD")
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(true, animated: true)
    }
//    override func viewDidAppear(_ animated: Bool) {
////        self.navigationController?.isNavigationBarHidden = true
//        self.navigationController?.setNavigationBarHidden(true, animated: true)
//    }

    private func setTV() {
        homeTableView.register(UINib(nibName: "CourseTableViewCell", bundle: nil), forCellReuseIdentifier: "CourseTableViewCell")
        homeTableView.register(UINib(nibName: "BannerTableViewCell", bundle: nil), forCellReuseIdentifier: "BannerTableViewCell")
        
        homeTableView.delegate = self
        homeTableView.dataSource = self
        homeTableView.separatorStyle = .none
        
        
    }
}


// MARK: - UITableViewDelegate
extension HomeViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        
        switch indexPath.row {
        case 0:
            return 200
        case 1, 2, 3, 4:
            return 430
        default:
            return 430
        }
        
        
    }
}


// MARK: - UITableViewDataSource
extension HomeViewController: UITableViewDataSource {
    // 행 개수
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 5
    }
    
    // 행 번호마다 셀 설정
    // 0 -> 배너
    // 1 ~ -> 클래스 랭킹
    // 마지막 -> 로드맵 (아직 구현 안됨)
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        switch indexPath.row {
        case 0:
            guard let cell = tableView.dequeueReusableCell(withIdentifier: BannerTableViewCell.identifier, for: indexPath) as? BannerTableViewCell else { return UITableViewCell() }
            cell.setData(BannerDataModel.sampleData)
            cell.delegate = self
            
            return cell
        case 1, 2, 3, 4, 5:
            guard let cell = tableView.dequeueReusableCell(withIdentifier: CourseTableViewCell.identifier, for: indexPath) as? CourseTableViewCell else { return UITableViewCell() }

    //        cell.setData(courseTableList[indexPath.row])
            cell.setData(CourseTableDataModel.sampleData[indexPath.row - 1])
            cell.delegate = self
            
            return cell
        default:
            return UITableViewCell()
        }
        
    }
    
    
}

extension HomeViewController: CourseCVCellDelegate {
    func CourseSelectedCVCell(index: Int, courseName: String) {
        guard let nextVC = UIStoryboard(name: "CourseInfoView", bundle: nil).instantiateViewController(withIdentifier: "CourseInfoViewController") as? CourseInfoViewController else { return }

        nextVC.courseTitle = courseName
//        print(courseName)
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
//        performSegue(withIdentifier: "CourseInfoView", sender: nil)
    }
    
}

extension HomeViewController: BannerCVCellDelegate {
    func BannerSelectedCVCell(index: Int, bannerName: String) {
        guard let nextVC = UIStoryboard(name: "HomeTab", bundle: nil).instantiateViewController(withIdentifier: "BannerInfoViewController") as? BannerInfoViewController else { return }
        
        nextVC.bannerImageName = bannerName
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
    }
}
