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
        static let m3u8Test = "http://the-edu.co.kr/static/videos/test-m3u8/test.m3u8"
        
        static let getCategory = "\(apiBaseUrl)/ref/category"
        static let getOngoingBanner = "\(apiBaseUrl)/events/ongoing"
        static let getEvent = "\(apiBaseUrl)/events"
        
        static let uploadImage = "\(apiBaseUrl)/file/image"
        
        // MARK: - Authentication
        static let signup = "\(apiBaseUrl)/auth/basic/signup"
        static let login = "\(apiBaseUrl)/auth/basic/login"
        static let withdraw = "\(apiBaseUrl)/auth/basic/withdraw"
        static let logout = "\(apiBaseUrl)/auth/basic/logout"
        static let reissue = "\(apiBaseUrl)/auth/reissue"
        
        static let kakaoLogin = "\(apiBaseUrl)/auth/kakao/login"
        static let kakaoLogout = "\(apiBaseUrl)/auth/kakao/logout"
        static let kakaoWithdraw = "\(apiBaseUrl)/auth/kakao/withdraw"
        
        static let appleSignup = "\(apiBaseUrl)/auth/apple/signup"
        static let appleLogin = "\(apiBaseUrl)/auth/apple/login"
        static let appleLogout = "\(apiBaseUrl)/auth/apple/logout"
        static let appleWithdraw = "\(apiBaseUrl)/auth/apple/withdraw"
        
        // MARK: - Member
        static let getProfile = "\(apiBaseUrl)/members"
        static let patchProfile = "\(apiBaseUrl)/members"
        static let changePassword = "\(apiBaseUrl)/members/me/password/change"
        static let resetPassword = "\(apiBaseUrl)/members/me/password/reset"
        
        // MARK: - MyCourse
        static let getMyCourses = "\(apiBaseUrl)/students"
        static let getMyWishCourses = "\(apiBaseUrl)/wish/courses"
        static let getMyOngoingCourses = "\(apiBaseUrl)/courses/ongoing"
        static let getMyCompletedCourses = "\(apiBaseUrl)/courses/completed"
        
        // MARK: - Course
        static let getCourseInfo = "\(apiBaseUrl)/courses"
        static let getCourseReview = "\(apiBaseUrl)/courses"
        static let getCourseInquiries = "\(apiBaseUrl)/courses"
        
        static let getSearchResult = "\(apiBaseUrl)/courses/keyword"
        static let getCategoryCourses = "\(apiBaseUrl)/courses/category"
        
        static let changeWishStatus = "\(apiBaseUrl)/wish/courses"
        static let isWishCourse = "\(apiBaseUrl)/wish/courses"
        
        static let enrollCourse = "\(apiBaseUrl)/enroll/courses"
        static let isEnrollCourse = "\(apiBaseUrl)/enroll/courses"
        static let getTickets = "\(apiBaseUrl)/courses"
        
        static let postPurchase = "\(apiBaseUrl)/purchase/items"
        
        static let getCourseRanking = "\(apiBaseUrl)/courses/category/ranking"
        
        // MARK: - Inquiry
        static let postInquiry = "\(apiBaseUrl)/courses/inquiries"
        static let getInquiry = "\(apiBaseUrl)/courses"
        
        // MARK: - Review
        static let postReview = "\(apiBaseUrl)/courses/reviews"
        static let getReview = "\(apiBaseUrl)/courses"
        
        // MARK: - Unit
        static let getUnit = "\(apiBaseUrl)/courses/units"
        static let saveRecord = "\(apiBaseUrl)/record/units"
        static let getRecord = "\(apiBaseUrl)/record/units"
        static let completeUnit = "\(apiBaseUrl)/record/complete/units"
        static let getUserCurriculum = "\(apiBaseUrl)/courses"
    }
}
