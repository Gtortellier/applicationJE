package com.example.guillaume.onglets.csv;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

//crée un liste de map(dico), chaque map correspond à une ligne (keys=titles, values=ligne)
public class CsvFiles1 implements CsvFile {

    public final static String DEFAULT_SEPARATOR = "\\|";
    private String separator = DEFAULT_SEPARATOR;
    private String[] titles= null;
    public List<String> listTitles;
    // on va lire un seul fichier csv, si ce fichier est vide, isEmpty passera à true et CsvFile writer réutilisera ce booléen
    // pour savoir si il faut réécrire le titre ou non
//    public static boolean isEmpty=true;
    private boolean isEmpty=true;

    private File file;
    private List<String> lines;
    private List<String[]> data;
    private List<Map<String, String>> mappedData;


    public CsvFiles1() {
    }

    public CsvFiles1(File file) {
        this.file = file;
        // Init
        init();
    }

    //à supprimer
//    public CsvFiles1(File file, String separator,sctx){
//        this.getApplicationContext();
//        Log.d("filedir",log);
//        new CsvFiles1(file,separator);
//    }
    //à supprimer
    public CsvFiles1(File file, String separator) {
        if (file == null) {
            throw new IllegalArgumentException("Le fichier file ne peut pas être null");
        }

        this.file = file;
        this.separator = separator;
        init();
    }

    // on crée cette fois une arraylist de listes de string. Chaque ligne se trouve dans une liste et l'arraylist regroupe ainsi toutes les lignes comme dans l'exemple suivant
    // [[mot1.1,mot1.2][mot2.1,mot2.2]]
    private void init() {
        try {
            lines = CsvFileHelper.readFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            String path =Environment.getExternalStorageDirectory().getAbsolutePath();
            Log.d("path", path);
            Log.e("fichier absent","le fichier"+file.toString()+" n'a pas été trouvé");
        }
            data = new ArrayList<String[]>(lines.size());
            String regex = separator;
            boolean first = true;
            for (String line : lines) {
                // Suppression des espaces de fin de ligne
                //line = line.trim();

                // On saute les lignes vides
                if (line.length() == 0) {
                    continue;
                }

                // On saute les lignes de commentaire
                if (line.startsWith("#")) {
                    continue;
                }
                isEmpty=false;//si on arrive jusque cette ligne,le fichier n'est pas vide
                String[] oneData = line.split(regex);
                if (first) {
                    titles = oneData;
                    first = false;
                    listTitles = new ArrayList<String>(Arrays.asList(titles));
                } else {
                    data.add(oneData);
                }
            }


        if (!isEmpty){mapData();
        }
        if (isEmpty){
            Log.d("vide","fichierVide");
        }

    }

    private void mapData() {
        mappedData = new ArrayList<Map<String, String>>(); //data.size()

        final int titlesLength = titles.length;
        int oneDataLength = 0;
        String value="";
        for (String[] oneData : data) {
            oneDataLength = oneData.length;
            final Map<String, String> map = new ConcurrentHashMap<String, String>(10);
            //for (int i = 0; i < titlesLength+1; i++) {
            for (int i = 0; i < listTitles.size(); i++) {
                //final String key = CsvFileHelper.cleanKey(titles[i]);
                final String keyb=listTitles.get(i);
                //if titles[i]
                if (i<oneDataLength) {
                    value = oneData[i];
                    map.put(titles[i], oneData[i]);
                }
                else {
                    value="";
                    map.put(listTitles.get(i), "");
                }
            }
            Map mapb=new ConcurrentHashMap<String, String>(map);
            mappedData.add(mapb);
        }
    }

//    private void mapData() {
//        mappedData = new ArrayList<Map<String, String>>(data.size());
//
//        final int titlesLength = titles.length;
//        int oneDataLength;
//        String value="";
//
//
//        for (String[] oneData : data) {
//            oneDataLength = oneData.length;
//            final Map<String, String> map = new HashMap<String, String>();
//            for (int i = 0; i < titlesLength; i++) {
//                //final String key = CsvFileHelper.cleanKey(titles[i]);
//                if (i<oneDataLength) {
//                    value = oneData[i];
//                    map.put(listTitles.get(i), oneData[i]);
//                }
//                else {
//                    value="";
//                    map.put(listTitles.get(i), "");
//                }
//            }
//            mappedData.add(map);
//        }
//    }

    @Override
    public String[] getTitles() {
        return titles;
    }


//    public List<String> getListTitles() {
//        return listTitles;
//    }


    public List<String> getListTitles() {
        return listTitles;
    }

    @Override
    public File getFile() {
        return file;
    }

    public List<String> getLines() {
        return lines;
    }

    @Override
    public List<String[]> getData() {
        return data;
    }

    @Override
    public List<Map<String, String>> getMappedData() {
        if (mappedData==null){
            mappedData = new ArrayList<Map<String, String>>(0);
        }
        return mappedData;
    }

    @Override
    public boolean getIsEmpty() {return isEmpty;}
}



