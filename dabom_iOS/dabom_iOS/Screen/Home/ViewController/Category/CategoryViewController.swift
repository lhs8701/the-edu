//
//  CategoryViewController.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/09.
//

import UIKit

class CategoryViewController: UIViewController {
    
    // MARK: - IBOutlet

    @IBOutlet weak var firstCategory: UIButton!
    @IBOutlet weak var secondCategory: UIButton!
    @IBOutlet weak var thirdCategory: UIButton!
    @IBOutlet weak var fourthCategory: UIButton!
    @IBOutlet weak var fifthCategory: UIButton!
    
    
    // MARK: - Life Cycle
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        configureView()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        setNavi()
    }
    
    
    // MARK: - configure
    
    private func configureView() {
        self.firstCategory.layer.drawLineAt(edges: [.bottom], color: UIColor.black, width: 2.0)
        self.secondCategory.layer.drawLineAt(edges: [.bottom], color: UIColor.black, width: 2.0)
        self.thirdCategory.layer.drawLineAt(edges: [.bottom], color: UIColor.black, width: 2.0)
        self.fourthCategory.layer.drawLineAt(edges: [.bottom], color: UIColor.black, width: 2.0)
        self.fifthCategory.layer.drawLineAt(edges: [.bottom], color: UIColor.black, width: 2.0)
    }
    
    private func setNavi() {
        self.navigationController?.setNavigationBarHidden(false, animated: true)
        self.navigationController?.navigationBar.topItem?.backButtonTitle = ""
    }
        
    
    // MARK: - IBAction
    
    // 카테고리 선택했을 때 카테고리 결과 화면으로 이동
    @IBAction func categorySelected(_ sender: UIButton) {
        let nextVC = UIStoryboard.init(name: Const.Storyboard.Name.homeTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.categoryResult) as! ResultVC
        
        nextVC.resultTitle = sender.titleLabel?.text
        nextVC.kind = "category"
        
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
    }

}
