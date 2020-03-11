//
//  SignUpViewController.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 27.02.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import UIKit
import FirebaseAuth
import GoogleSignIn

class SignUpViewController: UIViewController, GIDSignInDelegate {
    func sign(_ signIn: GIDSignIn!, didSignInFor user: GIDGoogleUser!, withError error: Error!) {
        
    }
    
    
    @IBOutlet weak var firstNameTextField: UITextField!
    @IBOutlet weak var lastNameTextField: UITextField!
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var signUpButton: UIButton!
    @IBOutlet weak var errorLabel: UILabel!
    @IBOutlet weak var signInGoogleButton: UIButton!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setUpElements()
        GIDSignIn.sharedInstance()?.delegate = self
        GIDSignIn.sharedInstance()?.presentingViewController = self
    }

    @IBAction func signInGoogleTapped(_ sender: Any) {
        
        GIDSignIn.sharedInstance().signIn()
    }
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
            return "Please enter a valid ku student email"
        }
        
        let password = passwordTextField.text!
        if !Utilities.isPasswordValid(password) {
            return "Password should be at least 6 characters"
        }
        
        return nil
    }
    
}
