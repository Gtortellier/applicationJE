package com.example.guillaume.onglets.GED;

import java.io.File;

public class FileDoc {

    private File file;
    private String ref;
    private String mcl;
    private String txt;
    private String page;

    public FileDoc(File file, String ref, String mcl, String txt) {
        this.file = file;
        this.ref = ref;
        this.page = "";
        this.mcl = mcl;
        this.txt = txt;
    }

    public FileDoc(String ref, String page, String mcl, String txt) {
        this.file = null;
        this.ref = ref;
        this.page = page;
        this.mcl = mcl;
        this.txt = txt;
    }

    public File getFile() {return file;}

    public String getMcl() {
        return mcl;
    }

    public String getTxt() {
        return txt;
    }

    public String getRef() {
        return ref;
    }

    public String getPage() {
        return page;
    }
}
