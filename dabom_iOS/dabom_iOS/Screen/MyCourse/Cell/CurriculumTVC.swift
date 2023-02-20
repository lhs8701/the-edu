//
//  CurriculumTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/07.
//

import UIKit

class CurriculumTVC: UITableViewCell {

    // MARK: - IBOutlet
    
    @IBOutlet weak var curriculumTitle: UILabel!
    
    
    // MARK: - Life Cycle
    
    override func awakeFromNib() {
        super.awakeFromNib()

        // Label 크기에 맞추어서 글자 크기 줄이기
        curriculumTitle.adjustsFontSizeToFitWidth = true
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

    }
    
    override func prepareForReuse() {
        super.prepareForReuse()
        
        self.contentView.backgroundColor = .white
    }
    
}
