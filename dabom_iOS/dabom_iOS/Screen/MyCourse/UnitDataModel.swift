//
//  UnitDataModel.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/03.
//

import Foundation

struct UnitDataModel: Codable {
    let unitId: Int
    let sequence: Int
    let chapterId: Int?
    let title: String
    let description: String
    let videoInfo: videoData
    
}

struct videoData: Codable {
    let filePath: String
}
