//
//  CourseTableDataModel.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/05.
//

import UIKit

struct CourseTableDataModel {
    var rankingCategoryTitle: String
    
    var thumbnailData: Array<CourseThumbnailDataModel>
}

extension CourseTableDataModel {
    static let sampleData: [CourseTableDataModel] = [
        CourseTableDataModel(rankingCategoryTitle: "주간 클래스 랭킹", thumbnailData: [
            CourseThumbnailDataModel(thumbnailImageName: "testThumb01", courseTitle: "디지털 노마드", creatorName: "김태현", categoryName: "프로그래밍"),
            CourseThumbnailDataModel(thumbnailImageName: "testThumb02", courseTitle: "강아지 훈육하기", creatorName: "김태현", categoryName: "자격증"),
            CourseThumbnailDataModel(thumbnailImageName: "testThumb03", courseTitle: "초보자를 위한 헬스", creatorName: "김태현", categoryName: "재테크"),
            CourseThumbnailDataModel(thumbnailImageName: "testThumb04", courseTitle: "나만의 여행 가이드", creatorName: "김태현", categoryName: "재테크"),
        ]),
        CourseTableDataModel(rankingCategoryTitle: "프로그래밍 클래스 랭킹", thumbnailData: [
            CourseThumbnailDataModel(thumbnailImageName: "testThumb01", courseTitle: "디지털 노마드", creatorName: "김태현", categoryName: "프로그래밍"),
            CourseThumbnailDataModel(thumbnailImageName: "testThumb02", courseTitle: "강아지 훈육하기", creatorName: "김태현", categoryName: "자격증"),
            CourseThumbnailDataModel(thumbnailImageName: "testThumb03", courseTitle: "초보자를 위한 헬스", creatorName: "김태현", categoryName: "재테크"),
            CourseThumbnailDataModel(thumbnailImageName: "testThumb04", courseTitle: "나만의 여행 가이드", creatorName: "김태현", categoryName: "재테크"),
        ]),
        CourseTableDataModel(rankingCategoryTitle: "자격증 클래스 랭킹", thumbnailData: [
            CourseThumbnailDataModel(thumbnailImageName: "testThumb01", courseTitle: "디지털 노마드", creatorName: "김태현", categoryName: "프로그래밍"),
            CourseThumbnailDataModel(thumbnailImageName: "testThumb02", courseTitle: "강아지 훈육하기", creatorName: "김태현", categoryName: "자격증"),
            CourseThumbnailDataModel(thumbnailImageName: "testThumb03", courseTitle: "초보자를 위한 헬스", creatorName: "김태현", categoryName: "재테크"),
            CourseThumbnailDataModel(thumbnailImageName: "testThumb04", courseTitle: "나만의 여행 가이드", creatorName: "김태현", categoryName: "재테크"),
        ]),
        CourseTableDataModel(rankingCategoryTitle: "재테크 클래스 랭킹", thumbnailData: [
            CourseThumbnailDataModel(thumbnailImageName: "testThumb01", courseTitle: "디지털 노마드", creatorName: "김태현", categoryName: "프로그래밍"),
            CourseThumbnailDataModel(thumbnailImageName: "testThumb02", courseTitle: "강아지 훈육하기", creatorName: "김태현", categoryName: "자격증"),
            CourseThumbnailDataModel(thumbnailImageName: "testThumb03", courseTitle: "초보자를 위한 헬스", creatorName: "김태현", categoryName: "재테크"),
            CourseThumbnailDataModel(thumbnailImageName: "testThumb04", courseTitle: "나만의 여행 가이드", creatorName: "김태현", categoryName: "재테크"),
        ])
    ]
}
