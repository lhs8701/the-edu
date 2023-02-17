//
//  TicketDataModel.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/17.
//

import Foundation

struct TicketDataModel: Codable {
    let id: Int
    let costPrice: Int
    let discountedPrice: Int
    let coursePeriod: CoursePeriod
}

struct CoursePeriod: Codable {
    let code: String
    let description: String
}
