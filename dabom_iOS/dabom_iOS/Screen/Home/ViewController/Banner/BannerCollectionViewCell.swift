//
//  BannerCollectionViewCell.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/06.
//

import UIKit
import Kingfisher

class BannerCollectionViewCell: UICollectionViewCell {

    // MARK: - IBOutlet
    
    @IBOutlet weak var bannerImageView: UIImageView!
    
    
    // MARK: - Life Cycle
    
    override func awakeFromNib() {
        super.awakeFromNib()

    }
    
    
    // MARK: - func
    
    func setData(bannerData: BannerDataModel) {
        bannerImageView.kf.indicatorType = .activity
        bannerImageView.setImage(with: bannerData.bannerImage.mediumFilePath)
    }

}
