//
//  PlanTableViewController.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 4.04.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import UIKit
import RealmSwift

class PlanTableViewController: UITableViewController {
    let burgundy = UIColor(red:0.72, green:0.00, blue:0.00, alpha:1.00)
    let realm = try! Realm()
    var plans : Results<easyPlan>!
    var pageToShow = 0
   
    override func viewDidLoad() {
        super.viewDidLoad()
        navigationItem.title = "Plans"
        navigationController?.navigationBar.prefersLargeTitles = true
        
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false
        
        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        self.navigationItem.rightBarButtonItem = self.editButtonItem
        loadPlans()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        tableView.reloadData()
    }
    

    
    func loadPlans(){
        plans = realm.objects(easyPlan.self).sorted(byKeyPath: "title", ascending: true)
    }
    
    // MARK: - Table view data source
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return plans.count
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        if plans[section].isExpanded {
            return 0
        }
        return plans[section].schedules.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "planCell", for: indexPath) as! PlanTableViewCell
        let schedule = plans[indexPath.section].schedules[indexPath.row]
       
        if schedule.title.count < 3 {
                  cell.nameLabel.text = "Schedule #\(Int(schedule.title)! + 1)"
              } else {
                  cell.nameLabel.text = "\(schedule.title)"
              }
        return cell
    }
    
    override func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let headerView = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 150))
        headerView.backgroundColor = burgundy
        headerView.layer.borderWidth = 1.0
        headerView.layer.borderColor = UIColor.black.cgColor
        let headerLabel = UILabel(frame: CGRect(x: 50, y: 10, width:tableView.bounds.size.width, height: 25))
        headerLabel.textColor = UIColor.white
        headerLabel.text = plans[section].title.capitalized
        headerLabel.textAlignment = .left
        headerView.addSubview(headerLabel)
        
        let button = UIButton(frame: CGRect(x: 20, y:10, width:20, height:20))
        button.backgroundColor = burgundy
        button.tintColor = UIColor.white
        let isExpanded = plans[section].isExpanded
        button.setImage(isExpanded ? UIImage(systemName: "chevron.down") : UIImage(systemName: "chevron.up"), for: .normal)
        
        
        let trashButton = UIButton(frame: CGRect(x:headerView.frame.size.width - 40, y:10, width:25, height:25))
        trashButton.backgroundColor = burgundy
        trashButton.tintColor = UIColor.white
        trashButton.setImage(UIImage(systemName: "trash.fill"), for: .normal)
        
        trashButton.addTarget(self, action: #selector(deletePlan), for: .touchUpInside)
        trashButton.tag = section
        button.addTarget(self, action: #selector(handleExpand), for: .touchUpInside)
        button.tag = section
        headerView.addSubview(button)
        headerView.addSubview(trashButton)
        return headerView
        
    }
    
     @objc func deletePlan(button: UIButton){
        let section = button.tag
        let plan = plans[section]
        do {
            try realm.write {
                for schedule in plan.schedules{
                    for course in schedule.courses {
                         realm.delete(course)
                    }
                   realm.delete(schedule)
                }
                realm.delete(plan)
            }
        } catch {
            print("Error saving favorite status \(error)")
        }
        
        tableView.reloadData()
        
    }
    
    @objc func handleExpand(button: UIButton){
        let section = button.tag
        let plan = plans[section]
        do {
            try realm.write {
                plan.isExpanded = !plan.isExpanded
            }
        } catch {
            print("Error saving expanded status \(error)")
        }
        button.setImage(plan.isExpanded ? UIImage(systemName: "chevron.down") : UIImage(systemName: "chevron.up"), for: .normal)
//        if plan.isExpanded {
//            UIView.transition(with: button, duration: 1.5, options: .transitionFlipFromRight, animations: {
//                button.setImage(UIImage(systemName: "chevron.down"), for: .normal)
//        }, completion: nil) }
//        else {
//            UIView.transition(with: button, duration: 1.5, options: .transitionFlipFromRight, animations: {
//                    button.setImage(UIImage(systemName: "chevron.up"), for: .normal)
//            }, completion: nil) }
//
        
        tableView.reloadData()
       
    }
    
    override func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 40
    }
    
    override func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        let animation = AnimationFactory.makeFade(duration: 0.3, delayFactor: 0.005)
                let animator = Animator(animation: animation)
                animator.animate(cell: cell, at: indexPath, in: tableView)
        
    }
    /*
     // Override to support conditional editing of the table view.
     override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
     // Return false if you do not want the specified item to be editable.
     return true
     }
     */
    
  
     // Override to support editing the table view.
     override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
     if editingStyle == .delete {
     // Delete the row from the data source
        let section = indexPath.section
        plans[section].schedules.remove(at: indexPath.row)
        tableView.reloadData()
//        tableView.deleteRows(at: [indexPath.row], with: .fade)
//     } else if editingStyle == .insert {
//     // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
     }
     }
     
    
    /*
     // Override to support rearranging the table view.
     override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {
     
     }
     */
    
    /*
     // Override to support conditional rearranging of the table view.
     override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
     // Return false if you do not want the item to be re-orderable.
     return true
     }
     */
    
    
     // MARK: - Navigation

     // In a storyboard-based application, you will often want to do a little preparation before navigation
     override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
     // Get the new view controller using segue.destination.
     // Pass the selected object to the new view controller.
    
        if let cell = sender as? UITableViewCell {
            let indexPath = self.tableView.indexPath(for: cell)!
            guard let destination = segue.destination as? plannerViewController else {
                             return
                         }
            destination.page = indexPath.row
            destination.plan = indexPath.section
    }
    }
    
}
