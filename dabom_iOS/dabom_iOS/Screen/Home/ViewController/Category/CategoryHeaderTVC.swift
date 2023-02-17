//
//  CategoryHeaderTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/05.
//

import UIKit

class CategoryHeaderTVC: UITableViewHeaderFooterView {

    // MARK: - IBOutlet
    
    @IBOutlet weak var categoryGroup: UILabel!
    
    
    // MARK: - Life Cycle
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    override func prepareForReuse() {
        super.prepareForReuse()
    }
    
    
    // MARK: - func
    
    func setGroupName(groupName: String) {
        self.categoryGroup.text = groupName
    }
    
}
