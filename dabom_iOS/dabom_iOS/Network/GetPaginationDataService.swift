//
//  GetPaginationDataService.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/27.
//

import Foundation
import Alamofire

struct GetPaginationDataService {
    static let shared = GetPaginationDataService()
    
    func getPagination(kind: String, keyword: String, page: Int, size: Int, completion: @escaping (NetworkResult<Any>) -> Void) {
        let sort = "title,ASC"
        
        var URL: String!
        
        if kind == "category" {
            URL = "\(Const.Url.getCategoryCourses)/\(keyword)?page=\(page)&size=\(size)&sort=\(sort)".addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed) ?? ""
        } else if kind == "search" {
            URL = "\(Const.Url.getSearchResult)/\(keyword)?page=\(page)&size=\(size)&sort=\(sort)".addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed) ?? ""
        }
        
        let request = AF.request(URL!, method: .get, encoding: JSONEncoding.default)
        
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
        
        guard let decodedData = try? decoder.decode(PaginationDataModel.self, from: data) else {
            print("decode fail")
            return .pathErr }
        
        return .success(decodedData)
    }
}
