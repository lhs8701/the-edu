//
//  CourseCurriculumAllVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/09.
//

import UIKit

class CourseCurriculumAllVC: UIViewController {

    @IBOutlet weak var curriculumTV: UITableView!
    
    @IBOutlet weak var curriculumTitle: UILabel!
    
    var curriculum: CurriculumDataModel?
    var courseId: Int?
    
    override func viewDidLoad() {
        super.viewDidLoad()

        configure()
        setTV()
        setCurriculum()
    }
    
    private func configure() {
        self.curriculumTitle.layer.drawLineAt(edges: [.bottom], color: UIColor(named: "mainColor") ?? .yellow, width: 4.0)
    }
    
    private func setTV() {
        self.curriculumTV.delegate = self
        self.curriculumTV.dataSource = self
        self.curriculumTV.register(UINib(nibName: Const.Xib.Name.curriculumTVC, bundle: nil), forCellReuseIdentifier: Const.Xib.Identifier.curriculumTVC)
        self.curriculumTV.register(UINib(nibName: Const.Xib.Name.curriculumHeaderTVC, bundle: nil), forHeaderFooterViewReuseIdentifier: Const.Xib.Identifier.curriculumHeaderTVC)
    }
    
    private func setCurriculum() {
        if let courseId = courseId {
            CurriculumDataService.shared.getCurriculum(courseId: courseId) { response in
                switch response {
                case .success(let data):
                    if let data = data as? CurriculumDataModel {
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


}

extension CourseCurriculumAllVC: UITableViewDelegate, UITableViewDataSource {
    func numberOfSections(in tableView: UITableView) -> Int {
        self.curriculum?.chapters.count ?? 0
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        self.curriculum?.chapters[section].units.count ?? 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: Const.Xib.Identifier.curriculumTVC) as! CurriculumTVC
        
        if let curriculum = curriculum {
            cell.curriculumTitle.text = curriculum.chapters[indexPath.section].units[indexPath.row].title
            
        }
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let header = tableView.dequeueReusableHeaderFooterView(withIdentifier: Const.Xib.Identifier.curriculumHeaderTVC) as! CurriculumHeaderTVC
        
        if let curriculum = curriculum {
            header.chapterTitle.text = curriculum.chapters[section].title
        }
        
        return header
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        40
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        40
    }
}
