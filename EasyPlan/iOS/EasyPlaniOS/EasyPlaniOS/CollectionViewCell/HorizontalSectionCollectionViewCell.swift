//
//  HorizontalSectionCollectionViewCell.swift
//  EasyPlaniOS
//
//  Created by Buğra Sipahioğlu on 19.05.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import UIKit

@IBDesignable
class HorizontalSectionCollectionViewCell: UICollectionViewCell {


  
    

    
    
    required init?(coder: NSCoder) {
        super.init(coder:coder)
        setup()
    }

    override init(frame: CGRect) {
        super.init(frame:frame)
        setup()
    }

    func setup() {
        self.layer.borderWidth = 1.0
        self.layer.borderColor = UIColor.black.cgColor
        self.layer.cornerRadius = 5.0
        
    }
//

    
}
