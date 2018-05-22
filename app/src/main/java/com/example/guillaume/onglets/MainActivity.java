package com.example.guillaume.onglets;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guillaume.onglets.GED.AffichageDoc;
import com.example.guillaume.onglets.GED.FileDoc;
import com.example.guillaume.onglets.GED.GEDclass;
import com.example.guillaume.onglets.GED.ResultSet;
import com.example.guillaume.onglets.csv.CsvFicheTerrainDao;
import com.example.guillaume.onglets.csv.CsvFile;
import com.example.guillaume.onglets.csv.CsvFileHelper;
import com.example.guillaume.onglets.csv.CsvFileWriterTest;
import com.example.guillaume.onglets.csv.CsvFiles1;
import com.example.guillaume.onglets.csv.CsvFilesWriter01;
import com.github.barteksc.pdfviewer.PDFView;
//import com.example.guillaume.onglets.csv.FicheTerrain;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.addAll;
import static org.osmdroid.views.util.constants.MapViewConstants.NOT_SET;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private TextView mTextMessage2;
    private TextView mTextMessage3;
    private TextView mTextMessage4;
    private TextView mTextMessage5;
    private Integer id = 2;
    private EditText DOC;
    private EditText ID;
    private EditText MOTDIRECTE;
    private EditText COORDX;
    private EditText COORDY;
    private EditText Adresse;
    private EditText Age;
    private ListView listDoc;
    private ListView listDocTemp=null;
    private Integer i_comportement=null;
    private Integer i_comportementRecherche=null;
    private static GeoPoint startPoint;
    private static Integer zoom;
    private String d_id = "";
    private String d_adresse = "";
    private String d_age = "";
    private String d_motdirecte = "";
    private String d_coordx = "";
    private String d_coordy = "";
    private String s_id = d_id;
    private String s_motdirecte = d_motdirecte;
    private String s_coordx = d_coordx;
    private String s_coordy = d_coordy;
    private String s_adresse = d_adresse;
    private String s_doc = "";
    private String s_age = d_age;
    private String s_date="";
    private String s_datecrea="";
    BottomNavigationView navigation;
    private String android_id;
    private Button valider = null;
    private Button doc = null;
    private CheckBox c1 = null;
    private CheckBox c2 = null;
    private CheckBox c3 = null;
    private Switch s;
    private ViewGroup rootView;
    Boolean b_c1 = false;
    Boolean b_c2 = false;
    Boolean b_c3 = false;
    private Boolean b_parcelleMultiple = false;
    String s_murs = "";
    String s_mur1 = "";
    String s_mur2 = "";
    String s_mur3 = "";
    List<String> murs = new ArrayList<>();
    String[] motsClés;
    List<String> IDs = new ArrayList<>();
    List<String> ADRESSEs = new ArrayList<>();
    List<String> X_WGS84s = new ArrayList<>();
    List<String> Y_WGS84s = new ArrayList<>();
    List<String> MOTDIRECTEs = new ArrayList<>();
    List<FileDoc> listeDocs = null;
    List <Integer> clickedItemsIndex= new ArrayList<>();
    List <String>stringFields;
    Map<String, String> contenuChamps = new LinkedHashMap<String, String>();
    List <Map<String,String>> mapsParcelle = new ArrayList<>();
    List<String>datesCrea=new ArrayList<>();
    Integer i_fiche=0;// quand il y a plusieurs fiches pour une seule parcelle, i_fiche correspond à l'indice de la fiche actuellement affichée dans l'onglet2
    Map<String,Map<String,String>> contenuCheckBox = new LinkedHashMap<String,Map<String,String>>();
    public Map<String,Map<String,String>> surnom;
    GEDclass ged;
    ResultSet resultSet=null;
    String mapSource="Vue aérienne";
    //Map<String,String> contenuRadioGroup = new LinkedHashMap<String,List<String>>();
    //ArrayList<FileDoc> listeDocs = null;
     // sélection plusieurs parcelle
    // map
    MapView map;
    // Dao
//    File[] sd = getExternalFilesDirs(null);
//    String path = getFilesDir().getAbsolutePath();
//    Context sctx=getApplicationContext();
    File file1 = CsvFileHelper.getResource("/FichiersCsv/parcelles2016v2.csv");
    CsvFile csv = new CsvFiles1(file1, ";");
    String[] titles = csv.getTitles();
    List<Map<String, String>> adresses = csv.getMappedData();
    List<String> lignesCsv = new ArrayList<>();
    Map<String,Map<String,List<String>>> fields = new LinkedHashMap<>();
    List<String>fieldsName= new ArrayList<String>();

    // a faire :
//    créer une DAO avec tous les types de données qui apparaissent dans le fichier d'émilien'
//    puis créer une DAO correspondant exactement aux fichiers d'émilien'
//    type :
//    int
//    datetime
//    string
//
//    élément de la vue:
//    texte prérempli --> TexteView
//    texte à remplir --> EditText

    final String indexPath = CsvFileHelper.getCompleteFileName("/index");
    //File file = CsvFileHelper.getResource("/FichiersCsv/Fichier csv.csv");
    File file = CsvFileHelper.getResource("/FichiersCsv/CsvLogiciel.csv");
    File fileWriter = CsvFileHelper.getResource("/FichiersCsv/CsvAJour.csv");
    File fileConfig = CsvFileHelper.getResource("/config.yaml");
    //File file = CsvFileHelper.getResource("/FichiersCsv/csv write.csv");
    //File file = CsvFileHelper.getResource("/FichiersCsv/Fichier csv quotes.csv");
    final boolean fichierExiste = file.exists();
    //CsvFiles1 csvFile= new CsvFiles1(file);
    List<String> lines = CsvFileHelper.readFile(file);
    CsvFicheTerrainDao Dao = new CsvFicheTerrainDao(file);
    CsvFicheTerrainDao DaoAJour = new CsvFicheTerrainDao(fileWriter);
    List<Map<String, String>> maps = Dao.getMaps();

    //List<FicheTerrain> fiches = Dao.findAllFiches();
    //FicheTerrain fiche;
