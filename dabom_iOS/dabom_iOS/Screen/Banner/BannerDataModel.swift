//
//  BannerDataModel.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/06.
//

import UIKit

struct BannerDataModel {
    let bannerImageName: String
    var bannerImage: UIImage? {
        return UIImage(named: bannerImageName)
    }
}


extension BannerDataModel {
    static let sampleData: [BannerDataModel] = [
        BannerDataModel(bannerImageName: "testThumb01"),
        BannerDataModel(bannerImageName: "testThumb02"),
        BannerDataModel(bannerImageName: "testThumb03"),
        BannerDataModel(bannerImageName: "testThumb04")
    ]
}
