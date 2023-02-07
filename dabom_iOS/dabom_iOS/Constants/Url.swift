//
//  Url.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/18.
//

import Foundation

extension Const {
    struct Url {
        
        static let serverIP = "218.38.127.26:8080"
        static let baseUrl = "http://the-edu.co.kr"
        static let apiBaseUrl = "http://\(serverIP)/api"
//        static let m3u8Test = "https://d2zihajmogu5jn.cloudfront.net/bipbop-advanced/bipbop_16x9_variant.m3u8"
        static let m3u8Test = "http://the-edu.co.kr/static/videos/test-m3u8/test.m3u8"
        
        static let getCategory = "\(apiBaseUrl)/ref/category"
        
        // MARK: - Authentication
        static let signup = "http://\(serverIP)/api/auth/basic/signup"
        static let login = "http://\(serverIP)/api/auth/basic/login"
        static let withdraw = "http://\(serverIP)/api/auth/basic/withdraw"
        static let logout = "http://\(serverIP)/api/auth/basic/logout"
        static let reissue = "http://\(serverIP)/api/auth/reissue"
        
        static let kakaoLogin = "http://\(serverIP)/api/auth/kakao/login"
        static let kakaoLogout = "http://\(serverIP)/api/auth/kakao/logout"
        static let kakaoWithdraw = "http://\(serverIP)/api/auth/kakao/withdraw"
        
        static let appleSignup = "\(apiBaseUrl)/auth/apple/signup"
        static let appleLogin = "\(apiBaseUrl)/auth/apple/login"
        static let appleLogout = "\(apiBaseUrl)/auth/apple/logout"
        static let appleWithdraw = "\(apiBaseUrl)/auth/apple/withdraw"
        
        // MARK: - Member
        static let getProfile = "http://\(serverIP)/api/members"
        static let patchProfile = "http://\(serverIP)/api/members"
        
        // MARK: - MyCourse
        static let getMyCourses = "http://\(serverIP)/api/students"
        static let getMyWishCourses = "http://\(serverIP)/api/students"
        static let getMyOngoingCourses = "\(apiBaseUrl)/courses/ongoing"
        static let getMyCompletedCourses = "\(apiBaseUrl)/courses/completed"
        
        // MARK: - Course
        static let getCourseInfo = "http://\(serverIP)/api/courses"
        static let getCourseReview = "http://\(serverIP)/api/courses"
        static let getCourseInquiries = "http://\(serverIP)/api/courses"
        
        static let getSearchResult = "http://\(serverIP)/api/courses/keyword"
        static let getCategoryCourses = "http://\(serverIP)/api/courses/category"
        
        static let changeWishStatus = "http://\(serverIP)/api/courses"
        static let isWishCourse = "http://\(serverIP)/api/courses"
        
        static let enrollCourse = "\(apiBaseUrl)/courses"
        static let isEnrollCourse = "\(apiBaseUrl)/courses"
        
        // MARK: - Inquiry
        static let postInquiry = "http://\(serverIP)/api/courses/inquiries"
        static let getInquiry = "http://\(serverIP)/api/courses"
        
        // MARK: - Review
        static let postReview = "http://\(serverIP)/api/courses/reviews"
        static let getReview = "http://\(serverIP)/api/courses"
        
        // MARK: - Unit
        static let getUnit = "\(apiBaseUrl)/courses/units"
        static let saveRecord = "\(apiBaseUrl)/record/units"
        static let getRecord = "\(apiBaseUrl)/record/units"
        static let completeUnit = "\(apiBaseUrl)/record/complete/units"
        static let getCurriculum = "\(apiBaseUrl)/courses"
    }
}