//    FicheTerrain fiche2 = Dao.findFicheTerrainById(2);

    CsvFilesWriter01 csvFileWriter = new CsvFilesWriter01(fileWriter);
    List<Map<String, String>> data = CsvFileWriterTest.createMap();

    public MainActivity() throws IOException {
    }

    public List<Map<String, String>> FicheToDao() {
        //file = CsvFileHelper.getResource(fileName);
        //csvFileWriter = new CsvFilesWriter01(file);

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        Map<String, String> oneData1 = new LinkedHashMap<String, String>();
        Log.d("champs spéciaux",s_adresse+s_id+s_coordy+s_coordx+s_date);
        oneData1.put("REFERENCE_CADASTRALE", (s_id));
        oneData1.put("DATE_MODIF", s_date);
        oneData1.put("DATE_CREA", s_datecrea);
        oneData1.put("MOTDIRECTE", s_motdirecte);
        oneData1.put("ADRESSE", s_adresse);
        oneData1.put("COORDX", s_coordx);
        oneData1.put("COORDY", s_coordy);
        // il faut que les champs spéciaux ne fassent pas partie des fieldsname ou ils seront remplacé par la valeur de fieldsname
        for (String name : fieldsName){
            String type = fields.get(name).get("type").get(0);
            String contenu = contenuChamps.get(name);
            oneData1.put(name, contenu);
        }
        data.add(oneData1);// ce qui est écrit dans le fichier csv
        Log.d("onedata",oneData1.toString());
        // Désormais, on enregistre toutes les fiches même si il y en a déjà une qui existe avec le même REFCadastrale
//        for (int i=0;i<maps.size();i++){
//            Map m = maps.get(i);
//            if (m.containsKey("REFERENCE_CADASTRALE")&&m.get("REFERENCE_CADASTRALE")!=null&&m.get("REFERENCE_CADASTRALE").equals(s_id)) {
//                maps.set(i, oneData1);
//                return data;
//            }
//        }
        maps.add(oneData1);
        // la maps qui regroupe toutes les fiches (anciennes et celles ajoutées sur la tablette)
        //FicheTerrain fiche = Dao.mapToFiche(oneData1);
        //fiches.add(fiche);// il faut ajouter la nouvelle fiche à la Dao déjà existante au cas où on veuille accéder à nouveau à cette fiche.
        // action réalisée au moment de l'écriture du fichier dans le csv
        return data;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    affichageOnglet1();
                    return true;
                case R.id.navigation_dashboard:
                    affichageOnglet2();
                    return true;
                case R.id.navigation_notification:
                    affichageOnglet3();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ged = new GEDclass();
        startPoint = new GeoPoint (45.7748,4.8625);
        zoom=18;
        i_comportement=R.id.parcelleUnique;
        i_comportementRecherche=R.id.recherchePrecise;
        super.onCreate(savedInstanceState);
        maps.addAll(DaoAJour.getMaps());// on ajoute à maps du fichier global, toutes les maps issues du CSVAJour
        // dans maps, il y a tout ce qui a déjà été enregistré (base préexistante du fichier CsvLogiciel ou ce qui a été enregistré sur la tablette grâce au fichier CsvAJour
//        for (Map map : DaoAJour.getMaps()){
//            Boolean idFound = false;
//            for (int i=0;i<maps.size();i++){
//                Map m = maps.get(i);
//                if (m.containsKey("REFERENCE_CADASTRALE")&&m.get("REFERENCE_CADASTRALE")!=null&&map.containsKey("REFERENCE_CADASTRALE")&&map.get("REFERENCE_CADASTRALE")!=null&&m.get("REFERENCE_CADASTRALE").equals(map.get("REFERENCE_CADASTRALE"))) {
//                    maps.set(i, map);
//                    idFound=true;
//                    break;
//                }
//            }
//            if(!idFound){
//                maps.add(map);
//            }
//        }
        config conf = new config(fileConfig);
        surnom = conf.getSurnom();
        Log.d("map",conf.getFields().toString());
        Log.d("map",String.valueOf(conf.getFields().size()));
        fields=conf.getFields();
//        fields.put("test1","string");
//        fields.put("test2","string");
//        fields.put("test3","choiceboxlist");
//        fields.put("test1",new LinkedHashMap<String,List<String>>(){{put("type",Arrays.asList("string"));}});
//        fields.put("test2",new LinkedHashMap<String,List<String>>(){{put("type",Arrays.asList("string"));}});
//        fields.put("test3",new LinkedHashMap<String,List<String>>(){{put("type",Arrays.asList("choiceboxlist"));;put("data", Arrays.asList("rb1","rb2"));}});
//        fields.put("test4",new LinkedHashMap<String,List<String>>(){{put("type",Arrays.asList("checkboxlist"));;put("data", Arrays.asList("cb1","cb2"));}});
//        fields.put("test5",new LinkedHashMap<String,List<String>>(){{put("type",Arrays.asList("checkboxlist"));;put("data", Arrays.asList("cb1","cb2"));}});
//        fields.put("test6",new LinkedHashMap<String,List<String>>(){{put("type",Arrays.asList("checkboxlist"));;put("data", Arrays.asList("cb1","cb2"));}});
//        fields.put("test7",new LinkedHashMap<String,List<String>>(){{put("type",Arrays.asList("checkboxlist"));;put("data", Arrays.asList("cb1","cb2"));}});
//        fields.put("test8",new LinkedHashMap<String,List<String>>(){{put("type",Arrays.asList("checkboxlist"));;put("data", Arrays.asList("cb1","cb2"));}});
        Log.d("map",fields.toString());
        fieldsName.addAll(fields.keySet());// on récupère tous les noms des champs de la fiche terrain
        initFields();
        //affichageOnglet4();
        affichageOnglet1();
    }
    private void initFields(){
        for (String fieldName : fieldsName){
            String type = fields.get(fieldName).get("type").get(0);
            if (type.equals("choiceboxlist")){
                String defaut = fields.get(fieldName).get("data").get(0);
                contenuChamps.put(fieldName,defaut);// faire en sorte que le defaut du choicebox soit en premier lors de la lecture
            }
            else {
                contenuChamps.put(fieldName, "");
                if (type.equals("checkboxlist")||type.equals("checkbox")) {
                    List<String> checkboxNames = fields.get(fieldName).get("data");
                    Map<String, String> initCheckboxes = new LinkedHashMap<>();
                    for (String name : checkboxNames) {
                        initCheckboxes.put(name, "");
                    }
                    contenuCheckBox.put(fieldName, initCheckboxes);
                }
            }
        }
    }
    //code nécessaire à l'affichage de n'imorte quelle vue
    private void preAffichage() {
        setContentView(R.layout.activity_main);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    // intégrer toutes les modifications du layout de l'onglet 1
    private void affichageOnglet1() {
        preAffichage();
        DOC=(EditText)findViewById(R.id.et1);
        listDoc = (ListView)findViewById(R.id.listDoc);
        lignesCsv.clear();
        // affichage du contenu du fichier csv (juste les valurs de chaque map, pas l'ordre dans le quel le fichier csv est écrit)
        for (Map<String,String>m :maps){
            lignesCsv.add (String.valueOf(m.values()));
        }
        if (listDocTemp!=null){
            listDoc=listDocTemp;
        }
//        try {
//            csvFileWriter.write(data);
//        } catch (IOException e) {
//            Log.e("ecriture","écriture impossible");
//            e.printStackTrace();
//        }
//        try {
//            lines = CsvFileHelper.readFile(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        mTextMessage = (TextView) findViewById(R.id.message1);
        mTextMessage2 = (TextView) findViewById(R.id.message2);
        mTextMessage3 = (TextView) findViewById(R.id.message3);
        mTextMessage4 = (TextView) findViewById(R.id.message4);
        //mTextMessage.setText("fichier csv trouvé " + String.valueOf(fichierExiste));
        if (resultSet!=null){
            mTextMessage.setText(resultSet.toString());
        }
        mTextMessage2.setText("contenu du fichier csv" + String.valueOf(lignesCsv));
        mTextMessage3.setText("fichier vide" + lignesCsv.size());
        doc = (Button) findViewById(R.id.doc);
        //mTextMessage4.setText(Dao.);
        //mTextMessage4.setText(String.valueOf(Dao.isEmpty()));
//        mTextMessage3.setText(fiche2.getCouleur());
//        mTextMessage4.setText(fiche2.toString());
        File dir = new File(indexPath);
        Log.d("index", String.valueOf(dir.exists()));
        if (listeDocs!=null){
            ListDoc();
        }
        doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                motsClés = DOC.getText().toString().split(" ");
                if (!motsClés.equals(new String[]{})) {
                    initListDoc(motsClés);
                }
            }
        });

    }

    public void initListDoc (String [] motsClés) {
        s_doc = "";
        try {
            resultSet=ged.searchDoc(motsClés, (indexPath));
            listeDocs = resultSet.getResults();
            mTextMessage.setText(resultSet.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ListDoc();
    }
    public void ListDoc(){
        List<String> arrayDoc = new ArrayList<>();
        final List<FileDoc> listeDocsBis = new ArrayList<>();
        Log.d("docs", String.valueOf(listeDocs.size()));
        if (listeDocs != null) {
            for (FileDoc fd : listeDocs) {
                s_doc += fd.getRef() + "\n";
                arrayDoc.add(fd.getRef());
                listeDocsBis.add(fd);
            }
        }
//        String[] stringsDoc =new String [arrayDoc.size()];
//        arrayDoc.toArray(stringsDoc);
        String[] stringsDoc =new String [arrayDoc.size()];
        arrayDoc.toArray(stringsDoc);
        //stringsDoc = new String[]{"test","test2",""};
        mTextMessage = (TextView) findViewById(R.id.message1);
        //mTextMessage.setText(s_doc);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, stringsDoc);
        listDoc.setAdapter(adapter);
        listDoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Object o = listDoc.getItemAtPosition(position);
                mTextMessage.setText(o.toString());
                //affichageDoc("PasseportBis.JPG");
                //affichageDoc("billetAvion.pdf");
                //affichageDoc(o.toString());
                affichageDoc(listeDocsBis.get(position));
            }
        });
    }



    private void affichageOnglet2() {
        setContentView(R.layout.dashboard);
        ViewGroup constraint = (ViewGroup) findViewById(R.id.container);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_dashboard);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        mTextMessage = (TextView) findViewById(R.id.message2);
        mTextMessage3 = (TextView) findViewById(R.id.message3);
        mTextMessage4 = (TextView) findViewById(R.id.message4);
        mTextMessage5 = (TextView) findViewById(R.id.message5);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        // check perssion at runtime impossible pour une api 22 soit la tablette du client
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
        String date = df.format(Calendar.getInstance().getTime());

        s_date=date;
        if (s_datecrea.equals("")){
            s_datecrea=date;
        }
        mTextMessage4.setText(s_date);
        mTextMessage5.setText(s_datecrea);

        Button precedent=(Button)findViewById(R.id.précédent);
        Button suivant=(Button)findViewById(R.id.suivant);
        if (mapsParcelle.size()>1){// si il y existe plusieurs fiches dans maps pour l'adresse sur laquelle on a cliqué
            String textCurrentFiche = "Nombre de fiches trouvées pour cette parcelle : "+mapsParcelle.size() +"\n" + "Fiche actuelle : "+String.valueOf(i_fiche+1);
            mTextMessage3.setText(textCurrentFiche);
            mTextMessage3.setVisibility(View.VISIBLE);
            if (!i_fiche.equals(0)) {
                precedent.setVisibility(View.VISIBLE);
            }
            if (!i_fiche.equals(mapsParcelle.size()-1)) {
                suivant.setVisibility(View.VISIBLE);
            }
            contenuChamps=mapsParcelle.get(i_fiche);
            s_datecrea= datesCrea.get(i_fiche);
        }
        //mTextMessage.setText(s_id);
        ID = (EditText) findViewById(R.id.et1);
        MOTDIRECTE = (EditText) findViewById(R.id.et2);
        Adresse = (EditText) findViewById(R.id.et3);
        COORDX = (EditText) findViewById(R.id.et4);
        COORDY = (EditText) findViewById(R.id.et5);
        //Age=(EditText)findViewById(R.id.et4);
        ID.setText(s_id);
        MOTDIRECTE.setText(s_motdirecte);
        Adresse.setText(s_adresse);
        COORDX.setText(s_coordx);
        COORDY.setText(s_coordy);
        //Age.setText(s_age);
        ID.addTextChangedListener(textWatcher);
        MOTDIRECTE.addTextChangedListener(textWatcher);
        COORDX.addTextChangedListener(textWatcher);
        COORDY.addTextChangedListener(textWatcher);
        Adresse.addTextChangedListener(textWatcher);
        //Age.addTextChangedListener(textWatcher);
        //ID.setEnabled(false);
        c1 = (CheckBox) findViewById(R.id.checkBox);
        c1.setOnClickListener(checkListener);
        c1.setChecked(b_c1);
        //c1.setOnClickListener(onCheckboxClicked(c1));
        c2 = (CheckBox) findViewById(R.id.checkBox2);
        c2.setChecked(b_c2);
        c2.setOnClickListener(checkListener);
        c3 = (CheckBox) findViewById(R.id.checkBox3);
        c3.setChecked(b_c3);
        c3.setOnClickListener(checkListener);
        valider = (Button) findViewById(R.id.valider);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (s_id.equals("")){
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Il est impossible d'enregistrer une fiche sans reference cadastrale", Toast.LENGTH_LONG);
                    toast1.show();
                }
                else {
                    //enregistrer DAO
                    mTextMessage3.setText("bouton valider fonctionne");
                    //s_murs = s_mur1 + ";" + s_mur2 + ";" + s_mur3;// créer un array de string qu'on transforme en string au dernier moment
                    s_murs = Arrays.toString(murs.toArray());
                    List<String> checkBoxNames = new ArrayList<String>();
                    checkBoxNames.addAll(contenuCheckBox.keySet());
                    for (String checkBoxName : checkBoxNames) {
                        String contenuChamp = contenuCheckBox.get(checkBoxName).values().toString(); // on récupère
                        Log.d("contenucheckbox", contenuChamp);
                        contenuChamp = contenuChamp.replaceAll("(\\[|\\])", "");
                        contenuChamp = contenuChamp.replaceAll("((, ))", " ; ");
                        //contenuChamp=contenuChamp.replaceAll("(^;|;$)","");
                        Log.d("contenucheckbox", contenuChamp);
                        contenuChamps.put(checkBoxName, contenuChamp);
                    }
                    try {
                        csvFileWriter.write(FicheToDao());
                        // revenir sur la map
                    } catch (IOException e) {
                        Log.e("ecriture", "écriture impossible");// il faut initialiser les strings utilisés
                        e.printStackTrace();
                    }
                    //fiche = fiches.get(fiches.size()-1);:
                    //mTextMessage4.setText(fiche.toString());
                    i_comportement = R.id.parcelleUnique; // on ne remet pas à jour le type de recherche
                    defaultEditText();
                    defautFiche();
                    defautCheckBox(); // a voir avec les checkbox
                    IDs.clear(); // on supprime les id de parcelles qui étaient enregistrées quand plusieurs parcelles on étaient séléctionnées sur la carte (multi parcelles)
                    ADRESSEs.clear();
                    X_WGS84s.clear();
                    Y_WGS84s.clear();
                    MOTDIRECTEs.clear();
                    clickedItemsIndex.clear(); // cf IDs sauf que maintenant on supprime les indexs dans l'Overlay;
                    murs.clear();
                    initFields();
                    affichageOnglet3();
                }
            }
        });
        List<Integer> placesbis = Arrays.asList(R.id.layoutParameters,R.id.layoutParameters3,R.id.layoutParameters4,R.id.layoutParameters5,R.id.layoutParameters6,R.id.layoutParameters7,R.id.layoutParameters8,R.id.layoutParameters9);// list des ids des editsTexts sur lesquels se baser pour positionner les nouveaux editTexts définis par le client
        List<Integer> places = Arrays.asList(R.id.layoutParameters,R.id.layoutParameters3,R.id.layoutParameters4,R.id.layoutParameters5,R.id.layoutParameters6,R.id.layoutParameters7,R.id.layoutParameters8,R.id.layoutParameters9,R.id.layoutParameters10,R.id.layoutParameters11,R.id.layoutParameters12,R.id.layoutParameters13,R.id.layoutParameters14,R.id.layoutParameters15,R.id.layoutParameters16,R.id.layoutParameters17,R.id.layoutParameters18,R.id.layoutParameters19,R.id.layoutParameters20,R.id.layoutParameters21,R.id.layoutParameters22,R.id.layoutParameters23,R.id.layoutParameters24,R.id.layoutParameters25,R.id.layoutParameters26,R.id.layoutParameters27,R.id.layoutParameters28,R.id.layoutParameters29,R.id.layoutParameters30,R.id.layoutParameters31,R.id.layoutParameters32,R.id.layoutParameters33,R.id.layoutParameters34);
