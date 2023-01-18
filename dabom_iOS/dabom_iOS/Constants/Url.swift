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
        
        // MARK: - Course
        static let getCourseInfo = "http://\(serverIP)/api/courses/"
        static let getCourseReview = "http://\(serverIP)/api/courses/"
        static let getCourseInquiries = "http://\(serverIP)/api/courses/"
        
        static let getCategoryCourses = "http://\(serverIP)/api/courses/category/"
        
        // MARK: - Student
        static let getMyCourses = "http://\(serverIP)/api/students/"
        static let getMyWishCourses = "http://\(serverIP)/api/students/"
        
        // MARK: - Authentication
        static let signup = "http://\(serverIP)/api/auth/basic/signup"
        static let login = "http://\(serverIP)/api/auth/basic/login"
        
    }
}
