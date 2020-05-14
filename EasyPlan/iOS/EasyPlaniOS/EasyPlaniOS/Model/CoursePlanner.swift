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
    
    
    var ref:DatabaseReference?
    var databaseHandle:DatabaseHandle?
    
    struct EntitiesTBPlanned {
        var coursesTBPlanned: [Course]
        var uniqueEntityDict: [String : Int]
    }
    
    
    
    
    init() {
        allCoursesInDatabase = []
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
    
    
    func getPlan(selectedCourseNames:[String], planName:String, constraint: Constraint ) -> ExpandablePlan {
        print("[DEBUG] Starting plan calculations...")
        
        let entitiesTBPlannedPre = self.getEntitiesForPlanning(selectedCourseNames: selectedCourseNames)
        let entitiesTBPlannedPost = self.checkForConstraints(entity: entitiesTBPlannedPre, constraint: constraint)
        
        if entitiesTBPlannedPost.coursesTBPlanned.count == 0 {
            return ExpandablePlan(name: "Fail", isExpanded: false, scheduleList: [])
        }
        
        let schedules = getNonConflictingSchedules(entity: entitiesTBPlannedPost)
        let plan = ExpandablePlan(name: planName, isExpanded: false, scheduleList: schedules)
        
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
        
        var coursesTBPlanned =  entity.coursesTBPlanned
        
        //        for course in coursesTBPlanned {
        //
        //            print(course.toString() + "\n")
        //        }
        
        
        for courseA in coursesTBPlanned {
            coursesTBPlanned.shuffle()
            coursesForOneSchedule = []
            coursesForOneSchedule.append(courseA)
            
            for courseB in coursesTBPlanned {
                
                if self.isTheSameCourse(courseA: courseA, courseB: courseB) {
                    
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
            
            var isAdded = true
            
            for c in coursesForOneSchedule {
                
                for c2 in coursesForOneSchedule {
                    
                    if !self.isTheSameCourse(courseA: c, courseB: c2) {
                        
                        if self.isHappenAtTheSameTime(courseA: c, courseB: c2) {
                            
                            //                            print("!! " + courseA.toString() + " ==== " + c2.toString())
                            isAdded = false
                            break
                        }
                        
                    }
                }
                
                if isAdded == false  {
                    
                    break
                }
            }
            
            if !isAdded {
                continue
            }
            
            courseLists.append(coursesForOneSchedule)
            schedules.append(Schedule(name: String(scheduleCounter), scheduleCourseList: coursesForOneSchedule))
            scheduleCounter += 1
            
            if schedules.count == 10 {
                
                return schedules
            }
            
            
        }
        
        if(schedules.count > 0) {
            print("[DEBUG] Schedules has been created.")
        } else {
            print("[DEBUG] Schedules cannot be created.")
        }
        
        return schedules
        
    }
    
    func isTheSameCourse(courseA:Course, courseB: Course) -> Bool {
        
        return courseA.catalog == courseB.catalog && courseA.subject == courseB.subject && courseA.id == courseB.id && courseA.section == courseB.section
        
        
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
            && courseA.saturday == courseB.saturday && courseA.sunday == courseB.sunday && courseA.mtgStart == courseB.mtgStart && courseA.mtgEnd == courseB.mtgEnd)
    }
    
    
    func checkForConstraints(entity: EntitiesTBPlanned, constraint:Constraint) -> EntitiesTBPlanned {
        print("[DEBUG] Checking wheter selections conform contraints or not...")
        
//        print("[DEBUG] Constraints: " + constraint.toString())
        
        var dict = entity.uniqueEntityDict
        var courseList = entity.coursesTBPlanned
        
        for course in courseList {
            if !self.doesConformConstraints(course: course, constraint:constraint) {
                
                //Decrease the dictionary, if its zero rise error and stop. else; remove course from course list
                let courseTypeName = self.getCourseTypeAndName(course: course)
                dict[courseTypeName, default:0] -= 1
                
                if dict[courseTypeName] == 0 {
                    
                    courseList.removeAll()
                    dict.removeAll()
                    return EntitiesTBPlanned(coursesTBPlanned: courseList, uniqueEntityDict: dict)
                }
                
                courseList = courseList.filter { $0 != course }
                
            }
        }
        
        return EntitiesTBPlanned(coursesTBPlanned: courseList, uniqueEntityDict: dict)
    }
    
    
    func doesConformConstraints(course: Course, constraint: Constraint) -> Bool {
                
        if constraint.startTime == "" && constraint.endTime == "" && !constraint.moWeChecked && !constraint.tuThChecked && !constraint.frChecked {
            
            return true
        }
        
        if constraint.startTime != "" &&  constraint.endTime == "" {
            
            
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "h:mm a"
            
            let classStartDate = dateFormatter.date(from: course.mtgStart)
            //            let classEndDate = dateFormatter.date(from: course.mtgEnd)
            dateFormatter.dateFormat = "HH:mm"
            let courseStartTime = dateFormatter.string(from: classStartDate!)
            //            let courseEndTime = dateFormatter.string(from: classEndDate!)
            
            
            let constraintStartDate = dateFormatter.date(from: constraint.startTime)
            //            let constraintEndDate = dateFormatter.date(from: constraint.endTime)
            dateFormatter.dateFormat = "HH:mm"
            let constraintStartTime = dateFormatter.string(from: constraintStartDate!)
            //            let constraintEndTime = dateFormatter.string(from: constraintEndDate!)
            
            if (courseStartTime < constraintStartTime) {
                
                return false
            }
            
            
        }
        
        else if constraint.startTime == "" &&  constraint.endTime != "" {
            
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "h:mm a"
            
            //            let classStartDate = dateFormatter.date(from: course.mtgStart)
            let classEndDate = dateFormatter.date(from: course.mtgEnd)
            dateFormatter.dateFormat = "HH:mm"
            //            let courseStartTime = dateFormatter.string(from: classStartDate!)
            let courseEndTime = dateFormatter.string(from: classEndDate!)
            
            
            //            let constraintStartDate = dateFormatter.date(from: constraint.startTime)
            let constraintEndDate = dateFormatter.date(from: constraint.endTime)
            dateFormatter.dateFormat = "HH:mm"
            //            let constraintStartTime = dateFormatter.string(from: constraintStartDate!)
            let constraintEndTime = dateFormatter.string(from: constraintEndDate!)
            
            
            if (courseEndTime > constraintEndTime) {
                
                return false
            }
            
            
        }
        
        else if constraint.startTime != "" &&  constraint.endTime != "" {

            
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "h:mm a"
            
            let classStartDate = dateFormatter.date(from: course.mtgStart)
            let classEndDate = dateFormatter.date(from: course.mtgEnd)
            dateFormatter.dateFormat = "HH:mm"
            let courseStartTime = dateFormatter.string(from: classStartDate!)
            let courseEndTime = dateFormatter.string(from: classEndDate!)
            
            
            let constraintStartDate = dateFormatter.date(from: constraint.startTime)
            let constraintEndDate = dateFormatter.date(from: constraint.endTime)
            dateFormatter.dateFormat = "HH:mm"
            let constraintStartTime = dateFormatter.string(from: constraintStartDate!)
            let constraintEndTime = dateFormatter.string(from: constraintEndDate!)
            
            if (courseStartTime < constraintStartTime || courseEndTime > constraintEndTime) {
                
                print("Course: " + course.toString())
                print("Constraints: " + constraintStartTime + " -- " + constraintEndTime )
                return false
            }
            
        }
        
        else {
            
            if constraint.moWeChecked {
                
                if constraint.tuThChecked && constraint.frChecked { // GOES TO SCHOOL EVERY DAY
                    
                    return true
                }
                    
                else if constraint.tuThChecked && !constraint.frChecked {
                    
                    if (course.friday == "Y") {
                        
                        return false
                    } else {
                        return true
                    }
                    
                }
                    
                else if !constraint.tuThChecked && constraint.frChecked {
                    
                    if course.tuesday == "Y" || course.thursday == "Y" {
                        
                        return false
                    } else {
                        return true
                    }
                    
                }
                    
                else if !constraint.tuThChecked && !constraint.frChecked {
                    
                    if course.tuesday == "Y" || course.thursday == "Y" || course.friday == "Y" {
                        
                        return false
                    } else {
                        return true
                    }
                    
                }
                
            } else {
                
                if constraint.tuThChecked && constraint.frChecked {
                    
                    if course.monday == "Y" || course.wednesday == "Y" {
                        
                        return false
                    } else {
                        return true
                    }
                    
                    
                }
                    
                else if constraint.tuThChecked && !constraint.frChecked {
                    
                    if course.monday == "Y" || course.wednesday == "Y" || course.friday == "Y" {
                        
                        return false
                    } else {
                        
                        return true
                    }
                    
                }
                    
                else if !constraint.tuThChecked && constraint.frChecked {
                    
                    if course.monday == "Y" || course.wednesday == "Y" || course.tuesday == "Y" || course.thursday == "Y" {
                        
                        return false
                    } else {
                        
                        return true
                    }
                    
                }
                    
                else if !constraint.tuThChecked && !constraint.frChecked { // GOES TO SCHOOL EVERY DAY
                    
                    return true
                }
                
            }

            
        }
        
        
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
