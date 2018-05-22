package com.example.guillaume.onglets.csv;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CsvFilesWriter01 implements CsvFileWriter{
    private File file;
    private String separator;
    private boolean isEmpty = CsvFicheTerrainDao.isEmpty;
     // si le fichier est vide puis que j'écris dessus, il faut que static isEmpty passe à false

    public CsvFilesWriter01(File file) {
        this(file,"|");
    }

    public CsvFilesWriter01(File file, String separator){

        if (file == null) {
            throw new IllegalArgumentException("Le fichier ne peut pas être null");
        }

        this.file=file;
        this.separator = separator;
    }
    private void writeEmptyFile(){
    }

@Override
    public void write (List<Map<String,String>> mappedData) throws IOException {
        if (mappedData == null) {
            throw new IllegalArgumentException("la liste ne peut pas être nulle");
        }

        if (mappedData.isEmpty()) {
            writeEmptyFile();
        }
        final Map<String, String> oneData = mappedData.get(0);
        final String[] titles = new String[oneData.size()];

        int i = 0;
        for (String key : oneData.keySet()) {
            titles[i++] = key;
        }
        write(mappedData, titles);
    }

    public void write(List<Map<String, String>> mappedData, String[] titles) throws IOException {

        if (mappedData == null) {
            throw new IllegalArgumentException("la liste ne peut pas être nulle");
        }

        if (titles == null) {
            throw new IllegalArgumentException("les titres ne peuvent pas être nuls");
        }

        if (mappedData.isEmpty()) {
            writeEmptyFile();
        }

        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);

        // Les titres
        boolean first = true;
        if (isEmpty) {
            for (String title : titles) {
                if (first) {
                    first = false;
                } else {

                    bw.write(separator);
                }
                write(title, bw);
            }
            bw.write("\n");
            isEmpty = false;
        }

        // Les données
        for (Map<String, String> oneData : mappedData) {
            first = true;
            for (String title : titles) {
                if (first) {
                    first = false;
                } else {
                    bw.write(separator);
                }
                final String value = oneData.get(title);
                write(value, bw);

            }
            bw.write("\n");
        }
        bw.close();
        fw.close();
    }
    private void write(String value, BufferedWriter bw) throws IOException {

        if (value == null) {
            value = "";
        }
        if (value.contains("|")) {
            Log.d("pipe","pipe");
            value=value.replace("|","\\"); // si le carctère pipe
        }
        bw.write(value);
    }
}




