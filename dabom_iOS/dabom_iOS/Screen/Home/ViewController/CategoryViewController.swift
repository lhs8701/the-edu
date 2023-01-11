//
//  CategoryViewController.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/09.
//

import UIKit

class CategoryViewController: UIViewController {
    

    @IBOutlet weak var firstCategory: UIButton!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
        self.navigationController?.setNavigationBarHidden(false, animated: true)
        self.navigationController?.navigationBar.topItem?.title = "카테고리"
        
        self.firstCategory.layer.drawLineAt(edges: [.bottom], color: UIColor.black, width: 2.0)
    }
        

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
