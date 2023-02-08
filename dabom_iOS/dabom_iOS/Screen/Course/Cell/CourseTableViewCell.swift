//
//  CourseTableViewCell.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/05.
//

import UIKit

protocol CourseCVCellDelegate {
    func CourseSelectedCVCell(courseId: Int, courseName: String)
}

class CourseTableViewCell: UITableViewCell {

    // MARK: - IBOutlet
    @IBOutlet weak var rankingCategoryTitle: UILabel!
    @IBOutlet weak var courseThumbnailCollectionView: UICollectionView!
    
    
    // MARK: - let, var
    static let identifier = "CourseTableViewCell"
    var thumbnailData: [SampleCourseThumbnail] = []
    var delegate: CourseCVCellDelegate?
    

    override func awakeFromNib() {
        super.awakeFromNib()
        
        setCV()
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

    }
    
    override func prepareForReuse() {
        super.prepareForReuse()
        
        rankingCategoryTitle.text = nil
        thumbnailData = [SampleCourseThumbnail]()
        courseThumbnailCollectionView.reloadData()
    }
    
    // MARK: - CollectionView Setting
    private func setCV() {
        self.courseThumbnailCollectionView.register(UINib(nibName: Const.Xib.Name.courseThumbnailCVC, bundle: nil), forCellWithReuseIdentifier: Const.Xib.Name.courseThumbnailCVC)
        self.courseThumbnailCollectionView.delegate = self
        self.courseThumbnailCollectionView.dataSource = self
        self.courseThumbnailCollectionView.isScrollEnabled = false
    }
    
    // MARK: - 랭킹별 강좌 Setting
    func setData(courseRankingData: CourseRankingDataModel) {
        rankingCategoryTitle.text = "\(courseRankingData.category) 클래스 랭킹"
        thumbnailData = courseRankingData.courseList
    }
}

// MARK: - UICollectionViewDelegate
extension CourseTableViewCell: UICollectionViewDelegate {
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.thumbnailData.count
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        if let delegate = delegate {
            delegate.CourseSelectedCVCell(courseId: thumbnailData[indexPath.row].courseId, courseName: thumbnailData[indexPath.row].title)
        }
    }
}


// MARK: - UICollectionViewDataSource
extension CourseTableViewCell: UICollectionViewDataSource {

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: CourseThumbnailCollectionViewCell.identifier, for: indexPath) as? CourseThumbnailCollectionViewCell else { return UICollectionViewCell() }

        cell.setTemp(thumbnailData[indexPath.row])
        
        return cell
    }
    
    
}

// MARK: - UICollectionViewDelegateFlowLayout
extension CourseTableViewCell: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        
        let cellWidth = (UIScreen.main.bounds.width - (20 * 3)) / 2
        let cellHeight = cellWidth
        
        return CGSize(width: cellWidth, height: cellHeight)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {
        
        return UIEdgeInsets(top: 3, left: 3, bottom: 3, right: 3)
  }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumInteritemSpacingForSectionAt section: Int) -> CGFloat {
        let spacingSize = 5
        
        return CGFloat(spacingSize)
    }
}
