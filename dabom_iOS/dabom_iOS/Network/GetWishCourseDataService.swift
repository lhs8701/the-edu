//
//  GetWishCourseDataService.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/26.
//

import Foundation
import Alamofire

struct GetWishCourseDataService {
    static let shared = GetWishCourseDataService()
    
    func getWishCourse(memberId: Int, completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.getMyWishCourses)/\(memberId)/courses/wish"
        print(URL)
        
        let accessToken = UserDefaults.standard.string(forKey: "accessToken")
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json",
            "X-AUTH-TOKEN" : accessToken!
        ]
        
        let request = AF.request(URL, method: .get, encoding: JSONEncoding.default, headers: header)
        
        request.responseData { dataResponse in
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
            case 500:
                print("Status 500")
                return .serverErr
            default:
                return .networkFail
            }
        }
        
    }
    
    private func isValidData(data: Data) -> NetworkResult<Any> {
        let decoder = JSONDecoder()
        
        guard let decodedData = try? decoder.decode([SampleCourseThumbnail].self, from: data) else {
            print("decode fail")
            return .pathErr }
        
        return .success(decodedData)
    }
}


