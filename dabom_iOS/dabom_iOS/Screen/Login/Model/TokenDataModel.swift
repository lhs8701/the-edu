//
//  TokenDataModel.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/10.
//

import Foundation

struct TokenDataModel: Codable {
    let accessToken: String
    let refreshToken: String
}
