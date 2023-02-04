//
//  CategoryDataModel.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/04.
//

import Foundation
import Alamofire

struct CategoryDataModel: Codable {
    let groupName: String
    let categoryList: [String]
}
