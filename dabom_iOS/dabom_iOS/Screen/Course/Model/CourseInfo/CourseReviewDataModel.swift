//
//  CourseReviewDataModel.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/30.
//

import Foundation
import Alamofire

struct CourseReviewDataModel: Codable {
    let reviewId: Int
    let course: String
    let content: String
    let likes: Int
    let rating: Int
    let writer: writerDataModel
}

struct writerDataModel: Codable {
    let id: Int
    let nickname: String
    let profileImage: ImageDataModel
}
