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

    // MARK: - IBOutlet
    @IBOutlet weak var unitThumbnailImage: UIImageView!
    
    @IBOutlet weak var playBtn: UIButton!
    
    @IBOutlet weak var unitTitleLabel: UILabel!
    
    @IBOutlet weak var curriculumTV: UITableView!
    
    @IBOutlet weak var courseCurriculum: UILabel!
    
    @IBOutlet weak var courseCurriclumView: UIView!
    
    // MARK: - let, var
    let Url = URL(string: Const.Url.m3u8Test)
    
    var courseId: Int?
    var unitId: Int?
    var unitTitle: String?
    var thumbnailImage: String = ""
    var unitData: UnitDataModel?
    var curriculum: UserCurriculumDataModel?
    
    var avPlayer = AVPlayer()
    var avController = AVPlayerViewController()
    
    // MARK: - Life Cycle
    override func viewDidLoad() {
        super.viewDidLoad()
        self.playBtn.isEnabled = false
       
        configure()
        setTV()
        getUnit()
        setCurriculum()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        print(avPlayer.currentTime().seconds)
        let currentTime = avPlayer.currentTime().seconds

        self.navigationController?.setNavigationBarHidden(false, animated: true)
        self.navigationController?.navigationBar.topItem?.backButtonTitle = ""
        
        if unitId == nil { return }
        guard let unitId = unitId else { return }
        guard let endTime = avPlayer.currentItem?.duration.seconds else { return }
        
        if currentTime + 10 >= endTime {
            UnitDataService.shared.completeUnit(unitId: unitId) { response in
                switch response {
                case .success:
                    print("complete Success")
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
        
        saveRecord(time: currentTime)
    }
    
    // MARK: - View configure
    private func configure() {
        self.courseCurriculum.layer.drawLineAt(edges: [.bottom], color: UIColor(named: "mainColor") ?? .yellow, width: 4.0)
        self.unitThumbnailImage.setImage(with: self.thumbnailImage)
        self.unitTitleLabel.text = self.unitTitle
        self.unitTitleLabel.adjustsFontSizeToFitWidth = true
    }
    
    // MARK: - TableView Setting
    private func setTV() {
        self.curriculumTV.delegate = self
        self.curriculumTV.dataSource = self
        self.curriculumTV.register(UINib(nibName: Const.Xib.Name.curriculumTVC, bundle: nil), forCellReuseIdentifier: Const.Xib.Identifier.curriculumTVC)
        self.curriculumTV.register(UINib(nibName: Const.Xib.Name.curriculumHeaderTVC, bundle: nil), forHeaderFooterViewReuseIdentifier: Const.Xib.Identifier.curriculumHeaderTVC)
    }
    
    // MARK: - 커리큘럼 정보 가져오기
    private func setCurriculum() {
        if let courseId = courseId {
            UserCurriculumDataService.shared.getUserCurriculum(courseId: courseId) { response in
                switch response {
                case .success(let data):
                    if let data = data as? UserCurriculumDataModel {
                        self.curriculum = data
                        self.curriculumTV.reloadData()
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
    
    // MARK: - Unit 정보 받아오기
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
    
    // MARK: - 시청 기록 저장하기
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
    
    // MARK: - 시청 기록 가져오기
    private func getRecord() {
        if let unitId = unitId {
            
            UnitDataService.shared.getRecord(unitId: unitId) { time in
                self.avPlayer.seek(to: CMTime(seconds: time, preferredTimescale: 600), toleranceBefore: .zero, toleranceAfter: .zero)
                self.playBtn.isEnabled = true
            }
            
        }
    }
    
    // MARK: - 동영상 플레이
    @IBAction func playBtnPressed(_ sender: Any) {
        print("playBtnPressed")

        self.present(avController, animated: true, completion: nil)
        avPlayer.play()
        
    }
    
    

}

extension CoursePlayerVC: UITableViewDelegate, UITableViewDataSource {
    func numberOfSections(in tableView: UITableView) -> Int {
        self.curriculum?.chapters.count ?? 0
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        self.curriculum?.chapters[section].units.count ?? 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: Const.Xib.Identifier.curriculumTVC) as! CurriculumTVC
        
        if let curriculum = curriculum {
            cell.curriculumTitle.text = "\(indexPath.row + 1). \(curriculum.chapters[indexPath.section].units[indexPath.row].title)"
            
            if curriculum.chapters[indexPath.section].units[indexPath.row].completed {
                cell.contentView.backgroundColor = UIColor(named: "completeUnit")
            }
            
        }
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let header = tableView.dequeueReusableHeaderFooterView(withIdentifier: Const.Xib.Identifier.curriculumHeaderTVC) as! CurriculumHeaderTVC
        
        if let curriculum = curriculum {
            header.chapterTitle.text = "\(section). \(curriculum.chapters[section].title)"
        }
        header.contentView.backgroundColor = UIColor(named: "lightMain")
        
        return header
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        40
    }
    
    func tableView(_ tableView: UITableView, heightForFooterInSection section: Int) -> CGFloat {
        .leastNormalMagnitude 
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        40
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        if let curriculum = curriculum {
            self.unitId = curriculum.chapters[indexPath.section].units[indexPath.row].unitId
            self.unitTitleLabel.text = curriculum.chapters[indexPath.section].units[indexPath.row].title
            self.playBtn.isEnabled = false
//            configure()
            getUnit()
            setCurriculum()
        }
    }
    
}
