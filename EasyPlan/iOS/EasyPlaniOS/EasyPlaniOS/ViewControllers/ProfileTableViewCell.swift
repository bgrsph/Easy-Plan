//
//  ProfileTableViewCell.swift
//  EasyPlaniOS
//
//  Created by Buğra Sipahioğlu on 31.03.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import UIKit

class ProfileTableViewCell: UITableViewCell {
    
    @IBOutlet var optionLabel: UILabel!
    @IBOutlet var optionIconImageView: UIImageView!

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
