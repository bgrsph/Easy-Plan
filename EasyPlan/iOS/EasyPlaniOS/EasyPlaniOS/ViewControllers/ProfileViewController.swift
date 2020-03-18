//
//  ProfileViewController.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 9.03.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import UIKit
import Firebase

class ProfileViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)

        // Hide the navigation bar on the this view controller
        self.tabBarController?.navigationController?.setNavigationBarHidden(true, animated: animated)
    }
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

    
    @IBAction func LogOutTapped(_ sender: Any) {
        let firebaseAuth = Auth.auth()
           do {
               try firebaseAuth.signOut()
            self.tabBarController?.navigationController?.popToRootViewController(animated: true)
           } catch let signOutError as NSError {
               print("Error signing out: %@", signOutError)
           }
    }
    
}
