//
//  CurriculumDataModel.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/07.
//

import Foundation

struct CurriculumDataModel: Codable {
    let chapterList: [chapterData]
}

struct chapterData: Codable {
    let title: String
    let unitList: [unitData]
}

struct unitData: Codable {
    let unitId: Int
    let title: String
}
