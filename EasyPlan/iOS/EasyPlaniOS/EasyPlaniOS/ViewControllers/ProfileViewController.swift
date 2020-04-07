//
//  ProfileViewController.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 9.03.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import UIKit
import Firebase
import StoreKit

class ProfileViewController: UIViewController, UITableViewDataSource, UITableViewDelegate{
    
    
    @IBOutlet weak var tableView: UITableView!
    let optionList = ["Personal Information", "Notifications", "Rate the Application!", "Share Us!", "Terms of Service"]
   
    var ref:DatabaseReference?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Create the nib file
        let nib = UINib(nibName: "ProfileTableViewCell", bundle: nil)
        
        // Register the cell to tableView with its nib
        tableView.register(nib, forCellReuseIdentifier: "ProfileTableViewCell")
        
        // Cast the tableView to UIView
        tableView.delegate = self
        tableView.dataSource = self
        
//        //Set the firebase reference
//        ref?.child("easy-plan-8e84f").observeSingleEvent(of: .value, with: { (snapshot) in
//            let termsOfServiceText = value?[forKey: "termsOfService"] as? String ?? ""
//        }, withCancel: <#T##((Error) -> Void)?##((Error) -> Void)?##(Error) -> Void#>)
        
    }
    
    
    
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)

        // Hide the navigation bar on the this view controller
        self.tabBarController?.navigationController?.setNavigationBarHidden(true, animated: animated)
    }

    
    
    
    @IBAction func LogOutTapped(_ sender: Any) {
        let firebaseAuth = Auth.auth()
           do {
               try firebaseAuth.signOut()
            self.tabBarController?.navigationController?.popToRootViewController(animated: true)
           } catch let signOutError as NSError {
               print("Error signing out: %@", signOutError)
           }
    }
    
    // TableView Functions
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return optionList.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        // Cast the prototype cell to the custom class
        let cell = tableView.dequeueReusableCell(withIdentifier: "ProfileTableViewCell", for: indexPath) as! ProfileTableViewCell
        
        //Set the labels
        cell.optionLabel.text = optionList[indexPath.row]
        
        //Set the icons
        cell.optionIconImageView.image = UIImage(named: optionList[indexPath.row])
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        if optionList[indexPath.row] == "Personal Information" {
            performSegue(withIdentifier: "goToProfileInfo", sender: self)
        }
        
        //TODO: Change it with the actual link if possible
        if optionList[indexPath.row] == "Share Us!" {
            let activityController = UIActivityViewController(activityItems: ["*** Apple Store Download Link ***"], applicationActivities: nil)
            self.present(activityController, animated: true, completion: nil)
        }
    
        if optionList[indexPath.row] == "Rate the Application!" {
            SKStoreReviewController.requestReview()
        }
        
        if optionList[indexPath.row] == "Terms of Service" {
            
            performSegue(withIdentifier: "goToTermsOfService", sender: self)

            
            
            
        }
        
        
        
    }
    
    
}
