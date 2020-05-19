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
    var plan: Int?
    let realm = try! Realm()
    var schedules : Results<easySchedule>!
    var plans : Results<easyPlan>!
    @IBOutlet weak var tableView: UITableView!
    
    // For the horizontal schedule
    @IBOutlet weak var gridView: UICollectionView!
    @IBOutlet weak var gridFlowLayout: UICollectionViewFlowLayout!
    var schedule2d = [[String]]()
//    layout.estimatedItemSize = CGSizeMake(1.f, 1.f);
    
    
    
    var hoursInDay = ["08:30 - 09:45", "10:00 - 11:15", "11:30 - 12:45", "13:00 - 14:15", "14:30 - 15:45", "16:00 - 17:15", "17:30 - 18:45"]
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        gridView.isHidden = true
        tableView.delegate = self
        tableView.dataSource = self
        loadPlans()
        pageControl.currentPage = updatePageCont()
        pageControl.numberOfPages = 5
        pageControl.currentPageIndicatorTintColor = .systemPink
        pageControl.pageIndicatorTintColor = UIColor(red: 249/255, green: 207/255, blue: 224/255, alpha: 1)
        //        navigationController?.navigationBar.largeTitleTextAttributes = [NSAttributedString.Key.foregroundColor: burgundy]
        checkPrevButton()
        checkNextButton()
        checkFavorite()
        
        let swipeRecognizerRight = UISwipeGestureRecognizer(target: self, action: #selector(self.handleSwipeRight))
        swipeRecognizerRight.direction = UISwipeGestureRecognizer.Direction.right
        swipeRecognizerRight.delegate = self
        tableView.addGestureRecognizer(swipeRecognizerRight)
        
        let swipeRecognizerLeft = UISwipeGestureRecognizer(target: self, action: #selector(self.handleSwipeLeft))
        swipeRecognizerLeft.direction = UISwipeGestureRecognizer.Direction.left
        swipeRecognizerLeft.delegate = self
        tableView.addGestureRecognizer(swipeRecognizerLeft)
        
        gridView.dataSource = self
        gridView.delegate = self
        gridView.frame = tableView.frame
        

//
    }
    
    
    @objc func handleSwipeRight(gesture: UISwipeGestureRecognizer) {
        getPrevSchedule()
    }
    
    @objc func handleSwipeLeft(gesture: UISwipeGestureRecognizer) {
        getNextSchedule()
    }
    
    
    func gestureRecognizer(_ gestureRecognizer: UIGestureRecognizer, shouldRecognizeSimultaneouslyWith otherGestureRecognizer: UIGestureRecognizer) -> Bool {
        return true
    }
    
    func loadPlans(){
        plans = realm.objects(easyPlan.self).sorted(byKeyPath: "title", ascending: true)
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
        let schedule = plans[plan!].schedules[page!]
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
        removeAllDataFromGrid()
        gridView.reloadData()
        
    }
    
    @IBAction func pencilTapped(_ sender: Any) {
        let schedule = plans[plan!].schedules[page!]
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
        let schedule = plans[plan!].schedules[page!]
        favButton.image =  !schedule.isFavorite ? UIImage(systemName: "suit.heart.fill") : UIImage(systemName: "suit.heart")
        do {
            try realm.write {
                schedule.isFavorite = !schedule.isFavorite
            }
        } catch {
            print("Error saving favorite status \(error)")
        }
    }
    
    func checkPrevButton(){
        prevButton.isEnabled = page == 0 ? false : true
        prevButton.alpha = prevButton.isEnabled ? 1.0 : 0.5
    }
    
    func checkNextButton(){
        nextButton.isEnabled = page == plans[plan!].schedules.count - 1 ? false : true
        nextButton.alpha = nextButton.isEnabled ? 1.0 : 0.5
    }
    
    func checkFavorite(){
        let schedule = plans[plan!].schedules[page!]
        favButton.image =  schedule.isFavorite ? UIImage(systemName: "suit.heart.fill") : UIImage(systemName: "suit.heart")
    }
    
    
    @IBAction func prevTapped(_ sender: Any) {
        getPrevSchedule()
        removeAllDataFromGrid()
        gridView.reloadData()
        
    }
    
    @IBAction func nextTapped(_ sender: Any) {
        getNextSchedule()
        removeAllDataFromGrid()
        gridView.reloadData()
    }
    
    func getPrevSchedule(){
        page = (page! - 1) % schedules.count
        let schedule = plans[plan!].schedules[page!]
        scheduleLabel.text = "Schedule #\(schedule.title)"
        rightSlide = false
        tableView.reloadData()
        pageControl.currentPage = page! % 5
        checkFavorite()
        
    }
    
    func getNextSchedule(){
        page = (page! + 1) % schedules.count
        let schedule = plans[plan!].schedules[page!]
        scheduleLabel.text = "Schedule #\(schedule.title)"
        rightSlide = true
        tableView.reloadData()
        pageControl.currentPage = page! % 5
        checkFavorite()
    }
    
    func updatePageCont() -> Int {
        return page! % schedules.count
    }
    
    
    // Horizontal View Protocols
    
    
    override func viewWillTransition(to size: CGSize, with coordinator: UIViewControllerTransitionCoordinator) {
        super.viewWillTransition(to: size, with: coordinator)
        if UIDevice.current.orientation.isLandscape {
            print("Landscape")
            tableView.isHidden = true
            self.tabBarController?.tabBar.isHidden = true
            gridView.isHidden = false
            removeAllDataFromGrid()
            gridView.reloadData()
            
        } else {
            print("Portrait")
            tableView.isHidden = false
            gridView.isHidden = true
            self.tabBarController?.tabBar.isHidden = false
            removeAllDataFromGrid()
            
        }
    }
    
    
    
    func removeAllDataFromGrid() {
        
        for i in 0...gridView.numberOfSections-1
        {
            for j in 0...gridView.numberOfItems(inSection: i) - 1
            {
                gridView.cellForItem(at: NSIndexPath(row: j, section: i) as IndexPath)?.contentView.subviews[0].removeFromSuperview()
                gridView.cellForItem(at: NSIndexPath(row: j, section: i) as IndexPath)?.backgroundColor = .white
            }
        }
        
    }
    
    
    
    
    
}


