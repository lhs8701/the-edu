//
//  CurriculumTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/07.
//

import UIKit

class CurriculumTVC: UITableViewCell {

    @IBOutlet weak var curriculumTitle: UILabel!
    
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        curriculumTitle.adjustsFontSizeToFitWidth = true
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    override func prepareForReuse() {
        super.prepareForReuse()
        
        self.contentView.backgroundColor = .white
    }
    
}
