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
        
        configureView()
        setNavi()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        getRecentSearch()
    }
    

    // MARK: - configure
    
    private func configureView() {
        self.searchBar.delegate = self
        self.searchBar.searchBarStyle = .minimal
        
        self.recentSearchTV.delegate = self
        self.recentSearchTV.dataSource = self
        self.searchBar.becomeFirstResponder()
    }
    
    private func setNavi() {
        self.navigationController?.setNavigationBarHidden(false, animated: true)
        self.navigationController?.navigationBar.topItem?.title = "검색"
    }
    
    
    // MARK: - func
    
    private func getRecentSearch() {
        self.recentSearchList = UserDefaults.standard.array(forKey: "recentSearch") as? [String]
        self.recentSearchTV.reloadData()
    }
    
}


// MARK: - extension

extension SearchVC: UISearchBarDelegate {
    
    // 검색 버튼 눌렀을 때
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        
        // 검색어 입력 안했을 때
        guard let searchTerm = searchBar.text, searchTerm.isEmpty == false else {
            self.searchBar.placeholder = "검색어를 입력해주세요"
            
            searchBar.becomeFirstResponder()
            return
        }
        
        // 키보드 내리기
        self.view.endEditing(true)
        
        // 검색 결과 화면
        let nextVC = UIStoryboard.init(name: Const.Storyboard.Name.homeTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.categoryResult) as! ResultVC
        
        nextVC.resultTitle = searchTerm
        nextVC.kind = "search"
        
        // 최근 검색어 없으면 배열 새로 만들어서 저장, 있으면 배열에 추가
        if recentSearchList == nil {
            var newRecent = [String]()
            
            newRecent.append(searchTerm)
            
            UserDefaults.standard.set(newRecent, forKey: "recentSearch")
        } else {
            recentSearchList?.insert(searchTerm, at: 0)
            
            // 최근 검색어 5개까지 저장
            if recentSearchList!.count > 5 {
                recentSearchList?.remove(at: 5)
            }
            
            UserDefaults.standard.set(recentSearchList, forKey: "recentSearch")
        }
        
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
    }
    
}


// MARK: - UITableViewDelegate

extension SearchVC: UITableViewDelegate {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        self.recentSearchList?.count ?? 0
    }
}


// MARK: - UITableViewDataSource

extension SearchVC: UITableViewDataSource {
    
    // cell 설정
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: Const.Xib.Identifier.recentSearchTVC, for: indexPath) as! RecentSearchTVC
        
        cell.recentSearchTerm.text = recentSearchList?[indexPath.row]
        
        return cell
    }
    
    // tableView cell 눌렀을 때 -> 최근 검색어 선택했을 때 검색 결과로 이동
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let nextVC = UIStoryboard.init(name: Const.Storyboard.Name.homeTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.categoryResult) as! ResultVC
        
        nextVC.resultTitle = recentSearchList?[indexPath.row]
        nextVC.kind = "search"
        tableView.deselectRow(at: indexPath, animated: true)
        
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
    }
    
}
