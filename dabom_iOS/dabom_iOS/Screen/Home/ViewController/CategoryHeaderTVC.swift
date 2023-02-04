//
//  CategoryHeaderTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/05.
//

import UIKit

class CategoryHeaderTVC: UITableViewHeaderFooterView {

    @IBOutlet weak var categoryGroup: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    override func prepareForReuse() {
        super.prepareForReuse()
    }
    
    func setGroupName(groupName: String) {
        self.categoryGroup.text = groupName
    }
    
}
