//
//  CourseReviewTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/30.
//

import UIKit
import SnapKit

// MARK: - 리뷰 모두 보기 버튼 Delegate
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
    
    var defaultImageView: UIImageView = UIImageView()
    var defaultImage = UIImage(named: "default_review")
    
    
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
        reviewTitle.layer.drawLineAt(edges: [.bottom], color: UIColor(named: "mainColor") ?? .yellow, width: 5)
        
        allReviewBtn.layer.cornerRadius = 5
        
        contentView.addSubview(defaultImageView)
        
        defaultImageView.image = defaultImage
        defaultImageView.contentMode = .scaleAspectFit
        defaultImageView.snp.makeConstraints {
            $0.edges.equalTo(self.reviewTV.snp.edges)
        }
    }
    
    private func setTV() {
        reviewTV.delegate = self
        reviewTV.dataSource = self
        reviewTV.register(UINib(nibName: Const.Xib.Name.reviewInquiryTVC, bundle: nil), forCellReuseIdentifier: Const.Xib.Identifier.reviewInquiryTVC)
    }
    
    
    // MARK: - setData
    
    func setData(_ data: [CourseReviewDataModel]?) {
        
        if let data = data {
            self.reviewData = data
            
            // 리뷰 데이터가 0개일 때 버튼 처리
            if reviewData.count == 0 {
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
