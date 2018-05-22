package com.example.guillaume.onglets.csv;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Guillaume on 11/12/2017.
 */

public abstract class CsvFields {
    List<String> libelle = Arrays.asList("IDENT","PARCELLE","MOTCLA","ADRESSE","X_WGS84","Y_WGS84");
    List<String> type = Arrays.asList("string","int","string","string","double","double");

    public List<String> getLibelle() {
        return libelle;
    }

    public List<String> getType() {
        return type;
    }
}
