//
//  ReviewInquiryTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/30.
//

import UIKit

class ReviewInquiryTVC: UITableViewCell {
    
    // MARK: - IBOutlet
    
    @IBOutlet weak var profileImageView: UIImageView!
    @IBOutlet weak var writerLabel: UILabel!
    @IBOutlet weak var contentLabel: UILabel!
    
    
    // MARK: - var, let
    
    var reviewData: CourseReviewDataModel?
    
    
    // MARK: - Life Cycle
    
    override func awakeFromNib() {
        super.awakeFromNib()

        profileImageView.layer.cornerRadius = profileImageView.frame.width / 2
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

    }
    
    
    // MARK: - setData
    
    // 리뷰일 때 리뷰 데이터 세팅
    func setReviewData(_ data: CourseReviewDataModel) {
        self.profileImageView.setImage(with: data.writer.profileImage.smallFilePath)
        print(data.writer.profileImage.smallFilePath)
        self.writerLabel.text = data.writer.nickname
        self.contentLabel.text = data.content
    }
    
    // 문의일 때 문의 데이터 세팅
    func setInquiryData(_ data: CourseInquiryDataModel) {
        self.profileImageView.setImage(with: data.writer.profileImage.smallFilePath)
        self.writerLabel.text = data.writer.nickname
        self.contentLabel.text = data.content
    }
    
}
