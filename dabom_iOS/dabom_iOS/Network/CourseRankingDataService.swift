//
//  GetCourseRankingDataService.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/08.
//

import Foundation
import Alamofire

struct CourseRankingDataService {
    static let shared = CourseRankingDataService()
    
    func getCourseRanking(completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.getCourseRanking)"
        print(URL)
        
        let request = AF.request(URL, method: .get, encoding: JSONEncoding.default)
        
        request.responseData { dataResponse in
            debugPrint(dataResponse)
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
            print("statusCode 400")
            return .pathErr
        case 401, 404:
            print("잘못된 리소스")
            return .resourceErr
        case 500:
            return .serverErr
        default:
            return .networkFail
        }
        
    }
    
    // MARK: - JSON Parsing
    private func isValidData(data: Data) -> NetworkResult<Any> {
        let decoder = JSONDecoder()
        
        guard let decodedData = try? decoder.decode([CourseRankingDataModel].self, from: data) else {
            print("decode fail")
            return .pathErr }
        
        return .success(decodedData)
    }
}
