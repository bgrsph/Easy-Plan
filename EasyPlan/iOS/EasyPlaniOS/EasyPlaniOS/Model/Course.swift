//
//  Course.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 19.03.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import Foundation

class Course{
    
    var selected:Bool
    let name:String
  
    
    init(selected:Bool, name:String) {
        self.selected = selected
        self.name = name
      
    }
}
