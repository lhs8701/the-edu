//
//  BannerTableViewCell.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/06.
//

import UIKit

// 배너 이미지 선택 시 화면 이동 위해 delegate 패턴 사용
protocol BannerCVCellDelegate {
    func BannerSelectedCVCell(eventId: Int, bannerName: String)
}

class BannerTableViewCell: UITableViewCell {
    
    // MARK: - IBOutlet
    
    @IBOutlet weak var bannerCollectionView: UICollectionView!
    
    
    // MARK: - let, var
    
    static let identifier = "BannerTableViewCell"
        
    var bannerData: [BannerDataModel] = []
    var delegate: BannerCVCellDelegate?
    var currentPage: Int = 0
    var autoStart: Bool = false
    
    
    // MARK: - Life Cycle
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        setCV()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

    }
    
    // MARK: - CollectionView Setting
    
    func setCV() {
        bannerCollectionView.register(UINib(nibName: Const.Xib.Name.bannerCVC, bundle: nil), forCellWithReuseIdentifier: Const.Xib.Identifier.bannerCVC)
        bannerCollectionView.delegate = self
        bannerCollectionView.dataSource = self
        
        bannerCollectionView.decelerationRate = .fast
        bannerCollectionView.isPagingEnabled = true
    }
    
    
    // MARK: - banner Data set
    
    func setData(bannerTableData: [BannerDataModel]) {
        bannerData = bannerTableData
        bannerCollectionView.reloadData()
        
        if autoStart {
            startAutoScroll()
        }
    }
    
    // MARK: - banner AutoScroll
    
    func startAutoScroll() {
        autoStart = false
        let totalCellCount = bannerData.count
        
        // 3초마다 배너 이미지 autoScroll
        DispatchQueue.global(qos: .default).async {
            while true {
                sleep(3)
                DispatchQueue.main.async {
                    // 오른쪽 방향으로
                    self.bannerCollectionView.scrollToItem(at: IndexPath(item: self.currentPage, section: 0), at: .right, animated: true)
                    
                    // 끝까지 갔으면 다시 첫번째로
                    if self.currentPage == totalCellCount - 1 {
                        self.currentPage = 0
                    } else {
                        self.currentPage += 1
                    }
                }
            }
        }
    }
    
}


// MARK: - UICollectionViewDelegate

extension BannerTableViewCell: UICollectionViewDelegate {
    
    // 배너 데이터 수만큼
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.bannerData.count
    }
    
    // 배너 이미지 선택 시 delegate 패턴을 통해 화면 이동
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        if let delegate = delegate {
            delegate.BannerSelectedCVCell(eventId: bannerData[indexPath.row].id, bannerName: bannerData[indexPath.row].title)
        }
    }
    
}


// MARK: - UICollectionViewDataSource

extension BannerTableViewCell: UICollectionViewDataSource {
    
    // collectionView cell 설정
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: Const.Xib.Identifier.bannerCVC, for: indexPath) as? BannerCollectionViewCell else { return UICollectionViewCell() }

        cell.setData(bannerData: self.bannerData[indexPath.row])
        
        return cell
    }
    
}


// MARK: - UICollectionViewDelegateFlowLayout

extension BannerTableViewCell: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        CGSize.init(width: bannerCollectionView.bounds.width, height: bannerCollectionView.bounds.height)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        return 0
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumInteritemSpacingForSectionAt section: Int) -> CGFloat {
        return 0
    }
}


// MARK: - UIScrollViewDelegate

extension BannerTableViewCell: UIScrollViewDelegate {
    
    // 배너를 유저가 스크롤했을 때 처리
    func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        let x = scrollView.contentOffset.x
        let w = scrollView.bounds.size.width
        currentPage = Int(ceil(x/w))
    }
    
}
