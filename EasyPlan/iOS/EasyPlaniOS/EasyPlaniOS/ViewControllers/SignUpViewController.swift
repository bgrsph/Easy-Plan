//
//  SignUpViewController.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 27.02.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import UIKit
import FirebaseAuth

class SignUpViewController: UIViewController {
    
    @IBOutlet weak var firstNameTextField: UITextField!
    @IBOutlet weak var lastNameTextField: UITextField!
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var signUpButton: UIButton!
    @IBOutlet weak var errorLabel: UILabel!
    override func viewDidLoad() {
        super.viewDidLoad()
        setUpElements()
        
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
    @IBAction func signUpTapped(_ sender: Any) {
        let error = validateFields()
        if error != nil {
            showError(error!)
        } else {
              if let email = emailTextField.text, let password = passwordTextField.text {
                          Auth.auth().createUser(withEmail: email, password: password) { authResult, error in
                              if let e = error {
                                self.showError("Error creating the User")
                                print(e.localizedDescription)
                              } else {
                                  //Navigate to the ChatViewController
                                  self.performSegue(withIdentifier: "SignUpToHome", sender: self)
                                print("Success")
                              }
                          }
                      }
            }
        }
    
    
    func showError(_ message:String){
        errorLabel.text = message
        errorLabel.alpha = 1
    }
    
    func setUpElements(){
        //        hide error label
        errorLabel.alpha = 0
        //        style the elements
        Utilities.styleTextField(firstNameTextField)
        Utilities.styleTextField(lastNameTextField)
        Utilities.styleTextField(emailTextField)
        Utilities.styleTextField(passwordTextField)
        Utilities.styleFilledButton(signUpButton)
    }
    
    //    check the fields and validate that the data is correct
    //    If everything is correct return nil
    //    Else return error label
    func validateFields()-> String? {
        //Check that all fields are filled in
        if firstNameTextField.text?.trimmingCharacters(in: .whitespacesAndNewlines) == "" || lastNameTextField.text?.trimmingCharacters(in: .whitespacesAndNewlines) == "" || emailTextField.text?.trimmingCharacters(in: .whitespacesAndNewlines) == "" || passwordTextField.text?.trimmingCharacters(in: .whitespacesAndNewlines) == ""{
            
            return "Please fill in all fields."
        }
        
        let email = emailTextField.text!
        if !Utilities.isValidEmail(email){
            return "Email should be valid"
        }
        
        let password = passwordTextField.text!
        if !Utilities.isPasswordValid(password) {
            return "Password should be at least 8 characters, containing a special character and a number"
        }
        
        
        
        return nil
    }
    
}
