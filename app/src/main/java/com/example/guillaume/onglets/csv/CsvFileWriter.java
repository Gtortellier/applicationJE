package com.example.guillaume.onglets.csv;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CsvFileWriter {
    void write(List<Map<String, String>> mappedData) throws IOException;

    void write(List<Map <String, String>> mappedData, String[] titles) throws IOException;
}
