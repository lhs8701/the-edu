//
//  SearchVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/10.
//

import UIKit

class SearchVC: UIViewController {

    // MARK: - IBOutlet
    @IBOutlet weak var searchBar: UISearchBar!
    
    @IBOutlet weak var recentSearchTV: UITableView!
    
    // MARK: - let, var
    var recentSearchList: Array<String>?
    
    // MARK: - Life Cycle
    override func viewDidLoad() {
        super.viewDidLoad()

        self.navigationController?.setNavigationBarHidden(false, animated: true)
        self.navigationController?.navigationBar.topItem?.title = "검색"
        
        self.searchBar.delegate = self
        self.searchBar.searchBarStyle = .minimal
        
        self.recentSearchTV.delegate = self
        self.recentSearchTV.dataSource = self
        self.searchBar.becomeFirstResponder()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.recentSearchList = UserDefaults.standard.array(forKey: "recentSearch") as? [String]
        self.recentSearchTV.reloadData()
        
    }
    

}

// MARK: - extension
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
        nextVC.kind = "search"
        
        if recentSearchList == nil {
            var newRecent = [String]()
            
            newRecent.append(searchTerm)
            
            UserDefaults.standard.set(newRecent, forKey: "recentSearch")
        } else {
            recentSearchList?.insert(searchTerm, at: 0)
            
            if recentSearchList!.count > 5 {
                recentSearchList?.remove(at: 5)
            }
            
            UserDefaults.standard.set(recentSearchList, forKey: "recentSearch")
        }
        
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
        
        print("검색어 : \(searchTerm)")
    }
}

extension SearchVC: UITableViewDelegate {
    
}

extension SearchVC: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        self.recentSearchList?.count ?? 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: Const.Xib.Identifier.recentSearchTVC, for: indexPath) as! RecentSearchTVC
        
        cell.recentSearchTerm.text = recentSearchList?[indexPath.row]
//        cell.selectionStyle = .none
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let nextVC = UIStoryboard.init(name: Const.Storyboard.Name.homeTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.categoryResult) as! ResultVC
        
        nextVC.resultTitle = recentSearchList?[indexPath.row]
        nextVC.kind = "search"
        tableView.deselectRow(at: indexPath, animated: true)
        
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
    }
}
