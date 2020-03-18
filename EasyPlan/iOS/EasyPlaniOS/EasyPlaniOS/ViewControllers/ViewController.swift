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

class ViewController: UIViewController {

    var videoPlayer:AVPlayer?
    var videoPlayerLayer:AVPlayerLayer?
    
    @IBOutlet weak var signUpButton: UIButton!
    @IBOutlet weak var loginButton: UIButton!
    override func viewDidLoad() {
        super.viewDidLoad()
        setUpElements()
        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        setUpVideo()
        self.navigationController?.setNavigationBarHidden(false, animated: animated)
    }

    func setUpElements(){
//        Utilities.styleFilledButton(loginButton)
        Utilities.styleHollowButton(signUpButton)
        Utilities.styleHollowButton(loginButton)

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
//        set frame
//        videoPlayerLayer?.frame = CGRect(x: 0, y: 0, width: self.view.frame.size.width, height: self.view.frame.size.height)
        videoPlayerLayer?.frame = CGRect(x: -self.view.frame.size.width*1.5, y: 0, width: self.view.frame.size.width*4, height: self.view.frame.size.height)
        view.layer.insertSublayer(videoPlayerLayer!, at: 0)
//        adjust the size and frame
        videoPlayer?.playImmediately(atRate: 1)
        
        
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

