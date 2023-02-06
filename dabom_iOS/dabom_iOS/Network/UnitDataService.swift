//
//  UnitDataService.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/03.
//

import Foundation
import Alamofire

struct UnitDataService {
    static let shared = UnitDataService()
    
    // MARK: - Unit 정보 가져오기
    func getUnit(unitId: Int, completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.getUnit)/\(unitId)"
        print(URL)
        
        let accessToken = UserDefaults.standard.string(forKey: "accessToken") ?? ""
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json",
            "X-AUTH-TOKEN" : accessToken
        ]
        
        let request = AF.request(URL, method: .get, encoding: JSONEncoding.default, headers: header)
        
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
    
    // MARK: - 시청 기록 가져오기
    func getRecord(unitId: Int, completion: @escaping (Double) -> Void) {
        let URL = "\(Const.Url.getRecord)/\(unitId)"
        print(URL)
        
        let accessToken = UserDefaults.standard.string(forKey: "accessToken") ?? ""
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json",
            "X-AUTH-TOKEN" : accessToken
        ]
        
        let request = AF.request(URL, method: .get, encoding: JSONEncoding.default, headers: header)
        
        request.responseData { dataResponse in
            debugPrint(dataResponse)
            switch dataResponse.result {
            case .success:
                guard let statusCode = dataResponse.response?.statusCode else {return}
                guard let value = dataResponse.value else {return}
                
                let data = Double(String(data: value, encoding: .utf8) ?? "0.0") ?? 0.0
                
                switch statusCode {
                case 200:
                    completion(data)
                case 400, 500:
                    completion(0.0)
                default:
                    completion(0.0)
                }
            case .failure:
                print("err")
            }
            
        }
    }
    
    // MARK: - 시청 기록 저장하기
    func saveRecord(unitId: Int, time: Double, completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.saveRecord)/\(unitId)"
        print(URL)
        
        let accessToken = UserDefaults.standard.string(forKey: "accessToken") ?? ""
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json",
            "X-AUTH-TOKEN" : accessToken
        ]
        
        let bodyData: Parameters = [
            "time" : time
        ] as Dictionary

        
        let request = AF.request(URL, method: .post, parameters: bodyData, encoding: JSONEncoding.default, headers: header)
        
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
    
    // MARK: - 강의 시청 완료 처리
    func completeUnit(unitId: Int, completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = "\(Const.Url.completeUnit)/\(unitId)"
        print(URL)
        
        let accessToken = UserDefaults.standard.string(forKey: "accessToken") ?? ""
        
        let header: HTTPHeaders = [
            "Content-Type" : "application/json",
            "X-AUTH-TOKEN" : accessToken
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
        } else {
            switch statusCode {
            case 200:
                return .success(true)
            case 400:
                print("statusCode 400")
                return .pathErr
            case 401:
                print("이미 계정이 존재합니다")
                return .pathErr
            case 500:
                return .serverErr
            default:
                print(statusCode)
                return .networkFail
            }
        }
        
    }
    
    // MARK: - JSON Parsing
    private func isValidData(data: Data) -> NetworkResult<Any> {
        let decoder = JSONDecoder()
        
        guard let decodedData = try? decoder.decode(UnitDataModel.self, from: data) else {
            print("decode fail")
            return .pathErr }
        
        return .success(decodedData)
    }
}
