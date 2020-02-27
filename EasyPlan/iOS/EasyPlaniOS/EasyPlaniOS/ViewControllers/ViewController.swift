//
//  ViewController.swift
//  EasyPlaniOS
//
//  Created by Buğra Sipahioğlu on 27.02.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var signUpButton: UIButton!
    @IBOutlet weak var loginButton: UIButton!
    override func viewDidLoad() {
        super.viewDidLoad()
        setUpElements()
        // Do any additional setup after loading the view.
    }

    func setUpElements(){
        Utilities.styleFilledButton(loginButton)
        Utilities.styleHollowButton(signUpButton)
            
               
           }

}

