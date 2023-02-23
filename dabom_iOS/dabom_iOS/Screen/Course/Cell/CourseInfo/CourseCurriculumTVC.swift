//
//  CourseCurriculumTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/09.
//

import UIKit

// MARK: - 커리큘럼 보기 버튼 Delegate 패턴
protocol allCurriculumBtnDelegate {
    func allCurriculumBtnPressed()
}

class CourseCurriculumTVC: UITableViewCell {

    // MARK: - IBOutlet
    
    @IBOutlet weak var curriculumTitle: UILabel!
    @IBOutlet weak var allCurriculumBtn: UIButton!
    
    
    // MARK: - let, var
    
    var delegate: allCurriculumBtnDelegate?
    
    
    // MARK: - Life Cycle
    
    override func awakeFromNib() {
        super.awakeFromNib()

        curriculumTitle.layer.drawLineAt(edges: [.bottom], color: UIColor(named: "mainColor") ?? .yellow, width: 5)
        
        allCurriculumBtn.layer.cornerRadius = 5
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

    }
    
    
    // MARK: - 커리큘럼 보기 버튼 눌렀을 때 Delegate 패턴
    
    @IBAction func allCurriculumBtnPressed(_ sender: Any) {
        delegate?.allCurriculumBtnPressed()
    }
    
}
