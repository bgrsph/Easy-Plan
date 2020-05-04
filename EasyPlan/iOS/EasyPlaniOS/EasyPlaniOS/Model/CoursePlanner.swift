//
//  CoursePlanner.swift
//  EasyPlaniOS
//
//  Created by Buğra Sipahioğlu on 3.05.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import Foundation
import FirebaseDatabase


class CoursePlanner {
    
    var allCoursesInDatabase:[Course]
    var planName:String
    
    
    var ref:DatabaseReference?
    var databaseHandle:DatabaseHandle?
    
    struct EntitiesTBPlanned {
        var coursesTBPlanned: [Course]
        var uniqueEntityDict: [String : Int]
    }
    
    
    init(planName:String) {
        allCoursesInDatabase = []
        self.planName = planName
    }
    
    func downloadCourseData() {
        print("[DEBUG] Downloading Course Data from Firebase...")
        ref = Database.database().reference()
        ref?.child("allCourses").observe(.value, with: { (snapshot) in
            if snapshot.childrenCount > 0 {
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
                    self.allCoursesInDatabase.append(newCourse)
                }
            }
            DispatchQueue.main.async  {
                
                if self.allCoursesInDatabase.count > 0 {
                    print("[DEBUG] Courses has been loaded")
                } else {
                    print("[DEBUG] Courses cannot loaded")
                }
            }
        })
        
    }
    
    
    func getPlan(selectedCourseNames:[String]) -> ExpandablePlan {
        print("[DEBUG] Starting plan calculations...")
        
        let entitiesTBPlannedPre = self.getEntitiesForPlanning(selectedCourseNames: selectedCourseNames)
        let entitiesTBPlannedPost = self.checkForConstraints(entity: entitiesTBPlannedPre)
        
        let schedules = getNonConflictingSchedules(entity: entitiesTBPlannedPost)
        
        
        
        let plan = ExpandablePlan(name: self.planName, isExpanded: false, scheduleList: schedules)
        
        if (plan.scheduleList.count > 0) {
            print("[DEBUG] Plan has been calculated")
        } else {
            
            print("[DEBUG] Plan cannot be calculated")
        }
        
        return plan
    }
    
    
    
    func getNonConflictingSchedules(entity: EntitiesTBPlanned) -> [Schedule] {
        
        var coursesForOneSchedule:[Course]
        var schedules:[Schedule] = []
        var courseLists:[[Course]] = []
        var scheduleCounter = 0
        
        for courseA in entity.coursesTBPlanned {
            
            coursesForOneSchedule = []
            coursesForOneSchedule.append(courseA)
           
            for courseB in entity.coursesTBPlanned {
                
                if (courseA == courseB) {
                    continue
                }
                
                if self.isHappenAtTheSameTime(courseA: courseA, courseB: courseB) {
                    continue
                }
                
                if self.isPreviouslyAddedWithSameType(course: courseB, prevAddedCourses: coursesForOneSchedule) {
                    continue
                }
                
                coursesForOneSchedule.append(courseB)
            }
            
            if entity.uniqueEntityDict.count > coursesForOneSchedule.count {
                
                continue
            }
        
//            //Add the schedule if unique
//            if courseLists.contains(coursesForOneSchedule) {
//
//                continue
//
//            }
            
            // Everything is OK; add the schedule to the list
            
            courseLists.append(coursesForOneSchedule)
            schedules.append(Schedule(name: String(scheduleCounter), scheduleCourseList: coursesForOneSchedule))
            scheduleCounter += 1
            
        }
        
        if(schedules.count > 0) {
            print("[DEBUG] Schedules has been created.")
        } else {
            print("[DEBUG] Schedules cannot be created.")
        }
        
        return schedules
        
    }
    
    func isPreviouslyAddedWithSameType(course:Course, prevAddedCourses: [Course]) -> Bool {
        
        let courseTypeAndName = self.getCourseTypeAndName(course: course)
        var isAdded = false
        for prevAddedCourse in prevAddedCourses {
            
            let prevAddedCourseTypeAndName = self.getCourseTypeAndName(course: prevAddedCourse)
            
            if courseTypeAndName == prevAddedCourseTypeAndName {
                
                isAdded = true
            }
            
        }
        
        return isAdded
    }
    
    
    func isHappenAtTheSameTime(courseA:Course, courseB:Course) -> Bool {
        
        return (courseA.monday == courseB.monday && courseA.tuesday == courseB.tuesday && courseA.wednesday == courseB.wednesday && courseA.thursday == courseB.thursday && courseA.friday == courseB.friday
            && courseA.mtgStart == courseB.mtgStart && courseA.mtgEnd == courseB.mtgEnd)
    }
    
    
    func checkForConstraints(entity: EntitiesTBPlanned) -> EntitiesTBPlanned {
        print("[DEBUG] Checking wheter selections conform contraints or not...")
        
        for course in entity.coursesTBPlanned {
            if !self.doesConformConstraints(course: course) {
                //Decrease the dictionary, if its zero rise error and stop. else; remove course from course list
            }
        }
        
        return entity
    }
    
    
    func doesConformConstraints(course: Course) -> Bool {
        
        return true
    }
    
    
    func getEntitiesForPlanning(selectedCourseNames:[String]) -> EntitiesTBPlanned {
        
        print("[DEBUG] Extracting selected courses from the database...")
        var coursesTBPlanned:[Course] = []
        var uniqueEntityDict = [String : Int]()
        
        for courseName in selectedCourseNames {
            for courseDB in self.allCoursesInDatabase {
                let courseNameDB = (courseDB.subject)  +  (courseDB.catalog)
                
                if courseName == courseNameDB {
                    coursesTBPlanned.append(courseDB)
                    uniqueEntityDict[self.getCourseTypeAndName(course: courseDB), default: 0] += 1 // Holds COMP 130LAB, ACWR 106SECTION, COMP 130SECTION etc.
                }
            }
        }
        
        if (coursesTBPlanned.count > 0 && uniqueEntityDict.count > 0) {
            print("[DEBUG] Selected Courses has been extracted. Number of total matches: " + String(coursesTBPlanned.count))
        } else {
            print("[DEBUG] Selected Courses cannot be extracted.")
        }
        
        return EntitiesTBPlanned(coursesTBPlanned: coursesTBPlanned, uniqueEntityDict: uniqueEntityDict)
    }
    
    
    func doesObeyAllConstraints(course:Course) -> Bool {
        
        return true
    }
    
    func getCourseTypeAndName(course:Course) -> String { //ACWR106SECTION
        
        if (course.section.contains("PS"))
        {
            return (course.subject + course.catalog + "PS")
        }
            
        else if(course.section.contains("LAB")) {
            return (course.subject + course.catalog + "LAB")
        }
            
        else {
            return (course.subject + course.catalog + "SECTION")
        }
        
    }
    
    
    
}
