//
//  Utilities.swift
//  customauth
//
//  Created by Christopher Ching on 2019-05-09.
//  Copyright © 2019 Christopher Ching. All rights reserved.
//

import Foundation
import UIKit

class Utilities {
    
    static func styleTextField(_ textfield:UITextField) {
        
        // Create the bottom line
        let bottomLine = CALayer()
        
        bottomLine.frame = CGRect(x: 0, y: textfield.frame.height - 2, width: textfield.frame.width, height: 2)
        
        bottomLine.backgroundColor = UIColor.init(red: 48/255, green: 173/255, blue: 99/255, alpha: 1).cgColor
        
        // Remove border on text field
        textfield.borderStyle = .none
        
        // Add the line to the text field
        textfield.layer.addSublayer(bottomLine)
        
    }
    
    static func styleFilledButton(_ button:UIButton) {
        
        // Filled rounded corner style
        button.backgroundColor = UIColor.init(red: 48/255, green: 173/255, blue: 99/255, alpha: 1)
        button.layer.cornerRadius = 25.0
        button.tintColor = UIColor.white
    }
    
    static func styleHollowButton(_ button:UIButton) {
        
        // Hollow rounded corner style
        button.layer.borderWidth = 2
        //button.layer.borderColor = UIColor.black.cgColor
        button.layer.borderColor = UIColor.white.cgColor
        button.layer.cornerRadius = 25.0
        //button.tintColor = UIColor.black
        button.tintColor = UIColor.white
    }
    
    static func styleHollowLabel(_ label:UILabel, i:Int) {
        let purple = UIColor(red:0.76, green:0.30, blue:0.96, alpha:1.00)
        let blue = UIColor(red:0.00, green:0.66, blue:1.00, alpha:1.00)
//        let green = UIColor(red:0.16, green:0.81, blue:0.46, alpha:1.00)
         let green = UIColor(red:0.00, green:1.00, blue:0.76, alpha:1.00)
        let turcoise = UIColor(red:0.00, green:1.00, blue:1.00, alpha:1.00)
        let pink = UIColor(red:1.00, green:0.08, blue:0.58, alpha:1.00)
        let yellow = UIColor(red:1.00, green:0.89, blue:0.01, alpha:1.00)
        let red = UIColor(red:0.95, green:0.10, blue:0.11, alpha:1.00)
        let orange = UIColor(red:1.00, green:0.51, blue:0.18, alpha:1.00)
        
           let colorArray = [purple, blue, green, turcoise, pink, yellow, red, orange]
           // Hollow rounded corner style
           label.layer.borderWidth = 1
           label.layer.borderColor = UIColor.black.cgColor
//           label.layer.borderColor = UIColor.white.cgColor
           label.layer.cornerRadius = 10.0
           label.tintColor = UIColor.black
        label.layer.backgroundColor = colorArray[i].cgColor
//           label.tintColor = UIColor.white
       }
    
    
    static func isPasswordValid(_ password : String) -> Bool {
        
        let passwordTest = NSPredicate(format: "SELF MATCHES %@", "^[A-Za-z\\d$@$#!%*?&]{6,}")
        return passwordTest.evaluate(with: password)
    }
    
//  we can add constraint for only ku mails
    static func isValidEmail(_ email: String) -> Bool {
        let firstpart = "[A-Z0-9a-z]([A-Z0-9a-z._%+-]{0,30}[A-Z0-9a-z])?"
        let serverpart = "ku.edu.tr"
        let emailRegEx = firstpart + "@" + serverpart
        let emailPred = NSPredicate(format:"SELF MATCHES %@", emailRegEx)
    return emailPred.evaluate(with: email)
    }
}

//color extension
extension UIColor {
    
    class func randomColor() -> UIColor {

        let hue = CGFloat(arc4random() % 100) / 100
        let saturation = CGFloat(arc4random() % 100) / 100
        let brightness = CGFloat(arc4random() % 100) / 100

        return UIColor(hue: hue, saturation: saturation, brightness: brightness, alpha: 1.0)
    }
}
