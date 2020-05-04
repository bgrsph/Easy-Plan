//
//  CourseService.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 23.03.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import Foundation
import FirebaseDatabase

protocol CourseServiceDelegate {
    func courseDictionaryLoaded(courseDictionary: [Course:Bool])
}

class CourseService {
    var delegate : CourseServiceDelegate?
    var courseDictionary : [Course:Bool] = [:]
    var ref:DatabaseReference?
    var databaseHandle:DatabaseHandle?
//    retrieve the course and listen for changes
    func loadCourses(){
        ref = Database.database().reference()
        databaseHandle = ref?.child("ugradCourses").observe(.value, with: { (snapshot) in
            if snapshot.childrenCount > 0 {
                self.courseDictionary.removeAll()
                for course in snapshot.children.allObjects as![DataSnapshot] {
                    let courseObject = course.value as? [String:Any]
                    let courseSubject = courseObject?["subject"]
                    let courseId = courseObject?["id"]
                    let courseCatalog = courseObject?["catalog"]
                    
                    
                    let monday = courseObject?["monday"]
                    let tuesday = courseObject?["tuesday"]
                    let wednesday = courseObject?["wednesday"]
                    let thursday = courseObject?["thursday"]
                    let friday = courseObject?["friday"]
                    let saturday = courseObject?["saturday"]
                    let sunday = courseObject?["sunday"]
                    let mtgStart = courseObject?["mtgStart"]
                    let mtgEnd = courseObject?["mtgEnd"]
                    let section = courseObject?["section"]
                    
                    let newCourse = Course(subject: courseSubject as! String, id: courseId as! String,
                                           catalog: courseCatalog as! String,
                                           monday: monday as! String,
                                           tuesday: tuesday as! String,
                                           wednesday: wednesday as! String,
                                           thursday: thursday as! String,
                                           friday: friday as! String,
                                           saturday: saturday as! String,
                                           sunday: sunday as! String,
                                           mtgStart: mtgStart as! String,
                                           mtgEnd: mtgEnd as! String,
                                           section: section as! String)
                    
                    self.courseDictionary[newCourse]=false
                    
                }
            }
//            print(self.courseDictionary)
            DispatchQueue.main.async  {
                self.delegate?.courseDictionaryLoaded(courseDictionary: self.courseDictionary)
                
            }
        })
        
    }
    
}
