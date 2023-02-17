//
//  InfoImageTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/16.
//

import UIKit

class InfoImageTVC: UITableViewCell {

    @IBOutlet weak var infoImageView: UIImageView!
    var infoImage: UIImage?
    
    override func awakeFromNib() {
        super.awakeFromNib()

    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)


    }
    
}
