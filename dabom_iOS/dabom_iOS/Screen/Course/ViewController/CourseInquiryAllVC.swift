//
//  CourseInquiryAllVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/30.
//

import UIKit

class CourseInquiryAllVC: UIViewController {

    @IBOutlet weak var inquiryTV: UITableView!
    
    var inquiryData: [CourseInquiryDataModel] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        inquiryTV.delegate = self
        inquiryTV.dataSource = self
        inquiryTV.register(UINib(nibName: "ReviewInquiryTVC", bundle: nil), forCellReuseIdentifier: "ReviewInquiryTVC")
    }
    
    func setData(_ data: [CourseInquiryDataModel]?) {
        
        if let data = data {
            self.inquiryData = data
        }
    }


}

extension CourseInquiryAllVC: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        self.inquiryData.count
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
