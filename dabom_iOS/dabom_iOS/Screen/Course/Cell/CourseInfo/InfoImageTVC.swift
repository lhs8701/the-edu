//
//  InfoImageTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/16.
//

import UIKit

class InfoImageTVC: UITableViewCell {

    // MARK: - IBOutlet
    
    @IBOutlet weak var infoImageView: UIImageView!
    
    
    // MARK: - let, var
    
    var infoImage: UIImage?
    
    
    // MARK: - Life Cycle
    
    override func awakeFromNib() {
        super.awakeFromNib()

    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

    }
    
}
