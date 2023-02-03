//
//  CourseInfoTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/15.
//

import UIKit

protocol CourseEnrollBtnDelegate {
    func CourseEnroll()
}

class CourseInfoTVC: UITableViewCell {
    
    // MARK: - IBOutlet
    @IBOutlet weak var classTitle: UILabel!
    @IBOutlet weak var courseDescription: UILabel!
    @IBOutlet weak var instructor: UILabel!
    @IBOutlet weak var courseThumbnailImageView: UIImageView!
    @IBOutlet weak var courseEnrollBtn: UIButton!
    
    // MARK: - let, var
    var delegate: CourseEnrollBtnDelegate?
    
    var isEnroll: Bool!
    
    // MARK: - Life Cycle
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
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
    
    // MARK: - 신청한 강좌인지 확인
    func setEnroll(_ data: Bool?) {
        if let data = data {
            self.isEnroll = data
        }
        
        if isEnroll {
            self.courseEnrollBtn.isEnabled = false
            self.courseEnrollBtn.setTitle("이미 신청한 강좌입니다", for: .normal)
            self.courseEnrollBtn.setTitleColor(.white, for: .normal)
            self.courseEnrollBtn.backgroundColor = .darkGray
        }
    }
    
    
    // MARK: - 수강 신청 버튼 눌렀을 때 delegate 패턴
    @IBAction func courseEnrollBtnPressed(_ sender: Any) {
        
        if let delegate = delegate {
            delegate.CourseEnroll()
        }
        
    }
}
