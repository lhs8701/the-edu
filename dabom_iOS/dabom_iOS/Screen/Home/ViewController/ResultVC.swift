//
//  CategoryResultVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/12.
//

import UIKit

class ResultVC: UIViewController {
    // MARK: - IBOutlet
    
    @IBOutlet weak var resultCV: UICollectionView!
    
    @IBOutlet weak var resultName: UILabel!
    
    @IBOutlet weak var resultKind: UILabel!
    
    // MARK: - let, var
    
    var resultTitle: String?
    
    var resultData: Array<SampleCourseThumbnail> = []
    var sampleApiData: Array<CourseThumbnailDataModel> = CourseThumbnailDataModel.sampleAPI
    
    var kind: String = "category"
    var page = 0
    var totalPage = 0
    var size = 10
    var isPaging: Bool = false
    var hasNextPage: Bool = false
    
    
    // MARK: - Life Cycle
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setCV()
        
        self.resultName.text = self.resultTitle ?? ""
        if kind == "category" {
            self.resultKind.text = "카테고리"
        } else if kind == "search" {
            self.resultKind.text = "검색 결과"
        }
        
        self.resultName.sizeToFit()
        self.resultName.layer.drawLineAt(edges: [.bottom], color: UIColor(named: "mainColor")!, width: 3.0)
        
        
        self.navigationController?.navigationBar.topItem?.backButtonTitle = ""

    }
    
    
    // MARK: - CollectionView Setting
    
    private func setCV() {
        self.resultCV.register(UINib(nibName: Const.Xib.Name.courseThumbnailCVC, bundle: nil), forCellWithReuseIdentifier: Const.Xib.Identifier.courseThumbnailCVC)
        self.resultCV.delegate = self
        self.resultCV.dataSource = self
        self.resultCV.isScrollEnabled = true
        
        self.getData(page: self.page)
    }
    
    
    // MARK: - getData
    
    func getData(page: Int) {
        print("getData!!")
        self.isPaging = true
        
        if page <= totalPage {
            GetPaginationDataService.shared.getPagination(kind: self.kind, keyword: self.resultTitle ?? "", page: self.page, size: self.size) { response in
                switch response {
                case .success(let paginationData):
                    if let data = paginationData as? PaginationDataModel {
                        self.page += 1
                        self.totalPage = data.totalPage
                        
//                        DispatchQueue.main.asyncAfter(deadline: .now() + 0.5, execute: {
//                            self.resultData.append(contentsOf: data.list)
//                            self.resultCV.reloadData()
//                            self.isPaging = false
//                        })
                        self.resultData.append(contentsOf: data.list)
                        self.resultCV.reloadData()
                        self.isPaging = false
                        
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

// MARK: - UICollectionViewDelegate

extension ResultVC: UICollectionViewDelegate {
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return resultData.count
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        guard let nextVC = UIStoryboard(name: Const.Storyboard.Name.courseInfoView, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.courseInfo) as? CourseInfoViewController else { return }
        
        nextVC.courseId = resultData[indexPath.row].courseId
        nextVC.courseTitle = resultData[indexPath.row].title
        
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        
        if resultCV.contentOffset.y > (resultCV.contentSize.height - resultCV.bounds.size.height) {
            if !isPaging {
                self.getData(page: self.page)
            }
        }
        
    }
}

// MARK: - UICollectionViewDataSource

extension ResultVC: UICollectionViewDataSource {
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: CourseThumbnailCollectionViewCell.identifier, for: indexPath) as? CourseThumbnailCollectionViewCell else { return UICollectionViewCell() }

//        cell.setData(resultData[indexPath.row])
        cell.setTemp(resultData[indexPath.row])
        
        return cell
    }
}

// MARK: - UICollectionViewDelegateFlowLayout

extension ResultVC: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        
        let cellWidth = (UIScreen.main.bounds.width - (10 * 3)) / 2
        let cellHeight = cellWidth

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
