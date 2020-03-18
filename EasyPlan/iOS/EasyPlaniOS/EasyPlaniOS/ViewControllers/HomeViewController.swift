//
//  HomeViewController.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 27.02.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.


import UIKit

class HomeViewController: UIViewController {
    
    let timePicker = UIDatePicker()
    let courses = ["comp491", "comp303", "comp302", "comp301", "comp304", "comp305", "acct201", "acct202", "ethr113","hums108"]
    var searchCourse = [String]()
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
        searchBar.delegate = self
     
        
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
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell")
        if searching {
            cell?.textLabel?.text = searchCourse[indexPath.row]
        } else {
            cell?.textLabel?.text = courses[indexPath.row]
        }
        return cell!
        
    }
}

extension HomeViewController: UISearchBarDelegate{
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        let text = searchText.lowercased()
        searchCourse = courses.filter({$0.prefix(text.count) == text})
        searching = true
        tableView.reloadData()
    }
    
}
