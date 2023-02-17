//
//  EventDataModel.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/08.
//

import Foundation

struct EventDataModel: Codable {
    let id: Int
    let title: String
    let content: String
    let writer: String
    let startDate: String
    let endDate: String
    let bannerImage: ImageDataModel
    let dday: Int
}
