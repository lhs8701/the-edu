//
//  CourseThumbnailDataModel.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/05.
//

import UIKit

struct CourseThumbnailDataModel {
    let thumbnailImageName: String
    var thumbnailImage: UIImage? {
        return UIImage(named: thumbnailImageName)
    }
    
    let courseTitle: String
    let creatorName: String
    let categoryName: String
}
