//
//  ViewController.swift
//  EasyPlaniOS
//
//  Created by Buğra Sipahioğlu on 27.02.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import UIKit
import AVKit
import Firebase
import GoogleSignIn
import FirebaseAuth


class ViewController: UIViewController, GIDSignInDelegate {
    
    
    func sign(_ signIn: GIDSignIn!, didSignInFor user: GIDGoogleUser!, withError error: Error!) {
        
//        if let authentication = user.authentication {
//             let credential = GoogleAuthProvider.credential(withIDToken: authentication.idToken, accessToken: authentication.accessToken)
//
//            Auth.auth().signIn(with: credential, completion: { (user, error) -> Void in
//                 if error != nil {
//                    print("Problem at signing in with google with error : \(String(describing: error))")
//                 } else if error == nil {
//                     print("signed in")
//                     self.performSegue(withIdentifier: "directPass", sender: self)
//                 }
//             })
//         }
        
        if error != nil {
            // ...
            return
        }

        guard let authentication = user.authentication else { return }
        let credential = GoogleAuthProvider.credential(withIDToken: authentication.idToken,
                                                       accessToken: authentication.accessToken)
        Auth.auth().signIn(with: credential) { (user, error) in
            if user == nil {
                print("Problem at signing in with google with error")
                return
            } else {
            
                print("\n\n\n\n\n--------signed in------\n")
                print(GIDSignIn.sharedInstance().currentUser.profile.email ?? "")
                print(GIDSignIn.sharedInstance().currentUser.profile.name ?? "")

                let words = GIDSignIn.sharedInstance().currentUser.profile.email.components(separatedBy: "@")
                if words[1] != "ku.edu.tr"{
                    
                    let user = Auth.auth().currentUser
                    user?.delete { error in
                      if let error = error {
                        // An error happened.
                      } else {
                        self.riseUIAlert(message: "You cannot use emails without Koç University domain")
                        self.navigationController?.popToRootViewController(animated: true)
                      }
                    }
                }
                
                else {
                    self.performSegue(withIdentifier: "directPass", sender: self)
                }
                
            }
        }
        
    }
    

    var videoPlayer:AVPlayer?
    var videoPlayerLayer:AVPlayerLayer?
    
    @IBOutlet weak var googleSignInButton: UIButton!
    @IBOutlet weak var signUpButton: UIButton!
    @IBOutlet weak var loginButton: UIButton!
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if Auth.auth().currentUser != nil {
                        performSegue(withIdentifier: "directPass", sender: self)
                    }
        
        setUpElements()
        // Do any additional setup after loading the view.
        
        GIDSignIn.sharedInstance()?.delegate = self
        GIDSignIn.sharedInstance()?.presentingViewController = self
    }
    
    override func viewWillAppear(_ animated: Bool) {
        setUpVideo()
        self.navigationController?.setNavigationBarHidden(false, animated: animated)
    }

    func setUpElements(){
//        Utilities.styleFilledButton(loginButton)
        Utilities.styleHollowButton(signUpButton)
        Utilities.styleHollowButton(loginButton)
        Utilities.styleHollowButton(googleSignInButton)


           }
    
    func setUpVideo(){
//        get the path to the resource in the bundle
        let bundlePath = Bundle.main.path(forResource: "koc_tanitim", ofType: "mp4")

        guard bundlePath != nil else {
            return
        }
//        create a url from the bundle
        let url = URL(fileURLWithPath: bundlePath!)
//        create the video player item
        let item = AVPlayerItem(url: url)
//        create the player
        videoPlayer = AVPlayer(playerItem: item)
//        create the layer
        videoPlayerLayer = AVPlayerLayer(player: videoPlayer!)
        videoPlayer?.isMuted = true
//        set frame
        
        videoPlayerLayer?.frame = CGRect(x: -self.view.frame.size.width*1.5, y: 0, width: self.view.frame.size.width*4, height: UIScreen.main.bounds.height)
    
        view.layer.insertSublayer(videoPlayerLayer!, at: 0)



//        adjust the size and frame
        videoPlayer?.playImmediately(atRate: 1)
        
        
    }
    
    
    func riseUIAlert(message: String) {
        let alert = UIAlertController(title: "Error", message: "You can't Sign In without Koç University email", preferredStyle: .alert)
         alert.addAction(UIAlertAction(title: NSLocalizedString("OK", comment: "Default action"), style: .default, handler: { _ in
         NSLog("The \"OK\" alert occured.")
         }))
         self.present(alert, animated: true, completion: nil)
    }
    
    
    
    
    @IBAction func GoogleSignInTapped(_ sender: Any) {
        
        GIDSignIn.sharedInstance()?.signIn()
        
    }
    
    @IBAction func SignUpTapped(_ sender: Any) {
        self.performSegue(withIdentifier: "signup", sender: self)
        videoPlayer?.pause()
    }
    
    @IBAction func LoginTapped(_ sender: Any) {
        videoPlayer?.pause()
        if Auth.auth().currentUser != nil {
                 performSegue(withIdentifier: "directPass", sender: self)
             } else {
        self.performSegue(withIdentifier: "login", sender: self)
        }
    }
}