// il y a 28 champs rempli par le fichier de config de base
        for (int i = 0;i<fieldsName.size();i++) {
            final String name = fieldsName.get(i);
            Log.d("nom",name);
            String type=fields.get(name).get("type").get(0);
            View answerInputField = null;
            Map <String,String>libelleMap;
            List<String> libelleList;
                if (type.equals("textfield")){
                    answerInputField = new EditText(this);
                //answerInputField.setId(R.id.layoutParameters1);
                final EditText et = (EditText) answerInputField;
                et.setHint(name);
                et.setText(contenuChamps.get(name));
                et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                if (fields.get(name).containsKey("largeur")){
                    Log.d("largeur",fields.get(name).get("largeur").get(0));
                    et.setMaxWidth(Integer.parseInt(fields.get(name).get("largeur").get(0)));
                }
                else{
                    et.setMaxWidth(525);
                }
                et.setMaxHeight(50);
                et.setMinLines(3);
                et.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        contenuChamps.put(name, et.getText().toString());
                    }
                });
            }
                else if (type.equals("choicebox")) {
                    answerInputField = new RadioGroup(this);
                    //answerInputField.setId(R.id.layoutParameters1);
                    final RadioGroup rg = (RadioGroup) answerInputField;
                    rg.setOrientation(LinearLayout.HORIZONTAL);
                    TextView rgtitle = new TextView((this));
                    rgtitle.setText(name);
                    rg.addView(rgtitle);
                    final Context ctx = this;
                    List<String> datas = fields.get(name).get("data");
                    String checked = contenuChamps.get(name);
                    libelleMap = surnom.get(name);
                    libelleList = new ArrayList<String>();
                    libelleList.addAll(libelleMap.keySet());
                    for (int k = 0; k < datas.size(); k++) {
                        String data = datas.get(k);
                        RadioButton rb = new RadioButton(this);
                        if (libelleList.contains(data)) {
                            rb.setText(libelleMap.get(data));
                            Log.d("libelle", libelleMap.get(data));
                        } else {
                            rb.setText(data);
                        }
                        rg.addView(rb);
//                        if (k == 0) {
//                            rb.setChecked(true);
//                            contenuChamps.put(name, data);
//                        }
                        if (checked.equals(data)) {
                            rb.setChecked(true);
                        }
                    }

//                    for (int k = 0; k < 2; k++) {
//                        RadioButton rb = new RadioButton(this);
//                        final String text = Integer.toString(k);
//                        rb.setText(text);
//                        //rb.setId(k);
//                        rg.addView(rb);
//                    }
                    final String[] toast = {"fail"};
                    rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            // checkedId is the RadioButton selected
                            RadioButton rb = (RadioButton) findViewById(checkedId);
                            String text = String.valueOf(rb.getText());
                            contenuChamps.put(name, text);
//                            Toast toast1 = Toast.makeText(ctx, text, Toast.LENGTH_LONG);
//                            toast1.show();
                        }
                    });
                }
                else if (type.equals("text")){
                    answerInputField = new TextView(this);
                    final TextView text = (TextView) answerInputField;
                    text.setGravity(Gravity.BOTTOM);
                    if (fields.get(name).containsKey("largeur")){
                        text.setMaxWidth(Integer.getInteger(fields.get(name).get("largeur").get(0)));
                    }
                    else{
                        text.setMaxWidth(525);
                    }
                    text.setMaxHeight(50);
                    text.setMinLines(3);
                    text.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                    if (contenuChamps.get(name).equals("")) {
                        text.setText(name);
                    }
                    else {
                        text.setText(contenuChamps.get(name));
                    }
                }
                else if (type.equals("checkbox")||type.equals("checkboxlist")) {
                    answerInputField = new LinearLayout(this);
                    final ViewGroup cbgroup = (ViewGroup) answerInputField;
                    //answerInputField.setId(R.id.layoutParameters1);
                    //cb.setOrientation(LinearLayout.HORIZONTAL);
                    final TextView cbtitle = new TextView((this));
                    cbtitle.setText(name);
                    cbgroup.addView(cbtitle);
                    final Context ctx2 = this;
                    List<String> datalist = fields.get(name).get("data");
                    libelleMap = surnom.get(name);
                    libelleList = new ArrayList<String>();
                    libelleList.addAll(libelleMap.keySet());
                    for (final String data : datalist) {
                        final CheckBox cb = new CheckBox(this);
                        if (libelleList.contains(data)) {
                            Log.d("libelle", libelleMap.get(data));
                            cb.setHint(libelleMap.get(data));
                        } else {
                            cb.setHint(data);
                        }
                        Map<String, String> checkboxes = contenuCheckBox.get(name);
                        if (checkboxes.get(data).equals(data)) {
                            cb.setChecked(true);
                        }
                        cb.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //String text = String.valueOf(cb.getText());
                                if (cb.isChecked()) {
                                    Map<String, String> checkboxes = contenuCheckBox.get(name);
                                    checkboxes.put(data, data);
//                                    Toast toast1 = Toast.makeText(ctx2, contenuCheckBox.get(name).values().toString(), Toast.LENGTH_LONG);
//                                    toast1.show();
                                } else {
                                    Map<String, String> checkboxes = contenuCheckBox.get(name);
                                    checkboxes.put(data, "");
//                                    Toast toast1 = Toast.makeText(ctx2, contenuCheckBox.get(name).values().toString(), Toast.LENGTH_LONG);
//                                    toast1.show();
                                }
                            }
                        });
                        cbgroup.addView(cb);
                    }
                }
