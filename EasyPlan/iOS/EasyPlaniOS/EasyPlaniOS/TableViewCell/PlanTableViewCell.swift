//
//  PlanTableViewCell.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 4.04.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import UIKit

class PlanTableViewCell: UITableViewCell {

    @IBOutlet weak var nameLabel: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
