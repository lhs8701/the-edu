//
//  EventVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/10.
//

import UIKit

class EventVC: UIViewController {
    
    // MARK: - IBOutlet
    
    @IBOutlet weak var eventTV: UITableView!
    
    
    // MARK: - let, var
    
    private var eventList: [BannerDataModel] = []
    
    
    // MARK: - Life Cycle
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setTV()
        setEvent()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        setNavi()
    }
    
    
    // MARK: - NavigationBar Setting
    
    func setNavi() {
        self.navigationController?.setNavigationBarHidden(false, animated: true)
        self.navigationController?.navigationBar.topItem?.title = "진행 중인 이벤트"
        self.navigationController?.navigationBar.topItem?.backButtonTitle = ""
    }
    
    
    // MARK: - TableView Setting
    
    func setTV() {
        eventTV.delegate = self
        eventTV.dataSource = self
        eventTV.register(UINib(nibName: Const.Xib.Identifier.eventTVC, bundle: nil), forCellReuseIdentifier: Const.Xib.Identifier.eventTVC)
    }
    
    
    // MARK: - Event 목록 가져오기
    
    func setEvent() {
        BannerDataService.shared.getOngoingBanner { response in
            switch response {
            case .success(let data):
                if let data = data as? [BannerDataModel] {
                    self.eventList = data
                    self.eventTV.reloadData()
                }
            case .requestErr(let message):
                print("requestErr", message)
            case .pathErr:
                print("pathErr")
            case .serverErr:
                print("serverErr")
            case .networkFail:
                print("networkFail")
            case .resourceErr:
                print("resourceErr")
            }
        }
    }
    
}


// MARK: - UITableView extension

extension EventVC: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return self.view.frame.width / 2
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return eventList.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: Const.Xib.Identifier.eventTVC, for: indexPath) as? EventTVC else { return UITableViewCell() }
        
        cell.setData(eventData: eventList[indexPath.row])
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        guard let nextVC = UIStoryboard(name: Const.Storyboard.Name.homeTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.bannerInfo) as? BannerInfoViewController else { return }
        
        nextVC.eventId = eventList[indexPath.row].id
        nextVC.bannerTitle = eventList[indexPath.row].title
        
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
    }
    
}
