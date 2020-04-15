//
//  ExpandablePlan.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 4.04.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import Foundation

class ExpandablePlan {
    let name:String
    var isExpanded:Bool
    var scheduleList:[Schedule]
    
    init(name: String,isExpanded:Bool, scheduleList: [Schedule]) {
        self.name = name
        self.isExpanded = isExpanded
        self.scheduleList = scheduleList
    }
}
