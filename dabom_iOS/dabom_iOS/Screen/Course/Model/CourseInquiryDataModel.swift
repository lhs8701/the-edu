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
    let writer: String
    let course: String
    let content: String
    let likes: Int
}
