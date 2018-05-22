
package com.example.guillaume.onglets.csv;
import android.os.Environment;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class CsvFileHelper {

    public static String getCompleteFileName (String fileName){
        final String completeFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + fileName;
        return completeFileName;
    }
    public static File getResource(String fileName) {
        final String completeFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + fileName;
        // création du file dans java à partir du chemin complet du fichier .csv
        File file = new File(completeFileName);
        return file;
    }


// placer le contenu du fichier csv dans une arrayList, chaque ligne étant déparée par espace et une virgule
        public static List<String> readFile(File file) throws IOException {

            List<String> result = new ArrayList<String>();

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                result.add(line);
            }

            br.close();
            fr.close();

            return result;
        }

//        public static String cleanKey(String key){
//            String cleanKey = key.toLowerCase();
//            return cleanKey;
//        }

}