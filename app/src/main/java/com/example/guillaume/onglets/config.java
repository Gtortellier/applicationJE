package com.example.guillaume.onglets;

import android.util.Log;

import com.example.guillaume.onglets.csv.CsvFileHelper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Guillaume on 09/03/2018.
 */
public class config {
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
    private List<Map<String, String>> mappedData;
    public Map<String,Map<String,List<String>>>fields= new LinkedHashMap<>();
    public Map<String,List<String>> fieldData;
    public Map<String,Map<String,String>> surnom= new LinkedHashMap<>();
    String name ="erreurName";
    String data ="erreurData";
    String type ="erreurType";
    String label ="erreurLabel";
    String defaut="erreurDefaut";
    Boolean rightWord =true;
    List<String> choix = new ArrayList<>();

    public config(File file){
        this.file=file;
        init();
    }

    public Map<String,Map<String,List<String>>> getFields(){
        return this.fields;
    }
    public Map<String,Map<String,String>> getSurnom(){
        return this.surnom;
    }
    public void init(){
        try {
            lines = CsvFileHelper.readFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("fichier absent","le fichier n'a pas été trouvé");
        }
        List<String> wrongFields = Arrays.asList("- NUMERO_PARCELLE","- DATE_MODIF:","- ENQUETEUR :","- ADRESSE:","- Fichelalala:");
        String regex = separator;
        boolean debutFiche = false;
        fieldData = new LinkedHashMap<String,List<String>>();
        for (String line : lines) {
            // Suppression des espaces de fin de ligne
            //line = line.trim();

            // On saute les lignes vides
            if (line.length() == 0) {
                continue;
            }

            // On saute les lignes de commentaire
            else if (line.startsWith("#")) {
                continue;
            }
//            if (!debutFiche) {
//                if (line.contains("Fichelalala")){
//                    debutFiche =true;
//                }
//            }
            else {registerField(line);
                }
            }
    }

    public void registerField(String line){
        List<String> wrongWords = Arrays.asList("REFERENCE_CADASTRALE","DATE_MODIF","DATE_CREA","MOTDIRECTE","ADRESSE","COORDX","COORDY");
//        if (line.contains("- ")){
//            if (line.startsWith("            ")) {// choix
//                if (rightWord) {
//                    choix = fields.get(name).get("data");
//                    String wordChoix = line.replaceAll("(  )+|- |: |(\")", "");
//                    choix.add(wordChoix);
//                    fieldData.put("data", choix);
//                    fields.put(name, fieldData);
//                }
//            }
//            else {//name
//                for(String wrongWord : wrongWords){
//                    if (line.contains(wrongWord)){
//                        rightWord=false;
//                        break;
//                    }
//                    rightWord=true;
//                }
//                if (rightWord) {
//                    name = line.replaceAll("( )+|-|:", "");
//                    //fieldData.clear();
//                    fieldData=new LinkedHashMap<>();
//                    //fieldData.put("type",Arrays.asList(type));
//                    fields.put(name, fieldData);
//                }
//            }
//        }+*
        if (line.contains("- nom")){
            for(String wrongWord : wrongWords){
                if (line.contains(wrongWord)){
                    rightWord=false;
                    break;
                }
                rightWord=true;
            }
            if (rightWord) {
                name = line.replaceAll("(( )+|-|:|nom)", "");
                //fieldData.clear();
                fieldData=new LinkedHashMap<>();
                //fieldData.put("type",Arrays.asList(type));
                fields.put(name, fieldData);
                Map <String,String> labelMap = new LinkedHashMap<>();
                surnom.put(name,labelMap);
            }
        }
        else if (line.contains("valeur")&&rightWord){
                choix = fields.get(name).get("data");
                String wordChoix = line.replaceAll("(  )+|- |: |(\")|valeur", "");
                Log.d("config",wordChoix);
                choix.add(wordChoix);
                fieldData.put("data", choix);
                fields.put(name, fieldData);

        }
        else if (line.contains("label:")&&rightWord) {
            label = line.replaceAll("(  )+|: |label", "");
            Map<String, String> labelMap = surnom.get(name);
            choix = fields.get(name).get("data");
            Log.d("config","test");
            Log.d("config",fields.toString());
            //Log.d("config",choix.toString());
            Log.d("config",label);
            labelMap.put(choix.get(choix.size() - 1), label);
            surnom.put(name, labelMap);
        }
        else if (line.contains("data")&&rightWord){
            data = line.replaceAll("(  )+|: |data","");
            fieldData.put("keyboard",Arrays.asList(data));
            fields.put(name,fieldData);
        }
        else if (line.contains("type")&&rightWord){
            type = line.replaceAll("( )+|: |type","");
            fieldData.put("type",Arrays.asList(type));
            fields.put(name,fieldData);
        }
        else if (line.contains("largeur")&&rightWord){
            String largeur = line.replaceAll("( )+|: |largeur","");
            fieldData.put("largeur",Arrays.asList(largeur));
            fields.put(name,fieldData);
        }
        else if (line.contains("choix")&&rightWord) {
            //choix.clear();
            choix = new ArrayList<>();
            fieldData.put("data", choix);
            fields.put(name, fieldData);
        }
        else if (line.contains("default")&&rightWord){
            choix=fields.get(name).get("data");
            String wordChoix = line.replaceAll("(  )+|- |: ", "");
            choix.remove(wordChoix);
            choix.add(0,wordChoix);
            fieldData.put("data",choix);
            fields.put(name,fieldData);
        }
    }
}
