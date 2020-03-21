//
//  HomeViewController.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 27.02.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.


import UIKit

class HomeViewController: UIViewController {
    
    var courses = [ Course(selected: false, name: "Comp491"), Course(selected: false, name: "Comp130"), Course(selected: false, name: "Comp131"),Course(selected: false, name: "Comp132"), Course(selected: false, name: "Comp200"),Course(selected: false, name: "Comp202"), Course(selected: false, name: "Comp301"), Course(selected: false, name: "Comp302"),Course(selected: false, name: "Comp303"), Course(selected: false, name: "Comp304"),Course(selected: false, name: "Comp305"), Course(selected: false, name: "Comp306"),Course(selected: false, name: "Ethr113"),Course(selected: false, name: "Ethr102"), Course(selected: false, name: "Ethr105"), Course(selected: false, name: "Acct201"), Course(selected: false, name: "Acct202")]
    
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
        header.layer.borderColor = UIColor(red:0.72, green:0.00, blue:0.00, alpha:1.00).cgColor
        header.layer.borderWidth = 5
        let selectionLabel = UILabel(frame: header.bounds)
        selectionLabel.text = "0 Course Selected"
        selectionLabel.textColor = UIColor(red:0.72, green:0.00, blue:0.00, alpha:1.00)
        selectionLabel.textAlignment = .center
        header.addSubview(selectionLabel)
        tableView.tableHeaderView = header
    }
    
    
    func setUpSearchBar(){
        searchBar.placeholder = "Search"
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
            selectedCourses -= 1
            if searching {
                searchCourse[indexPath.row].selected = false
            } else {
                courses[indexPath.row].selected = false
            }
        } else {
            tableView.cellForRow(at: indexPath)?.accessoryType = UITableViewCell.AccessoryType.checkmark
            selectedCourses += 1
            if searching {
                searchCourse[indexPath.row].selected = true
            } else {
                courses[indexPath.row].selected = true
            }
        }
        
        print(selectedCourses)
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
