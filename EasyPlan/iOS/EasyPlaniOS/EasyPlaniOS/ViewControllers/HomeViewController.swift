//
//  HomeViewController.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 27.02.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.


import UIKit

class HomeViewController: UIViewController {
    
   let timePicker = UIDatePicker()
    let courses = ["comp491", "comp303", "comp302", "comp301", "comp304", "comp305", "acct201", "acct202"]
    @IBOutlet weak var searchBar: UISearchBar!
   
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var searchCourseButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
//        setUpNavBar()
        self.navigationController?.navigationBar.prefersLargeTitles = true
       
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        // Hide the navigation bar on the this view controller
        self.tabBarController?.navigationController?.setNavigationBarHidden(true, animated: animated)
        
    }
    
    func setUpNavBar(){
        navigationItem.hidesSearchBarWhenScrolling = false
//        öteki VC için
//        navigationItem.largeTitleDisplayMode = .never
//        let searchController = UISearchController(searchResultsController: nil)
//        navigationItem.searchController = searchController
//
//        searchController.automaticallyShowsCancelButton = true
       
        
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

extension HomeViewController: UITableViewDataSource, UITableViewDelegate {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return courses.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell")
        cell?.textLabel?.text = courses[indexPath.row]
        return cell!
    }
    
    
}
