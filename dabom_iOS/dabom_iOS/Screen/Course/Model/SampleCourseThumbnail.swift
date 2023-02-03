//
//  SampleCourseThumbnail.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/26.
//

import Foundation

struct SampleCourseThumbnail: Codable {
    var courseId: Int
    var title: String
    var instructor: String
    var category: String
    var thumbnailImage: ImageDataModel
}
