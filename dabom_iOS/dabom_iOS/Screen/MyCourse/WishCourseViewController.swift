//
//  WishCourseViewController.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/04.
//

import UIKit

class WishCourseViewController: UIViewController {
    
    @IBOutlet weak var wishCourseCollectionView: UICollectionView!
    
    var wishCourseData: Array<CourseThumbnailDataModel>?
    let memberId: Int = UserDefaults.standard.integer(forKey: "memberId")
    let isLogin: Bool = UserDefaults.standard.bool(forKey: "isLogin")
    
    var temp: [SampleCourseThumbnail] = []
    
    // MARK: - Life Cycle
    override func viewDidLoad() {
        super.viewDidLoad()

        setCV()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        if self.isLogin {
            getWishCourse()
        }
        
    }
    
    // MARK: - func
    private func setCV() {
        self.wishCourseCollectionView.register(UINib(nibName: Const.Xib.Name.courseThumbnailCVC, bundle: nil), forCellWithReuseIdentifier: Const.Xib.Identifier.courseThumbnailCVC)
        self.wishCourseCollectionView.delegate = self
        self.wishCourseCollectionView.dataSource = self
        self.wishCourseCollectionView.isScrollEnabled = true
        
        wishCourseData = CourseThumbnailDataModel.sampleData
    }
    
    private func getWishCourse() {
        print("call getWishCourse!")
        GetWishCourseDataService.shared.getWishCourse(memberId: self.memberId) { response in
            switch response {
            case .success(let wishCourseData):
                if let data = wishCourseData as? [SampleCourseThumbnail] {
//                    for d in data {
//                        print(d)
//                    }
                    self.temp = data
                    self.wishCourseCollectionView.reloadData()
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


// MARK: - UICollectionViewDelegate
extension WishCourseViewController: UICollectionViewDelegate {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
//        if let wishCourseData = wishCourseData {
//            return wishCourseData.count
//        } else {
//            return 0
//        }
        return self.temp.count
        
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        guard let nextVC = UIStoryboard(name: Const.Storyboard.Name.courseInfoView, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.courseInfo) as? CourseInfoViewController else { return }

        nextVC.courseTitle = wishCourseData![indexPath.row].courseTitle
        
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
    }
    
}

// MARK: - UICollectionViewDataSource
extension WishCourseViewController: UICollectionViewDataSource {
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: CourseThumbnailCollectionViewCell.identifier, for: indexPath) as? CourseThumbnailCollectionViewCell else { return UICollectionViewCell() }
        
//        if let wishCourseData = wishCourseData {
//            cell.setData(wishCourseData[indexPath.row])
//        }
        
        
        cell.setTemp(temp[indexPath.row])
        
        return cell
    }
    
    
}

// MARK: - UICollectionViewDelegateFlowLayout
extension WishCourseViewController: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        
        let cellWidth = (UIScreen.main.bounds.width - (10 * 3)) / 2
        let cellHeight = cellWidth

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
