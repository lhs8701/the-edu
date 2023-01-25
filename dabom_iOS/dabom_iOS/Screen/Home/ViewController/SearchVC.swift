//
//  SearchVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/10.
//

import UIKit

class SearchVC: UIViewController {

    @IBOutlet weak var searchBar: UISearchBar!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        self.navigationController?.setNavigationBarHidden(false, animated: true)
        self.navigationController?.navigationBar.topItem?.title = "검색"
        
        self.searchBar.delegate = self
        self.searchBar.searchBarStyle = .minimal
    }
    

}

extension SearchVC: UISearchBarDelegate {
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        
        guard let searchTerm = searchBar.text, searchTerm.isEmpty == false else {
            self.searchBar.placeholder = "검색어를 입력해주세요"
            
            searchBar.becomeFirstResponder()
            return
        }
        
        self.view.endEditing(true)
        
        let nextVC = UIStoryboard.init(name: Const.Storyboard.Name.homeTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.categoryResult) as! ResultVC
        
        nextVC.resultTitle = searchTerm
        nextVC.kind = "검색 결과"
        
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
        
        print("검색어 : \(searchTerm)")
    }
}
