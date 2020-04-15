//
//  ConstraintViewController.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 21.03.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import UIKit

protocol EditItemViewControllerDelegate {
    func shouldAdd(param: [Course], param2: [Course])
}

class ConstraintViewController: UIViewController {
    
    var delegate: EditItemViewControllerDelegate!
    @IBOutlet weak var planLabel: UITextField!
    @IBOutlet weak var startLabel: UITextField!
    @IBOutlet weak var finishLabel: UITextField!
    
    @IBOutlet weak var frCheck: UIImageView!
    @IBOutlet weak var tuThCheck: UIImageView!
    @IBOutlet weak var moWeCheck: UIImageView!
    
    var frChecked = false
    var tuThChecked = false
    var moWeChecked = false
    
    enum Mode {
        case view
        case select
    }
    
    var mMode: Mode = .view {
        didSet {
            switch mMode {
            case .view:
                for (key, value) in indexPathDictionary{
                    if value {
                        collectionView.deselectItem(at: key, animated: true)
                    }
                }
                indexPathDictionary.removeAll()
                selectBarButton.title = "Edit"
                navigationItem.rightBarButtonItems = [selectBarButton]
                collectionView.allowsMultipleSelection = false
            case .select:
                selectBarButton.title = "Done"
                navigationItem.rightBarButtonItems = [selectBarButton, deleteBarButton]
                collectionView.allowsMultipleSelection = true
            }
        }
    }
    lazy var selectBarButton: UIBarButtonItem = {
        let barButtonItem = UIBarButtonItem(title: "Edit", style: .plain, target: self, action: #selector(didEditButtonClicked(_sender:)))
        return barButtonItem
    }()
    
    lazy var deleteBarButton: UIBarButtonItem = {
        let barButtonItem = UIBarButtonItem(barButtonSystemItem: .trash, target: self, action: #selector(didDeleteButtonClicked(_sender:)))
          return barButtonItem
      }()
    
    @IBOutlet weak var collectionView: UICollectionView!
    
    var indexPathDictionary: [IndexPath:Bool] = [:]
    var myCourses: [Course]?
    var deletedCourses: [Course] = []
    
    let start_picker = UIPickerView()
    let start_time = ["8:30", "10:00", "11:30", "13:00", "14:30", "16:00", "17:30"]
    
    let finish_picker = UIPickerView()
    let finish_time = ["9:45", "11:15", "12:45", "14:15", "15:45", "17.15", "18.45"]
    
    let plan_picker = UIPickerView()
    let plan_no = ["Plan 1", "Plan 2", "Plan 3", "Plan 4", "Plan 5", "Plan 6", "Plan 7", "Plan 8", "Plan 9", "Plan 10"]
    
    var colorChooser = 0
    var planPickerChosen = false
    var startPickerChosen = false
    var finishPickerChosen = false
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        createPicker()
        setUpBarButton()
        
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
    
    func setUpBarButton(){
        navigationItem.rightBarButtonItem = selectBarButton
    }
    
    @objc func cancelPressed(){
        self.view.endEditing(true)
        if finishPickerChosen {
            finishLabel.text = ""
            finishPickerChosen = false
        }
        if startPickerChosen {
            startLabel.text = ""
            startPickerChosen = false
        }
        if planPickerChosen {
            planLabel.text = ""
            planPickerChosen = false
        }
    }
    
    //        @objc func dateChanged(timePicker: UIDatePicker){
    //            let dateFormatter = DateFormatter()
    //            dateFormatter.timeStyle = .short
    //            timeslotTextField.text = dateFormatter.string(from: timePicker.date)
    //
    //        }
    
    @objc func donePressed(){
        if finishPickerChosen {
            let row = finish_picker.selectedRow(inComponent: 0)
            pickerView(finish_picker, didSelectRow: row, inComponent:0)
            finishPickerChosen = false
        }
        if startPickerChosen {
            let row = start_picker.selectedRow(inComponent: 0)
            pickerView(start_picker, didSelectRow: row, inComponent:0)
            startPickerChosen = false
        }
        if planPickerChosen {
            let row = plan_picker.selectedRow(inComponent: 0)
            pickerView(plan_picker, didSelectRow: row, inComponent:0)
            planPickerChosen = false
        }
        self.view.endEditing(true)
    }
    
    @objc func didEditButtonClicked(_sender: UIBarButtonItem){
        mMode = mMode == .view ? .select : .view
    }
    
    @objc func didDeleteButtonClicked(_sender: UIBarButtonItem){
        var  deleteNeededIndexPaths : [IndexPath] = []
        for (key, value) in indexPathDictionary {
            if value {
                deleteNeededIndexPaths.append(key)
            }
        }
        for i in deleteNeededIndexPaths.sorted(by: {$0.item > $1.item}){
            deletedCourses.append(myCourses![i.item])
            myCourses?.remove(at: i.item)
          
        }
        
        collectionView.deleteItems(at: deleteNeededIndexPaths)
        indexPathDictionary.removeAll()
        if let delegate = delegate {
            delegate.shouldAdd(param: deletedCourses, param2: myCourses!)
        }
       }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */
    
    @IBAction func planTapped(_ sender: Any) {
        
    }
    
    @IBAction func frTapped(_ sender: Any) {
        if frChecked {
            frCheck.image = UIImage(systemName: "square")
            frChecked = false
        } else if !frChecked {
             frCheck.image = UIImage(systemName: "checkmark.square")
            frChecked = true
        }
    }
    
    
    @IBAction func tuThTapped(_ sender: Any) {
        if tuThChecked {
            tuThCheck.image = UIImage(systemName: "square")
            tuThChecked = false
        } else if !frChecked {
             tuThCheck.image = UIImage(systemName: "checkmark.square")
            tuThChecked = true
        }
    }
    
    
    @IBAction func moWeTapped(_ sender: Any) {
        if moWeChecked {
            moWeCheck.image = UIImage(systemName: "square")
            moWeChecked = false
        } else if !moWeChecked {
             moWeCheck.image = UIImage(systemName: "checkmark.square")
            moWeChecked = true
        }
}
}


extension ConstraintViewController: UIPickerViewDataSource, UIPickerViewDelegate {
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if pickerView == finish_picker {
            finishPickerChosen = true
            startPickerChosen = false
            planPickerChosen = false
            return finish_time.count
        } else if pickerView == plan_picker {
            finishPickerChosen = true
            startPickerChosen = false
            planPickerChosen = true
            return plan_no.count
        } else {
            finishPickerChosen = false
            startPickerChosen = true
            planPickerChosen = false
            return start_time.count
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if pickerView == finish_picker {
            finishPickerChosen = true
            startPickerChosen = false
            planPickerChosen = false
            return finish_time[row]
        } else if pickerView == plan_picker {
            finishPickerChosen = false
            startPickerChosen = false
            planPickerChosen = true
            return plan_no[row]
        } else {
            finishPickerChosen = false
            startPickerChosen = true
            planPickerChosen = false
            return start_time[row]
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if pickerView == finish_picker {
            finishPickerChosen = false
            startPickerChosen = false
            planPickerChosen = false
            finishLabel.text = finish_time[row]
        } else if pickerView == plan_picker {
            finishPickerChosen = false
            startPickerChosen = false
            planPickerChosen = false
            planLabel.text = plan_no[row]
            //        self.view.endEditing(true)
        } else {
            finishPickerChosen = false
            startPickerChosen = false
            planPickerChosen = false
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
        
        cell.courseLabel.text = myCourses![indexPath.row].subject + myCourses![indexPath.row].catalog
        Utilities.styleHollowLabel(cell.courseLabel, i:colorChooser)
        colorChooser = (colorChooser + 1) % 8
//        print(colorChooser)
        
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
      if mMode == .select {
            indexPathDictionary[indexPath] = true
      } else if mMode == .view  {
        collectionView.deselectItem(at: indexPath, animated: true)
    }
    }
    
    func collectionView(_ collectionView: UICollectionView, didDeselectItemAt indexPath: IndexPath) {
        if mMode == .select {
            indexPathDictionary[indexPath] = false
        }
    }
    
    
}
