//
//  Schedule.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 4.04.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import Foundation

class Schedule: Equatable{
    
    static func == (lhs: Schedule, rhs: Schedule) -> Bool {
        
        return lhs.name == rhs.name && lhs.scheduleCourseList == rhs.scheduleCourseList
    }
    
    
    let name:String
    var scheduleCourseList:[Course]
    
    init(name: String, scheduleCourseList: [Course]) {
        self.name = name
        self.scheduleCourseList = scheduleCourseList
    }
    
    func addCourse(course:Course) {
        self.scheduleCourseList.append(course)
    }
    
    func toString() -> String {
        var res = "\nPrinting Schedule " + self.name
        for course in self.scheduleCourseList {
            res += ("\n" + course.toString())
        }
        res += "\n-------------------------------------\n"
        return res
    }
    
    
}
