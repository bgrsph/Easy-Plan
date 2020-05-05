//
//  easyCourse.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 5.05.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import Foundation
import RealmSwift

class easyCourse:Object {
    @objc dynamic var id : String = ""
     @objc dynamic var catalog : String = ""
     @objc dynamic var subject : String = ""
     @objc dynamic var monday : String = ""
     @objc dynamic var tuesday : String = ""
     @objc dynamic var wednesday : String = ""
     @objc dynamic var thursday : String = ""
     @objc dynamic var friday : String = ""
     @objc dynamic var mtgEnd : String = ""
     @objc dynamic var mtgStart : String = ""
     @objc dynamic var section : String = ""
    
    var parentSchedule = LinkingObjects(fromType: easySchedule.self, property: "courses")
}
