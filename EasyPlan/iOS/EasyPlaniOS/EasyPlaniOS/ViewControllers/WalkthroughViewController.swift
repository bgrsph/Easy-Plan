//
//  WalkthroughViewController.swift
//  EasyPlaniOS
//
//  Created by Sitare Arslantürk on 6.05.2020.
//  Copyright © 2020 Easy Plan. All rights reserved.
//

import UIKit

class WalkthroughViewController: UIViewController, WalkthroughPageViewControllerDelegate {
    func didUpdatePageIndex(currentIndex: Int) {
        updateUI()
    }
    
    
    @IBOutlet var pageControl: UIPageControl!{
        didSet {
            pageControl.numberOfPages = 4
        }
    }
    @IBOutlet var nextButton: UIButton!{
        didSet {
            nextButton.layer.cornerRadius = 25.0
            nextButton.layer.masksToBounds = true
        }
    }
    @IBOutlet var skipButton: UIButton!
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    var walkthroughPageViewController: WalkthroughPageViewController?
    
    @IBAction func nextTapped(_ sender: Any) {
        if let index = walkthroughPageViewController?.currentIndex {
            switch index {
            case 0...2:
                walkthroughPageViewController?.forwardPage()
            case 3:
                UserDefaults.standard.set(true, forKey: "hasViewedWalkthrough")
                dismiss(animated: true, completion: nil)
            default:
                break
            }
        }
        updateUI()
    }
    
    func updateUI(){
        if let index = walkthroughPageViewController?.currentIndex {
            switch index {
            case 0...2:
                nextButton.setTitle("NEXT", for: .normal)
                skipButton.isHidden = false
            case 3:
                nextButton.setTitle("GET STARTED", for: .normal)
                skipButton.isHidden = true
            default:
                break
            }
            pageControl.currentPage = index
        }
    }
    
    @IBAction func skipTapped(_ sender: Any) {
        UserDefaults.standard.set(true, forKey: "hasViewedWalkthrough")
        dismiss(animated: true, completion: nil)
    }
    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
        let destination = segue.destination
        if let pageViewController = destination as? WalkthroughPageViewController {
            walkthroughPageViewController = pageViewController
            walkthroughPageViewController?.walkthroughDelegate = self
        }
    }
    

}
