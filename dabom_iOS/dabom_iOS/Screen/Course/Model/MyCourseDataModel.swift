//
//  MyCourseDataModel.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/10.
//

import UIKit

struct MyCourseDataModel: Codable {
    let courseId: Int
    let title: String
    let instructor: String
    let category: String
    let completedUnits: Int
    let entireUnits: Int
    let nextUnitInfo: NextUnitInfo
    let thumbnailImage: ImageDataModel
}

struct NextUnitInfo: Codable {
    let unitId: Int
    let title: String
    let time: Int
}
