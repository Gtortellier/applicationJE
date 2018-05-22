package com.example.guillaume.onglets.csv;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CsvFicheTerrainDao extends CsvFields {

    public File file;
    public final static String separator = "\\|";
    public static boolean isEmpty; // avant d'écrire dans le fichier, il faut savoir si le fichier est vide initialement ou pas.
    //A l'ouverture de l'application, on crée un CsvFicheDao pour enregistrer toutes les données sous forme de map.
    //On regarde alors si le fichier est vide et si c'est le cas, on met à jour isEmpty


    private CsvFile csvFile;
    private CsvFileWriter fileWriter;
    String Id = new String();
    private List<Map<String,String>> maps;
    private String[] titles;

    public String getId(){
        return Id;
    }
    //ArrayList<String> libelle = new ArrayList<String>();
    //"ID","PARCELLE","MOTCLA","ADRESSE","X_WGS84","Y_WGS84"
    //"string","int","string","string","double","double"
    //ArrayList<String> type = new ArrayList<String>();
//    List<String> libelle = Arrays.asList("ID","PARCELLE","MOTCLA","ADRESSE","X_WGS84","Y_WGS84");
//    List<String> type = Arrays.asList("string","int","string","string","double","double");
    // libelle et fields sont décrits dans CsvFields et sont inutile pour la lecture mais uniquement pour l'écriture si le fichier csv est vide

    private Map<String, String> champs = new LinkedHashMap<String, String>();
    private int nbChamps=libelle.size();


    public Map<String, String> getChamps() {
        return champs;
    }

    public List<String> getListTitles() {
        List<String> listTitles = new ArrayList<String>(Arrays.asList(titles));
        return listTitles;
    }

    public CsvFicheTerrainDao(File file) {

        if (file == null) {
            throw new IllegalArgumentException("Le fichier file ne peut pas être null");
        }
        this.csvFile = new CsvFiles1(file, separator);
        titles=csvFile.getTitles();
        isEmpty=csvFile.getIsEmpty();
        for (int i=0; i<nbChamps;i++) {
            champs.put(libelle.get(i),type.get(i));
        }
        maps = findAllMaps();
    }

    public CsvFicheTerrainDao(File file,String separ) {

        if (file == null) {
            throw new IllegalArgumentException("Le fichier file ne peut pas être null");
        }

        this.csvFile = new CsvFiles1(file, separ);
        titles=csvFile.getTitles();
        isEmpty=csvFile.getIsEmpty();
        for (int i=0; i<nbChamps;i++) {
            champs.put(libelle.get(i),type.get(i));
        }
        maps = findAllMaps();
    }

    public CsvFicheTerrainDao(File file,List<String> libelleP,List<String> typeP) {
        this.libelle = libelleP;
        this.type = typeP;
        if (file == null) {
            throw new IllegalArgumentException("Le fichier file ne peut pas être null");
        }

        this.csvFile = new CsvFiles1(file, separator);
        titles=csvFile.getTitles();
        isEmpty=csvFile.getIsEmpty();
        for (int i=0; i<nbChamps;i++) {
            champs.put(libelle.get(i),type.get(i));
        }
        maps = findAllMaps();
    }



    public List<Map<String, String>> getMaps() {
        return maps;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public List<Map<String,String>> findAllMaps() {
        return csvFile.getMappedData();
    }

    // la seule fonction qui prend en compte un élément fixe de la dao,  savoir l'id
    public Map<String,String> findMapById(String id){
        Map<String,String> mapRes=null;// à implémenter
        for(Map<String,String> map : maps){
            if (map.get("ident").equals(id)){
                mapRes=map;
                break;
            }
        }
        if (mapRes == null) {
            throw new IllegalArgumentException("L'id de la fiche terrain n'a pas été trouvée dans le fichier csv");
        }
        return mapRes;// attention à ne pas retourner une fiche nulle si l'ID n'est pas trouvé
    }

//    public Object getValue (Map<String,String> map, String key){
//        // retourne la valeur correspondant à la clé ex : adresse renverra un String correspondant à l'adresse
//        if (champs.get(key)!=null) {
//            String type = champs.get(key);
//            String value = map.get(key);
//            // tout est en string pour faciliter l'affichage il faudrait peut être faire un check du type à un moment
////            if (type.equals("string")){
////                String resString= value;
////                return resString;
////            }
////            if (type.equals("int")){
////                int resString= Integer.parseInt(value);
////                return resString;
////            }
//            return value;
//        }
//        else {
//            throw new IllegalArgumentException("le champ n'existe pas");
//        }
//
//    }

//    private CsvFicheTerrainDao() {
//        super();
//    }

    public void writeMaps(List<Map<String, String>> mappedData, File file) throws IOException {
        if (mappedData.isEmpty()) {
            throw new IllegalArgumentException("La map ne peut pas être nulle");
        }
        if (file == null) {
            throw new IllegalArgumentException("Le fichier ne peut pas être nul");
        }
        fileWriter = new CsvFilesWriter01(file);
        fileWriter.write(mappedData);
    }
}

