package com.duythai.project.model;

import java.util.ArrayList;

public class Category {
    public ArrayList<String> list = new ArrayList<>();

    public Category(){
        list.add("Personal");
        list.add("Work");
        list.add("Health");
        list.add("Finance");
        list.add("Family");
    }
}
