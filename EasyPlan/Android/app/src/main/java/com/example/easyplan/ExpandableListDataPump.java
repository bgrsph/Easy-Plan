package com.example.easyplan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump{

    public static HashMap<String, String> getData (){
        HashMap<String, String> expandableListDetail = new HashMap<>();
        expandableListDetail.put(iQuestions.Q1, iAnswers.A1);
        expandableListDetail.put(iQuestions.Q2, iAnswers.A2);
        expandableListDetail.put(iQuestions.Q3, iAnswers.A3);
        return expandableListDetail;
    }

}
