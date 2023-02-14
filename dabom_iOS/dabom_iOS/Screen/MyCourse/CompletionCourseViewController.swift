//
//  CompletionCourseViewController.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/04.
//

import UIKit

class CompletionCourseViewController: UIViewController {
    
    // MARK: - IBOutlet
    @IBOutlet weak var completionCourseCV: UICollectionView!
    
    
    // MARK: - let, var
    var completionCourseData: Array<MyCourseDataModel>?
    let loginType: String? = UserDefaults.standard.string(forKey: "loginType")
    
    var defaultImageView: UIImageView = UIImageView(image: UIImage(named: "default_completion"))
    
    // MARK: - Life Cycle
    override func viewDidLoad() {
        super.viewDidLoad()

        
        view.addSubview(defaultImageView)
        defaultImageView.contentMode = .scaleAspectFit
        defaultImageView.snp.makeConstraints {
            $0.center.equalTo(completionCourseCV.snp.center)
            $0.leading.trailing.equalToSuperview()
            $0.height.equalTo(300)
        }
        defaultImageView.isHidden = true
        
        // Do any additional setup after loading the view.
        setCV()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        if self.loginType != nil {
            getCompletedCourses()
        }
    }
    
    // MARK: - func
    private func setCV() {
        self.completionCourseCV.register(UINib(nibName: Const.Xib.Name.myCourseCVC, bundle: nil), forCellWithReuseIdentifier: Const.Xib.Identifier.myCourseCVC)
        self.completionCourseCV.delegate = self
        self.completionCourseCV.dataSource = self
        self.completionCourseCV.isScrollEnabled = true
    }
    
    private func getCompletedCourses() {
        MyCourseDataService.shared.getCompleted { response in
            switch response {
            case .success(let data):
                if let data = data as? [MyCourseDataModel] {
                    self.completionCourseData = data
                    self.completionCourseCV.reloadData()
                    
                    if data.isEmpty {
                        self.defaultImageView.isHidden = false
                    } else {
                        self.defaultImageView.isHidden = true
                    }
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

extension CompletionCourseViewController: UICollectionViewDelegate {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        if let completionCourseData = completionCourseData {
            return completionCourseData.count
        } else {
            return 0
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
//        guard let nextVC = UIStoryboard(name: Const.Storyboard.Name.courseInfoView, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.courseInfo) as? CourseInfoViewController else { return }
        
        guard let nextVC = UIStoryboard(name: Const.Storyboard.Name.myCourseTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.coursePlayerVC) as? CoursePlayerVC else { return }
        
        if let completionCourseData = completionCourseData {
            nextVC.courseId = completionCourseData[indexPath.row].courseId
            nextVC.unitId = completionCourseData[indexPath.row].nextUnitInfo.unitId
            nextVC.unitTitle = completionCourseData[indexPath.row].nextUnitInfo.title
            nextVC.thumbnailImage = completionCourseData[indexPath.row].thumbnailImage.mediumFilePath
        }


//        nextVC.courseId = 1
//        if let completionCourseData = completionCourseData {
//            nextVC.courseId = completionCourseData[indexPath.row].courseId
//            nextVC.u
//        }

        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)

    }
}

extension CompletionCourseViewController: UICollectionViewDataSource {
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: MyCourseCVC.identifier, for: indexPath) as? MyCourseCVC else { return UICollectionViewCell() }
        
        if let completionCourseData = completionCourseData {
            cell.setData(completionCourseData[indexPath.row])
        }
        
        return cell
    }
}

extension CompletionCourseViewController: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        
        let cellWidth = (UIScreen.main.bounds.width - (10 * 3)) / 2
        let cellHeight = cellWidth * 1.2

        return CGSize(width: cellWidth, height: cellHeight)
    }

    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {

        return UIEdgeInsets(top: 10, left: 10, bottom: 10, right: 10)
    }

    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumInteritemSpacingForSectionAt section: Int) -> CGFloat {
        let spacingSize = 10

        return CGFloat(spacingSize)
    }

    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        let spacingSize = 10

        return CGFloat(spacingSize)
    }
}

