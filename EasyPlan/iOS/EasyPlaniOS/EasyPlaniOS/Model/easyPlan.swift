//
//  easyPlan.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 5.05.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import Foundation
import RealmSwift

class easyPlan:Object {
    @objc dynamic var title : String = ""
    @objc dynamic var isExpanded:Bool = false
    var schedules = List<easySchedule>()
}
