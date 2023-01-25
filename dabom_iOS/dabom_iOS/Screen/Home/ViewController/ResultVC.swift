//
//  CategoryResultVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/12.
//

import UIKit

class ResultVC: UIViewController {
    
    @IBOutlet weak var resultCV: UICollectionView!
    
    @IBOutlet weak var resultName: UILabel!
    
    @IBOutlet weak var resultKind: UILabel!
    
    
    var resultTitle: String?
    var kind: String?
    
    var resultData: Array<CourseThumbnailDataModel>?
    
    // MARK: - Life Cycle
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setCV()
        
        self.resultName.text = (self.resultTitle ?? "")
        self.resultKind.text = (self.kind ?? "")
        
        self.resultName.sizeToFit()
        self.resultName.layer.drawLineAt(edges: [.bottom], color: UIColor(named: "mainColor")!, width: 3.0)
        
        
        self.navigationController?.navigationBar.topItem?.backButtonTitle = ""

    }
    
    
    // MARK: - CollectionView Setting
    private func setCV() {
        self.resultCV.register(UINib(nibName: Const.Xib.Name.courseThumbnailCVC, bundle: nil), forCellWithReuseIdentifier: Const.Xib.Identifier.courseThumbnailCVC)
        self.resultCV.delegate = self
        self.resultCV.dataSource = self
        self.resultCV.isScrollEnabled = true
        
        resultData = CourseThumbnailDataModel.sampleData
    }
    
    
}

extension ResultVC: UICollectionViewDelegate {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        if let categoryResultData = resultData {
            return categoryResultData.count
        } else {
            return 0
        }
        
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        guard let nextVC = UIStoryboard(name: Const.Storyboard.Name.courseInfoView, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.courseInfo) as? CourseInfoViewController else { return }

        nextVC.courseTitle = resultData![indexPath.row].courseTitle
//        print(courseName)
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
//        self.present(nextVC, animated: true)
    }
}

extension ResultVC: UICollectionViewDataSource {
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: CourseThumbnailCollectionViewCell.identifier, for: indexPath) as? CourseThumbnailCollectionViewCell else { return UICollectionViewCell() }
        
        if let categoryResultData = resultData {
            cell.setData(categoryResultData[indexPath.row])
        }
        
        return cell
//        return UICollectionViewCell()
        
//        cell.setData(CourseThumbnailDataModel.sampleData[indexPath.row])
//        return cell
    }
}

extension ResultVC: UICollectionViewDelegateFlowLayout {
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
