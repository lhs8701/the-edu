//
//  CurriculumHeaderTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/07.
//

import UIKit

class CurriculumHeaderTVC: UITableViewHeaderFooterView {

    // MARK: - IBOutlet
    
    @IBOutlet weak var chapterTitle: UILabel!
    
    
    // MARK: - Life Cycle
    
    override func awakeFromNib() {
        super.awakeFromNib()

        // Label 크기에 맞추어서 글자 크기 줄이기
        chapterTitle.adjustsFontSizeToFitWidth = true
    }

    override func prepareForReuse() {
        super.prepareForReuse()
    }
    
}
