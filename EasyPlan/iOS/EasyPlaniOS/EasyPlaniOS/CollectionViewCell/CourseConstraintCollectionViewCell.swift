//
//  CourseConstraintCollectionViewCell.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 22.03.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import UIKit

class CourseConstraintCollectionViewCell: UICollectionViewCell {
    @IBOutlet weak var courseLabel: UILabel!
    @IBOutlet weak var highlightIndicator: UIImageView!
    @IBOutlet weak var selectIndicator: UIImageView!
    
    override var isHighlighted: Bool {
        didSet{
            highlightIndicator.isHidden = !isHighlighted
        }
    }
    
    override var isSelected: Bool {
        didSet {
            highlightIndicator.isHidden = !isSelected
            selectIndicator.isHidden = !isSelected
        }
    }
}
