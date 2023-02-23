//
//  CurriculumDataModel.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/09.
//

import Foundation

struct CurriculumDataModel: Codable {
    let chapters: [Chapters]
}

struct Chapters: Codable {
    let title: String
    let units: [Units]
}

struct Units: Codable {
    let unitId: Int
    let title: String
}
