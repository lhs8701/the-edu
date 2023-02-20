//
//  TabManViewController.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/04.
//

import UIKit
import Tabman
import Pageboy

class MyCourseTabManViewController: TabmanViewController {

    
    // MARK: - let, var
    
    private var viewControllers: Array<UIViewController> = []

    
    // MARK: - Life Cycle
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        configure()
        tapManSetting()
    }
    
    private func configure() {
        let inCourseVC = UIStoryboard.init(name: Const.Storyboard.Name.myCourseTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.inCourse) as! InCourseViewController
        let completionCourseVC = UIStoryboard.init(name: Const.Storyboard.Name.myCourseTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.completionCourse) as! CompletionCourseViewController
        let wishCourseVC = UIStoryboard.init(name: Const.Storyboard.Name.myCourseTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.wishCourse) as! WishCourseViewController
        
        viewControllers.append(inCourseVC)
        viewControllers.append(completionCourseVC)
        viewControllers.append(wishCourseVC)
    }
    
    private func tapManSetting() {
        self.dataSource = self
        
        let bar = TMBar.ButtonBar()
        bar.layout.transitionStyle = .snap
        bar.layout.alignment = .centerDistributed
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


// MARK: - extension

extension MyCourseTabManViewController: PageboyViewControllerDataSource, TMBarDataSource {
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
        switch index {
        case 0:
            return TMBarItem(title: "덜 봄")
        case 1:
            return TMBarItem(title: "다 봄")
        case 2:
            return TMBarItem(title: "찜")
        default:
            let title = "Page \(index)"
            return TMBarItem(title: title)
        }
    }


}
