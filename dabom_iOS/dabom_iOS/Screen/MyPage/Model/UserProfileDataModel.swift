//
//  UserProfileDataModel.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/02.
//

import Foundation

struct UserProfileDataModel: Codable {
    var id: Int?
    var account: String?
    var nickname: String?
    var email: String?
    var socialId: String?
    var profileImage: ImageDataModel
    var loginType: String?
    var certified: Bool?
}
