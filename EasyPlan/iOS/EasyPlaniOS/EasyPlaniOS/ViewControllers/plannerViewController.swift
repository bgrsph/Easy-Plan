//
//  plannerViewController.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 5.05.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import UIKit
import RealmSwift

class plannerViewController: UIViewController, UIGestureRecognizerDelegate {

    @IBOutlet weak var scheduleLabel: UILabel!
    @IBOutlet weak var favButton: UIBarButtonItem!
    @IBOutlet weak var prevButton: UIButton!
    @IBOutlet weak var nextButton: UIButton!
   
    let burgundy = UIColor(red:0.72, green:0.00, blue:0.00, alpha:1.00)
   
    var rightSlide = true
    @IBOutlet weak var pageControl: UIPageControl!
    var page: Int?
    let realm = try! Realm()
    var schedules : Results<easySchedule>!
    @IBOutlet weak var tableView: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        tableView.delegate = self
        tableView.dataSource = self
        loadPlans()
        pageControl.currentPage = updatePageCont()
        pageControl.numberOfPages = 5
        pageControl.currentPageIndicatorTintColor = .systemPink
        pageControl.pageIndicatorTintColor = UIColor(red: 249/255, green: 207/255, blue: 224/255, alpha: 1)
//        navigationController?.navigationBar.largeTitleTextAttributes = [NSAttributedString.Key.foregroundColor: burgundy]
        checkButton()
        
        let swipeRecognizerRight = UISwipeGestureRecognizer(target: self, action: #selector(self.handleSwipeRight))
        swipeRecognizerRight.direction = UISwipeGestureRecognizer.Direction.right
        swipeRecognizerRight.delegate = self
        tableView.addGestureRecognizer(swipeRecognizerRight)
        
        let swipeRecognizerLeft = UISwipeGestureRecognizer(target: self, action: #selector(self.handleSwipeLeft))
        swipeRecognizerLeft.direction = UISwipeGestureRecognizer.Direction.right
        swipeRecognizerLeft.delegate = self
        tableView.addGestureRecognizer(swipeRecognizerLeft)
        
    }
 
   @objc func handleSwipeRight(gesture: UISwipeGestureRecognizer) {
            print("swiped right")
    }
    
    @objc func handleSwipeLeft(gesture: UISwipeGestureRecognizer) {
            print("swiped left")
    }
    
    
    func gestureRecognizer(_ gestureRecognizer: UIGestureRecognizer, shouldRecognizeSimultaneouslyWith otherGestureRecognizer: UIGestureRecognizer) -> Bool {
            return true
    }
    
    func loadPlans(){
        
        schedules = realm.objects(easySchedule.self)
    }
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */
    
    @IBAction func trashTapped(_ sender: Any) {
        let schedule = schedules[page!]
        do {
            try realm.write {
                for course in schedule.courses{
                    realm.delete(course)
                }
                realm.delete(schedule)
            }
        } catch {
            print("Error saving favorite status \(error)")
        }
        
        tableView.reloadData()
        
    }
    
    @IBAction func pencilTapped(_ sender: Any) {
        let schedule = schedules[page!]
        var textField = UITextField()
        let alert = UIAlertController(title: "Change Schedule Name", message: "", preferredStyle: .alert)
        let action = UIAlertAction(title: "Done", style: .default) { (action) in
            self.scheduleLabel.text = textField.text
            do {
                try self.realm.write {
                    schedule.title = self.scheduleLabel.text!
                }
            } catch {
                print("Error renaming schedule \(error)")
            }
        }
        alert.addTextField { (alertTextField) in
            alertTextField.placeholder = "New Schedule Name"
            textField = alertTextField
        }
       
        let cancelAction = UIAlertAction(title: "Cancel", style: .cancel, handler: nil)

        alert.addAction(cancelAction)
        alert.addAction(action)
         
         alert.preferredAction = cancelAction
        present(alert, animated: true, completion: nil)
    }
    @IBAction func heartTapped(_ sender: Any) {
        let schedule = schedules[page!]
        favButton.image =  !schedule.isFavorite ? UIImage(systemName: "suit.heart.fill") : UIImage(systemName: "suit.heart")
        do {
            try realm.write {
                schedule.isFavorite = !schedule.isFavorite
            }
        } catch {
            print("Error saving favorite status \(error)")
        }
    }
    
    func checkButton(){
        prevButton.isEnabled = page == 0 ? false : true
        prevButton.alpha = prevButton.isEnabled ? 1.0 : 0.5
    }
    
    func checkFavorite(){
         let schedule = schedules[page!]
         favButton.image =  schedule.isFavorite ? UIImage(systemName: "suit.heart.fill") : UIImage(systemName: "suit.heart")
    }
    
    
    @IBAction func prevTapped(_ sender: Any) {
       
        page = (page! - 1) % schedules.count
        let schedule = schedules[page!]
        scheduleLabel.text = "Schedule #\(schedule.title)"
        rightSlide = false
        tableView.reloadData()
        pageControl.currentPage = page! % 5
        checkFavorite()
    }
    
    @IBAction func nextTapped(_ sender: Any) {
        page = (page! + 1) % schedules.count
        let schedule = schedules[page!]
        scheduleLabel.text = "Schedule #\(schedule.title)"
        rightSlide = true
        tableView.reloadData()
        pageControl.currentPage = page! % 5
        checkFavorite()
    }
    
    func updatePageCont() -> Int {
        return page! % schedules.count
    }
    
}

extension plannerViewController: UITableViewDataSource, UITableViewDelegate {
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
         return schedules[page!].courses.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "tableviewCell",
        for: indexPath) as! plannerTableViewCell
        let schedule = schedules[page!]
        checkButton()
        scheduleLabel.text = "Schedule #\(schedule.title)"
        scheduleLabel.textColor = burgundy
        cell.bgView.backgroundColor = UIColor.randomColor()
        
        let course = schedule.courses[indexPath.row]
        cell.courseNameLabel.text = "\(course.subject) \(course.catalog) - \(course.section)"
        
        var days = ""
        if(course.monday == "Y"){
            days.append("Mo")
        }
        if(course.tuesday == "Y"){
        days.append("Tu")
        }
        if(course.wednesday == "Y"){
        days.append("We")
        }
        if(course.thursday == "Y"){
                   days.append("Th")
               }
               if(course.friday == "Y"){
               days.append("Fr")
               }
        cell.profNameLabel.text = "\(course.mtgStart) - \(course.mtgEnd)"
        cell.dayLabel.text = "\(days)"
        cell.teacherLabel.text = "TBA"
        cell.layer.shadowRadius = 5
        cell.layer.shadowOpacity = 0.9
        cell.layer.shadowColor = UIColor.darkGray.cgColor
        cell.layer.shadowOffset = CGSize(width: 0, height: 2.0)
        cell.layer.masksToBounds = false
        return cell
    }
    
    func tableView(_: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 100
    }
    
    func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
            let animation = AnimationFactory.makeSlideIn(duration: 0.5, delayFactor: 0, right: rightSlide)
            let animator = Animator(animation: animation)
            animator.animate(cell: cell, at: indexPath, in: tableView)
    }
}

extension UITableView {
    func isLastVisibleCell(at indexPath: IndexPath) -> Bool {
        guard let lastIndexPath = indexPathsForVisibleRows?.last else {
            return false
        }
        return lastIndexPath == indexPath
    }
}


