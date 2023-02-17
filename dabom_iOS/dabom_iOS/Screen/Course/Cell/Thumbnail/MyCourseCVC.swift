//
//  MyCourseCVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/10.
//

import UIKit

class MyCourseCVC: UICollectionViewCell {

    // MARK: - IBOutlet

    @IBOutlet weak var thumbnailImage: UIImageView!
    @IBOutlet weak var courseTitle: UILabel!
    @IBOutlet weak var unitTitle: UILabel!
    @IBOutlet weak var progressUnitCount: UILabel!
    @IBOutlet weak var totalUnitCount: UILabel!
    @IBOutlet weak var percentage: UILabel!
    @IBOutlet weak var progressBar: UIProgressView!
    
    
    // MARK: - let, var
    
    static let identifier = "MyCourseCVC"
    
    
    // MARK: - Life Cycle
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        configureView()
    }

    
    // MARK: - configure
    
    private func configureView() {
        layer.masksToBounds = false
        layer.shadowColor = UIColor.black.cgColor
        layer.shadowOpacity = 0.5
        layer.shadowRadius = 2
        layer.shadowOffset = CGSize(width: 0, height: 0)

        contentView.layer.cornerRadius = 7
        contentView.layer.masksToBounds = true
        
        courseTitle.adjustsFontSizeToFitWidth = true
    }
    
    
    // MARK: - func
    
    func setData(_ myCourseData: MyCourseDataModel) {
        let rate = Float(myCourseData.completedUnits) / Float(myCourseData.entireUnits)
        
        thumbnailImage.setImage(with: myCourseData.thumbnailImage.mediumFilePath)
        courseTitle.text = myCourseData.title
        unitTitle.text = myCourseData.nextUnitInfo.title
        progressUnitCount.text = String(myCourseData.completedUnits)
        totalUnitCount.text = String(myCourseData.entireUnits)
    
        percentage.text = String(format: "%.1f", rate * 100)
        progressBar.progress = rate
    }
    
}
