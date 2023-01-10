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

    private var viewControllers: Array<UIViewController> = []

    override func viewDidLoad() {
        super.viewDidLoad()
        
        let inCourseVC = UIStoryboard.init(name: "MyCourseTab", bundle: nil).instantiateViewController(withIdentifier: "InCourseViewController") as! InCourseViewController
        let completionCourseVC = UIStoryboard.init(name: "MyCourseTab", bundle: nil).instantiateViewController(withIdentifier: "CompletionCourseViewController") as! CompletionCourseViewController
        let wishCourseVC = UIStoryboard.init(name: "MyCourseTab", bundle: nil).instantiateViewController(withIdentifier: "WishCourseViewController") as! WishCourseViewController
        
        viewControllers.append(inCourseVC)
        viewControllers.append(completionCourseVC)
        viewControllers.append(wishCourseVC)
        
        self.dataSource = self
        
        let bar = TMBar.ButtonBar()
        bar.layout.transitionStyle = .snap
        bar.layout.alignment = .centerDistributed
//        bar.layout.interButtonSpacing = view.bounds.width / 4
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
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}


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
//        let item = TMBarItem(title: "")
//        item.title = "Page \(index)"
//
//        return item
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