//                    rg.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            //RadioButton rb = (RadioButton)findViewById(rg.getCheckedRadioButtonId());
//                            RadioButton rb
//                            boolean checked = ((RadioButton) v).isChecked();
//                            if (checked) {
//                                //b_c1 = true;
//                                //s_mur1 = getString(R.string.mur1);
//                                toast[0] ="bouton "+text+" activé ";
//                            }
//                            // Put some meat on the sandwich
//                            else {
////                                        b_c1 = false;
////                                        s_mur1 = "";
//                                toast[0] ="bouton "+text+" désactivé ";
//                            }
//                            Toast toast1 = Toast.makeText(ctx, toast[0], Toast.LENGTH_LONG);
//                            toast1.show();
//                        }
//                    });
//                case "checkboxlist":
//                    // les checkbox ne sont pas liées par le code
//                    answerInputField = new RadioGroup(this);
//                    //answerInputField.setId(R.id.layoutParameters1);
//                    final RadioGroup rl = (RadioGroup) answerInputField;
//                    for (int k = 0; k < 2; k++) {
//                        RadioButton rb = new RadioButton(this);
//                        final Context ctx= this;
//                        final String text = Integer.toString(k);
//                        rb.setText(text);
//                        rl.addView(rb);
//                        final String[] toast = {"fail"};
//                        rl.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                boolean checked = ((CheckBox) v).isChecked();
//                                if (checked) {
//                                    //b_c1 = true;
//                                    //s_mur1 = getString(R.string.mur1);
//                                    toast[0] ="bouton "+text+" activé ";
//                                }
//                                // Put some meat on the sandwich
//                                else {
////                                        b_c1 = false;
////                                        s_mur1 = "";
//                                    toast[0] ="bouton "+text+" désactivé ";
//                                }
//                                Toast toast1 = Toast.makeText(ctx, toast[0], Toast.LENGTH_LONG);
//                                toast1.show();
//                            }
//                        });
//                    }
            if (!type.equals("vide")) { // si type==vide, il ne faut juste rien faire pour ce champ, ainsi le layout parameters saute
                EditText layoutParameters = (EditText) findViewById(places.get(i));
                ConstraintLayout.LayoutParams params =
                        (ConstraintLayout.LayoutParams) layoutParameters.getLayoutParams(); // on copie les paramètres de position de
                layoutParameters.setVisibility(View.INVISIBLE);
                // rendre la content view focusable fait disparaitre les views crées par java
                ConstraintLayout.LayoutParams newParams = new ConstraintLayout.LayoutParams(params); // on place la view qu'on vient de créer au bon endroit
                answerInputField.setLayoutParams(newParams);
                constraint.addView(answerInputField);
            }
        }
    }

