//
//  InCourseViewController.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/04.
//

import UIKit

class InCourseViewController: UIViewController {

    // MARK: - IBOutlet
    @IBOutlet weak var inCourseCV: UICollectionView!
    
    
    // MARK: - let, var
    var inCourseData: Array<MyCourseDataModel>?
    let loginType: String? = UserDefaults.standard.string(forKey: "loginType")
    
    
    // MARK: - Life Cycle
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
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
    
    private func getOngoingCourses() {
        MyCourseDataService.shared.getOngoing { response in
            switch response {
            case .success(let data):
                if let data = data as? [MyCourseDataModel] {
//                    for d in data {
//                        print(d)
//                    }
//                    self.temp = data
                    self.inCourseData = data
                    self.inCourseCV.reloadData()
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

extension InCourseViewController: UICollectionViewDelegate {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        if let inCourseData = inCourseData {
            return inCourseData.count
        } else {
            return 0
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {

        guard let nextVC = UIStoryboard(name: Const.Storyboard.Name.myCourseTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.coursePlayerVC) as? CoursePlayerVC else { return }
        
        if let inCourseData = inCourseData {
            nextVC.courseId = inCourseData[indexPath.row].courseId
            nextVC.unitId = inCourseData[indexPath.row].nextUnitInfo.unitId
            nextVC.unitTitle = inCourseData[indexPath.row].nextUnitInfo.title
            nextVC.thumbnailImage = inCourseData[indexPath.row].thumbnailImage.mediumFilePath
        }
        
//        nextVC.unitThumbnailImage.image = self.inCourseData[indexPath.row].thumbㅌ
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
        let cellHeight = cellWidth * 1.3

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
