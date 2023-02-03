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
    
    var avPlayer = AVPlayer()
    var avController = AVPlayerViewController()
    
    override func viewDidLoad() {
        super.viewDidLoad()

        
        avPlayer = AVPlayer(url: Url!)
//        avPlayer.seek(to: CMTime(seconds: 15, preferredTimescale: 600), toleranceBefore: .zero, toleranceAfter: .zero)
        avController.player = avPlayer
        avController.view.frame = self.view.frame
        unitThumbnailImage.image = UIImage(named: "testThumb01")
    }
    
    override func viewWillAppear(_ animated: Bool) {
        print(avPlayer.currentTime())
        print(avPlayer.currentTime().seconds)
        print("viewWillAppear")
        self.navigationController?.setNavigationBarHidden(false, animated: true)
        self.navigationController?.navigationBar.topItem?.backButtonTitle = ""
    }
    
    
    @IBAction func playBtnPressed(_ sender: Any) {
        
        self.present(avController, animated: true, completion: nil)
        avPlayer.play()
        
    }
    
    

}