//    private void affichageOnglet4(){
//        // test de créer des des champs via java dans un constraintView
//        setContentView(R.layout.javaview);
//        ViewGroup constraint = (ViewGroup) findViewById(R.id.javaview);
//        List<Integer> places = Arrays.asList(R.id.layoutParameters,R.id.layoutParameters3);// list des ids des editsTexts sur lesquels se baser pour positionner les nouveaux editTexts définis par le client
//        for (int i = 0;i<fieldsName.size();i++) {
//            String name = fieldsName.get(i);
//            Map<String,List<String>>typeMap=fields.get(name);
//            String type =
//            View answerInputField = null;
//            switch(type) {
//                case "string":
//                    answerInputField = new EditText(this);
//                    //answerInputField.setId(R.id.layoutParameters1);
//                    ((EditText)answerInputField).setHint(name);
//            }
//            EditText layoutParameters = (EditText) findViewById(places.get(i));
//            ConstraintLayout.LayoutParams params =
//                    (ConstraintLayout.LayoutParams) layoutParameters.getLayoutParams(); // on copie les paramètres de position de
//            layoutParameters.setVisibility(View.INVISIBLE);
//             // rendre la content view focusable fait disparaitre les views crées par java
//            ConstraintLayout.LayoutParams newParams = new ConstraintLayout.LayoutParams(params); // on place la view qu'on vient de créer au bon endroit
//            answerInputField.setLayoutParams(newParams);
//            constraint.addView(answerInputField);
//        }
//    }

    public static void updateStartPoint(GeoPoint center){
        startPoint=center;
    }
    public static void updateZoom(Integer actualZoom){
        zoom=actualZoom;
    }
    private void affichageOnglet3() {
        setSource(mapSource);
        setContentView(R.layout.map);
        String rasterDir = "osmdroid ter";
        String aerialDir = "osmdroid";
        final Drawable markerb = getResources().getDrawable(android.R.drawable.ic_menu_compass);//ic_menu_mylocation
        final Button vm=findViewById(R.id.validerMulti);
        RadioGroup comportement = findViewById(R.id.comportementClic);
        comportement.check(i_comportement);
        if(i_comportement==R.id.parcelleMultiple){
            vm.setVisibility(View.VISIBLE);
        }
        comportement.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb = (RadioButton) findViewById(checkedId);
                i_comportement = checkedId;
                if (checkedId!=R.id.parcelleMultiple){
                    vm.setVisibility(View.INVISIBLE);
                    IDs.clear();
                    ADRESSEs.clear();
                    X_WGS84s.clear();
                    Y_WGS84s.clear();
                    MOTDIRECTEs.clear();
                    for (Integer index : clickedItemsIndex){// si ça marche pas, essayer
                        Log.d("tag",String.valueOf(map.getOverlays().get(0)));
                        Log.d("tag",String.valueOf(map.getOverlays().get(1)));
                        ItemizedOverlayWithFocus<OverlayItem> newOverlay = ((ItemizedOverlayWithFocus<OverlayItem>)map.getOverlays().get(1));
                        newOverlay.getItem(index).setMarker(markerb);
                        map.getOverlays().set(1,newOverlay);
                        map.invalidate();
                        //ItemizedOverlayWithFocus<OverlayItem>
                        //getItem(index).setMarker(marker);
                    }
                    clickedItemsIndex.clear();
                }
                else{
                    vm.setVisibility(View.VISIBLE);
                }
            }
        });
        final RadioGroup recherche = findViewById(R.id.comportementRecherche);
        recherche.check(i_comportementRecherche);
        recherche.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb = (RadioButton) findViewById(checkedId);
                i_comportementRecherche = checkedId;
            }
        });
        s = (Switch)findViewById(R.id.switch1);
        s.setChecked(mapSource.equals("Vue ancienne"));
        s.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     if (s.isChecked()) {
                                         mapSource="Vue ancienne";
                                         affichageOnglet3();
                                     } else {
                                         mapSource="Vue aérienne";
                                         affichageOnglet3();
                                     }
                                 }
                             });
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_notification);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        map = (MapView) findViewById(R.id.map);
        //map.setTileSource(TileSourceFactory.MAPNIK);
        map.setTileSource(new XYTileSource("1845_5175_8cm_CC46", //4uMaps
                0, 20, 256, ".png", new String[]{
                "http://otile1.mqcdn.com/tiles/1.0.0/map/",}));
        map.setUseDataConnection(false);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        final Drawable marker = getResources().getDrawable(android.R.drawable.star_big_on);
        int markerWidth = marker.getIntrinsicWidth();
        int markerHeight = marker.getIntrinsicHeight();
        marker.setBounds(0, markerHeight, markerWidth, 0);

        MyItemizedOverlay myItemizedOverlay
                = new MyItemizedOverlay(marker, MainActivity.this, map, adresses);
        map.getOverlays().add(myItemizedOverlay);
        IMapController mapController = map.getController();
        mapController.setZoom(zoom); //9
