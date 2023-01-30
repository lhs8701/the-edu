//
//  CourseReviewAllVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/30.
//

import UIKit

class CourseReviewAllVC: UIViewController {

    // MARK: - IBOutlet
    @IBOutlet weak var reviewTV: UITableView!
    
    // MARK: - var, let
    var reviewData: [CourseReviewDataModel] = []
    
    // MARK: - Life Cycle
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        setTV()
    }
    
    // MARK: - setTV
    private func setTV() {
        reviewTV.delegate = self
        reviewTV.dataSource = self
        reviewTV.register(UINib(nibName: Const.Xib.Name.reviewInquiryTVC, bundle: nil), forCellReuseIdentifier: Const.Xib.Identifier.reviewInquiryTVC)
    }
    
    // MARK: - setData
    func setData(_ data: [CourseReviewDataModel]?) {
        if let data = data {
            self.reviewData = data
        }
    }

}

// MARK: - extension
extension CourseReviewAllVC: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        self.reviewData.count
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
