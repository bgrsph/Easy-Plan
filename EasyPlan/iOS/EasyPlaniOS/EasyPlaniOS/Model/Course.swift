//
//  Course.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 19.03.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import Foundation

class Course{
    
    let id:Int
    var selected:Bool
    let name:String
  
    
    init(selected:Bool, name:String, id:Int) {
        self.selected = selected
        self.name = name
        self.id = id
    }
}

extension Course : Hashable {
    static func == (lhs: Course, rhs: Course) -> Bool {
        return lhs.id == rhs.id
    }
    
    func hash(into hasher: inout Hasher) {
        hasher.combine(id)
        hasher.combine(name)
    }
}