//        GeoPoint startPoint = new GeoPoint (45.7748,4.8625);
        // (37.7739,-122.4312); san francisco
        //(45.7667,4.8833); //lyon(latitude, longitude)
        //(45.76667,4.88333) Villeurbanne
        // Dalle 1840_5175_8cm_CC46.ecw (45.78082,4.82986)
        //4.79986-4.86562,45.76082-45.80477
        // Dalle 1845_5175_8cm_CC46.ecw (45.77,4.88)
        //4.8641-4.9299,45.7598-45.8037
        mapController.setCenter(startPoint);

        // kml
//        KmlDocument kmlDocument = new KmlDocument();
//        File file1 = kmlDocument.getDefaultPathForAndroid("parcelles qgis.kml");
//        kmlDocument.parseKMLFile(file1);
//        FolderOverlay kmlOverlay = (FolderOverlay) kmlDocument.mKmlRoot.buildOverlay(map, null, null, kmlDocument);
//        // essayer de remplacer le folder overlay par un itemized overlay pour récupérer les polygones correspondants au contours des parcelles avec un onsingletap
//        // sinon : impossible de récupérer les plygone car seul l'itemized overlay régit au clic, pas le folderoverlay.
//        map.getOverlays().add(kmlOverlay);
//        map.invalidate();
//        BoundingBox bb = kmlDocument.mKmlRoot.getBoundingBox();
//        //map.getController().setCenter(bb.getCenter());
        //itemized overlay test

        //Markers
        MarkersAdresses markersAdresses = new MarkersAdresses(adresses);
        final ArrayList<OverlayItem> items = markersAdresses.getItems();
        for (OverlayItem item : items){
            item.setMarkerHotspot(OverlayItem.HotspotPlace.CENTER);
        }

        final int markerbWidth = markerb.getIntrinsicWidth() / 2;//96
        final int markerbHeight = markerb.getIntrinsicHeight() / 2;//96
        markerb.setBounds(markerbWidth/2, markerbHeight/2, markerbWidth/2, markerbHeight/2);
        final Drawable markerc = getResources().getDrawable(android.R.drawable.ic_menu_mylocation);//ic_menu_mylocation
        markerc.setBounds(markerbWidth/2, markerbHeight/2, markerbWidth/2, markerbHeight/2);
        markerc.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY );
        final ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(items, markerb, null, NOT_SET,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        //do something
//                        defaultEditText();
//                        defautCheckBox();
                        Map<String, String> adresse = ((MyOverlayItem) item).getAdresse();
                        mapsParcelle.clear();
                        datesCrea.clear();//on remet à jour les fiches la liste de fiche préexistante pour l'adresse sur laquelle on a cliqué
                        i_fiche=0; // On veut d'abord afficher la première fiche lorsqu'on va sur l'onglet2
                        if (i_comportementRecherche.equals(R.id.rechercheElargie)){
                            recherche(adresse);
                            affichageOnglet1();
                        }
                        else if (i_comportement.equals(R.id.parcelleUnique)){
                            defautFiche();
                            for (Map m : maps) {
                                Log.d("refcadatrale",m.toString());
                                if (((String)m.get("REFERENCE_CADASTRALE")).contains(adresse.get("IDENT"))) { // si on clique sur une fiche qui existe déjà dans la liste de maps
//                                    s_adresse = (String) m.get("ADRESSE");
//                                    s_motdirecte = (String) m.get("MOTDIRECTE");
//                                    s_id = (String) m.get("REFERENCE_CADASTRALE");
//                                    s_coordx = (String) m.get("COORDX");
//                                    s_coordy = (String) m.get("COORDY");
                                    s_datecrea= (String) m.get("DATE_CREA");
                                    mapsParcelle.add(m);
                                    contenuChamps = m;
                                    datesCrea.add(s_datecrea);
                                    List<String> nameCheckboxes = new ArrayList<String>();
                                    nameCheckboxes.addAll(contenuCheckBox.keySet());
                                    for (String ncb :nameCheckboxes){
                                        String contenumap =(String)m.get(ncb);
                                        Map<String,String> contenucb=new LinkedHashMap<>();
                                        contenucb=contenuCheckBox.get(ncb);
                                        List<String> nameCheckboxesChoix = new ArrayList<String>();
                                        nameCheckboxesChoix.addAll(contenucb.keySet());
                                        for (String nameChoix : nameCheckboxesChoix) {
                                            if (contenumap.contains(nameChoix)) {
                                                contenucb.put(nameChoix,nameChoix);
                                            }
                                        }
                                        contenuCheckBox.put(ncb,contenucb);
                                    }
//                                    break; // il n'est censé exister qu'une seule ligne pour un seul id
                                }
                            }
                            s_id = adresse.get("IDENT");
                            s_adresse=adresse.get("ADRESSE");
                            s_motdirecte=adresse.get("MOTDIRECTE");
                            s_coordx=adresse.get("X_WGS84");
                            s_coordy=adresse.get("Y_WGS84");
                            Log.d("infoparcelle",s_id+s_adresse+s_motdirecte+s_coordx+s_coordy);
                            recherche(adresse);
                            if(listeDocs.isEmpty()){
                                affichageOnglet2();
                            }
                            else{
                                affichageOnglet1();
                            }
                        }
                        else if (i_comportement.equals(R.id.parcelleMultiple)){
                            for (Map m : maps) {
                                if (((String)m.get("REFERENCE_CADASTRALE")).contains(adresse.get("IDENT"))) { // si on clique sur une fiche qui existe déjà dans la liste de maps
//                                    Toast idExistant = Toast.makeText(getApplicationContext(), "La parcelle "+m.get("REFERENCE_CADASTRALE")+" a déjà été enregistrée", Toast.LENGTH_LONG);
//                                    idExistant.show();
//                                    return true; // il n'est censé exister qu'une seule ligne pour un seul id
                                    mapsParcelle.add(m);
                                }
                            }
                            if (clickedItemsIndex.contains(index)){
                                item.setMarker(markerb);
                                IDs.remove(adresse.get("IDENT"));
                                X_WGS84s.remove(adresse.get("X_WGS84"));
                                Y_WGS84s.remove(adresse.get("Y_WGS84"));
                                MOTDIRECTEs.remove(adresse.get("MOTDIRECTE"));
                                ADRESSEs.remove(adresse.get("ADRESSE"));
                                clickedItemsIndex.remove((Object) index);
                            }
                            else {
                                IDs.add(adresse.get("IDENT"));
                                X_WGS84s.add(adresse.get("X_WGS84"));
                                Y_WGS84s.add(adresse.get("Y_WGS84"));
                                MOTDIRECTEs.add(adresse.get("MOTDIRECTE"));
                                ADRESSEs.add(adresse.get("ADRESSE"));
                                clickedItemsIndex.add(index);
                                Log.d("mul", Arrays.toString(IDs.toArray()));
                                item.setMarker(markerc);
                            }
                            map.invalidate();
                        }
                        else{
                            Log.d("comportement clic",i_comportement.toString());
                        }
                        return true;
                    }
                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return false;
                    }
                }, getApplicationContext());
        // for item in mOverlay : item.setmarkerhotspot()HotspotPlace.CENTER°.
        for (Integer index : clickedItemsIndex){// si ça marche pas, essayer
            mOverlay.getItem(index).setMarker(markerc);
        }
        map.getOverlays().add(mOverlay);
        vm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String ids = Arrays.toString(IDs.toArray());
                ids=ids.replaceAll("(\\[|\\])",""); // on enlève les virgules suivies d'espaces
                s_id=ids.replaceAll(", "," ; ");
                String adresses = Arrays.toString(ADRESSEs.toArray());
                adresses=adresses.replaceAll("(\\[|\\])",""); // on enlève les virgules suivies d'espaces
                s_adresse=adresses.replaceAll(", "," ; ");
                String xs = Arrays.toString(X_WGS84s.toArray());
                xs=xs.replaceAll("(\\[|\\])",""); // on enlève les virgules suivies d'espaces
                s_coordx=xs.replaceAll(", "," ; ");
                String ys = Arrays.toString(Y_WGS84s.toArray());
                ys=ys.replaceAll("(\\[|\\])",""); // on enlève les virgules suivies d'espaces
                s_coordy=ys.replaceAll(", "," ; ");
                String motsd = Arrays.toString(MOTDIRECTEs.toArray());
                motsd=motsd.replaceAll("(\\[|\\])",""); // on enlève les virgules suivies d'espaces
                s_motdirecte=motsd.replaceAll(", "," ; ");
                Log.d("mul",s_id);
                affichageOnglet2();
            }
        });
    }

    private void affichageDoc(FileDoc docName) {
        setContentView(R.layout.affichage_doc);
        TextView titre = (TextView)findViewById(R.id.titre);
        ImageView jpgView = (ImageView)findViewById(R.id.jpgView);
        PDFView pdfView = (PDFView)findViewById(R.id.pdfView);
        //titre.setText(docName);
        TextView txtView = findViewById(R.id.txtView);
        txtView.setMovementMethod(new ScrollingMovementMethod());
        AffichageDoc afficheur = new AffichageDoc(this,titre,pdfView,jpgView,txtView);
        afficheur.afficherDoc(docName);
    }

    // gérer les textes watcher
    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s == ID.getEditableText()) {
                s_id = (ID.getText().toString());
                // mTextMessage.setText(s_id);
            } else if (s == MOTDIRECTE.getEditableText()) {
                s_motdirecte = MOTDIRECTE.getText().toString();
                // mTextMessage.setText(s_motdirecte);
            } else if (s == Adresse.getEditableText()) {
                s_adresse = Adresse.getText().toString();
                //mTextMessage.setText(s_adresse);
                //   mTextMessage.setText(s_age);
            } else if (s == COORDX.getEditableText()) {
                s_coordx = (COORDX.getText().toString());
                //   mTextMessage.setText(s_age);
            } else if (s == COORDY.getEditableText()) {
                s_coordy = (COORDY.getText().toString());
                //   mTextMessage.setText(s_age);
            }
        }
    };

