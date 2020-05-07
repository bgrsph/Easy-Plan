//
//  Course.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 19.03.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import Foundation

class Course {
    
    let id:String
    let catalog:String
    let subject:String
    
    let monday:String
    let tuesday:String
    let wednesday:String
    let thursday:String
    let friday:String
    let saturday:String
    let sunday:String
    let mtgEnd:String
    let mtgStart:String
    let section:String
    
    init(subject:String, id:String, catalog:String, monday:String, tuesday:String, wednesday:String,thursday:String,friday:String,saturday:String,sunday:String,mtgStart:String,mtgEnd:String,section:String) {
        self.catalog = catalog
        self.subject = subject
        self.id = id
        
        self.monday = monday
        self.tuesday = tuesday
        self.wednesday = wednesday
        self.thursday = thursday
        self.friday = friday
        self.saturday = saturday
        self.sunday = sunday
        self.mtgStart = mtgStart
        self.mtgEnd = mtgEnd
        self.section = section
    }
    
    func toString() ->String {
        
        var days = ""
        
        if (self.monday == "Y") {
            days += " Monday "
        }
        
        if (self.tuesday == "Y") {
            days += "Tuesday "
        }
        if (self.wednesday == "Y") {
            days += "Wednesday "
        }
        if (self.thursday == "Y") {
            days += "Thursday "
        }
        if (self.saturday == "Y") {
            days += "Saturday "
        }
        if (self.sunday == "Y") {
            days += "Sunday"
        }
    
        return (self.subject + " -- " + self.catalog + " -- " +  self.section + " -- " + self.mtgStart + " -- " + self.mtgEnd + "--" + days)
        
    }
}

extension Course : Hashable {
    static func == (lhs: Course, rhs: Course) -> Bool {
        return lhs.id == rhs.id
    }
    
    func hash(into hasher: inout Hasher) {
        hasher.combine(id)
        hasher.combine(subject)
    }
}
