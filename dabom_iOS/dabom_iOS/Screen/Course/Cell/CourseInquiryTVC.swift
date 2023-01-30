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

    @IBOutlet weak var inquiryTitle: UILabel!
    
    @IBOutlet weak var inquiryTV: UITableView!
    
    @IBOutlet weak var allReviewBtn: UIButton!
    
    var delegate: allInquiryBtnDelegate?
    
    var inquiryData: [CourseInquiryDataModel] = []
    
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
    
    func setData(_ data: [CourseInquiryDataModel]?) {
        
        if let data = data {
            self.inquiryData = data
        }
    }
    
    @IBAction func allBtnPressed(_ sender: UIButton) {
        delegate?.allInquiryBtnPressed()
    }
    
    
}

extension CourseInquiryTVC: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if self.inquiryData.count < 4 {
            return self.inquiryData.count
        } else {
            return 3
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        150
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = inquiryTV.dequeueReusableCell(withIdentifier: "ReviewInquiryTVC", for: indexPath) as? ReviewInquiryTVC else {return UITableViewCell()}
        cell.setInquiryData(self.inquiryData[indexPath.row])
        cell.selectionStyle = .none
        
        return cell
    }
    
    
}
