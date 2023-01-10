//
//  MyCourseCVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/10.
//

import UIKit

class MyCourseCVC: UICollectionViewCell {
    
    static let identifier = "MyCourseCVC"

    @IBOutlet weak var thumbnailImage: UIImageView!
    @IBOutlet weak var courseTitle: UILabel!
    @IBOutlet weak var unitTitle: UILabel!
    @IBOutlet weak var progressUnitCount: UILabel!
    @IBOutlet weak var totalUnitCount: UILabel!
    @IBOutlet weak var percentage: UILabel!
    
    @IBOutlet weak var progressBar: UIProgressView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        layer.cornerRadius = 7
    }

    func setData(_ myCourseData: MyCourseDataModel) {
        thumbnailImage.image = myCourseData.thumbnailImage
        courseTitle.text = myCourseData.courseTitle
        unitTitle.text = myCourseData.unitTitle
        progressUnitCount.text = String(myCourseData.progressUnitCount)
        totalUnitCount.text = String(myCourseData.totalUnitCount)
        percentage.text = String((myCourseData.percentage ?? 0.0) * 100)
        
        progressBar.progress = Float(myCourseData.percentage ?? 0.0)
    }
}
