//
//  IntroductionVC.swift
//  dabom_iOS
//
//  Created by 김태현 on 2023/01/11.
//

import UIKit

class IntroductionVC: UIViewController {

    @IBOutlet weak var firstImageView: UIImageView!
    @IBOutlet weak var secondImageView: UIImageView!
    
    @IBOutlet weak var tabmanSV: UIScrollView!
    
    var firstImage: UIImage!
    var secondImage: UIImage!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        firstImageView.image = firstImage
        secondImageView.image = secondImage
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
