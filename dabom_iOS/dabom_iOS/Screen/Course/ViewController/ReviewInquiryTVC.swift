//
//  ReviewInquiryTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/30.
//

import UIKit

class ReviewInquiryTVC: UITableViewCell {
    
    
    @IBOutlet weak var writerLabel: UILabel!
    @IBOutlet weak var contentLabel: UILabel!
    
    var reviewData: CourseReviewDataModel?
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func setData(data: CourseReviewDataModel) {
        self.writerLabel.text = data.writer
        self.contentLabel.text = data.content
    }
    
}
