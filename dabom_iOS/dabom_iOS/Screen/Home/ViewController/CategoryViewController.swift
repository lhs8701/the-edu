//
//  CategoryViewController.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/09.
//

import UIKit

class CategoryViewController: UIViewController {
    

    @IBOutlet weak var firstCategory: UIButton!
    @IBOutlet weak var secondCategory: UIButton!
    @IBOutlet weak var thirdCategory: UIButton!
    @IBOutlet weak var fourthCategory: UIButton!
    @IBOutlet weak var fifthCategory: UIButton!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        
        
        self.firstCategory.layer.drawLineAt(edges: [.bottom], color: UIColor.black, width: 2.0)
        self.secondCategory.layer.drawLineAt(edges: [.bottom], color: UIColor.black, width: 2.0)
        self.thirdCategory.layer.drawLineAt(edges: [.bottom], color: UIColor.black, width: 2.0)
        self.fourthCategory.layer.drawLineAt(edges: [.bottom], color: UIColor.black, width: 2.0)
        self.fifthCategory.layer.drawLineAt(edges: [.bottom], color: UIColor.black, width: 2.0)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(false, animated: true)
//        self.navigationController?.navigationBar.topItem?.title = "카테고리"
        self.navigationController?.navigationBar.topItem?.backButtonTitle = ""
    }
        
    
    @IBAction func categorySelected(_ sender: UIButton) {
        let nextVC = UIStoryboard.init(name: Const.Storyboard.Name.homeTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.categoryResult) as! ResultVC
        
        nextVC.resultTitle = sender.titleLabel?.text
        nextVC.kind = "category"
        
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
        
    }
    
    

}
