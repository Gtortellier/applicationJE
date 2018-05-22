package com.example.guillaume.onglets;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.Map;

/**
 * Created by Guillaume on 21/12/2017.
 */

public class MyOverlayItem extends OverlayItem {
    private Map<String,String> adresse;
    public MyOverlayItem(String aTitle, String aSnippet, IGeoPoint aGeoPoint) {
        super(aTitle, aSnippet, aGeoPoint);
    }
    public MyOverlayItem (Map<String,String> adresse,IGeoPoint aGeoPoint){
        super("","", aGeoPoint);
        this.adresse=adresse;
    }
    public MyOverlayItem (Map<String,String> adresse,OverlayItem item){
        super(item.getTitle(),item.getSnippet(), item.getPoint());
        this.adresse=adresse;
    }

    public Map<String, String> getAdresse() {
        return adresse;
    }
}
