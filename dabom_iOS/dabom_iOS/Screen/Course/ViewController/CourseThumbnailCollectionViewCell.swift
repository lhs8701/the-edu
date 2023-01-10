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
        layer.cornerRadius = 7
    }
    
    
    func setData(_ thumbnailData: CourseThumbnailDataModel) {
        thumbnailImage.image = thumbnailData.thumbnailImage
        courseTitle.text = thumbnailData.courseTitle
        creatorName.text = thumbnailData.creatorName
        categoryName.text = thumbnailData.categoryName
    }
}
