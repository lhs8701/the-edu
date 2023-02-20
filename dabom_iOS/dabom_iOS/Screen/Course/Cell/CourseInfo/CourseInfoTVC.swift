//
//  CourseInfoTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/15.
//

import UIKit
import SnapKit

// MARK: - 수강 신청하기 버튼 Delegate 패턴
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
    @IBOutlet weak var courseEnrollBtn: UIButton!
    
    
    // MARK: - let, var
    
    var delegate: CourseEnrollBtnDelegate?
    
    var isEnroll: Bool!
    
    
    // MARK: - Life Cycle
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        setLabel()
        
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

    }
    
    
    // MARK: - setLabel
    
    private func setLabel() {
        // Label 크기에 맞추어서 글자 크기 줄이기
        classTitle.adjustsFontSizeToFitWidth = true
        instructor.adjustsFontSizeToFitWidth = true
        
        // Line Spacing 설정
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
            // 유료 강좌면 신청하기 버튼 hidden
            self.courseEnrollBtn.isHidden = true
        } else {
            self.courseEnrollBtn.isHidden = false
        }
        
        // 이미 신청한 강좌일 때 버튼 설정
        if isEnroll {
            self.courseEnrollBtn.isHidden = false
            self.courseEnrollBtn.isEnabled = false
            self.courseEnrollBtn.setTitle("이미 수강 중인 강좌입니다", for: .normal)
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
    
    
    
    // MARK: - 샘플 강의 재생 버튼 눌렀을 때 delegate 패턴
    
    @IBAction func samplePlayBtnPressed(_ sender: Any) {
        
        if let delegate = delegate {
            delegate.CourseSamplePlay()
        }
        
    }
    
}
