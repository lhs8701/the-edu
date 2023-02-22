//
//  TicketDataService.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/17.
//

import Foundation
import Alamofire

struct TicketDataService {
    static let shared = TicketDataService()
    
    // MARK: - 강좌 수강권 정보 가져오기
    func getTickets(courseId: Int, completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.getTickets)/\(courseId)/tickets"
        print(URL)
        
        let request = AF.request(URL, method: .get, encoding: JSONEncoding.default)
        
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
    
    // MARK: - 강좌 주문 승인
    func postPurchase(itemId: Int, completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.postPurchase)/\(itemId)"
        
        let accessToken = UserDefaults.standard.string(forKey: "accessToken") ?? ""
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json",
            "ACCESS" : accessToken
        ]
        
        let bodyData: Parameters = [:
        ]
        
        let request = AF.request(URL, method: .post, parameters: bodyData, encoding: JSONEncoding.default, headers: header)
        
        request.responseData(emptyResponseCodes: [200, 201, 204, 205]) { dataResponse in
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
                return .pathErr
            case 403, 404:
                return .resourceErr
            case 500:
                return .serverErr
            default:
                return .networkFail
            }
        } else {
            switch statusCode {
            case 200:
                return .success(true)
            case 400:
                return .pathErr
            case 403, 404:
                return .resourceErr
            case 500:
                return .serverErr
            default:
                return .networkFail
            }
        }
        
    }
    
    // MARK: - JSON Parsing
    private func isValidData(data: Data) -> NetworkResult<Any> {
        let decoder = JSONDecoder()
        
        guard let decodedData = try? decoder.decode(TicketDataModel.self, from: data) else {
            print("decode fail")
            return .pathErr }
        
        return .success(decodedData)
    }

}
