//
//  CourseThumbnailDataModel.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/05.
//

import UIKit

struct CourseThumbnailDataModel: Codable {
    let thumbnailImageName: String
    var thumbnailImage: UIImage? {
        return UIImage(named: thumbnailImageName)
    }
    
//    let courseID: Int
    let courseTitle: String
    let creatorName: String
    let categoryName: String
}

extension CourseThumbnailDataModel {
    static let sampleData: [CourseThumbnailDataModel] = [
        CourseThumbnailDataModel(thumbnailImageName: "testThumb01", courseTitle: "디지털 노마드", creatorName: "김태현", categoryName: "프로그래밍"),
        CourseThumbnailDataModel(thumbnailImageName: "testThumb02", courseTitle: "강아지 훈육하기", creatorName: "김태현", categoryName: "자격증"),
        CourseThumbnailDataModel(thumbnailImageName: "testThumb03", courseTitle: "초보자를 위한 헬스", creatorName: "김태현", categoryName: "재테크"),
        CourseThumbnailDataModel(thumbnailImageName: "testThumb04", courseTitle: "나만의 여행 가이드", creatorName: "김태현", categoryName: "재테크"),
        CourseThumbnailDataModel(thumbnailImageName: "testThumb01", courseTitle: "디지털 노마드", creatorName: "김태현", categoryName: "프로그래밍"),
        CourseThumbnailDataModel(thumbnailImageName: "testThumb02", courseTitle: "강아지 훈육하기", creatorName: "김태현", categoryName: "자격증"),
        CourseThumbnailDataModel(thumbnailImageName: "testThumb03", courseTitle: "초보자를 위한 헬스", creatorName: "김태현", categoryName: "재테크"),
        CourseThumbnailDataModel(thumbnailImageName: "testThumb04", courseTitle: "나만의 여행 가이드", creatorName: "김태현", categoryName: "재테크"),
        CourseThumbnailDataModel(thumbnailImageName: "testThumb04", courseTitle: "나만의 여행 가이드", creatorName: "김태현", categoryName: "재테크"),
        CourseThumbnailDataModel(thumbnailImageName: "testThumb01", courseTitle: "디지털 노마드", creatorName: "김태현", categoryName: "프로그래밍")
    ]
    
    static let sampleAPI: [CourseThumbnailDataModel] = [
        CourseThumbnailDataModel(thumbnailImageName: "testThumb01", courseTitle: "디지털 노마드", creatorName: "김태현", categoryName: "프로그래밍"),
        CourseThumbnailDataModel(thumbnailImageName: "testThumb02", courseTitle: "강아지 훈육하기", creatorName: "김태현", categoryName: "자격증"),
        CourseThumbnailDataModel(thumbnailImageName: "testThumb03", courseTitle: "초보자를 위한 헬스", creatorName: "김태현", categoryName: "재테크"),
        CourseThumbnailDataModel(thumbnailImageName: "testThumb04", courseTitle: "나만의 여행 가이드", creatorName: "김태현", categoryName: "재테크"),
        CourseThumbnailDataModel(thumbnailImageName: "testThumb01", courseTitle: "디지털 노마드", creatorName: "김태현", categoryName: "프로그래밍"),
        CourseThumbnailDataModel(thumbnailImageName: "testThumb02", courseTitle: "강아지 훈육하기", creatorName: "김태현", categoryName: "자격증"),
        CourseThumbnailDataModel(thumbnailImageName: "testThumb03", courseTitle: "초보자를 위한 헬스", creatorName: "김태현", categoryName: "재테크"),
        CourseThumbnailDataModel(thumbnailImageName: "testThumb04", courseTitle: "나만의 여행 가이드", creatorName: "김태현", categoryName: "재테크"),
        CourseThumbnailDataModel(thumbnailImageName: "testThumb04", courseTitle: "나만의 여행 가이드", creatorName: "김태현", categoryName: "재테크"),
        CourseThumbnailDataModel(thumbnailImageName: "testThumb01", courseTitle: "디지털 노마드", creatorName: "김태현", categoryName: "프로그래밍")
    ]
}
