//
//  CourseThumbnailCollectionViewCell.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/05.
//

import UIKit

class CourseThumbnailCollectionViewCell: UICollectionViewCell {
    
    // MARK: - IBOutlet
    
    @IBOutlet weak var thumbnailImage: UIImageView!
    @IBOutlet weak var courseTitle: UILabel!
    @IBOutlet weak var creatorName: UILabel!
    @IBOutlet weak var categoryName: UILabel!
    
    
    // MARK: - let, var
    
    static let identifier = "CourseThumbnailCollectionViewCell"
    
    
    // MARK: - Life Cycle
    
    override func awakeFromNib() {
        super.awakeFromNib()

        configureView()
    }
    
    override func prepareForReuse() {
        super.prepareForReuse()
        
    }
    
    
    // MARK: - configure
    
    private func configureView() {
        // 그림자, 둥근 모서리 설정
        layer.masksToBounds = false
        layer.shadowColor = UIColor.black.cgColor
        layer.shadowOpacity = 0.5
        layer.shadowRadius = 2
        layer.shadowOffset = CGSize(width: 0, height: 0)
        
        courseTitle.adjustsFontSizeToFitWidth = true
        
        contentView.layer.cornerRadius = 7
        contentView.layer.masksToBounds = true
    }
    
    
    // MARK: - func
    
    func setData(_ thumbnailData: CourseThumbnailDataModel) {
        thumbnailImage.image = thumbnailData.thumbnailImage
        courseTitle.text = thumbnailData.courseTitle
        creatorName.text = thumbnailData.creatorName
        categoryName.text = thumbnailData.categoryName
        
        
    }
    
    func setTemp(_ thumbnailData: SampleCourseThumbnail) {
        thumbnailImage.setImage(with: thumbnailData.thumbnailImage.mediumFilePath)
        courseTitle.text = thumbnailData.title
        creatorName.text = thumbnailData.instructor
        categoryName.text = thumbnailData.category
        
    }
}
