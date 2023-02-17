//
//  CategoryTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/05.
//

import UIKit

class CategoryTVC: UITableViewCell {

    // MARK: - IBOutlet
    
    @IBOutlet weak var categoryName: UILabel!
    
    
    // MARK: - Life Cycle
    
    override func awakeFromNib() {
        super.awakeFromNib()

    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

    }
 
    
    // MARK: - func
    
    func setCategoryName(categoryName: String) {
        self.categoryName.text = categoryName
    }
    
}
