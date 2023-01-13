//
//  UserViewController.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/04.
//

import UIKit

class UserViewController: UIViewController {

    @IBOutlet weak var profileImageView: UIImageView!
    
    @IBOutlet weak var profileView: UIView!
    @IBOutlet weak var userNameLabel: UILabel!
    
    @IBOutlet weak var myCouponBtn: UIButton!
    
    @IBOutlet weak var noticeBtn: UIButton!
    
    @IBOutlet weak var eventBtn: UIButton!
    
    @IBOutlet weak var informationBtn: UIButton!
    
    @IBOutlet weak var settingBtn: UIButton!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        

        // Do any additional setup after loading the view.
        profileImageView.image = UIImage(named: "testProfile")
        myCouponBtn.layer.drawLineAt(edges: [.bottom], color: UIColor.lightGray, width: 1.0)
        noticeBtn.layer.drawLineAt(edges: [.bottom], color: UIColor.lightGray, width: 1.0)
        eventBtn.layer.drawLineAt(edges: [.bottom], color: UIColor.lightGray, width: 1.0)
        informationBtn.layer.drawLineAt(edges: [.bottom], color: UIColor.lightGray, width: 1.0)
        settingBtn.layer.drawLineAt(edges: [.bottom], color: UIColor.lightGray, width: 1.0)
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
