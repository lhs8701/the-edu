//
//  CourseRankingDataModel.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/08.
//

import Foundation

struct CourseRankingDataModel: Codable {
    let category: String
    let courseList: [SampleCourseThumbnail]
}
