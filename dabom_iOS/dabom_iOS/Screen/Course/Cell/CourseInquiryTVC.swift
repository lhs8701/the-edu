//
//  CourseInquiryTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/30.
//

import UIKit

protocol allInquiryBtnDelegate {
    func allInquiryBtnPressed()
}

class CourseInquiryTVC: UITableViewCell {
    
    // MARK: - IBOutlet
    @IBOutlet weak var inquiryTitle: UILabel!
    
    @IBOutlet weak var inquiryTV: UITableView!
    
    @IBOutlet weak var allReviewBtn: UIButton!
  
    // MARK: - var, let
    var delegate: allInquiryBtnDelegate?
    
    var inquiryData: [CourseInquiryDataModel] = []
    
    // MARK: - Life Cycle
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        inquiryTitle.layer.drawLineAt(edges: [.bottom], color: UIColor(named: "mainColor") ?? .yellow, width: 5)
        
        allReviewBtn.layer.cornerRadius = 5
        
        inquiryTV.delegate = self
        inquiryTV.dataSource = self
        inquiryTV.register(UINib(nibName: "ReviewInquiryTVC", bundle: nil), forCellReuseIdentifier: "ReviewInquiryTVC")

    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    // MARK: - setData
    func setData(_ data: [CourseInquiryDataModel]?) {
        
        if let data = data {
            self.inquiryData = data
        }
    }
    
    // MARK: - IBAction
    @IBAction func allBtnPressed(_ sender: UIButton) {
        delegate?.allInquiryBtnPressed()
    }
    
    
}

// MARK: - extension
extension CourseInquiryTVC: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // 데이터가 3개 이하면 갯수만큼 보여주기
        if self.inquiryData.count < 4 {
            return self.inquiryData.count
        } else {
            // 데이터가 4개 이상이면 3개만 보여주기
            return 3
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        150
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = inquiryTV.dequeueReusableCell(withIdentifier: Const.Xib.Identifier.reviewInquiryTVC, for: indexPath) as? ReviewInquiryTVC else {return UITableViewCell()}
        cell.setInquiryData(self.inquiryData[indexPath.row])
        cell.selectionStyle = .none
        
        return cell
    }
    
    
}
