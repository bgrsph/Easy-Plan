//
//  HelpViewController.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 9.03.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import UIKit
import MessageUI

extension HelpViewController: MFMailComposeViewControllerDelegate{
    func mailComposeController(_ controller: MFMailComposeViewController, didFinishWith result: MFMailComposeResult, error: Error?) {
        if let _ = error {
            controller.dismiss(animated: true)
            return
        }
         controller.dismiss(animated: true)
    }
}

class HelpViewController: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        navigationItem.title = "Help Center"
        navigationController?.navigationBar.prefersLargeTitles = true
        
        // Do any additional setup after loading the view.
    }
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        // Hide the navigation bar on the this view controller
        self.tabBarController?.navigationController?.setNavigationBarHidden(true, animated: animated)
    }
    
    @IBAction func ContactUsTapped(_ sender: Any) {
        let email = "sarslanturk16@ku.edu.tr"
        let email_subject = "Easy Plan"
        showMailComposer(receiver: email, subject: email_subject)
    }
    
    @IBAction func KusisHelpTapped(_ sender: Any) {
        let email = "kusis@ku.edu.tr"
        let email_subject = "Kusis"
        showMailComposer(receiver: email, subject: email_subject)
    }
    
    func showMailComposer(receiver:String, subject:String){
        guard MFMailComposeViewController.canSendMail() else {
            return
        }
        
        let composer = MFMailComposeViewController()
        composer.mailComposeDelegate = self
        composer.setToRecipients([receiver])
        composer.setSubject(subject+" Help!")
        composer.setMessageBody("Hi "+subject+" Team,", isHTML: false)
        present(composer, animated: true)
    }

    
    @IBAction func RegistrationTapped(_ sender: Any) {
        guard let url = URL(string: "telprompt://+902123381000") else {
            return
        }
        
        UIApplication.shared.open(url)
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
