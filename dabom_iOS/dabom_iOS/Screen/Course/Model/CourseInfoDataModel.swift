//
//  CourseInfoDataModel.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/18.
//

import UIKit

struct CourseInfoDataModel: Codable {
    let id: Int
    let title: String
    let description: String
    let instructor: String
    let category: String
    let thumbnailImage: ImageDataModel
    let descriptionImages: [ImageDataModel]
    let score: Double
    let price: Int
    let wish: Int
}
