//
//  CourseInfoTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/15.
//

import UIKit

class CourseInfoTVC: UITableViewCell {

    @IBOutlet weak var classTitle: UILabel!
    
    @IBOutlet weak var shortIntro: UILabel!
    
    @IBOutlet weak var creatorName: UILabel!
    
    
    
    
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        setLabel()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    private func setLabel() {
        let attrString = NSMutableAttributedString(string: shortIntro.text ?? "")
        let paragraphStyle = NSMutableParagraphStyle()
        paragraphStyle.lineSpacing = 4
        attrString.addAttribute(NSAttributedString.Key.paragraphStyle, value: paragraphStyle, range: NSMakeRange(0, attrString.length))
        shortIntro.attributedText = attrString
    }
    
}
