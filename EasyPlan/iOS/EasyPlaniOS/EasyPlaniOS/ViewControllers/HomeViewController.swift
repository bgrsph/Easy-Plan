//
//  HomeViewController.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 27.02.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.


import UIKit

extension HomeViewController: CourseServiceDelegate {
    func courseDictionaryLoaded(courseDictionary: [Course : Bool]) {
        self.courseDict = courseDictionary
        let keys = courseDict.keys
        var sortCourses : [Course] = []
        sortCourses.append(contentsOf: keys)
        self.courses = sortCourses.sorted { $0.subject.lowercased() < $1.subject.lowercased() }
        self.tableView.reloadData()

    }
    
    
}

class HomeViewController: UIViewController, EditItemViewControllerDelegate {
    func shouldAdd(param: [Course], param2: [Course]) {
        selectedCourses = param2.count
        for course in param {
            courseDict[course]=false
            courseDictionary.removeValue(forKey: course)
        }
        
    }
    
    let courseService = CourseService()
    var courses = [Course]()
    var courseDict : [Course:Bool] = [:]

    var courseDictionary : [Course:Int] = [:]
    let burgundy = UIColor(red:0.72, green:0.00, blue:0.00, alpha:1.00)
    var selectedCourses:Int = 0
    var searchCourse = [Course]()
    var filtered:[String] = []
    var searching = false
    @IBOutlet weak var filterButton: UIButton!
    @IBOutlet weak var searchBar: UISearchBar! {
        didSet {
             searchBar.returnKeyType = .done
             searchBar.enablesReturnKeyAutomatically = false
         }
    }
    
 
    @IBOutlet weak var tableView: UITableView!
    
    var activityIndicatorView: UIActivityIndicatorView!
    let dispatchQueue = DispatchQueue(label: "Example Queue")
    
    override func loadView() {
        super.loadView()
        activityIndicatorView = UIActivityIndicatorView(style: .large)
        activityIndicatorView.color = burgundy
        
        tableView.backgroundView = activityIndicatorView
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setUpSearchBar()
        
        self.navigationController?.navigationBar.prefersLargeTitles = true
       
        tableView.delegate = self
        tableView.dataSource = self
        
        setUpHeader()

        searchBar.delegate = self
        
        courseService.delegate = self
        courseService.loadCourses()

    }
    
     @objc func donePressed(){
        view.endEditing(true)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        // Hide the navigation bar on the this view controller
        self.tabBarController?.navigationController?.setNavigationBarHidden(true, animated: animated)
        tableView.reloadData()
        if selectedCourses < 1 {
            filterButton.isEnabled = false
        }
        searchBar.text = ""
        if (courseDict.keys.count == 0) {
            activityIndicatorView.startAnimating()
            
            dispatchQueue.async {
                Thread.sleep(forTimeInterval: 2)
                OperationQueue.main.addOperation {
                    self.activityIndicatorView.stopAnimating()
                    self.tableView.reloadData()
                }
            }
        }
    }
    
    
       // MARK: - Navigation

       // In a storyboard-based application, you will often want to do a little preparation before navigation
       override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
           // Get the new view controller using segue.destination.
           // Pass the selected object to the new view controller.
        guard let destination = segue.destination as? ConstraintViewController else {
            return
        }
        destination.delegate = self
        searching = false
        let arr = courseDictionary.map {$0.key}
        destination.myCourses = arr
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
    
   
    
    @IBAction func clearAllTapped(_ sender: Any) {
        for course in courseDictionary.keys {
            courseDict[course] = false
        }
        courseDictionary.removeAll()
        selectedCourses = 0
        tableView.reloadData()
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
// DispatchQueue.main.async  {
    if self.searching {
        let selectionCourse = searchCourse[indexPath.row]
            cell.courseNameLabel.text = selectionCourse.subject + selectionCourse.catalog
        if courseDict[selectionCourse]! {
                cell.accessoryType = UITableViewCell.AccessoryType.checkmark
            } else {
                cell.accessoryType = UITableViewCell.AccessoryType.none
            }
        } else {
        let selectionCourse = courses[indexPath.row]
            cell.courseNameLabel.text = selectionCourse.subject + selectionCourse.catalog
        if courseDict[selectionCourse]! {
                cell.accessoryType = UITableViewCell.AccessoryType.checkmark
            } else {
                cell.accessoryType = UITableViewCell.AccessoryType.none
            }
        }
        cell.cellDelegate = self
        cell.index = indexPath
//        }
        setUpHeader()
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {

       
        let selected = courses[indexPath.row]
        if tableView.cellForRow(at: indexPath)?.accessoryType == UITableViewCell.AccessoryType.checkmark {
            tableView.cellForRow(at: indexPath)?.accessoryType = UITableViewCell.AccessoryType.none
            if searching {
                let searchSelected = searchCourse[indexPath.row]
                courseDict[searchSelected] = false
                selectedCourses -= 1
                courseDictionary.removeValue(forKey: searchCourse[indexPath.row])
               
            } else {
                courseDict[selected] = false
                selectedCourses -= 1
                courseDictionary.removeValue(forKey: courses[indexPath.row])
                
            }
        } else {
            tableView.cellForRow(at: indexPath)?.accessoryType = UITableViewCell.AccessoryType.checkmark
            selectedCourses += 1
            if searching {
                let searchSelected = searchCourse[indexPath.row]
                courseDict[searchSelected] = true
                courseDictionary[searchCourse[indexPath.row]] = selectedCourses
                
            } else {
                courseDict[selected] = true
                courseDictionary[courses[indexPath.row]] = selectedCourses
                
            }
        }
        setUpHeader()
        
        if selectedCourses < 1 {
            filterButton.isEnabled = false
        } else {
             filterButton.isEnabled = true
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
        searchCourse = courses.filter({($0.subject.lowercased()+$0.catalog).prefix(text.count) == text})
        searching = true
        tableView.reloadData()
     
    }
    
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        searchBar.endEditing(true)
    }
}


extension HomeViewController: TableViewNew {
    func onClickCellInfo(index: Int) {
        print("Info Clicked")
    }
}


