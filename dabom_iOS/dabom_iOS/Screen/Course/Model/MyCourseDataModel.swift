//
//  MyCourseDataModel.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/10.
//

import UIKit

struct MyCourseDataModel: Codable {
//    let thumbnailImageName: String
//    var thumbnailImage: UIImage? {
//        return UIImage(named: thumbnailImageName)
//    }
//    let courseTitle: String
//    let unitTitle: String
//    let creatorName: String
//
//    let progressUnitCount: Int
//    let totalUnitCount: Int
//
//    var percentage: Double? {
//        return Double(progressUnitCount) / Double(totalUnitCount)
//    }
    let courseId: Int
    let title: String
    let instructor: String
    let category: String
    let thumbnailImage: ImageDataModel
    let completedNumber: Int
}

//extension MyCourseDataModel {
//    static let sampleData : [MyCourseDataModel] = [
//        MyCourseDataModel(thumbnailImageName: "testThumb01", courseTitle: "디지털 노마드", unitTitle: "디지털 노마드08", creatorName: "김태현", progressUnitCount: 8, totalUnitCount: 20),
//        MyCourseDataModel(thumbnailImageName: "testThumb03", courseTitle: "초보자를 위한 헬스", unitTitle: "초보자를 위한 헬스14", creatorName: "김태현", progressUnitCount: 14, totalUnitCount: 20),
//        MyCourseDataModel(thumbnailImageName: "testThumb02", courseTitle: "강아지 훈육하기", unitTitle: "강아지 훈육하기16", creatorName: "김태현", progressUnitCount: 16, totalUnitCount: 20)
////        MyCourseDataModel(thumbnailImageName: "testThumb04", courseTitle: "나만의 여행 가이드", unitTitle: "나만의 여행 가이드 04", creatorName: "김태현", progressUnitCount: 4, totalUnitCount: 20)
//    ]
//}
