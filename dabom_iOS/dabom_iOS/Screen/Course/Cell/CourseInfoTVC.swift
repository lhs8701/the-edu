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
    @IBOutlet var costHidden: [UILabel]!
    
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
        
        // 결제 구현 전까지 가격에 대한 정보 Hidden
        for label in costHidden {
            label.isHidden = true
        }
        
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
    
    // MARK: - 신청한 강좌인지 확인
    func setStatus(isEnroll: Bool?, isCharge: Bool?) {
        guard let isEnroll = isEnroll else {return}
        guard let isCharge = isCharge else {return}
        
        if isCharge {
            self.courseEnrollBtn.isEnabled = false
            self.courseEnrollBtn.setTitle("유료 강좌는 웹에서 구매 가능합니다", for: .normal)
            self.courseEnrollBtn.setTitleColor(.white, for: .normal)
            self.courseEnrollBtn.backgroundColor = .darkGray
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
    
    @IBAction func samplePlayBtnPressed(_ sender: Any) {
        
        if let delegate = delegate {
            delegate.CourseSamplePlay()
        }
        
    }
    
}
