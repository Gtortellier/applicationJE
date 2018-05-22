package com.example.guillaume.onglets.csv;
import java.io.File;
import java.util.List;
import java.util.Map;

public interface CsvFile {

    File getFile();

    List<String[]> getData();

    String[] getTitles();

    boolean getIsEmpty();

    List<Map<String,String>> getMappedData();
}
