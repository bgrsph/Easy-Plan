//
//  WalkthroughPageViewController.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 6.05.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import UIKit

protocol WalkthroughPageViewControllerDelegate: class {
    func didUpdatePageIndex(currentIndex: Int)
}

class WalkthroughPageViewController: UIPageViewController, UIPageViewControllerDataSource, UIPageViewControllerDelegate {
    
    weak var walkthroughDelegate: WalkthroughPageViewControllerDelegate?
    
    var pageHeading = ["Search available courses", "Plan your semester as you desire", "See your possible choices", "Plan Easily!"]
    var pageImage = ["onboard1", "onboard2", "onboard3", "onboard4"]
    var pageSubHeadings = ["You can search through all of the courses that the university has offered for the next semester. You can create multiple plans to see your options.", "Most of the courses will have multiple sections, which means that you have many options. For a better performance and usability, use constraints on which days and hours you want to have classes.", "For a number of courses, you can have multiple schedules. Mark those you love the most, delete what you do not want.", "Start planning your semester now!"]
    var currentIndex = 0

    func pageViewController(_ pageViewController: UIPageViewController, viewControllerBefore viewController: UIViewController) -> UIViewController? {
        var index = (viewController as! WalkthroughContentViewController).index
        index -= 1
        
        return contentViewController(at: index)
    }
    
    func pageViewController(_ pageViewController: UIPageViewController, viewControllerAfter viewController: UIViewController) -> UIViewController? {
         var index = (viewController as! WalkthroughContentViewController).index
               index += 1
               
               return contentViewController(at: index)
    }
    
    func contentViewController(at index: Int) -> WalkthroughContentViewController? {
        if index < 0 || index >= pageHeading.count{
            return nil
        }
        let storyboard = UIStoryboard(name: "Onboarding", bundle: nil)
        if let pageContentViewController = storyboard.instantiateViewController(identifier: "WalkthroughContentViewController") as? WalkthroughContentViewController {
            pageContentViewController.imageFile = pageImage[index]
            pageContentViewController.heading = pageHeading[index]
            pageContentViewController.subHeading = pageSubHeadings[index]
            pageContentViewController.index = index
            return pageContentViewController
        }
        
        return nil
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        dataSource = self
        delegate = self
        // Do any additional setup after loading the view.
    
        if let startingViewController = contentViewController(at: 0){
            setViewControllers([startingViewController], direction: .forward, animated: true, completion: nil)
        
    }
}
    
    func forwardPage(){
        currentIndex += 1
        if let nextViewController = contentViewController(at: currentIndex) {
            setViewControllers([nextViewController], direction: .forward, animated: true, completion: nil)
    }
    }

    func pageViewController(_ pageViewController: UIPageViewController, didFinishAnimating finished: Bool, previousViewControllers: [UIViewController], transitionCompleted completed: Bool) {
        if completed {
            if let contentViewController = pageViewController.viewControllers?.first as? WalkthroughContentViewController {
                currentIndex = contentViewController.index
                walkthroughDelegate?.didUpdatePageIndex(currentIndex: currentIndex)
            }
        }
    }
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
