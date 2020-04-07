//
//  TermsOfServiceViewController.swift
//  EasyPlaniOS
//
//  Created by Buğra Sipahioğlu on 7.04.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import UIKit
import FirebaseDatabase

class TermsOfServiceViewController: UIViewController {
    @IBOutlet weak var termsOfServiceTextView: UITextView!
    
    
    var ref:DatabaseReference?
    var databaseHandle:DatabaseHandle?
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        ref = Database.database().reference()
        self.termsOfServiceTextView.isScrollEnabled=true

        ref?.child("termsOfService").observeSingleEvent(of: .value, with: { (snaphot) in
            let termsOfServiceHTML = snaphot.value as? String
            
            let attrStr = try! NSAttributedString(
                data: termsOfServiceHTML!.data(using: String.Encoding.unicode, allowLossyConversion: true)!,
                options:[NSAttributedString.DocumentReadingOptionKey.documentType: NSAttributedString.DocumentType.html], documentAttributes: nil)
            self.termsOfServiceTextView.attributedText = attrStr
            
        }, withCancel: { (error) in
            print("Problem with reading terms of service from the database")
        })
}


}
