package com.example.guillaume.onglets.GED;

import android.graphics.Bitmap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

//import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by Guillaume on 25/12/2017.
 */

public class GEDclass {

        ArrayList<FileDoc> results;
        Pattern pattern;
        Bitmap.Config conf;
        List<String> excludedTerms;
        Pattern filePattern;

        public GEDclass() {
            this.results = new ArrayList<FileDoc>();
            String regex = ".*\\.[A-Za-z]{3,4}$";
            pattern = Pattern.compile(regex);
            filePattern = Pattern.compile(regex);
            excludedTerms = new ArrayList<String>(); // à remplir avec les mots interdits
            String[] excludedTermsList = new String[]{"ALLEE", "AVENUE", "BOULEVARD", "CANAL", "COURS", "IMPASSE", "PASSAGE", "PLACE", "PONT", "PROMENADE", "PETITE", "ROUTE", "RUE", "ESPLANADE", "de", "la", "du", "le","d","l"};
            for (String term : excludedTermsList){
                excludedTerms.add(term.toLowerCase());
            }
        }

        /**
         *
         * @param keywordsList
         *  Les mots clefs à chercher
         * @param dirPath
         *  Les fichiers .txt dans lesquels chercher
         * @return
         */
//        public ArrayList<FileDoc> searchDoc(String[] keywordsList, String dirPath) throws IOException {
//            File dir = new File(dirPath);
//            results.clear();
//            for (File file : dir.listFiles()) {
//                if (file.isFile() && (file.getName().substring(file.getName().length()-4)).equals(".txt")) {
//                    FileInputStream fis = new FileInputStream(file);
//                    InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-15");
//                    BufferedReader br = new BufferedReader(isr);
//
//                    String line;
//                    A : while((line = br.readLine()) != null){
//                        if (line.trim().equals("REF")) {
//                            String ref = br.readLine();
//                            if (br.readLine().trim().equals("MCL")) {
//                                String mcl = br.readLine();
//                                for (String keyword : keywordsList) {
//                                    // si il faut comparer avec l'adresse
//                                    if (!mcl.contains(keyword)) {
//                                        continue A;
//                                    }
//                                }
//                                String txt = "Non renseigné";
//                                if (br.readLine().trim().equals("TXT")) txt = br.readLine();
//
//                                if (pattern.matcher(ref).find()) {
//                                    results.add(new FileDoc(searchFile(dir, ref), ref, mcl, txt));
//                                }
//                                else {
//                                    results.add(new FileDoc(file, ref, mcl, txt));
//                                }
//                            }
//                        }
//                    }
//                    br.close();
//                }
//            }
//            return results;
//        }

