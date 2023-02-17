//
//  StatementVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/06.
//

import UIKit

class StatementVC: UIViewController {

    // MARK: - IBOutlet
    @IBOutlet weak var statement: UITextView!
    
    
    // MARK: - let, var
    var statementText: String?
    
    
    // MARK: - Life Cycle
    override func viewDidLoad() {
        super.viewDidLoad()

        self.statement.text = statementText
        self.statement.isEditable = false
    }
    



}
