//
//  CourseCurriculumTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/09.
//

import UIKit

protocol allCurriculumBtnDelegate {
    func allCurriculumBtnPressed()
}

class CourseCurriculumTVC: UITableViewCell {

    
    @IBOutlet weak var curriculumTitle: UILabel!
    
    @IBOutlet weak var allCurriculumBtn: UIButton!
    
    var delegate: allCurriculumBtnDelegate?
    
    override func awakeFromNib() {
        super.awakeFromNib()

        curriculumTitle.layer.drawLineAt(edges: [.bottom], color: UIColor(named: "mainColor") ?? .yellow, width: 5)
        
        allCurriculumBtn.layer.cornerRadius = 5
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    @IBAction func allCurriculumBtnPressed(_ sender: Any) {
        
        delegate?.allCurriculumBtnPressed()
    }
}
