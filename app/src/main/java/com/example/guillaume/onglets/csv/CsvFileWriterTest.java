package com.example.guillaume.onglets.csv;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class CsvFileWriterTest {
    private static final String FILE_NAME = "FichiersCsv/write csv.csv";
    private static String separator ="|";
    private static File file;
    private static CsvFileWriter csvFileWriter;




    public static List<Map<String, String>> createMap() {
        //file = CsvFileHelper.getResource(fileName);
        //csvFileWriter = new CsvFilesWriter01(file);

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        Map<String, String> oneData1 = new HashMap<String, String>();
        oneData1.put("id", "1");
        oneData1.put("prénom", "Idéfix");
        oneData1.put("couleur", "Blanc");
        oneData1.put("age", "15");
        data.add(oneData1);

        Map<String, String> oneData2 = new HashMap<String, String>();
        oneData2.put("id", "2");
        oneData2.put("prénom", "Milou de Tintin");
        oneData2.put("couleur", "Blanc");
        oneData2.put("age", "7");
        data.add(oneData2);

        return data;
    }


    public static void testWrite() {
        // Param
        final List<Map<String, String>> data = createMap();

        // Resultat attendu
        final List<String> wantedTitles = Arrays.asList("Age", "Couleur", "Prénom", "Id" );

        // Appel
        try {
            csvFileWriter.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final CsvFile csvFile = new CsvFiles1(file, separator);
        final List<String> titlesFromFile = Arrays.asList(csvFile.getTitles());

        // Test
        // pas d'ordre dans les titres
//        for (String title : titlesFromFile) {
//            assertTrue(wantedTitles.contains(title));
//        }

    }


    public void testWriteAvecOrdre() throws IOException {
        // Param
        final List<Map<String, String>> data = createMap();
        final String[] titles = { "Id", "Prénom", "Age", "Couleur" };

        // Resultat attendu
        final String[] wantedTitles = { "Id", "Prénom", "Age", "Couleur" };

        // Appel
        csvFileWriter.write(data, titles);
        final CsvFile csvFile = new CsvFiles1(file, separator);
        final String[] titlesFromFile = csvFile.getTitles();

        // Test
        // L'ordre compte
    }
}
