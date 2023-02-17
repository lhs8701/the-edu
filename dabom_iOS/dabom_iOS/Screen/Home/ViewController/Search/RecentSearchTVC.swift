//
//  RecentSearchTVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/25.
//

import UIKit

class RecentSearchTVC: UITableViewCell {
    
    // MARK: - IBOutlet

    @IBOutlet weak var recentSearchTerm: UILabel!
    
    
    // MARK: - Life Cycle
    
    override func awakeFromNib() {
        super.awakeFromNib()

    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

    }

}
