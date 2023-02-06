//
//  StatementVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/06.
//

import UIKit

class StatementVC: UIViewController {

    @IBOutlet weak var statement: UITextView!
    
    var statementText: String?
    
    override func viewDidLoad() {
        super.viewDidLoad()

        self.statement.text = statementText
        self.statement.isEditable = false
    }


}
