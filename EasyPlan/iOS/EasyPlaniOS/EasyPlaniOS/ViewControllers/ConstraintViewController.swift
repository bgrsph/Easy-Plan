//
//  ConstraintViewController.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 21.03.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import UIKit

class ConstraintViewController: UIViewController {
    @IBOutlet weak var planLabel: UITextField!
    @IBOutlet weak var startLabel: UITextField!
    @IBOutlet weak var finishLabel: UITextField!
    
    
    @IBOutlet weak var collectionView: UICollectionView!
    
    var myCourses: [Course]?
    
    let start_picker = UIPickerView()
    let start_time = ["8:30", "10:00", "11:30", "13:00", "14:30", "16:00", "17:30"]
    
    let finish_picker = UIPickerView()
    let finish_time = ["9:45", "11:15", "12:45", "14:15", "15:45", "17.15", "18.45"]
    
    let plan_picker = UIPickerView()
    let plan_no = ["Plan 1", "Plan 2", "Plan 3", "Plan 4", "Plan 5", "Plan 6", "Plan 7", "Plan 8", "Plan 9", "Plan 10"]
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        createPicker()
        
        start_picker.delegate = self
        start_picker.dataSource = self
        
        finish_picker.delegate = self
        finish_picker.dataSource = self
        
        plan_picker.delegate = self
        plan_picker.dataSource = self
        
        collectionView.delegate = self
        collectionView.dataSource = self
        
        // Do any additional setup after loading the view.
    }
    
    func createPicker(){
        let toolbar = UIToolbar()
        toolbar.sizeToFit()
        
        //        bar done button
        let doneButton = UIBarButtonItem(barButtonSystemItem: .done, target: nil, action: #selector(donePressed))
        let spaceButton = UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: nil, action: nil)
        let cancelButton = UIBarButtonItem(barButtonSystemItem: .cancel, target: self, action: #selector(cancelPressed))
        toolbar.setItems([cancelButton,spaceButton,doneButton], animated: true)
        //    assign toolbar
        startLabel.inputAccessoryView = toolbar
        startLabel.inputView = start_picker
        
        finishLabel.inputAccessoryView = toolbar
        finishLabel.inputView = finish_picker
        
        planLabel.inputAccessoryView = toolbar
        planLabel.inputView = plan_picker
    }
    
    @objc func cancelPressed(){
        self.view.endEditing(true)
        startLabel.text = ""
    }
    
    //        @objc func dateChanged(timePicker: UIDatePicker){
    //            let dateFormatter = DateFormatter()
    //            dateFormatter.timeStyle = .short
    //            timeslotTextField.text = dateFormatter.string(from: timePicker.date)
    //
    //        }
    
    @objc func donePressed(){
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


extension ConstraintViewController: UIPickerViewDataSource, UIPickerViewDelegate {
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if pickerView == finish_picker {
            return finish_time.count
        } else if pickerView == plan_picker {
            return plan_no.count
        } else {
            return start_time.count
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if pickerView == finish_picker {
            return finish_time[row]
        } else if pickerView == plan_picker {
            return plan_no[row]
        } else {
            return start_time[row]
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if pickerView == finish_picker {
            finishLabel.text = finish_time[row]
        } else if pickerView == plan_picker {
            planLabel.text = plan_no[row]
            //        self.view.endEditing(true)
        } else {
            startLabel.text = start_time[row]
        }
    }
}

extension ConstraintViewController: UICollectionViewDelegate, UICollectionViewDataSource {

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return myCourses!.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "constraintCollection", for: indexPath) as! CourseConstraintCollectionViewCell
        
        cell.courseLabel.text = myCourses![indexPath.row].name
        Utilities.styleHollowLabel(cell.courseLabel)
        return cell
    }
    
    
}
