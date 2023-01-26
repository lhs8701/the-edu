//
//  UserGrantInfoDataModel.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/19.
//

import Foundation

struct UserGrantInfoDataModel: Codable {
    let memberId: Int
    let grantType: String
    let accessToken: String
    let refreshToken: String
}
