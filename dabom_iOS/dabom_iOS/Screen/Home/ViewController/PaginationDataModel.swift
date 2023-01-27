//
//  PaginationDataModel.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/27.
//

import Foundation

struct PaginationDataModel: Codable {
    let page: Int
    let totalPage: Int
    let list: [SampleCourseThumbnail]
}
