//
//  HomeViewController.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 27.02.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.


import UIKit

class HomeViewController: UIViewController {
    
   let timePicker = UIDatePicker()
    
    @IBOutlet weak var facultyTextField: UITextField!
    @IBOutlet weak var courseCodeTextField: UITextField!
    @IBOutlet weak var timeslotTextField: UITextField!
    @IBOutlet weak var searchCourseButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setUpElements()
        createDatePicker()
        
        let center = UNUserNotificationCenter.current()
        center.requestAuthorization(options: [.alert, .sound, .badge]) { granted, error in
            
            if let error = error {
                // Handle the error here.
            }
            
            // Enable or disable features based on the authorization.
        }
       
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        // Hide the navigation bar on the this view controller
        self.tabBarController?.navigationController?.setNavigationBarHidden(true, animated: animated)
    }
    
    func setUpElements(){
        
        Utilities.styleTextField(facultyTextField)
        Utilities.styleTextField(courseCodeTextField)
        Utilities.styleTextField(timeslotTextField)
        Utilities.styleFilledButton(searchCourseButton)
        
    }
    
    func getTimeIntervalForDate()->(min : Date, max : Date){

    let calendar = Calendar.current
        var minDateComponent = calendar.dateComponents([.hour, .minute], from: Date())
    minDateComponent.hour = 08 // Start time
    minDateComponent.minute = 30

    let formatter = DateFormatter()
    formatter.dateFormat = "h:mma"
    let minDate = calendar.date(from: minDateComponent)
//    print(" min date : \(formatter.string(from: minDate!))")

    var maxDateComponent = calendar.dateComponents([.hour, .minute], from: Date())
    maxDateComponent.hour = 16 //EndTime
    maxDateComponent.minute = 0

    let maxDate = calendar.date(from: maxDateComponent)
//    print(" max date : \(formatter.string(from: maxDate!))")

    return (minDate!,maxDate!)
    }
    
    func createDatePicker(){
        let toolbar = UIToolbar()
        toolbar.sizeToFit()
        
//        bar done button
        let doneButton = UIBarButtonItem(barButtonSystemItem: .done, target: nil, action: #selector(donePressed))
        let spaceButton = UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: nil, action: nil)
        let cancelButton = UIBarButtonItem(barButtonSystemItem: .cancel, target: self, action: #selector(cancelPressed))
        toolbar.setItems([cancelButton,spaceButton,doneButton], animated: true)
        //    assign toolbar
        timeslotTextField.inputAccessoryView = toolbar
        timePicker.addTarget(self, action: #selector(HomeViewController.dateChanged(timePicker:)), for: .valueChanged)
        timeslotTextField.inputView = timePicker
        
        timePicker.datePickerMode = .time
        timePicker.minuteInterval = 30
        let dates = getTimeIntervalForDate()
        timePicker.minimumDate = dates.min
        timePicker.maximumDate = dates.max

    }
    
    @objc func cancelPressed(){
        self.view.endEditing(true)
        timeslotTextField.text = ""
    }
    
    @objc func dateChanged(timePicker: UIDatePicker){
        let dateFormatter = DateFormatter()
        dateFormatter.timeStyle = .short
        timeslotTextField.text = dateFormatter.string(from: timePicker.date)
        
    }

    @objc func donePressed(){
        let formatter = DateFormatter()
        formatter.dateStyle = .none
        formatter.timeStyle = .short
        timeslotTextField.text = formatter.string(from: timePicker.date)
        self.view.endEditing(true)
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
