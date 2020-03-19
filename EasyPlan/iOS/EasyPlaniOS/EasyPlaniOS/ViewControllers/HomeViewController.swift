//
//  HomeViewController.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 27.02.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.


import UIKit

class HomeViewController: UIViewController {
    
    let start_picker = UIPickerView()
    let start_time = ["8:30", "10:00", "11:30", "13:00", "14:30", "16:00", "17:30"]
    
    let finish_picker = UIPickerView()
    let finish_time = ["9:45", "11:15", "12:45", "14:15", "15:45", "17.15", "18.45"]
    
    let plan_picker = UIPickerView()
    let plan_no = ["Plan 1", "Plan 2", "Plan 3", "Plan 4", "Plan 5", "Plan 6", "Plan 7", "Plan 8", "Plan 9", "Plan 10"]
    
    var courses = [ Course(selected: false, name: "Comp491"), Course(selected: false, name: "Comp319"), Course(selected: false, name: "Comp130"),Course(selected: false, name: "Comp305"), Course(selected: false, name: "Comp301"),Course(selected: false, name: "Comp303"), Course(selected: false, name: "Comp302"),Course(selected: false, name: "Ethr113"),Course(selected: false, name: "Ethr102"), Course(selected: false, name: "Ethr105"), Course(selected: false, name: "Acct201"), Course(selected: false, name: "Acct202")]
    
    var searchCourse = [Course]()
    var filtered:[String] = []
    var searching = false
    @IBOutlet weak var searchBar: UISearchBar!
    
    @IBOutlet weak var planLabel: UITextField!
    @IBOutlet weak var finishLabel: UITextField!
    @IBOutlet weak var startLabel: UITextField!
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var searchCourseButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setUpSearchBar()
        
        self.navigationController?.navigationBar.prefersLargeTitles = true
        
        tableView.delegate = self
        tableView.dataSource = self
        
        searchBar.delegate = self
        
        start_picker.delegate = self
        start_picker.dataSource = self
        
        finish_picker.delegate = self
        finish_picker.dataSource = self
        
        plan_picker.delegate = self
        plan_picker.dataSource = self
        
        createPicker()
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        // Hide the navigation bar on the this view controller
        self.tabBarController?.navigationController?.setNavigationBarHidden(true, animated: animated)
        
    }
    
    func setUpSearchBar(){
        searchBar.placeholder = "Search"
        //        navigationItem.hidesSearchBarWhenScrolling = false
        //        öteki VC için
        //        navigationItem.largeTitleDisplayMode = .never
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
}

extension HomeViewController: UITableViewDataSource, UITableViewDelegate {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if searching {
            return searchCourse.count
        }
        return courses.count
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath) as! CourseSearchTableViewCell
        
        if searching {
            cell.courseNameLabel.text = searchCourse[indexPath.row].name
            if searchCourse[indexPath.row].selected {
                cell.accessoryType = UITableViewCell.AccessoryType.checkmark
            } else {
                cell.accessoryType = UITableViewCell.AccessoryType.none
            }
        } else {
            cell.courseNameLabel.text = courses[indexPath.row].name
            if courses[indexPath.row].selected {
                cell.accessoryType = UITableViewCell.AccessoryType.checkmark
            } else {
                cell.accessoryType = UITableViewCell.AccessoryType.none
            }
        }
        cell.cellDelegate = self
        cell.index = indexPath
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if tableView.cellForRow(at: indexPath)?.accessoryType == UITableViewCell.AccessoryType.checkmark {
            tableView.cellForRow(at: indexPath)?.accessoryType = UITableViewCell.AccessoryType.none
            if searching {
                searchCourse[indexPath.row].selected = false
            } else {
                courses[indexPath.row].selected = false
            }
        } else {
            tableView.cellForRow(at: indexPath)?.accessoryType = UITableViewCell.AccessoryType.checkmark
            if searching {
                searchCourse[indexPath.row].selected = true
            } else {
                courses[indexPath.row].selected = true
            }
        }
    }
    
    //    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
    //        if editingStyle == UITableViewCell.EditingStyle.delete {
    //           if searching {
    //            searchCourse.remove(at: indexPath.row)
    //            } else {
    //            courses.remove(at: indexPath.row)
    //            }
    //            tableView.reloadData()
    //        }
    //    }
}

extension HomeViewController: UISearchBarDelegate{
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        let text = searchText.lowercased()
        searchCourse = courses.filter({$0.name.lowercased().prefix(text.count) == text})
        searching = true
        tableView.reloadData()
    }
}


extension HomeViewController: TableViewNew {
    func onClickCellInfo(index: Int) {
        print("Info Clicked")
    }
}

extension HomeViewController: UIPickerViewDataSource, UIPickerViewDelegate {
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
