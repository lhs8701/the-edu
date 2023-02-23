//
//  CourseInquiryDataModel.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/30.
//

import Foundation
import Alamofire

struct CourseInquiryDataModel: Codable {
    let inquiryId: Int
    let course: String
    let content: String
    let likes: Int
    let writer: writerDataModel
    let reply: replyDataModel?
}

struct replyDataModel: Codable {
    let content: String
    let writer: String
    let createdTime: String
    let modifiedTime: String
}
