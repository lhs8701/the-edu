//
//  GetCourseInfoDataService.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/18.
//

import Foundation
import Alamofire

struct CourseInfoDataService {
    static let shared = CourseInfoDataService()
    
    // MARK: - Course 기본 정보 가져오기
    func getCourseInfo(id: Int, completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.getCourseInfo)/\(id)"
        print(URL)
        let dataRequest = AF.request(URL, method: .get, encoding: JSONEncoding.default)
        
        dataRequest.responseData { dataResponse in
            switch dataResponse.result {
            case .success:
                guard let statusCode = dataResponse.response?.statusCode else {return}
                guard let value = dataResponse.value else {return}
                
                let networkResult = self.judgeStatus(by: statusCode, value)
                completion(networkResult)
            case .failure:
                completion(.pathErr)
            }
        }
    }
    
    // MARK: - Course가 찜한 강좌인지 확인
    func isWishCourse(courseId: Int, completion: @escaping (Bool) -> Void) {
        let URL = "\(Const.Url.isWishCourse)/\(courseId)/wish/check"
        let accessToken = UserDefaults.standard.string(forKey: "accessToken")
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json",
            "X-AUTH-TOKEN" : accessToken!
        ]
        
        let request = AF.request(URL, method: .post, encoding: JSONEncoding.default, headers: header)
        
        request.responseData { dataResponse in
            debugPrint(dataResponse)
            switch dataResponse.result {
            case .success:
                guard let statusCode = dataResponse.response?.statusCode else {return}
                guard let value = dataResponse.value else {return}
                
                let data = String(data: value, encoding: .utf8).flatMap(Bool.init) ?? false
                
                switch statusCode {
                case 200:
                    completion(data)
                case 400, 500:
                    completion(false)
                default:
                    completion(false)
                }
            case .failure:
                print("err")
            }
            
        }
        
    }
    
    // MARK: - 찜하기
    func changeWishCourse(courseId: Int, completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.changeWishStatus)/\(courseId)/wish"
        print(URL)
        let accessToken = UserDefaults.standard.string(forKey: "accessToken")
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json",
            "X-AUTH-TOKEN" : accessToken!
        ]
        
        let request = AF.request(URL, method: .post, encoding: JSONEncoding.default, headers: header)
        
        request.responseData(emptyResponseCodes: [200, 204, 205]) { dataResponse in
            debugPrint(dataResponse)
            switch dataResponse.result {
            case .success:
                guard let statusCode = dataResponse.response?.statusCode else {return}
                
                let networkResult = self.judgeStatus(by: statusCode, nil)
                completion(networkResult)
            case .failure:
                completion(.pathErr)
            }
        }
    }
    
    // MARK: - 수강 신청하기
    func enrollCourse(courseId: Int, completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.enrollCourse)/\(courseId)/enroll"
        print(URL)
        let accessToken = UserDefaults.standard.string(forKey: "accessToken")
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json",
            "X-AUTH-TOKEN" : accessToken!
        ]
        
        let request = AF.request(URL, method: .post, encoding: JSONEncoding.default, headers: header)
        
        request.responseData(emptyResponseCodes: [200, 204, 205]) { dataResponse in
            debugPrint(dataResponse)
            switch dataResponse.result {
            case .success:
                guard let statusCode = dataResponse.response?.statusCode else {return}
                
                let networkResult = self.judgeStatus(by: statusCode, nil)
                completion(networkResult)
            case .failure:
                completion(.pathErr)
            }
        }
        
    }
    
    
    // MARK: - Status Code 분기
    private func judgeStatus(by statusCode: Int, _ data: Data?) -> NetworkResult<Any> {
        
        if let data = data {
            switch statusCode {
            case 200:
                return isValidData(data: data)
            case 400:
                print("Status 400")
                return .pathErr
            case 500:
                print("Status 500")
                return .serverErr
            default:
                return .networkFail
            }
        } else {
            switch statusCode {
            case 200:
                return .success(true)
            case 400:
                print("Status 400")
                return .pathErr
            case 404:
                return .resourceErr
            case 500:
                print("Status 500")
                return .serverErr
            default:
                return .networkFail
            }
        }
        
    }
    
    // MARK: - JSON Parsing
    private func isValidData(data: Data) -> NetworkResult<Any> {
        let decoder = JSONDecoder()
        
        guard let decodedData = try? decoder.decode(CourseInfoDataModel.self, from: data) else {
            print("decode fail")
            return .pathErr }
        
        return .success(decodedData)
    }
}
