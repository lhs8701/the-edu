//
//  GetCategoryDataService.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/04.
//

import Foundation
import Alamofire

struct GetCategoryDataService {
    static let shared = GetCategoryDataService()
    
    // MARK: - 카테고리 목록 가져오기
    func getCategory(completion: @escaping (NetworkResult<Any>) -> Void) {
        let URL = Const.Url.getCategory
        
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
            print("Status 400")
            return .pathErr
        case 500:
            print("Status 500")
            return .serverErr
        default:
            return .networkFail
        }

    }
    
    // MARK: - JSON Parsing
    private func isValidData(data: Data) -> NetworkResult<Any> {
        let decoder = JSONDecoder()
        
        guard let decodedData = try? decoder.decode([CategoryDataModel].self, from: data) else {
            print("decode fail")
            return .pathErr }
        
        return .success(decodedData)
    }
}
