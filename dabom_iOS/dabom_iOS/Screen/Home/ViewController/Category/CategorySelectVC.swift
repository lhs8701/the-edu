//
//  CategorySelectVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/02/04.
//

import UIKit

class CategorySelectVC: UIViewController {
    
    // MARK: - IBOutlet
    
    @IBOutlet weak var categoryTV: UITableView!
    
    
    // MARK: - let, var
    
    var category: [CategoryDataModel] = []
    
    
    // MARK: - Life Cycle
    
    override func viewDidLoad() {
        super.viewDidLoad()

        setTV()
        setCategory()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(false, animated: true)
        self.navigationController?.navigationBar.topItem?.backButtonTitle = ""
    }
    
    
    // MARK: - TableView Setting
    
    private func setTV() {
        self.categoryTV.delegate = self
        self.categoryTV.dataSource = self
        self.categoryTV.register(UINib(nibName: Const.Xib.Name.categoryTVC, bundle: nil), forCellReuseIdentifier: Const.Xib.Identifier.categoryTVC)
        self.categoryTV.register(UINib(nibName: Const.Xib.Name.categoryHeaderTVC, bundle: nil), forHeaderFooterViewReuseIdentifier: Const.Xib.Identifier.categoryHeaderTVC)
    }
    
    
    // MARK: - Category 목록 가져오기
    
    private func setCategory() {
        GetCategoryDataService.shared.getCategory { response in
            switch response {
            case .success(let data):
                if let data = data as? [CategoryDataModel] {
                    self.category = data
                    self.categoryTV.reloadData()
                    print(self.category)
                }
            case .requestErr(let message):
                print("requestErr", message)
            case .pathErr:
                print("pathErr")
            case .serverErr:
                print("serverErr")
            case .networkFail:
                print("networkFail")
            case .resourceErr:
                print("resourceErr")
            }
        }
    }

}


// MARK: - UITableView Extension

extension CategorySelectVC: UITableViewDelegate, UITableViewDataSource {
    
    // section 수
    func numberOfSections(in tableView: UITableView) -> Int {
        category.count
    }
    
    // section당 row 수
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        if category.isEmpty {
            return 0
        } else {
            return self.category[section].categoryList.count
        }
    }

    // cell 설정
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: Const.Xib.Identifier.categoryTVC) as! CategoryTVC
        
        cell.categoryName.text = category[indexPath.section].categoryList[indexPath.row]
        cell.setCategoryName(categoryName: category[indexPath.section].categoryList[indexPath.row])
        
        
        return cell
    }
    
    // cell 높이
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        50
    }
    
    // header 높이
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        50
    }

    // header 설정
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let header = tableView.dequeueReusableHeaderFooterView(withIdentifier: Const.Xib.Identifier.categoryHeaderTVC) as! CategoryHeaderTVC
        
        if !category.isEmpty {
            header.setGroupName(groupName: category[section].groupName)
        }
        
        return header
    }
    
    // 카테고리 선택 시에 결과 화면으로 이동
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let nextVC = UIStoryboard(name: Const.Storyboard.Name.homeTab, bundle: nil).instantiateViewController(withIdentifier: Const.ViewController.Identifier.categoryResult) as! ResultVC
        
        nextVC.resultTitle = self.category[indexPath.section].categoryList[indexPath.row]
        nextVC.kind = "category"
        tableView.deselectRow(at: indexPath, animated: true)
        
        nextVC.modalPresentationStyle = .fullScreen
        self.navigationController?.pushViewController(nextVC, animated: true)
    }
    
}
