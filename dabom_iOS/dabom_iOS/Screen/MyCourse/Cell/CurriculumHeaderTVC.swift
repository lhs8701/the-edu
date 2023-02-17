//
//  CurriculumHeaderTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/07.
//

import UIKit

class CurriculumHeaderTVC: UITableViewHeaderFooterView {

    @IBOutlet weak var chapterTitle: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        chapterTitle.adjustsFontSizeToFitWidth = true
    }

    override func prepareForReuse() {
        super.prepareForReuse()
    }
    
}