        public ResultSet searchDoc(String[] keywordsList, String dirPath) throws IOException {
            File dir = new File(dirPath);
            ResultSet resultSet = new ResultSet();
            results.clear();
            List<Pattern> keywordPaternsList = new ArrayList<Pattern>(keywordsList.length);
            for (String keyword : keywordsList) {
                if (!excludedTerms.contains(keyword.toLowerCase())) {
                    resultSet.addKeyword(keyword);
                    try {
                        Integer.valueOf(keyword);
                        keywordPaternsList.add(Pattern.compile("(^|[^0-9])"+keyword.toLowerCase()+"([^0-9]|$)"));
                    }
                    catch (NumberFormatException e) {
                        keywordPaternsList.add(Pattern.compile(keyword.toLowerCase()));
                    }
                }
            }
            for (File file : dir.listFiles()) {
                if (file.isFile() && (file.getName().substring(file.getName().length()-4)).equals(".txt")) {
                    FileInputStream fis = new FileInputStream(file);
                    InputStreamReader isr = new InputStreamReader(fis, "Cp1252");
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    String  title = "";
                    A : while((line = br.readLine()) != null){
                        if (line.trim().equals("REF") || line.trim().equals("PAGE")) {
                            String page = "";
                            String ref = "";
                            if (line.trim().equals("PAGE")) {
                                page = br.readLine();
                            }
                            else {
                                ref = br.readLine();
                            }
                            if (br.readLine().trim().equals("MCL")) {
                                String mcl = br.readLine();
                                for (Pattern keywordPatern : keywordPaternsList) {
                                    if (!keywordPatern.matcher(mcl.toLowerCase()).find()) {
                                        continue A;
                                    }
                                }
                                String txt = "";
                                if (br.readLine().trim().equals("TXT")) {
                                    txt = br.readLine();
                                    while ((line = br.readLine()).trim().length() > 0) {
                                        txt += "\n" +line;
                                    }
                                }
                                if (filePattern.matcher(ref).find()) {
                                    File refFile = new File(dirPath + "/doc/" + ref);
                                    if (refFile.exists())
                                        resultSet.addResult(new FileDoc(refFile, ref, mcl, txt));
                                    else
                                        resultSet.addResult(new FileDoc(ref, "", mcl, txt));
                                }
                                else if (!page.equals("")) {
                                    resultSet.addResult(new FileDoc(file.getName(), page, mcl, txt));
                                }
                                else {
                                    resultSet.addResult(new FileDoc(ref, "", mcl, txt));
                                }
                            }
                        }
                    }
                    br.close();
                }
            }
            return resultSet;
        }


    public ResultSet getPlotDoc(String address, String dirPath) throws IOException {
        if (address != null && dirPath != null) {
            String[] keywords = address.split(" ");
            return this.searchDoc(keywords, dirPath);
        }
        return null;
    }
        // on enlève les chiffres/bis et ter de keywords
    public ResultSet getPlotDocLarge(String address, String dirPath) throws IOException {
        if (address != null && dirPath != null) {
            String[] keywords = address.split(" ");
            List<String> keywords2 = new ArrayList<String>();
            for (int i = 0 ; i < keywords.length; i++)
                try {
                    Integer.valueOf(keywords[i]);
                }
                catch (NumberFormatException e) {
                    if (!keywords[i].toLowerCase().endsWith("bis") && !keywords[i].toLowerCase().endsWith("ter"))  {
                        keywords2.add(keywords[i]);
                    }
                }
            return this.searchDoc(keywords2, dirPath);
        }
        return null;
    }
    public ResultSet searchDoc(List<String> mcl, String dirPath) throws IOException {
        String[] keywords = mcl.toArray(new String[mcl.size()]);
        return searchDoc(keywords, dirPath);
    }




    public File searchFile(File dir, String fileName) {
            for (File f: dir.listFiles()) {
                if (f.isFile()) {
                    if (f.getName().equals(fileName)) {
                        return f;
                    }
                }
                else if (f.isDirectory()){
                    File a = searchFile(f, fileName);
                    if (a!= null) {
                        return a;

                    }
                }
            }
            return null;
        }

        public ArrayList<FileDoc> searchDoc(String mcl) {
            ArrayList<FileDoc> fileList= new ArrayList<FileDoc>();
            return fileList;
        }
// Pour l'instant, on a pas besoin d'ajouter de documents depuis la tablette
//        public void addDoc(String ref, String mcl, String txt, File file, File refFile) throws IOException {
//            BufferedWriter writer = null;
//
//            writer = new BufferedWriter(new FileWriter(refFile, true));
//            writer.newLine();
//            writer.write ("REF");
//            writer.newLine();
//            writer.write(ref);
//            writer.newLine();
//            writer.write("MCL");
//            writer.newLine();
//            writer.write(mcl);
//            writer.newLine();
//            writer.write("TXT");
//            writer.newLine();
//            writer.write(txt);
//            writer.newLine();
//            writer.close();
//            Files.move(Paths.get(file.getAbsolutePath()), Paths.get(refFile.getParent() + "\\" + ref), REPLACE_EXISTING);
//        }



}
