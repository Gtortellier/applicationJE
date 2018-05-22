package com.example.guillaume.onglets;

import com.example.guillaume.onglets.geopoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Guillaume on 11/12/2017.
 */

public class findMapParcelle {

    private List<Map<String,String>> adresses;
    private geopoint p;

    public findMapParcelle(List<Map<String,String>> adresses, geopoint p){
        this.p=p;
        this.adresses=adresses;

    }

    public Map<String, String> getParcelle() {
        Map<String,String> parcelle = null;
        double lat=0;
        double lon=0;
        double distance=10;//arbitraire mais ça devrait être suffisant
        double distanceMin=5;
        for (Map<String,String> map : this.adresses){
            lat= Double.valueOf(map.get("Y_WGS84"));
            lon= Double.valueOf(map.get("X_WGS84"));
            geopoint pbis = new geopoint(lat, lon);
            distance=this.p.distance(pbis);
            // dans le fichier d'adresses, plusieurs parcelles d'ident différents ont exactement les mêmes coordonnées
            // mais les autres infos qu'on récupère sont les mêmes (PARCELLE;MOTCLA;ADRESSE;X_WGS84;Y_WGS84)
            //on récupèrera donc uniquement la première parcelle.
            //autre possibilité : supprimer les lignes doublons
            if (distance<distanceMin){
                distanceMin=distance;
                parcelle=map;
            }
        }
        return parcelle;
    }

    public void setP(geopoint p) {
        this.p = p;
    }

    public geopoint getP() {
        return p;
    }
}
