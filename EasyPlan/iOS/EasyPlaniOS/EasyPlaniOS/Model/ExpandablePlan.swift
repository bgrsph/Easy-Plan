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
    
    func addSchedule(scheduele:Schedule) {
        
        self.scheduleList.append(scheduele)
        
    }
    
    
    func toString() -> String {
        var res = "\nPrinting Plan: " + self.name + "\n"
        
        for schedule in self.scheduleList {
            
            res += schedule.toString()
        }
        res += "\n\n"
        return res
    }
}
