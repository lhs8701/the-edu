//
//  CurriculumDataModel.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/07.
//

import Foundation

struct CurriculumDataModel: Codable {
    let chapters: [chapterData]
}

struct chapterData: Codable {
    let title: String
    let units: [unitData]
}

struct unitData: Codable {
    let unitId: Int
    let title: String
    let completed: Bool
}
