//
//  CourseInfoTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/15.
//

import UIKit
import SnapKit

protocol CourseEnrollBtnDelegate {
    func CourseEnroll()
    func CourseSamplePlay()
}

class CourseInfoTVC: UITableViewCell {
    
    // MARK: - IBOutlet
    
    @IBOutlet weak var classTitle: UILabel!
    @IBOutlet weak var courseDescription: UILabel!
    @IBOutlet weak var instructor: UILabel!
    @IBOutlet weak var courseThumbnailImageView: UIImageView!
    
    // MARK: - let, var
    var delegate: CourseEnrollBtnDelegate?
    
    var isEnroll: Bool!
    
    // MARK: - Life Cycle
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        courseDescription.snp.updateConstraints {
            $0.trailing.equalToSuperview().inset(30)
        }
        
        setLabel()
        
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    // MARK: - setLabel
    private func setLabel() {
        let attrString = NSMutableAttributedString(string: courseDescription.text ?? "")
        let paragraphStyle = NSMutableParagraphStyle()
        paragraphStyle.lineSpacing = 4
        attrString.addAttribute(NSAttributedString.Key.paragraphStyle, value: paragraphStyle, range: NSMakeRange(0, attrString.length))
        courseDescription.attributedText = attrString
    }
    
    // MARK: - 샘플 강의 재생 버튼 눌렀을 때 delegate 패턴
    @IBAction func samplePlayBtnPressed(_ sender: Any) {
        
        if let delegate = delegate {
            delegate.CourseSamplePlay()
        }
        
    }
    
}
