package com.example.guillaume.onglets.GED;

/**
 * Created by Guillaume on 09/04/2018.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ResultSet {
    private List<String> keywords;
    private List<FileDoc> results;

    public ResultSet(List<String> keywords){
        this.keywords = keywords;
        this.results = new LinkedList<FileDoc>();
    }

    public ResultSet(){
        this.keywords = new ArrayList<String>();
        this.results = new LinkedList<FileDoc>();
    }

    public void addResult(FileDoc doc) {
        this.results.add(doc);
    }

    public void addKeyword(String keyword) {
        this.keywords.add(keyword);
    }

    public List<FileDoc> getResults() {
        return results;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    @Override
    public String toString() {
        String keywords = "";
        for (String keyword : this.keywords) {
            keywords += " ; " + keyword;
        }
        if (keywords.length()>3) keywords = keywords.substring(3);
        return "Mots recherchés : " + keywords + " (" + results.size() + " resultats)";
    }
}

