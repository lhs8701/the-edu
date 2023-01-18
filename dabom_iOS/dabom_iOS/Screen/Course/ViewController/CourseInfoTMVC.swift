//
//  CourseInfoTMVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/11.
//

import UIKit
import Tabman
import Pageboy

class CourseInfoTMVC: TabmanViewController {
    
    private var viewControllers: Array<UIViewController> = []

    override func viewDidLoad() {
        super.viewDidLoad()

        // MARK: - VC setting
        let introductionVC = UIStoryboard.init(name: Const.Storyboard.Name.courseInfoView, bundle: nil).instantiateViewController(withIdentifier: "IntroductionVC") as! IntroductionVC
        let curriculumVC = UIStoryboard.init(name: Const.Storyboard.Name.courseInfoView, bundle: nil).instantiateViewController(withIdentifier: "CurriculumVC") as! CurriculumVC
        let courseReviewVC = UIStoryboard.init(name: Const.Storyboard.Name.courseInfoView, bundle: nil).instantiateViewController(withIdentifier: "CourseReviewVC") as! CourseReviewVC
        let courseInquireVC = UIStoryboard.init(name: Const.Storyboard.Name.courseInfoView, bundle: nil).instantiateViewController(withIdentifier: "CourseInquireVC") as! CourseInquireVC
        
        introductionVC.firstImage = UIImage(named: "testIntro01")!
        introductionVC.secondImage = UIImage(named: "testIntro02")!
        
        curriculumVC.firstImage = UIImage(named: "testIntro01")!
        curriculumVC.secondImage = UIImage(named: "testIntro02")
        
        viewControllers.append(introductionVC)
        viewControllers.append(curriculumVC)
        viewControllers.append(courseReviewVC)
        viewControllers.append(courseInquireVC)
        
        
        self.dataSource = self
        
        // MARK: - Bar setting
        let bar = TMBar.ButtonBar()
        bar.layout.transitionStyle = .snap
        bar.layout.alignment = .centerDistributed
        bar.layout.interButtonSpacing = 0
        bar.layout.contentMode = .fit
        
        bar.backgroundView.style = .clear
        
        bar.buttons.customize { (button) in
            button.tintColor = UIColor(named: "unSelectedColor")
            button.selectedTintColor = .black
        }
        
        bar.indicator.weight = .medium
        bar.indicator.tintColor = UIColor(named: "mainColor")
        
        addBar(bar, dataSource: self, at: .top)
        
        
        
    }
    

}

extension CourseInfoTMVC: PageboyViewControllerDataSource, TMBarDataSource {
    func numberOfViewControllers(in pageboyViewController: Pageboy.PageboyViewController) -> Int {
        return viewControllers.count
    }

    func viewController(for pageboyViewController: Pageboy.PageboyViewController, at index: Pageboy.PageboyViewController.PageIndex) -> UIViewController? {
        return viewControllers[index]
    }

    func defaultPage(for pageboyViewController: Pageboy.PageboyViewController) -> Pageboy.PageboyViewController.Page? {
        return nil
    }

    func barItem(for bar: Tabman.TMBar, at index: Int) -> Tabman.TMBarItemable {
//        let item = TMBarItem(title: "")
//        item.title = "Page \(index)"
//
//        return item
        switch index {
        case 0:
            return TMBarItem(title: "클래스 소개")
        case 1:
            return TMBarItem(title: "커리큘럼")
        case 2:
            return TMBarItem(title: "수강 후기")
        case 3:
            return TMBarItem(title: "문의사항")
        default:
            let title = "Page \(index)"
            return TMBarItem(title: title)
        }
    }
}
