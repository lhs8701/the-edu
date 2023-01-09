//
//  CourseInfoViewController.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/09.
//

import UIKit

class CourseInfoViewController: UIViewController {
    
    var courseTitle: String?

    override func viewDidLoad() {
        super.viewDidLoad()
        
        // rightBarButtonItem 설정
        let onOffImage = UIImage(named: "onoff")?.withRenderingMode(.alwaysOriginal)
        let onOffButton = UIButton.init(frame: CGRect.init(x: 0, y: 0, width: 50, height: 30))
        onOffButton.setImage(onOffImage, for: .normal)
        let onOff = UIBarButtonItem(customView: onOffButton)
        
        let heartImage = UIImage(named: "heart")
        let heartButton = UIButton()
        heartButton.setImage(heartImage, for: .normal)
        let heart = UIBarButtonItem(customView: heartButton)

        navigationItem.rightBarButtonItems = [heart, onOff]

        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(false, animated: true)
        self.navigationController?.navigationBar.topItem?.title = courseTitle
    }

    
    

}
