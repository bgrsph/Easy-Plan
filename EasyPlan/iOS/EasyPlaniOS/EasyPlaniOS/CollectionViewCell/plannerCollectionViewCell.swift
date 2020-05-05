//
//  plannerCollectionViewCell.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 5.05.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import UIKit

class plannerCollectionViewCell: UICollectionViewCell {
    
    @IBOutlet weak var tableView: UITableView!
    
    
}

extension plannerCollectionViewCell {
    
    func setTableViewDataSourceDelegate<D: UITableViewDataSource & UITableViewDelegate>(_ dataSourceDelegate: D, forRow row: Int) {
        
        tableView.delegate = dataSourceDelegate
        tableView.dataSource = dataSourceDelegate
        tableView.reloadData()
    }
}
