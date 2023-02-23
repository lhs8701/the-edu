//
//  CourseInquiryTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/30.
//

import UIKit
import SnapKit

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
    
    var defaultImageView: UIImageView = UIImageView()
    var defaultImage = UIImage(named: "default_inquiry")
    
    // MARK: - Life Cycle
    override func awakeFromNib() {
        super.awakeFromNib()
        
        configureView()
        setTV()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

    }
    
    private func configureView() {
        inquiryTitle.layer.drawLineAt(edges: [.bottom], color: UIColor(named: "mainColor") ?? .yellow, width: 5)
        
        allReviewBtn.layer.cornerRadius = 5
        
        contentView.addSubview(defaultImageView)
        
        defaultImageView.image = defaultImage
        defaultImageView.contentMode = .scaleAspectFit
        defaultImageView.snp.makeConstraints {
            $0.edges.equalTo(self.inquiryTV.snp.edges)
        }
    }
    
    private func setTV() {
        inquiryTV.delegate = self
        inquiryTV.dataSource = self
        inquiryTV.register(UINib(nibName: Const.Xib.Name.reviewInquiryTVC, bundle: nil), forCellReuseIdentifier: Const.Xib.Identifier.reviewInquiryTVC)
    }
    
    
    // MARK: - setData
    
    func setData(_ data: [CourseInquiryDataModel]?) {
        
        if let data = data {
            self.inquiryData = data
            
            // 문의사항 데이터가 0개일 때
            if inquiryData.count == 0 {
                defaultImageView.isHidden = false
                allReviewBtn.isEnabled = false
                allReviewBtn.setTitleColor(.white, for: .normal)
                allReviewBtn.backgroundColor = .lightGray
            } else {
                defaultImageView.isHidden = true
            }
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