//    private TextWatcher textWatcherDoc = new TextWatcher() {
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count,
//                                      int after) {
//
//        }

//        @Override
//        public void afterTextChanged(Editable s) {
//            String[] motsClés = {DOC.getText().toString()};
//            String res = "";
//            try {
//                listeDocs = ged.searchDoc(motsClés,(CsvFileHelper.getResourcePath("index")));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println(listeDocs.size());
//            for (FileDoc fd : listeDocs){
//                res+=fd.toString;
//            }
//
//            if (s==ID.getEditableText()){
//                s_id = (ID.getText().toString());
//                // mTextMessage.setText(s_id);
//            }
//            else if (s==MOTDIRECTE.getEditableText()){
//                s_motdirecte = MOTDIRECTE.getText().toString();
//                // mTextMessage.setText(s_motdirecte);
//            }
//            else if (s==Adresse.getEditableText()){
//                s_adresse = Adresse.getText().toString();
//                //mTextMessage.setText(s_adresse);
//            }
//            else if (s==Age.getEditableText()){
//                s_age = (Age.getText().toString());
//                //   mTextMessage.setText(s_age);
//            }
//        }
//    };
    private void recherche(Map<String, String> adresse){
        if(i_comportementRecherche.equals(R.id.recherchePrecise)) {
            try {
                resultSet = ged.getPlotDoc(adresse.get("ADRESSE"), indexPath);
                listeDocs = resultSet.getResults();
                Log.d("recherchePrecise", listeDocs.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                resultSet = ged.getPlotDocLarge(adresse.get("ADRESSE"), indexPath);
                listeDocs = resultSet.getResults();
                Log.d("rechercheElargie", listeDocs.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private View.OnClickListener checkListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String check = "";
            int nb = 0;
            boolean checked = ((CheckBox) v).isChecked();
            int c_id = v.getId();
            // Check which checkbox was clicked
            if (c_id == R.id.checkBox) {
                if (checked) {
                    check = "checkbox1";
                    b_c1 = true;
                    s_mur1 = getString(R.string.mur1);
                }
                // Put some meat on the sandwich
                else {
                    b_c1 = false;
                    s_mur1 = "";
                }
            }
            // Remove the meat
            if (c_id == R.id.checkBox2) {
                if (checked) {
                    check = "checkbox2";
                    b_c2 = true;
                    s_mur2 = getString(R.string.mur2);
                    // Cheese me
                }
                // I'm lactose intolerant

                else {
                    b_c2 = false;
                    s_mur2 = "";
                }
            }
            if (c_id == R.id.checkBox3) {
                if (checked) {
                    check = "checkbox3";
                    b_c3 = true;
                    s_mur3 = getString(R.string.mur3);
                } else {
                    b_c3 = false;
                    s_mur3 = "";
                }
            }
            // créer une liste qui enregistre tous les noms checkbox validés (les enlever de la liste quand on décoche le checkbox)
            // à l'ouverture de l'onglet, récupérer tous les checkbox et si un nom de checkbox est dans la liste, le mettre à true
            //
            //mTextMessage4.setText(check);
        }


    };

    public void defautCheckBox() {
        b_c3 = false;
        b_c1 = false;
        b_c2 = false;
        s_murs = "";
        s_mur1 = "";
        s_mur2 = "";
        s_mur3 = "";
    }

//    public void onCheckboxClicked(View view) {
//        // Is the view now checked?
//        String check = "";
//        boolean checked = ((CheckBox) view).isChecked();
//
//        // Check which checkbox was clicked
//        switch(view.getId()) {
//            case R.id.checkBox:
//                if (checked)
//                    check= "checkbox1";
//                // Put some meat on the sandwich
//            else
//                // Remove the meat
//                break;
//            case R.id.checkBox2:
//                if (checked)
//                    check= "checkbox2";
//                // Cheese me
//            else
//                // I'm lactose intolerant
//                break;
//            case R.id.checkBox3:
//                if (checked)
//                    check= "checkbox3";
//        }
//        mTextMessage4.setText(check);
//    }

    public void defaultEditText() {
        ID.setHint(R.string.reference_cadastrale);
        MOTDIRECTE.setHint(R.string.motdirecte);
        Adresse.setHint(R.string.adresse);
        COORDX.setHint(R.string.coordX);
        COORDY.setHint(R.string.coordY);
        //Age.setHint(R.string.age);
        // pas besoin de modifier les EditTexts de fields puisqu'ils sont recréés à chaque affichage onglet 2 avec les valeurs de contenuChamps
    }

    public void defautFiche() {
        s_id = d_id;
        s_adresse = d_adresse;
        s_motdirecte= d_motdirecte;
        s_coordx=d_coordx;
        s_coordy=d_coordy;
        s_datecrea="";
        for (String name : fieldsName){
            contenuChamps.put(name,""); // on réinitialise les valeurs de contenuChamps
        }
        //s_age=d_age;
        //s_motdirecte=d_motdirecte;
    }
    public void closeDoc(View view) {
        affichageOnglet1();
        ListDoc();
    }
    public void ficheSuivante(View view) {
        if (!i_fiche.equals(mapsParcelle.size()-1)) {
            i_fiche++;
        }
        else{
            Toast toast1 = Toast.makeText(getApplicationContext(), "Vous avez atteint la dernière fiche préexistante", Toast.LENGTH_LONG);
            toast1.show();
        }
        affichageOnglet2();
    }
    public void fichePrecedente(View view) {
        if (!i_fiche.equals(0)) {
            i_fiche--;
        }
        else{
            Toast toast1 = Toast.makeText(getApplicationContext(), "Vous avez atteint la première fiche préexistante", Toast.LENGTH_LONG);
            toast1.show();
        }
        affichageOnglet2();
    }
    public void setSource(String dir) {
        // on considère que le dossier est situé à la racine de la tablette, au même niveau que osmdroid
//        File path = new File(Environment.getExternalStorageDirectory(), File.separator + dir + File.separator);
//        Configuration.getInstance().setOsmdroidBasePath(path.getAbsoluteFile());
//        Log.d("tag", path.getAbsoluteFile().toString());
//        affichageOnglet3();
        //setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_main);
        String removableStoragePath="faux";
        File fileList[] = new File("/storage/").listFiles();
        for (File file : fileList) {
            if (!file.getAbsolutePath().equalsIgnoreCase(Environment.getExternalStorageDirectory().getAbsolutePath()) && file.isDirectory() && file.canRead())
                removableStoragePath = file.getAbsolutePath();
            break;
        }
        File[] sd = this.getExternalFilesDirs(null);
        removableStoragePath=sd[1].getAbsolutePath().replaceAll("(\\/Android(.)*)","");
        Log.d("tag", removableStoragePath);
        File path = new File(removableStoragePath, File.separator + dir + File.separator);
        Configuration.getInstance().setOsmdroidBasePath(path.getAbsoluteFile());
    }
}
