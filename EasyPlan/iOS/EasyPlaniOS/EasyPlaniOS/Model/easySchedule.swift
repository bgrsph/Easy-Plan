//
//  easySchedule.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 5.05.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import Foundation
import RealmSwift

class easySchedule:Object {
    @objc dynamic var title : String = ""
    @objc dynamic var isFavorite : Bool = false
    var courses = List<easyCourse>()
    var parentPlan = LinkingObjects(fromType: easyPlan.self, property: "schedules")
}
