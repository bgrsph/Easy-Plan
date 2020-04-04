//
//  Schedule.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 4.04.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import Foundation

class Schedule {
    let name:String
    let scheduleCourseList:[Course]
    
    init(name: String, scheduleCourseList: [Course]) {
        self.name = name
        self.scheduleCourseList = scheduleCourseList
    }
}
