package com.example.guillaume.onglets;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.widget.Toast;

import org.osmdroid.api.IMapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Guillaume on 14/12/2017.
 */

class MyItemizedOverlay extends ItemizedOverlay{
    Context context;
    MapView map;
    List<Map<String,String>> adresses;
    GeoPoint startPoint = new GeoPoint (45.7748,4.8625);
    int zoom = 18;


    public MyItemizedOverlay(Drawable c, Context context, MapView map, List<Map<String,String>> adresses) {
        super(c);
// TODO Auto-generated constructor stub
        populate();
        this.context = context;
        this.map=map;
        this.adresses=adresses;
    }

    @Override
    protected OverlayItem createItem(int i) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }


//    @Override public boolean onSingleTapConfirmed(MotionEvent e, MapView mapView){
//        Projection proj = map.getProjection();
//        GeoPoint loc = (GeoPoint) proj.fromPixels((int)e.getX(), (int)e.getY());
//
////        Double longitude = (((double)loc.getLongitude()));
////        Double latitude = (((double)loc.getLatitude()));
////        String sLongitude = Double.toString(longitude);
////        String sLatitude = Double.toString(latitude);
////        geopoint p = new geopoint(latitude,longitude);
////        findMapParcelle f = new findMapParcelle(adresses,p);
////        Map<String,String> mapbis = new HashMap<String, String>(f.getParcelle());
////        //toast = mapbis.keySet().toArray()[6].getClass().getName()+mapbis.keySet().toArray()[6].toString().compareTo("hashcode")+mapbis.keySet().toString()+mapbis.keySet().toArray()[6]+mapbis.get("ID")+mapbis.get(mapbis.keySet().toArray()[6])+mapbis.containsKey(mapbis.keySet().toArray()[6]);
////        String toast = "IDENT : "+mapbis.get("IDENT")+", PARCELLE : "+mapbis.get("PARCELLE")+", ADRESSE : "+mapbis.get("ADRESSE")+", MOTCLE : "+mapbis.get("MOTCLA")+", LAT : "+mapbis.get("Y_WGS84")+", LON : "+mapbis.get("X_WGS84");
////        //String toast = mapbis.toString();
////        Toast toast1 = Toast.makeText(context, toast, Toast.LENGTH_LONG);
////        toast1.show();
//////        Toast.makeText(context,
//////                "Touch on marker: \n" ,
//////                Toast.LENGTH_LONG).show();
//        startPoint=new GeoPoint(loc.getLatitude(),loc.getLongitude());
//        Toast toast1 = Toast.makeText(context, startPoint.toString(), Toast.LENGTH_LONG);
//        toast1.show();
//        return true;
//    }
    @Override public boolean onTouchEvent(MotionEvent e, MapView mapView){
        Projection proj = map.getProjection();
        startPoint = (GeoPoint) proj.fromPixels((int)e.getX(), (int)e.getY());
        zoom = mapView.getZoomLevel();
        MainActivity.updateStartPoint(startPoint);
        MainActivity.updateZoom(zoom);
//        Toast toast1 = Toast.makeText(context, startPoint.toString(), Toast.LENGTH_LONG);
//        toast1.show();
       return false;
    }
    public GeoPoint getStartPoint(){
        return startPoint;
    }


    @Override
    public boolean onSnapToItem(int x, int y, Point snapPoint, IMapView mapView) {
        return false;
    }
}
