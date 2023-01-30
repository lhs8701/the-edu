//
//  CourseReviewTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/30.
//

import UIKit

class CourseReviewTVC: UITableViewCell {

    @IBOutlet weak var reviewTitle: UILabel!
    
    @IBOutlet weak var reviewTV: UITableView!
    
    @IBOutlet weak var allReviewBtn: UIButton!
    
    var reviewData: [CourseReviewDataModel]!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        reviewTitle.layer.drawLineAt(edges: [.bottom], color: UIColor(named: "mainColor") ?? .yellow, width: 5)
        
//        firstContentView.layer.drawLineAt(edges: [.top], color: .lightGray, width: 0.5)
//        [firstContentView, secondContentView, thirdContentView].forEach {
//            $0?.layer.drawLineAt(edges: [.bottom], color: .lightGray, width: 0.5)
//        }
        allReviewBtn.layer.cornerRadius = 5
        
        reviewTV.delegate = self
        reviewTV.dataSource = self
        reviewTV.register(UINib(nibName: "ReviewInquiryTVC", bundle: nil), forCellReuseIdentifier: "ReviewInquiryTVC")

    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func setData(_ data: [CourseReviewDataModel]?) {
        
        if let data = data {
            self.reviewData = data
        }
    }
}

extension CourseReviewTVC: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        self.reviewData?.count ?? 0
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        150
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = reviewTV.dequeueReusableCell(withIdentifier: "ReviewInquiryTVC", for: indexPath) as? ReviewInquiryTVC else {return UITableViewCell()}
        cell.setData(data: self.reviewData[indexPath.row])
        
        return cell
    }
    
    
}
