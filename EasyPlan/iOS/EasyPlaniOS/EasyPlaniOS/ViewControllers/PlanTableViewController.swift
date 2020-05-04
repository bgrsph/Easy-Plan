//
//  PlanTableViewController.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 4.04.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import UIKit

class PlanTableViewController: UITableViewController {
    let burgundy = UIColor(red:0.72, green:0.00, blue:0.00, alpha:1.00)
    
    var twoDimensionalArray = [ExpandablePlan(name: "Plan1", isExpanded: false,
                                              scheduleList: [Schedule(name: "S1",
                                                                      scheduleCourseList:
                                                [Course(subject: "Acct", id: "1", catalog: "201",monday: "Y", tuesday: "Y",wednesday: "T", thursday: "T", friday: "T", saturday: "T", sunday: "T", mtgStart: "10:00 AM", mtgEnd: "10:00 AM", section: "01")])])]
    override func viewDidLoad() {
        super.viewDidLoad()
        navigationItem.title = "Plans"
        navigationController?.navigationBar.prefersLargeTitles = true
        
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false
        
        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        self.navigationItem.rightBarButtonItem = self.editButtonItem
    }
    
    // MARK: - Table view data source
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return twoDimensionalArray.count
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        if twoDimensionalArray[section].isExpanded {
            return 0
        }
        return twoDimensionalArray[section].scheduleList.count
    }
    
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "planCell", for: indexPath) as! PlanTableViewCell
        let plan = twoDimensionalArray[indexPath.section].scheduleList[indexPath.row]
        cell.nameLabel.text = plan.name
        return cell
    }
    
    override func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let headerView = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 120))
        headerView.backgroundColor = burgundy
        let headerLabel = UILabel(frame: CGRect(x: 10, y: 10, width:tableView.bounds.size.width, height: 15))
        headerLabel.textColor = UIColor.white
        headerLabel.text = twoDimensionalArray[section].name
        headerLabel.textAlignment = .left
        headerView.addSubview(headerLabel)
        
        let button = UIButton(frame: CGRect(x:headerView.frame.size.width - 100, y:0, width:100, height:28))
        button.backgroundColor = burgundy
        button.tintColor = UIColor.white
        let isExpanded = twoDimensionalArray[section].isExpanded
        button.setImage(isExpanded ? UIImage(systemName: "chevron.down") : UIImage(systemName: "chevron.up"), for: .normal)
        
        button.addTarget(self, action: #selector(handleExpand), for: .touchUpInside)
        button.tag = section
        headerView.addSubview(button)
        return headerView
        
    }
    
    @objc func handleExpand(button: UIButton){
//        delete to collapse
        let section = button.tag
        var indexPaths = [IndexPath]()
        for row in twoDimensionalArray[section].scheduleList.indices {
            let indexPath = IndexPath(row: row, section: section)
            indexPaths.append(indexPath)
        }
        let isExpanded = twoDimensionalArray[section].isExpanded
        twoDimensionalArray[section].isExpanded = !isExpanded
        
        button.setImage(isExpanded ? UIImage(systemName: "chevron.up") : UIImage(systemName: "chevron.down"), for: .normal)
        
        if isExpanded {
            tableView.insertRows(at: indexPaths, with: .fade)
            
        } else {
          tableView.deleteRows(at: indexPaths, with: .fade)
        }
       
    }
    
    override func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 40
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
        twoDimensionalArray[section].scheduleList.remove(at: indexPath.row)
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
    
    /*
     // MARK: - Navigation
     
     // In a storyboard-based application, you will often want to do a little preparation before navigation
     override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
     // Get the new view controller using segue.destination.
     // Pass the selected object to the new view controller.
     }
     */
    
}
