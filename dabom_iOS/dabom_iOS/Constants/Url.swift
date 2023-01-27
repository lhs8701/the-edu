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
        static let baseUrl = "http://\(serverIP)/api"
        
        // MARK: - Authentication
        static let signup = "http://\(serverIP)/api/auth/basic/signup"
        static let login = "http://\(serverIP)/api/auth/basic/login"
        static let withdraw = "http://\(serverIP)/api/auth/basic/withdraw"
        static let logout = "http://\(serverIP)/api/auth/basic/logout"
        static let reissue = "http://\(serverIP)/api/auth/reissue"
        
        static let kakaoLogin = "http://\(serverIP)/api/auth/kakao/login"
        static let kakaoLogout = "http://\(serverIP)/api/auth/kakao/logout"
        static let kakaoWithdraw = "http://\(serverIP)/api/auth/kakao/withdraw"
        
        // MARK: - Member
        static let getProfile = "http://\(serverIP)/api/members"
        static let patchProfile = "http://\(serverIP)/api/members"
        
        // MARK: - Student
        static let getMyCourses = "http://\(serverIP)/api/students"
        static let getMyWishCourses = "http://\(serverIP)/api/students"
        
        // MARK: - Course
        static let getCourseInfo = "http://\(serverIP)/api/courses"
        static let getCourseReview = "http://\(serverIP)/api/courses"
        static let getCourseInquiries = "http://\(serverIP)/api/courses"
        static let getCategoryCourses = "http://\(serverIP)/api/courses/category"
        static let changeWishStatus = "http://\(serverIP)/api/courses/wish"
        static let isWishCourse = "http://\(serverIP)/api/courses/wish/check"
        
        // MARK: - Inquiry
        static let postInquiry = "http://\(serverIP)/api/courses/inquiries"
        static let getInquiry = "http://\(serverIP)/api/courses"
        
        // MARK: - Review
        static let postReview = "http://\(serverIP)/api/courses/reviews"
        static let getReview = "http://\(serverIP)/api/courses"
        
        
    }
}
