//
//  BannerCollectionViewCell.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/06.
//

import UIKit

class BannerCollectionViewCell: UICollectionViewCell {

    @IBOutlet weak var bannerImageView: UIImageView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }
    
    func setData(bannerData: BannerDataModel) {
        bannerImageView.setImage(with: bannerData.bannerImage.mediumFilePath)
    }

}
