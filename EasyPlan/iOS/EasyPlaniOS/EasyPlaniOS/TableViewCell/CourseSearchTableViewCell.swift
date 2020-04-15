//
//  CourseSearchTableViewCell.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 19.03.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import UIKit

protocol TableViewNew {
    func onClickCellInfo(index: Int)
}

class CourseSearchTableViewCell: UITableViewCell {

    @IBOutlet weak var courseNameLabel: UILabel!
    
    
    var cellDelegate: TableViewNew?
    var index: IndexPath?
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

    @IBAction func infoClicked(_ sender: Any) {
        cellDelegate?.onClickCellInfo(index: (index?.row)!)
    }
    
    
 
}
