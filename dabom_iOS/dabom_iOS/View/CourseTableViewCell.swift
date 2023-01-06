//
//  CourseTableViewCell.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/05.
//

import UIKit

class CourseTableViewCell: UITableViewCell {
    
    static let identifier = "CourseTableViewCell"
    
    var thumbnailData: Array<CourseThumbnailDataModel>?

    @IBOutlet weak var rankingCategoryTitle: UILabel!
    @IBOutlet weak var courseThumbnailCollectionView: UICollectionView!
    

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        setCV()
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    private func setCV() {
        self.courseThumbnailCollectionView.register(UINib(nibName: "CourseThumbnailCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: "CourseThumbnailCollectionViewCell")
        self.courseThumbnailCollectionView.delegate = self
        self.courseThumbnailCollectionView.dataSource = self
        self.courseThumbnailCollectionView.isScrollEnabled = false
    }
    
    func setData(_ courseTableData: CourseTableDataModel) {
        rankingCategoryTitle.text = courseTableData.rankingCategoryTitle
        thumbnailData = courseTableData.thumbnailData
    }
}

// MARK: - UICollectionViewDelegate
extension CourseTableViewCell: UICollectionViewDelegate {
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return 4
    }
}


// MARK: - UICollectionViewDataSource
extension CourseTableViewCell: UICollectionViewDataSource {

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: CourseThumbnailCollectionViewCell.identifier, for: indexPath) as? CourseThumbnailCollectionViewCell else { return UICollectionViewCell() }
        cell.setData(thumbnailData![indexPath.row])
        
        return cell
    }
    
    
}

// MARK: - UICollectionViewDelegateFlowLayout
extension CourseTableViewCell: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let cellWidth = 170
        let cellHeight = 170
        
        return CGSize(width: cellWidth, height: cellHeight)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {
        
        return UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 0)
  }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumInteritemSpacingForSectionAt section: Int) -> CGFloat {
        let spacingSize = 3
        
        return CGFloat(spacingSize)
    }
}
