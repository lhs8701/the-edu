//
//  CourseInfoViewController.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/09.
//

import UIKit

class CourseInfoViewController: UIViewController {
        
    // MARK: - IBOutlet

    @IBOutlet weak var mainTV: UITableView!
    
    @IBOutlet weak var stickyView: UIView!
    
    @IBOutlet weak var upperConstraint: NSLayoutConstraint! {
        didSet {
            upperConstraint.constant = maxUpper
        }
    }
    
    @IBOutlet weak var segmentCtrl: UISegmentedControl!
    
    
    // MARK: - 변수, 상수
    var courseTitle: String?

    let maxUpper: CGFloat = 450.0
    let minUpper: CGFloat = 0.0
    // MARK: - Life Cycle
    
    override func viewDidLoad() {
        super.viewDidLoad()

        setRightBarButton()
        
        self.mainTV.delegate = self
        self.mainTV.dataSource = self
        self.mainTV.register(UINib(nibName: "InfoImageTVC", bundle: nil), forCellReuseIdentifier: "InfoImageTVC")
        self.mainTV.register(UINib(nibName: "CourseInfoTVC", bundle: nil), forCellReuseIdentifier: "CourseInfoTVC")
        self.mainTV.register(UINib(nibName: "SegmentTVC", bundle: nil), forCellReuseIdentifier: "SegmentTVC")

        
        let backgroundImg = UIImage(named: "segment")
        let Img = UIImage()
        self.segmentCtrl.setBackgroundImage(Img, for: .normal, barMetrics: .default)
        self.segmentCtrl.setBackgroundImage(backgroundImg, for: .selected, barMetrics: .default)
        self.segmentCtrl.setBackgroundImage(Img, for: .highlighted, barMetrics: .default)
        
        
        self.segmentCtrl.setDividerImage(Img, forLeftSegmentState: .selected, rightSegmentState: .normal, barMetrics: .default)
        self.segmentCtrl.setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.gray, .font: UIFont.systemFont(ofSize: 17)], for: .normal)
        self.segmentCtrl.setTitleTextAttributes([NSAttributedString.Key.foregroundColor: UIColor.black, .font: UIFont.systemFont(ofSize: 17, weight: .bold)], for: .selected)
                
        self.segmentCtrl.addTarget(self, action: #selector(segCtrlValChanged(_:)), for: .valueChanged)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        setNavigationBar()
    }
    
    @objc func segCtrlValChanged(_ sender: UISegmentedControl) {
        switch sender.selectedSegmentIndex {
        case 0:
            self.mainTV.scrollToRow(at: NSIndexPath(row: 2, section: 0) as IndexPath, at: UITableView.ScrollPosition.top, animated: true)
        case 1:
            self.mainTV.scrollToRow(at: NSIndexPath(row: 3, section: 0) as IndexPath, at: UITableView.ScrollPosition.top, animated: true)
        case 2:
            self.mainTV.scrollToRow(at: NSIndexPath(row: 4, section: 0) as IndexPath, at: UITableView.ScrollPosition.top, animated: true)
        case 3:
            self.mainTV.scrollToRow(at: NSIndexPath(row: 5, section: 0) as IndexPath, at: UITableView.ScrollPosition.top, animated: true)
        default:
            break
        }
    }
    
    
    // MARK: - navigationBar 설정
    
    private func setNavigationBar() {
        self.navigationController?.setNavigationBarHidden(false, animated: true)
        self.navigationController?.navigationBar.topItem?.backButtonTitle = ""
    }
    
    
    // MARK: - rightBarButtonItem 설정
    
    private func setRightBarButton() {
        let onOffImage = UIImage(named: "onoff")?.withRenderingMode(.alwaysOriginal)
        let onOffButton = UIButton.init(frame: CGRect.init(x: 0, y: 0, width: 60, height: 30))
        onOffButton.setImage(onOffImage, for: .normal)
        let onOff = UIBarButtonItem(customView: onOffButton)
        
        let heartImage = UIImage(named: "heart")
        let heartButton = UIButton.init(frame: CGRect.init(x: 0, y: 0, width: 30, height: 30))
        heartButton.setImage(heartImage, for: .normal)
        let heart = UIBarButtonItem(customView: heartButton)

        navigationItem.rightBarButtonItems = [heart, onOff]
    }
    
    
    
    
    // MARK: - courseInfo setting
    
//    private func setInfo() {
//        self.classTitle.text = courseTitle
//        self.shortIntro.text = "어쩌고 저쩌고 한 사람에게 좋은 강의 이렇고 저렇고 한 사람에게 좋은 강의"
//        self.creatorName.text = "김태현"
//    }
    

}


extension CourseInfoViewController: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        switch indexPath.row {
        case 0:
            return 450
        case 1:
            return 50
        default:
            return 800
        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 7
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        switch indexPath.row {
        case 0:
            guard let cell = mainTV.dequeueReusableCell(withIdentifier: "CourseInfoTVC", for: indexPath) as? CourseInfoTVC else { return UITableViewCell() }
            cell.classTitle.text = courseTitle
            cell.shortIntro.text = "어쩌고 저쩌고 한 사람에게 좋은 강의 이렇고 저렇고 한 사람에게 좋은 강의"
            cell.creatorName.text = "김태현"
            return cell
        case 1:
            guard let cell = mainTV.dequeueReusableCell(withIdentifier: "SegmentTVC", for: indexPath) as? SegmentTVC else { return UITableViewCell() }
            return cell
        case 2:
            guard let cell = mainTV.dequeueReusableCell(withIdentifier: "InfoImageTVC", for: indexPath) as? InfoImageTVC else { return UITableViewCell() }
            cell.infoImageView.image = UIImage(named: "testIntro01")
            return cell
        case 3,4,5,6:
            guard let cell = mainTV.dequeueReusableCell(withIdentifier: "InfoImageTVC", for: indexPath) as? InfoImageTVC else { return UITableViewCell() }
            cell.infoImageView.image = UIImage(named: "testIntro02")
            return cell
        default:
            return UITableViewCell()
        }
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if scrollView.contentOffset.y > 0 {
            upperConstraint.constant = max((maxUpper - scrollView.contentOffset.y), minUpper)
        } else {
            upperConstraint.constant = maxUpper - scrollView.contentOffset.y
        }
    }
}
