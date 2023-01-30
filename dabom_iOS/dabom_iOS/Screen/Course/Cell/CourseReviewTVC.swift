//
//  CourseReviewTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/30.
//

import UIKit

protocol allReviewBtnDelegate {
    func allReviewBtnPressed()
}

class CourseReviewTVC: UITableViewCell {

    // MARK: - IBOutlet
    @IBOutlet weak var reviewTitle: UILabel!
    
    @IBOutlet weak var reviewTV: UITableView!
    
    @IBOutlet weak var allReviewBtn: UIButton!
    
    // MARK: - var, let
    var delegate: allReviewBtnDelegate?
    
    var reviewData: [CourseReviewDataModel] = []
    
    // MARK: - Life Cycle
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        reviewTitle.layer.drawLineAt(edges: [.bottom], color: UIColor(named: "mainColor") ?? .yellow, width: 5)
        
        allReviewBtn.layer.cornerRadius = 5
        
        reviewTV.delegate = self
        reviewTV.dataSource = self
        reviewTV.register(UINib(nibName: Const.Xib.Name.reviewInquiryTVC, bundle: nil), forCellReuseIdentifier: Const.Xib.Identifier.reviewInquiryTVC)

    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    // MARK: - setData
    func setData(_ data: [CourseReviewDataModel]?) {
        
        if let data = data {
            self.reviewData = data
        }
    }
    
    // MARK: - IBAction
    @IBAction func allBtnPressed(_ sender: Any) {
        delegate?.allReviewBtnPressed()
    }
    
}

// MARK: - extension
extension CourseReviewTVC: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // 데이터가 3개 이하면 데이터 갯수만큼 보여주기
        if self.reviewData.count < 4 {
            return self.reviewData.count
        } else {
            // 데이터가 4개 이상이면 3개만 보여주기
            return 3
        }
        
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        150
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = reviewTV.dequeueReusableCell(withIdentifier: Const.Xib.Identifier.reviewInquiryTVC, for: indexPath) as? ReviewInquiryTVC else {return UITableViewCell()}
        cell.setReviewData(self.reviewData[indexPath.row])
        cell.selectionStyle = .none
        
        return cell
    }
    
    
}
