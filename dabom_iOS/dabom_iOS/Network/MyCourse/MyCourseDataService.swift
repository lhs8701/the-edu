//
//  MyCourseDataService.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/06.
//

import Foundation
import Alamofire

struct MyCourseDataService {
    static let shared = MyCourseDataService()
    
    // MARK: - 덜 본 강좌 불러오기
    func getOngoing(completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.getMyOngoingCourses)"
        
        let accessToken = UserDefaults.standard.string(forKey: "accessToken") ?? ""
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json",
            "ACCESS" : accessToken
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
    
    // MARK: - 다 본 강좌 불러오기
    func getCompleted(completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.getMyCompletedCourses)"
        
        let accessToken = UserDefaults.standard.string(forKey: "accessToken") ?? ""
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json",
            "ACCESS" : accessToken
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
    
    // MARK: - Status Code 분기
    private func judgeStatus(by statusCode: Int, _ data: Data) -> NetworkResult<Any> {
        
        switch statusCode {
        case 200:
            return isValidData(data: data)
        case 400:
            return .pathErr
        case 500:
            return .serverErr
        default:
            return .networkFail
        }
        
    }
    
    // MARK: - JSON Parsing
    private func isValidData(data: Data) -> NetworkResult<Any> {
        let decoder = JSONDecoder()
        
        guard let decodedData = try? decoder.decode([MyCourseDataModel].self, from: data) else {
            print("decode fail")
            return .pathErr }
        
        return .success(decodedData)
    }
}