extension plannerViewController:  UICollectionViewDataSource, UICollectionViewDelegateFlowLayout{
    
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return 6
    }
    
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 7
    }
    
    

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        //        title.text = String(indexPath.section) + "," + String(indexPath.row)
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "sectionCell", for: indexPath) as! HorizontalSectionCollectionViewCell
        
        let title = UILabel(frame: CGRect(x: 0, y: 0, width: cell.bounds.size.width, height: cell.bounds.size.height))
        let schedule = plans[plan!].schedules[page!]
        checkPrevButton()
        checkNextButton()
        
        if indexPath.row == 0 {
            title.text = hoursInDay[indexPath.section]
            title.font = UIFont(name: "AvenirNext-Bold", size: 15)
            title.textAlignment = .center
            cell.contentView.addSubview(title)
            return cell
        }

        else {

            if indexPath.row == 1 && indexPath.section == 0 {

                for course in schedule.courses {

                    if course.monday == "Y" && course.mtgStart == "08:30 AM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }
            }

            if indexPath.row == 1 && indexPath.section == 1 {
                for course in schedule.courses {

                    if course.monday == "Y" && course.mtgStart == "10:00 AM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 1 && indexPath.section == 2 {
                for course in schedule.courses {

                    if course.monday == "Y" && course.mtgStart == "11:30 AM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }
            if indexPath.row == 1 && indexPath.section == 3 {
                for course in schedule.courses {

                    if course.monday == "Y" && course.mtgStart == "01:00 PM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 1 && indexPath.section == 4 {
                for course in schedule.courses {

                    if course.monday == "Y" && course.mtgStart == "02:30 PM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 1 && indexPath.section == 5 {
                for course in schedule.courses {

                    if course.monday == "Y" && course.mtgStart == "04:00 PM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 1 && indexPath.section == 6 {
                for course in schedule.courses {

                    if course.monday == "Y" && course.mtgStart == "17:30 AM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 2 && indexPath.section == 0 {
                for course in schedule.courses {

                    if course.tuesday == "Y" && course.mtgStart == "08:30 AM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 2 && indexPath.section == 1 {
                for course in schedule.courses {

                    if course.tuesday == "Y" && course.mtgStart == "10:00 AM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 2 && indexPath.section == 2 {
                for course in schedule.courses {

                    if course.tuesday == "Y" && course.mtgStart == "11:30 AM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 2 && indexPath.section == 3 {
                for course in schedule.courses {

                    if course.tuesday == "Y" && course.mtgStart == "01:00 PM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 2 && indexPath.section == 4 {
                for course in schedule.courses {

                    if course.tuesday == "Y" && course.mtgStart == "02:30 PM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 2 && indexPath.section == 5 {
                for course in schedule.courses {

                    if course.tuesday == "Y" && course.mtgStart == "04:00 PM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 2 && indexPath.section == 6 {
                for course in schedule.courses {

                    if course.tuesday == "Y" && course.mtgStart == "05:30 PM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }

            }

            if indexPath.row == 3 && indexPath.section == 0 {
                for course in schedule.courses {

                    if course.wednesday == "Y" && course.mtgStart == "08:30 AM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 3 && indexPath.section == 1 {
                for course in schedule.courses {

                    if course.wednesday == "Y" && course.mtgStart == "10:00 AM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 3 && indexPath.section == 2 {
                for course in schedule.courses {

                    if course.wednesday == "Y" && course.mtgStart == "11:30 AM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 3 && indexPath.section == 3 {
                for course in schedule.courses {

                    if course.wednesday == "Y" && course.mtgStart == "01:00 PM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 3 && indexPath.section == 4 {
                for course in schedule.courses {

                    if course.wednesday == "Y" && course.mtgStart == "02:30 PM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 3 && indexPath.section == 5 {
                for course in schedule.courses {

                    if course.wednesday == "Y" && course.mtgStart == "04:00 PM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 3 && indexPath.section == 6 {
                for course in schedule.courses {

                    if course.wednesday == "Y" && course.mtgStart == "05:30 PM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 4 && indexPath.section == 0 {
                for course in schedule.courses {

                    if course.thursday == "Y" && course.mtgStart == "08:30 AM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 4 && indexPath.section == 1 {
                for course in schedule.courses {

                    if course.thursday == "Y" && course.mtgStart == "10:00 AM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 4 && indexPath.section == 2 {
                for course in schedule.courses {

                    if course.thursday == "Y" && course.mtgStart == "11:30 AM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 4 && indexPath.section == 3 {
                for course in schedule.courses {

                    if course.thursday == "Y" && course.mtgStart == "01:00 PM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 4 && indexPath.section == 4 {
                for course in schedule.courses {

                    if course.thursday == "Y" && course.mtgStart == "02:30 PM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 4 && indexPath.section == 5 {

                for course in schedule.courses {

                    if course.thursday == "Y" && course.mtgStart == "04:00 PM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }

            }

            if indexPath.row == 4 && indexPath.section == 6 {

                for course in schedule.courses {

                    if course.thursday == "Y" && course.mtgStart == "05:30 PM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 5 && indexPath.section == 0 {

                for course in schedule.courses {

                    if course.friday == "Y" && course.mtgStart == "08:30 AM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }

            }

            if indexPath.row == 5 && indexPath.section == 1 {
                for course in schedule.courses {

                    if course.friday == "Y" && course.mtgStart == "10:00 AM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 5 && indexPath.section == 2 {
                for course in schedule.courses {

                    if course.friday == "Y" && course.mtgStart == "11:30 AM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 5 && indexPath.section == 3 {
                for course in schedule.courses {

                    if course.friday == "Y" && course.mtgStart == "01:00 PM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 5 && indexPath.section == 4 {

                for course in schedule.courses {

                    if course.friday == "Y" && course.mtgStart == "02:30 PM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }

            }

            if indexPath.row == 5 && indexPath.section == 5 {
                for course in schedule.courses {

                    if course.friday == "Y" && course.mtgStart == "04:00 PM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }


            }

            if indexPath.row == 5 && indexPath.section == 6 {
                for course in schedule.courses {

                    if course.friday == "Y" && course.mtgStart == "05:30 PM" {
                        title.text = "\(course.subject) \(course.catalog)"
                        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
                        title.textAlignment = .center
                        cell.backgroundColor = .systemGreen
                        cell.contentView.addSubview(title)
                        return cell
                    }
                }

            }

        }
            
            
            
        title.text = ""
        title.font = UIFont(name: "AvenirNext-Bold", size: 15)
        title.textAlignment = .center
        cell.contentView.addSubview(title)
        return cell

    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        return 0.0
    }
    
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        
        return CGSize(width: (gridView.bounds.width)/7, height: (gridView.bounds.height)/8)
    }
    
    
    
    
    
    
}

extension plannerViewController: UITableViewDataSource, UITableViewDelegate {
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return plans[plan!].schedules[page!].courses.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let capitalTitle = plans[plan!].title.capitalized
        
        self.navigationItem.title = "\(capitalTitle)"
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "tableviewCell",
                                                 for: indexPath) as! plannerTableViewCell
        let schedule = plans[plan!].schedules[page!]
        checkPrevButton()
        checkNextButton()
        
        if schedule.title.count < 3 {
            scheduleLabel.text = "Schedule \(Int(schedule.title)! + 1)"
        } else {
            scheduleLabel.text = "\(schedule.title)"
        }
        
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


