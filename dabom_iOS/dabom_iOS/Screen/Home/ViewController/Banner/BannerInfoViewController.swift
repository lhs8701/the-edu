//
//  BannerInfoViewController.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/09.
//

import UIKit

class BannerInfoViewController: UIViewController {
    
    // MARK: - IBOutlet
    @IBOutlet weak var bannerImageView: UIImageView!
    @IBOutlet weak var contentTitleLabel: UILabel!
    @IBOutlet weak var contentLabel: UILabel!
    @IBOutlet weak var endDateLabel: UILabel!
    @IBOutlet weak var writerLabel: UILabel!
    
    
    // MARK: - let, var
    var bannerTitle: String?
    var eventId: Int?

    
    // MARK: - Life Cycle
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
//        self.navigationController?.setNavigationBarHidden(false, animated: true)
//        self.navigationController?.navigationBar.topItem?.title = bannerTitle
        
        setContent()
        getEvent()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(false, animated: true)
//        self.navigationController?.navigationBar.topItem?.title = bannerTitle
    }
    
    // MARK: - Content 설정
    private func setContent() {
        self.contentTitleLabel.layer.drawLineAt(edges: [.bottom], color: UIColor(named: "mainColor") ?? .yellow, width: 4.0)
    }
    
    // MARK: - 내용 들어가는 Label 높이 설정
    private func setLabel() {
        let newSize = self.contentLabel.sizeThatFits(CGSize(width: view.frame.width, height: CGFloat.greatestFiniteMagnitude))
        self.contentLabel.frame.size = newSize
    }
    
    // MARK: - Event 정보 가져오기
    private func getEvent() {
        if let eventId = eventId {
            EventDataService.shared.getEvent(eventId: eventId) { response in
                switch response {
                case .success(let data):
                    if let data = data as? EventDataModel {
                        self.contentLabel.text = data.content
                        self.endDateLabel.text = data.endDate
                        self.writerLabel.text = data.writer
                        self.bannerImageView.setImage(with: data.bannerImage.mediumFilePath)
                        self.navigationController?.navigationBar.topItem?.title = self.bannerTitle
                        self.setLabel()
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

}
