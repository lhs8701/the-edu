//
//  InCourseViewController.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/04.
//

import UIKit

class InCourseViewController: UIViewController {

    @IBOutlet weak var inCourseCV: UICollectionView!
    
    var inCourseData: Array<MyCourseDataModel>?
    
    // MARK: - Life Cycle
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        setCV()
    }
    
    // MARK: - func
    private func setCV() {
        self.inCourseCV.register(UINib(nibName: Const.Xib.Name.myCourseCVC, bundle: nil), forCellWithReuseIdentifier: Const.Xib.Identifier.myCourseCVC)
        self.inCourseCV.delegate = self
        self.inCourseCV.dataSource = self
        self.inCourseCV.isScrollEnabled = true
        
        inCourseData = MyCourseDataModel.sampleData
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
//        guard let nextVC = UIStoryboard(name: Const.Storyboard.Name.courseInfoView, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.courseInfo) as? CourseInfoViewController else { return }
        guard let nextVC = UIStoryboard(name: Const.Storyboard.Name.myCourseTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.coursePlayerVC) as? CoursePlayerVC else { return }
//        nextVC.courseTitle = inCourseData![indexPath.row].courseTitle
        nextVC.unitId = 6
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
