//
//  EventTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/10.
//

import UIKit

class EventTVC: UITableViewCell {
    
    // MARK: - IBOutlet
    @IBOutlet weak var eventThumbnail: UIImageView!
    
    
    // MARK: - Life Cycle
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        // Configure the view for the selected state
    }
    
    // MARK: - Data Set
    func setData(eventData: BannerDataModel) {
        eventThumbnail.setImage(with: eventData.bannerImage.mediumFilePath)
    }
    
}
