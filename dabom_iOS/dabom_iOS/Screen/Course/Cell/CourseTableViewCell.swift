//
//  CourseTableViewCell.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/05.
//

import UIKit

protocol CourseCVCellDelegate {
    func CourseSelectedCVCell(courseId: Int, courseName: String)
}

class CourseTableViewCell: UITableViewCell {

    // MARK: - IBOutlet
    
    @IBOutlet weak var rankingCategoryTitle: UILabel!
    @IBOutlet weak var courseThumbnailCollectionView: UICollectionView!
    
    
    // MARK: - let, var
    
    static let identifier = "CourseTableViewCell"
    var thumbnailData: [SampleCourseThumbnail] = []
    var delegate: CourseCVCellDelegate?
    
    var defaultImageView: UIImageView = UIImageView(image: UIImage(named: "default_course"))

    
    // MARK: - Life Cycle
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        configureView()
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

    }
    
    override func prepareForReuse() {
        super.prepareForReuse()
        
        rankingCategoryTitle.layer.sublayers?.remove(at: 0)
        rankingCategoryTitle.text = nil
        rankingCategoryTitle.sizeToFit()
        thumbnailData = [SampleCourseThumbnail]()
        courseThumbnailCollectionView.reloadData()
    }
    
    
    // MARK: - configure
    
    private func configureView() {
        contentView.addSubview(defaultImageView)
        defaultImageView.contentMode = .scaleAspectFit
        defaultImageView.snp.makeConstraints {
            $0.center.equalTo(courseThumbnailCollectionView.snp.center)
            $0.leading.trailing.equalToSuperview()
            $0.height.equalTo(300)
        }
        defaultImageView.isHidden = true
        
        setCV()
    }
    
    
    // MARK: - CollectionView Setting
    
    private func setCV() {
        self.courseThumbnailCollectionView.register(UINib(nibName: Const.Xib.Name.courseThumbnailCVC, bundle: nil), forCellWithReuseIdentifier: Const.Xib.Name.courseThumbnailCVC)
        self.courseThumbnailCollectionView.delegate = self
        self.courseThumbnailCollectionView.dataSource = self
        self.courseThumbnailCollectionView.isScrollEnabled = false
    }
    
    
    // MARK: - 랭킹별 강좌 Setting
    
    func setData(courseRankingData: CourseRankingDataModel) {
        rankingCategoryTitle.text = "\(courseRankingData.category) 클래스 랭킹"
        rankingCategoryTitle.sizeToFit()
        rankingCategoryTitle.layer.drawLineAt(edges: [.bottom], color: UIColor(named: "mainColor") ?? .yellow, width: 4.0)
        
        thumbnailData = courseRankingData.courseList
        
        if thumbnailData.isEmpty {
            self.defaultImageView.isHidden = false
        } else {
            self.defaultImageView.isHidden = true
        }
    }
    
}


// MARK: - UICollectionViewDelegate

extension CourseTableViewCell: UICollectionViewDelegate {
    
    // thumbnail Data 개수만큼
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.thumbnailData.count
    }
    
    // cell 선택했을 때 delegte 패턴 통해 화면 이동
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        if let delegate = delegate {
            delegate.CourseSelectedCVCell(courseId: thumbnailData[indexPath.row].courseId, courseName: thumbnailData[indexPath.row].title)
        }
    }
    
}


// MARK: - UICollectionViewDataSource

extension CourseTableViewCell: UICollectionViewDataSource {

    // cell 설정
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: CourseThumbnailCollectionViewCell.identifier, for: indexPath) as? CourseThumbnailCollectionViewCell else { return UICollectionViewCell() }

        cell.setTemp(thumbnailData[indexPath.row])
        
        return cell
    }
    
    
}


// MARK: - UICollectionViewDelegateFlowLayout

extension CourseTableViewCell: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        
        let cellWidth = (UIScreen.main.bounds.width - (20 * 3)) / 2
        let cellHeight = cellWidth
        
        return CGSize(width: cellWidth, height: cellHeight)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {
        
        return UIEdgeInsets(top: 3, left: 3, bottom: 3, right: 3)
  }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumInteritemSpacingForSectionAt section: Int) -> CGFloat {
        let spacingSize = 5
        
        return CGFloat(spacingSize)
    }
}
