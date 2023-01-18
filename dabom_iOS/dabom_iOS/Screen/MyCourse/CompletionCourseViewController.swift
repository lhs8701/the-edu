//
//  CompletionCourseViewController.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/04.
//

import UIKit

class CompletionCourseViewController: UIViewController {

    @IBOutlet weak var completionCourseCV: UICollectionView!
    
    var completionCourseData: Array<MyCourseDataModel>?
    
    // MARK: - Life Cycle
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        setCV()
    }
    
    // MARK: - func
    private func setCV() {
        self.completionCourseCV.register(UINib(nibName: "MyCourseCVC", bundle: nil), forCellWithReuseIdentifier: "MyCourseCVC")
        self.completionCourseCV.delegate = self
        self.completionCourseCV.dataSource = self
        self.completionCourseCV.isScrollEnabled = true
        
        completionCourseData = MyCourseDataModel.sampleData
    }
}

extension CompletionCourseViewController: UICollectionViewDelegate {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        if let completionCourseData = completionCourseData {
            return completionCourseData.count
        } else {
            return 0
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        guard let nextVC = UIStoryboard(name: Const.Storyboard.Name.courseInfoView, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.courseInfo) as? CourseInfoViewController else { return }

        nextVC.courseTitle = completionCourseData![indexPath.row].courseTitle
//        print(courseName)
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
//        self.present(nextVC, animated: true)
    }
}

extension CompletionCourseViewController: UICollectionViewDataSource {
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: MyCourseCVC.identifier, for: indexPath) as? MyCourseCVC else { return UICollectionViewCell() }
        
        if let completionCourseData = completionCourseData {
            cell.setData(completionCourseData[indexPath.row])
        }
        
        return cell
    }
}

extension CompletionCourseViewController: UICollectionViewDelegateFlowLayout {
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

