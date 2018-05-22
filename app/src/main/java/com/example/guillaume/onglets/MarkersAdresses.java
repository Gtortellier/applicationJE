package com.example.guillaume.onglets;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Guillaume on 21/12/2017.
 */

public class MarkersAdresses {
    private List<Map<String,String>> adresses;
    ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();

    public MarkersAdresses (List<Map<String,String>> adresses){
        this.adresses=adresses;
        for (Map<String,String> adresse : adresses){
            GeoPoint p =new GeoPoint(Double.parseDouble(adresse.get("Y_WGS84")),Double.parseDouble(adresse.get("X_WGS84")));
            MyOverlayItem item = new MyOverlayItem(adresse,p);
            items.add(item);
        }
    }

    public ArrayList<OverlayItem> getItems(){
        return items;
    }

}
