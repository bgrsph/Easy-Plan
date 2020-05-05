//
//  plannerViewController.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 5.05.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import UIKit
import RealmSwift

class plannerViewController: UIViewController {

    let realm = try! Realm()
    var schedules : Results<easySchedule>!
    @IBOutlet weak var collectionView: UICollectionView!
    override func viewDidLoad() {
        super.viewDidLoad()
        collectionView.delegate = self
        collectionView.dataSource = self
        loadPlans()
        // Do any additional setup after loading the view.
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

}

extension plannerViewController: UITableViewDataSource, UITableViewDelegate {
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
         return schedules[section].courses.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "tableviewCell",
        for: indexPath) as! plannerTableViewCell
  
        
        let course = schedules[indexPath.section].courses[indexPath.row]
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
        cell.profNameLabel.text = "\(days) \(course.mtgStart) - \(course.mtgEnd)"
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
    
    
}

extension plannerViewController: UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 1
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return schedules.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "collectionCell", for: indexPath) as! plannerCollectionViewCell
//        cell.tableView.delegate = self
//        cell.tableView.dataSource = self
        
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, willDisplay cell: UICollectionViewCell, forItemAt indexPath: IndexPath) {
        guard let collectionCell = cell as? plannerCollectionViewCell else { return }
        collectionCell.setTableViewDataSourceDelegate(self, forRow: indexPath.section)
    }
    
//    override func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
//
//        guard let tableViewCell = cell as? TableViewCell else { return }
//
//        tableViewCell.setCollectionViewDataSourceDelegate(self, forRow: indexPath.row)
//        tableViewCell.collectionViewOffset = storedOffsets[indexPath.row] ?? 0
//    }
    

    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: collectionView.frame.size.width - 10, height: collectionView.frame.size.height)
    }
    
    
}


