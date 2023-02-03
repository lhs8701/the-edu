//
//  CoursePlayerVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/02.
//

import UIKit
import AVFoundation
import AVKit

class CoursePlayerVC: UIViewController {

    @IBOutlet weak var unitThumbnailImage: UIImageView!
    
    @IBOutlet weak var playBtn: UIButton!
    
    let Url = URL(string: Const.Url.m3u8Test)
    
    var unitId: Int?
    var unitData: UnitDataModel?
    
    var avPlayer = AVPlayer()
    var avController = AVPlayerViewController()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.playBtn.isEnabled = false
    
        unitThumbnailImage.image = UIImage(named: "testThumb01")
        
        getUnit()
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        print(avPlayer.currentTime().seconds)
        let currentTime = avPlayer.currentTime().seconds

        self.navigationController?.setNavigationBarHidden(false, animated: true)
        self.navigationController?.navigationBar.topItem?.backButtonTitle = ""
        
        saveRecord(time: currentTime)
    }
    
    private func getUnit() {
        if let unitId = unitId {
            UnitDataService.shared.getUnit(unitId: unitId) { response in
                switch response {
                case .success(let data):
                    if let data = data as? UnitDataModel {
                        self.unitData = data
                        guard let videoUrl = URL(string: "\(Const.Url.baseUrl)\(data.videoInfo.filePath)") else {return}
                        self.avPlayer = AVPlayer(url: videoUrl)
                        self.avController.player = self.avPlayer
                        self.avController.view.frame = self.view.frame
                        self.getRecord()
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
    
    private func saveRecord(time: Double) {
        if let unitId = unitId {
            UnitDataService.shared.saveRecord(unitId: unitId, time: time) { response in
                switch response {
                case .success:
                    print("save Success")
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
    
    private func getRecord() {
        if let unitId = unitId {
            
            UnitDataService.shared.getRecord(unitId: unitId) { time in
                self.avPlayer.seek(to: CMTime(seconds: time, preferredTimescale: 600), toleranceBefore: .zero, toleranceAfter: .zero)
                self.playBtn.isEnabled = true
            }
            
        }
    }
    
    @IBAction func playBtnPressed(_ sender: Any) {
        print("playBtnPressed")

        self.present(avController, animated: true, completion: nil)
        avPlayer.play()
        
    }
    
    

}
