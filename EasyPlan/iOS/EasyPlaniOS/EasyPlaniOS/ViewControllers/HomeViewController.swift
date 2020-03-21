//
//  HomeViewController.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 27.02.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.


import UIKit

class HomeViewController: UIViewController {
    
    var courses = [ Course(selected: false, name: "Comp491", id: 1), Course(selected: false, name: "Comp130", id: 2), Course(selected: false, name: "Comp131", id: 3), Course(selected: false, name: "Comp132", id: 4), Course(selected: false, name: "Comp200", id: 5), Course(selected: false, name: "Comp202", id: 6), Course(selected: false, name: "Comp301", id: 7), Course(selected: false, name: "Comp302", id: 8), Course(selected: false, name: "Comp303", id: 9), Course(selected: false, name: "Comp304", id: 10), Course(selected: false, name: "Comp305", id: 11), Course(selected: false, name: "Comp306", id: 12), Course(selected: false, name: "Econ100", id: 13), Course(selected: false, name: "Econ101", id: 14), Course(selected: false, name: "Econ102", id: 15) ]
    
    var courseDictionary : [Course:Int] = [:]
    let burgundy = UIColor(red:0.72, green:0.00, blue:0.00, alpha:1.00)
    var selectedCourses:Int = 0
    var searchCourse = [Course]()
    var filtered:[String] = []
    var searching = false
    @IBOutlet weak var searchBar: UISearchBar!
    
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var searchCourseButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setUpSearchBar()
        
        self.navigationController?.navigationBar.prefersLargeTitles = true
       
        tableView.delegate = self
        tableView.dataSource = self
        
        setUpHeader()

        searchBar.delegate = self
        

        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        // Hide the navigation bar on the this view controller
        self.tabBarController?.navigationController?.setNavigationBarHidden(true, animated: animated)
        
    }
    
    func setUpHeader(){
        let header = UIView(frame: CGRect(x: 0, y: 0, width: view.frame.size.width, height: 45))
        header.layer.borderColor = burgundy.cgColor
        header.layer.borderWidth = 5
        let selectionLabel = UILabel(frame: header.bounds)
        if (selectedCourses==1){
             selectionLabel.text = " \(selectedCourses) Course Selected"
        } else {
        selectionLabel.text = " \(selectedCourses) Courses Selected"
        }
        selectionLabel.textColor = burgundy
        selectionLabel.textAlignment = .center
        header.addSubview(selectionLabel)
        tableView.tableHeaderView = header
    }
    
    
    func setUpSearchBar(){
        searchBar.placeholder = "Search"
//        searchBar.barTintColor = burgundy
        //        navigationItem.hidesSearchBarWhenScrolling = false
        //        öteki VC için
        //        navigationItem.largeTitleDisplayMode = .never
    }
    
   
    
    
    @IBAction func nextTapped(_ sender: Any) {
        self.performSegue(withIdentifier: "constraint", sender: self)
        
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
                selectedCourses -= 1
                courseDictionary.removeValue(forKey: searchCourse[indexPath.row])
                print(courseDictionary)
            } else {
                courses[indexPath.row].selected = false
                selectedCourses -= 1
                courseDictionary.removeValue(forKey: courses[indexPath.row])
                print(courseDictionary)
            }
        } else {
            tableView.cellForRow(at: indexPath)?.accessoryType = UITableViewCell.AccessoryType.checkmark
            selectedCourses += 1
            if searching {
                searchCourse[indexPath.row].selected = true
                courseDictionary[searchCourse[indexPath.row]] = selectedCourses
                print(courseDictionary)
            } else {
                courses[indexPath.row].selected = true
                courseDictionary[courses[indexPath.row]] = selectedCourses
                print(courseDictionary)
            }
        }
        setUpHeader()
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
