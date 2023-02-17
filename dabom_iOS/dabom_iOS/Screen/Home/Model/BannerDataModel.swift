//
//  BannerDataModel.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/06.
//

import UIKit

struct BannerDataModel: Codable {
    let id: Int
    let title: String
    let startDate: String
    let endDate: String
    let bannerImage: ImageDataModel
    let dday: Int
}
