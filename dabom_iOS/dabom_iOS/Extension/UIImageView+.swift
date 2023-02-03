//
//  UIImageView+.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/02.
//

import UIKit
import Kingfisher

extension UIImageView {
    func setImage(with urlString: String) {
        let imageUrl = "\(Const.Url.baseUrl)\(urlString)"
        
        ImageCache.default.retrieveImage(forKey: imageUrl, options: nil) { result in
            switch result {
            case .success(let value):
                if let image = value.image {
                    //캐시가 존재하는 경우
                    print("캐시 존재")
                    self.image = image
                } else {
                    //캐시가 존재하지 않는 경우
                    print("캐시 없음")
                    guard let url = URL(string: imageUrl) else { return }
                    let resource = ImageResource(downloadURL: url, cacheKey: imageUrl)
                    self.kf.setImage(with: resource)
                }
            case .failure(let error):
                print(error)
            }
        }
    }
}
