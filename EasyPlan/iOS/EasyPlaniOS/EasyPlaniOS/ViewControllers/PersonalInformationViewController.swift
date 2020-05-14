//
//  PersonalInformationViewController.swift
//  EasyPlaniOS
//
//  Created by Buğra Sipahioğlu on 31.03.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import UIKit
import GoogleSignIn
class PersonalInformationViewController: UIViewController, UITableViewDelegate, UITableViewDataSource{

    
    var information = ["Profile Name:       Buğra Sipahioglu ", "Email:                    bsipahioglu15@ku.edu.tr "]

    @IBOutlet weak var tableView: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        tableView.delegate = self
        tableView.dataSource = self
        // Do any additional setup after loading the view.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        return self.information.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "personalInfoCell", for: indexPath)
        
//      info = ["Profile Name:\t" + GIDSignIn.sharedInstance().currentUser.profile.email ?? "", "Email:\t"GIDSignIn.sharedInstance().currentUser.profile.name ?? ""]
        cell.textLabel?.text = self.information[indexPath.row]
        
        return cell
    }
    

}



