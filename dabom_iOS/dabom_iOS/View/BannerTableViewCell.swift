//
//  BannerTableViewCell.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/06.
//

import UIKit

class BannerTableViewCell: UITableViewCell {

    static let identifier = "BannerTableViewCell"
    
    @IBOutlet weak var bannerCollectionView: UICollectionView!
    
    var bannerData: Array<BannerDataModel>?
    
    var currentPage: Int = 0
    
    
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        bannerCollectionView.register(UINib(nibName: "BannerCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: "BannerCollectionViewCell")
        bannerCollectionView.delegate = self
        bannerCollectionView.dataSource = self
        
        bannerCollectionView.decelerationRate = .fast
        bannerCollectionView.isPagingEnabled = true
//        bannerCollectionView.scroll
        
        startAutoScroll()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func setData(_ bannerTableData: [BannerDataModel]) {
        bannerData = bannerTableData
    }
    
    func startAutoScroll() {
        let totalCellCount = bannerCollectionView.numberOfItems(inSection: 0)
        
        DispatchQueue.global(qos: .default).async {
            while true {
                sleep(3)
                DispatchQueue.main.async {
                    self.bannerCollectionView.scrollToItem(at: IndexPath(item: self.currentPage, section: 0), at: .right, animated: true)
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
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return 4
    }
    
}

extension BannerTableViewCell: UICollectionViewDataSource {
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "BannerCollectionViewCell", for: indexPath) as? BannerCollectionViewCell else { return UICollectionViewCell() }
        
//        cell.bannerImageView.image =
        cell.setData(bannerData![indexPath.row])
        
        return cell
    }
    
    
}

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

extension BannerTableViewCell: UIScrollViewDelegate {
    func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        let x = scrollView.contentOffset.x
        let w = scrollView.bounds.size.width
        currentPage = Int(ceil(x/w))
    }
}
