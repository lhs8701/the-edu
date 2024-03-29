//
//  InCourseViewController.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/04.
//

import UIKit
import SnapKit

class InCourseViewController: UIViewController {

    // MARK: - IBOutlet
    
    @IBOutlet weak var inCourseCV: UICollectionView!
    
    
    // MARK: - let, var
    
    var inCourseData: Array<MyCourseDataModel>?
    let loginType: String? = UserDefaults.standard.string(forKey: "loginType")
    
    var defaultImageView: UIImageView = UIImageView(image: UIImage(named: "default_incourse"))
    
    
    // MARK: - Life Cycle
    
    override func viewDidLoad() {
        super.viewDidLoad()

        setDefaultImage()
        setCV()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        if self.loginType != nil {
            getOngoingCourses()
        }
    }
    
    
    // MARK: - func
    
    private func setCV() {
        self.inCourseCV.register(UINib(nibName: Const.Xib.Name.myCourseCVC, bundle: nil), forCellWithReuseIdentifier: Const.Xib.Identifier.myCourseCVC)
        self.inCourseCV.delegate = self
        self.inCourseCV.dataSource = self
        self.inCourseCV.isScrollEnabled = true
        
    }
    
    
    // MARK: - 수강 중인 강좌 목록 가져오기
    
    private func getOngoingCourses() {
        MyCourseDataService.shared.getOngoing { response in
            switch response {
            case .success(let data):
                if let data = data as? [MyCourseDataModel] {
                    self.inCourseData = data
                    self.inCourseCV.reloadData()
                    
                    if data.isEmpty {
                        self.defaultImageView.isHidden = false
                    } else {
                        self.defaultImageView.isHidden = true
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
    
    
    // MARK: - 기본 이미지 세팅 (수강 중인 강좌 목록이 없을 때 노출)
    
    private func setDefaultImage() {
        view.addSubview(defaultImageView)
        defaultImageView.contentMode = .scaleAspectFit
        defaultImageView.snp.makeConstraints {
            $0.center.equalTo(inCourseCV.snp.center)
            $0.leading.trailing.equalToSuperview()
            $0.height.equalTo(300)
        }
        defaultImageView.isHidden = true
    }

}


// MARK: - extension

extension InCourseViewController: UICollectionViewDelegate {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        if let inCourseData = inCourseData {
            return inCourseData.count
        } else {
            return 0
        }
    }
    
    // 셀 선택 시 플레이어로 이동
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {

        guard let nextVC = UIStoryboard(name: Const.Storyboard.Name.myCourseTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.coursePlayerVC) as? CoursePlayerVC else { return }
        
        if let inCourseData = inCourseData {
            nextVC.courseId = inCourseData[indexPath.row].courseId
            nextVC.unitId = inCourseData[indexPath.row].nextUnitInfo.unitId
            nextVC.unitTitle = inCourseData[indexPath.row].nextUnitInfo.title
            nextVC.thumbnailImage = inCourseData[indexPath.row].thumbnailImage.mediumFilePath
        }
        
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
    }
    
}


extension InCourseViewController: UICollectionViewDataSource {
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: MyCourseCVC.identifier, for: indexPath) as? MyCourseCVC else { return UICollectionViewCell() }
        
        if let inCourseData = inCourseData {
            cell.setData(inCourseData[indexPath.row])
        }
        
        return cell
    }
}


extension InCourseViewController: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        
        let cellWidth = (UIScreen.main.bounds.width - (10 * 3)) / 2
        let cellHeight = cellWidth * 1.25

        return CGSize(width: cellWidth, height: cellHeight)
    }

    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {

        return UIEdgeInsets(top: 10, left: 10, bottom: 10, right: 10)
    }

    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumInteritemSpacingForSectionAt section: Int) -> CGFloat {
        let spacingSize = 10

        return CGFloat(spacingSize)
    }

    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        let spacingSize = 10

        return CGFloat(spacingSize)
    }
}
