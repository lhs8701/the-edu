//
//  CategoryTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/05.
//

import UIKit

class CategoryTVC: UITableViewCell {

    @IBOutlet weak var categoryName: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
 
    func setCategoryName(categoryName: String) {
        self.categoryName.text = categoryName
    }
}
