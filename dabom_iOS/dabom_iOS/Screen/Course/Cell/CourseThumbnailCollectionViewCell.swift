//
//  CourseThumbnailCollectionViewCell.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/05.
//

import UIKit

class CourseThumbnailCollectionViewCell: UICollectionViewCell {
    
    static let identifier = "CourseThumbnailCollectionViewCell"
    
    @IBOutlet weak var thumbnailImage: UIImageView!
    @IBOutlet weak var courseTitle: UILabel!
    @IBOutlet weak var creatorName: UILabel!
    @IBOutlet weak var categoryName: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
//        self.layer.cornerRadius = 7
//        self.clipsToBounds = true
        layer.masksToBounds = false
        layer.shadowColor = UIColor.black.cgColor
        layer.shadowOpacity = 0.5
        layer.shadowRadius = 2
        layer.shadowOffset = CGSize(width: 0, height: 0)

        contentView.layer.cornerRadius = 7
        contentView.layer.masksToBounds = true
    }
    
    
    func setData(_ thumbnailData: CourseThumbnailDataModel) {
        thumbnailImage.image = thumbnailData.thumbnailImage
        courseTitle.text = thumbnailData.courseTitle
        creatorName.text = thumbnailData.creatorName
        categoryName.text = thumbnailData.categoryName
        
        
    }
    
    func setTemp(_ thumbnailData: SampleCourseThumbnail) {
//        thumbnailImage.image = UIImage(named: "testThumb01")
        thumbnailImage.setImage(with: thumbnailData.thumbnailImage.mediumFilePath)
        courseTitle.text = thumbnailData.title
        creatorName.text = thumbnailData.instructor
        
    }
}
