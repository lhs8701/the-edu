//
//  CategoryResultVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/12.
//

import UIKit
import SnapKit

class ResultVC: UIViewController {
    
    // MARK: - IBOutlet
    
    @IBOutlet weak var resultCV: UICollectionView!
    @IBOutlet weak var resultName: UILabel!
    @IBOutlet weak var resultKind: UILabel!
    
    
    // MARK: - let, var
    
    var resultTitle: String?
    var resultData: Array<SampleCourseThumbnail> = []
    var kind: String = "category"
    
    var page = 0
    var totalPage = 0
    var size = 10
    var isPaging: Bool = false
    var hasNextPage: Bool = false
    
    var defaultImageView: UIImageView = UIImageView(image: UIImage(named: "default_course"))
    
    
    // MARK: - Life Cycle
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setCV()
        configureView()
        setNavi()
    }
    
    
    // MARK: - configure
    
    private func configureView() {
        resultName.text = resultTitle ?? ""
        
        // 이전 뷰에서 넘겨준 kind 값이 category -> 카테고리 결과를 보여줌
        if kind == "category" {
            resultKind.text = "카테고리"
        } else if kind == "search" {
            // 이전 뷰에서 넘겨준 kind 값이 search -> 검색 결과를 보여줌
            resultKind.text = "검색 결과"
        }
        
        resultName.sizeToFit()
        resultName.layer.drawLineAt(edges: [.bottom], color: UIColor(named: "mainColor")!, width: 3.0)
        
        
        // 결과가 없을 때 보여줄 defulatImage 설정
        view.addSubview(defaultImageView)
        defaultImageView.contentMode = .scaleAspectFit
        defaultImageView.snp.makeConstraints {
            $0.center.equalTo(resultCV.snp.center)
            $0.leading.trailing.equalToSuperview()
            $0.height.equalTo(300)
        }
        defaultImageView.isHidden = true
    }
    
    private func setNavi() {
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
    
    
    // MARK: - getData -> pagination
    
    func getData(page: Int) {
        
        // 한번에 여러번 pagination 되는 것을 막기 위한 flag
        self.isPaging = true
        
        // 총 페이지 수를 넘지 않았을 때만 다음 페이지 데이터 불러오기
        if page <= totalPage {
            GetPaginationDataService.shared.getPagination(kind: self.kind, keyword: self.resultTitle ?? "", page: self.page, size: self.size) { response in
                switch response {
                case .success(let paginationData):
                    if let data = paginationData as? PaginationDataModel {
                        self.page += 1
                        self.totalPage = data.totalPage
                        
                        // 받아온 데이터를 배열에 추가하고 reload
                        self.resultData.append(contentsOf: data.list)
                        self.resultCV.reloadData()
                        self.isPaging = false
                        
                        // 만약 받아온 결과가 없다면 defualt image 등장
                        if self.resultData.isEmpty {
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
    
}


// MARK: - UICollectionViewDelegate

extension ResultVC: UICollectionViewDelegate {
    
    // 받아온 데이터 갯수만큼 표시
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return resultData.count
    }
    
    // 썸네일 눌렀을 때 강좌 상세보기로 이동
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        guard let nextVC = UIStoryboard(name: Const.Storyboard.Name.courseInfoView, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.courseInfo) as? CourseInfoViewController else { return }
        
        nextVC.courseId = resultData[indexPath.row].courseId
        nextVC.courseTitle = resultData[indexPath.row].title
        
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
    }
    
    // 스크롤이 바닥에 닿았을 때 다음 페이지 불러오기 -> pagination
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        
        if resultCV.contentOffset.y > (resultCV.contentSize.height - resultCV.bounds.size.height) {
            // 현재 페이징 중이 아니라면
            if !isPaging {
                self.getData(page: self.page)
            }
        }
        
    }
}


// MARK: - UICollectionViewDataSource

extension ResultVC: UICollectionViewDataSource {
    
    // collectionView cell data setting
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: CourseThumbnailCollectionViewCell.identifier, for: indexPath) as? CourseThumbnailCollectionViewCell else { return UICollectionViewCell() }

        cell.setTemp(resultData[indexPath.row])
        
        return cell
    }
}


// MARK: - UICollectionViewDelegateFlowLayout

extension ResultVC: UICollectionViewDelegateFlowLayout {
    
    // collectionView cell 사이즈 지정
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
