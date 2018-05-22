package com.example.guillaume.onglets.GED;

/**
 * Created by Guillaume on 14/01/2018.
 */

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guillaume.onglets.csv.CsvFileHelper;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static android.content.ContentValues.TAG;

public class AffichageDoc implements OnPageChangeListener,OnLoadCompleteListener{
//    public static final String SAMPLE_FILE = "DOC/billetAvion.pdf";
//    PDFView pdfView;
//     Integer pageNumber = 0;
//
//    String myJpgPath = "Doc/PasseportBis.jpg";
//    final String completeFileName = Environment.getExternalStorageDirectory().getAbsolutePath() +File.separator+ SAMPLE_FILE;
//    final String imageName = Environment.getExternalStorageDirectory().getAbsolutePath() +File.separator+ myJpgPath;
    String TxtName = "Doc1/txtDocument.txt";
//    File filePdf = new File(completeFileName);
//    File fileImage = new File (imageName);

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    Integer pageNumber = 0;
    String pdfFileName="pas de nom de pdf";
    TextView titreView;
    TextView txtView;
    PDFView pdfView;
    ImageView jpgView;
    File filePdf;
    String nomDossier="DOC";
    Activity activity;
    List<String> lines;

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
////        verifyStoragePermissions(this);
////        pdfView= (PDFView)findViewById(R.id.pdfView);
////        titrePdf = findViewById(R.id.tv_header);
////        titrePdf.setText(pdfFileName);
////        displayFromFile(filePdf);
//        Log.d("image", String.valueOf(fileImage.exists()));
//        ImageView jpgView = (ImageView)findViewById(R.id.jpgview);
//
//        //UPDATE WITH YOUR OWN JPG FILE
//        titrePdf = findViewById(R.id.tv_header);
//        titrePdf.setText(imageName);
//        Bitmap bm = BitmapFactory.decodeFile(imageName);
//        jpgView.setImageBitmap(bm);
//    }

    public AffichageDoc(Activity activity, TextView titreView,PDFView pdfView,ImageView jpgView,TextView txtView){
        this.titreView=titreView;
        this.jpgView=jpgView;
        this.pdfView=pdfView;
        this.txtView=txtView;
        this.activity=activity;
        verifyStoragePermissions(this.activity);
    }
    public void afficherDoc(FileDoc filedoc){
        String docName=filedoc.getRef();
        if (!docName.contains("."))
            Log.d("doc", "le doc ne contient pas de point, on ne peut donc pas identifier son format");
        else {
            String regex;
            regex = "\\.";
            String []oneData = docName.split(regex);
            int p =oneData.length;
            String type = oneData[p-1];
            if (type.equals("pdf"))
                afficherPdf(docName);
            else if (type.equals("JPG"))
                afficherImage(docName);
            else if (type.equals("txt"))
                afficherTxt(filedoc);
            else Log.d("doc", "le format n'est pas pris en charge");
        }
//        afficherTxt(TxtName);
    }
//    public void afficherDoc(String docName){
//        if (!docName.contains("."))
//            Log.d("doc", "le doc ne contient pas de point, on ne peut donc pas identifier son format");
//        else {
//            String regex;
//            regex = "\\.";
//            String []oneData = docName.split(regex);
//            int p =oneData.length;
//            String type = oneData[p-1];
//            if (type.equals("pdf"))
//                afficherPdf(docName);
//            else if (type.equals("JPG"))
//                afficherImage(docName);
//            else if (type.equals("txt"))
//                afficherTxt(docName);
//            else Log.d("doc", "le format n'est pas pris en charge");
//        }
////        afficherTxt(TxtName);
//    }

    private void afficherTxt(FileDoc fd) {
        String txtString="MCL : "+fd.getMcl()+"\n"+fd.getTxt();
        afficherTitre(fd.getRef());
        this.txtView.setText(txtString);
    }

    private void afficherTitre(String titre){
        //titreView.setVisibility(View.VISIBLE);
        titreView.setText(titre);
    }
    public void afficherPdf(String pdfFileName){
        //titreView.setVisibility(View.GONE);
        this.pdfFileName=pdfFileName;
        afficherTitre(pdfFileName);
        final String completeFileName = Environment.getExternalStorageDirectory().getAbsolutePath() +File.separator+nomDossier+File.separator+ pdfFileName;
        filePdf = new File(completeFileName);
        displayFromFile(filePdf);
    }
    public void afficherImage(String imageName){
        afficherTitre(imageName);
        final String completeImageName = Environment.getExternalStorageDirectory().getAbsolutePath() +File.separator+nomDossier+File.separator+ imageName;
        Bitmap bm = BitmapFactory.decodeFile(completeImageName);
        jpgView.setImageBitmap(bm); // au format JPG
    }


    private void displayFromFile(File file) {
        pdfView.fromFile(file)
//        pdfView.fromAsset(pdfFileName)
                .defaultPage(pageNumber)
                .enableSwipe(true)

                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(activity.getApplicationContext()))
                .load();
    }


    @Override    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        //activity.setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }


    @Override    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

}
