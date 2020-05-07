//
//  Constraint.swift
//  EasyPlaniOS
//
//  Created by Buğra Sipahioğlu on 7.05.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import Foundation


class Constraint {
    
    var frChecked:Bool
    var tuThChecked:Bool
    var moWeChecked:Bool
    var startTime:String
    var endTime:String
    
    
    init(frChecked:Bool, tuThChecked:Bool, moWeChecked:Bool, startTime:String, endTime:String ) {
        self.frChecked = frChecked
        self.tuThChecked = tuThChecked
        self.moWeChecked = moWeChecked
        self.startTime = startTime
        self.endTime = endTime
    }
    
    
    
}
