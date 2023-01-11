//
//  CourseInfoViewController.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/09.
//

import UIKit

class CourseInfoViewController: UIViewController {
    
    var courseTitle: String?

    @IBOutlet weak var classTitle: UILabel!
    
    @IBOutlet weak var shortIntro: UILabel!
    
    @IBOutlet weak var creatorName: UILabel!

    
    @IBOutlet weak var tabmanView: UIView!
    
    @IBOutlet weak var courseInfoSV: UIScrollView!
    
    
    // MARK: - Life Cycle
    override func viewDidLoad() {
        super.viewDidLoad()

        setRightBarButton()
        
        setInfo()
        
        setLabel()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(false, animated: true)
        self.navigationController?.navigationBar.topItem?.title = courseTitle
        self.navigationController?.navigationBar.topItem?.backButtonTitle = ""
    }
    
    
    // MARK: - rightBarButtonItem 설정
    private func setRightBarButton() {
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
    
    // MARK: - label 줄 간격 설정
    private func setLabel() {
        let attrString = NSMutableAttributedString(string: shortIntro.text ?? "")
        let paragraphStyle = NSMutableParagraphStyle()
        paragraphStyle.lineSpacing = 4
        attrString.addAttribute(NSAttributedString.Key.paragraphStyle, value: paragraphStyle, range: NSMakeRange(0, attrString.length))
        shortIntro.attributedText = attrString
    }
    
    
    // MARK: - courseInfo setting
    private func setInfo() {
        self.classTitle.text = courseTitle
        self.shortIntro.text = "어쩌고 저쩌고 한 사람에게 좋은 강의 이렇고 저렇고 한 사람에게 좋은 강의"
        self.creatorName.text = "김태현"
    }
    

}
