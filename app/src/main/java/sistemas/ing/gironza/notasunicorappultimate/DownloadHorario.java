package sistemas.ing.gironza.notasunicorappultimate;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.StringTokenizer;

import BasedeDatos.SQLite_OpenHelper;

import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperJueves;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperLunes;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperMartes;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperMiercoles;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperSabado;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperViernes;

public class DownloadHorario extends AppCompatActivity {

    Handler handlerForJavascriptInterface = new Handler();
    private String TextoHorario = "1";
    WebView webview;
    ClipData clip;
    String TextoSnack="Esperando...";
    ClipboardManager clipboard;
    LinearLayout telon;
    ListaEnlazadaMat listaGuardar=new ListaEnlazadaMat();
    ProgressBar loadingProgressBar,loadingTitle;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1 )
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_horario);

        webview = findViewById(R.id.browser);
        loadingProgressBar=findViewById(R.id.progressbar_Horizontal);
        telon=findViewById(R.id.telon);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        getSupportActionBar().setTitle("Sincronización con Pow.Campus");
        getSupportActionBar().setSubtitle("Desde powercampus.unicordoba.edu.co");
        WebSettings settings=webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        webview.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

                verificarInicioDeSesion();
                webview.loadUrl("javascript:window.HtmlViewer.showHTML" +
                        "('<html>'+document.getElementById('VisibleReportContentRvReportes_ctl10').innerText+'</html>');");
            }
        });

        //webview.loadUrl("http://powercampus.unicordoba.edu.co/UniversidadDeCordoba/Administracion/Parametrizacion/Reportes/ReportesRegistroAcademico.aspx?path=Horario%2fHorarioEstudiantePeriodoActual&prPersonId=61528");
        webview.loadUrl("http://powercampus.unicordoba.edu.co/Reportes/RegistroAcademico/HorarioEstudiante/HomeHorarioEstudiante.aspx");
        verSiCargo(0);
        verlinkParaHorario(0);

        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                loadingProgressBar.setProgress(newProgress);
                //loadingTitle.setProgress(newProgress);
                // hide the progress bar if the loading is complete
                if (newProgress == 100) {
                    loadingProgressBar.setVisibility(View.GONE);

                } else{
                    loadingProgressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    int c=0;

    private void verlinkParaHorario(int i) {
        c = i;
        if (c == 0) {

            webview.loadUrl("javascript:window.HtmlViewer.showHTML" +
                    "(document.getElementById('ctl00_mainContentZone_txlinkHorarioPeriodoActual').href);");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if(!TextoHorario.equals("1")) {
                        telon.setVisibility(View.VISIBLE);
                        webview.loadUrl(TextoHorario);
                        Toast t=Toast.makeText(getApplicationContext(), "Sincronizando horario, espere por favor. Att: Notas UnicorApp ;)", Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER,0,0);
                        t.show();
                        Toast t2=Toast.makeText(getApplicationContext(), "Sincronizando horario, espere por favor. Att: Notas UnicorApp ;)", Toast.LENGTH_LONG);
                        t2.setGravity(Gravity.CENTER,0,0);
                        t2.show();
                        c++;
                        TextoSnack="Cargando...";
                    } else {
                        verlinkParaHorario(c);
                    }

                }
            }, 1000);
        }
    }

    private boolean verificarInicioDeSesion() {

        if(webview != null) {
            if (webview.getUrl().equals(("http://powercampus.unicordoba.edu.co/Login.aspx?ReturnUrl=%2fReportes%2fRegistroAcademico%2fHorarioEstudiante%2fHomeHorarioEstudiante.aspx").trim())) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(webview.getContext());
                alertDialog.setTitle("Primero debe iniciar sesión");
                alertDialog.setMessage("Inicie sesion para descargar su horario");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }

                });
                alertDialog.show();
                return false;
            } else {
                return true;
            }
        }else{
            verificarInicioDeSesion();
            return false;
        }
    }

    int a;

    private void verSiCargo(int i) {
        a = i;
        if (a == 0) {

            webview.loadUrl("javascript:window.HtmlViewer.showHTML" +
                    "('<html>'+document.getElementById('VisibleReportContentRvReportes_ctl10').innerText+'</html>');");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (TextoHorario.length() < 200) {
                        //Toast.makeText(getApplicationContext(), "Aun no", Toast.LENGTH_SHORT).show();
                        if (TextoSnack.equals("Cargando...")) {
                            Snackbar.make(webview, TextoSnack, Snackbar.LENGTH_SHORT).show();
                        }
                        verSiCargo(a);

                    } else {

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(webview.getContext());
                        alertDialog.setTitle("Horario leído con exito!");
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage("¿Desea reemplazar su horario actual por el de Powercampus?");
                        alertDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                obtenerDatos();
                                CrearDBNueva();
                                Intent a=new Intent(getApplicationContext(),MainActivityNav.class);
                                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(a);
                                finish();
                            }

                        });
                        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent a=new Intent(getApplicationContext(),MainActivityNav.class);
                                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(a);
                                finish();
                            }
                        });
                        alertDialog.show();
                        a++;
                    }

                }
            }, 3000);
        }

    }

    public void CrearDBNueva(){

        deleteDatabase("BasedeDatos0");
        deleteDatabase("BasedeDatos01");
        deleteDatabase("BasedeDatos2");
        deleteDatabase("BasedeDatos3");
        deleteDatabase("BasedeDatos4");
        deleteDatabase("BasedeDatos5");

        HelperLunes= new SQLite_OpenHelper(this,"BasedeDatos0",null,1,0);
        HelperMartes= new SQLite_OpenHelper(this,"BasedeDatos01",null,1,1);
        HelperMiercoles= new SQLite_OpenHelper(this,"BasedeDatos2",null,1,2);
        HelperJueves= new SQLite_OpenHelper(this,"BasedeDatos3",null,1,3);
        HelperViernes= new SQLite_OpenHelper(this,"BasedeDatos4",null,1,4);
        HelperSabado= new SQLite_OpenHelper(this,"BasedeDatos5",null,1,5);

        Nodo temp=listaGuardar.getCabeza();
        Materia Nueva_Edit;
        String horas,luga,nombr;

        for (int i = 0; i <listaGuardar.CantMaterias() ; i++) {

            int dia=Integer.parseInt(temp.getDatoMateria().getIdM());

            try {
                horas=reducirHoras(temp.getDatoMateria().getHoras());
            }catch (Exception e){
                horas="0 a 0 pm";
                Toast.makeText(this, "Existen materias que no han terminado de ser registradas", Toast.LENGTH_SHORT).show();
            }
            try {
                luga=reducirLugar(temp.getDatoMateria().getSalon());

            }catch (Exception e){
                luga="no especificado";
                Toast.makeText(this, "Existen materias que no han terminado de ser registradas", Toast.LENGTH_SHORT).show();
            }

            nombr=temp.getDatoMateria().getNombre();
            nombr=nombr.toUpperCase().charAt(0) + nombr.substring(1, nombr.length()).toLowerCase();

            Nueva_Edit=new Materia(temp.getDatoMateria().getIdM(),nombr,horas,luga.trim(),temp.getDatoMateria().getCorte1(),temp.getDatoMateria().getCorte2(),temp.getDatoMateria().getCorte3(),temp.getDatoMateria().getMeta(),temp.getDatoMateria().getCreditos(),temp.getDatoMateria().getNotifTime());

            switch (dia){

                case 0:
                    HelperLunes.abrir();
                    HelperLunes.insertarRegistros(Nueva_Edit.getIdM(),Nueva_Edit.getNombre(),Nueva_Edit.getHoras(),Nueva_Edit.getSalon(),Nueva_Edit.getCorte1(),Nueva_Edit.getCorte2(),Nueva_Edit.getCorte3(),Nueva_Edit.getMeta(),Nueva_Edit.getCreditos(),dia,Nueva_Edit.getNotifTime());
                    HelperLunes.cerrar();
                    break;

                case 1:
                    HelperMartes.abrir();
                    HelperMartes.insertarRegistros(Nueva_Edit.getIdM(),Nueva_Edit.getNombre(),Nueva_Edit.getHoras(),Nueva_Edit.getSalon(),Nueva_Edit.getCorte1(),Nueva_Edit.getCorte2(),Nueva_Edit.getCorte3(),Nueva_Edit.getMeta(),Nueva_Edit.getCreditos(),dia,Nueva_Edit.getNotifTime());
                    HelperMartes.cerrar();
                    break;

                case 2:
                    HelperMiercoles.abrir();
                    HelperMiercoles.insertarRegistros(Nueva_Edit.getIdM(),Nueva_Edit.getNombre(),Nueva_Edit.getHoras(),Nueva_Edit.getSalon(),Nueva_Edit.getCorte1(),Nueva_Edit.getCorte2(),Nueva_Edit.getCorte3(),Nueva_Edit.getMeta(),Nueva_Edit.getCreditos(),dia,Nueva_Edit.getNotifTime());
                    HelperMiercoles.cerrar();
                    break;

                case 3:
                    HelperJueves.abrir();
                    HelperJueves.insertarRegistros(Nueva_Edit.getIdM(),Nueva_Edit.getNombre(),Nueva_Edit.getHoras(),Nueva_Edit.getSalon(),Nueva_Edit.getCorte1(),Nueva_Edit.getCorte2(),Nueva_Edit.getCorte3(),Nueva_Edit.getMeta(),Nueva_Edit.getCreditos(),dia,Nueva_Edit.getNotifTime());
                    HelperJueves.cerrar();
                    break;

                case 4:
                    HelperViernes.abrir();
                    HelperViernes.insertarRegistros(Nueva_Edit.getIdM(),Nueva_Edit.getNombre(),Nueva_Edit.getHoras(),Nueva_Edit.getSalon(),Nueva_Edit.getCorte1(),Nueva_Edit.getCorte2(),Nueva_Edit.getCorte3(),Nueva_Edit.getMeta(),Nueva_Edit.getCreditos(),dia,Nueva_Edit.getNotifTime());
                    HelperViernes.cerrar();
                    break;

                case 5:
                    HelperSabado.abrir();
                    HelperSabado.insertarRegistros(Nueva_Edit.getIdM(),Nueva_Edit.getNombre(),Nueva_Edit.getHoras(),Nueva_Edit.getSalon(),Nueva_Edit.getCorte1(),Nueva_Edit.getCorte2(),Nueva_Edit.getCorte3(),Nueva_Edit.getMeta(),Nueva_Edit.getCreditos(),dia,Nueva_Edit.getNotifTime());
                    HelperSabado.cerrar();
                    break;

            }
            temp=temp.getSiguiente();
        }
    }


    private String reducirLugar(@NonNull String lugar){

        String lug="";
        int pos=lugar.indexOf(";");
        pos+=1;
        int des=(pos);

        for (int i = des; i <lugar.length() ; i++) {
            lug+=""+lugar.charAt(i);
        }
        return lug;
    }

    private String reducirHoras(@NonNull String horas) {

        char d,h,p;
        int num;
        String hor="";
        String ha,de,ap;

        d=horas.charAt(0);
        de=d+""+horas.charAt(1);

        if(horas.charAt(1)==':'){
            de=d+"";
            ha=horas.charAt(11)+"";

            if(horas.charAt(12)==':'){
                num=Integer.parseInt(ha+"");
                if(num==12){
                    num=1;
                }else {
                    num++;
                }
                ha=""+(num);
                ap=horas.charAt(19)+"";
            }else{
                ha=horas.charAt(11)+""+horas.charAt(12);
                num=Integer.parseInt(ha);
                if(ha.equals("12")){
                    num=1;
                }else{
                    num++;
                }
                ha=""+(num);
                ap=horas.charAt(20)+"";

            }

        }else{
            de=d+""+horas.charAt(1);

            ha=horas.charAt(12)+"";
            if(horas.charAt(13)==':'){

                num=Integer.parseInt(ha);
                num++;
                ha=String.valueOf(num);

                ap=horas.charAt(20)+"";

            }else{
                ha=horas.charAt(12)+""+horas.charAt(13);
                num=Integer.parseInt(ha);
                if(num==12){
                    num=1;
                }else{
                    num++;
                }
                ha=String.valueOf(num);
                ap=horas.charAt(21)+"";
            }
        }

        if(ap.equals("A")){
            ap="am";

        }else{
            ap="pm";
        }

        hor=de+" a "+ha+" "+ap;

        return hor;
    }

    private void obtenerDatos() {

        String dia="";
        String id="";//Tomare id como el que seleccione los dias para agregarlo a la base de datos
        String lector="",nombre="", grupo="", creditos="", horari="", lugar="", nom_docente="";
        String pausa="\t\n";
        ReescribirTextoHorario();
        StringTokenizer token = new StringTokenizer(TextoHorario, pausa);

        String Texto="";
        token.nextToken();

        while (true){

            Texto=token.nextToken();

            if(Texto.trim().equals("HorarioEstudiantePeriodoActual")){
                break;
            }else{
                id = definirDiaidM(Texto) + "";
            }

            token.nextToken();

            nombre  = token.nextToken()+"";

            Texto = token.nextToken();

            creditos  = token.nextToken()+"";

            horari = token.nextToken();

            lugar = token.nextToken();

            nom_docente = token.nextToken();


            Materia nueva = new Materia(id, nombre, horari, lugar, Double.parseDouble("3.0"),
                    Double.parseDouble("3.0"), Double.parseDouble("3.0"),
                    Double.parseDouble("3.0"),
                    Integer.parseInt("0"+creditos.trim()), "15");
            Nodo nuevo = new Nodo(nueva);
            listaGuardar.AgregarNodos(nuevo);
        }

    }

    private void ReescribirTextoHorario() {

        int pos=TextoHorario.indexOf("Docente(s)");
        pos+=9;

        String tex="";
        for (int i = pos; i <TextoHorario.length() ; i++) {
            tex+=TextoHorario.charAt(i);
        }
        TextoHorario=tex;
    }


    public class MyJavaScriptInterface {
        private Context ctx;

        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        @JavascriptInterface
        public void showHTML(final String html) {
            final String html_ = html;
            TextoHorario = html_;
            handlerForJavascriptInterface.post(new Runnable() {
                @Override
                public void run() {

                }
            });
        }


    }

    public void accion(View a) {
        Toast.makeText(this, "Espere por favor...", Toast.LENGTH_SHORT).show();
    }

    public int definirDiaidM(String dia){
        int d=0;
        if(dia.equals("Lunes")){
            d=0;
        }else{
            if(dia.equals("Martes")){
                d=1;
            }else{
                if(dia.equals("Miércoles")){
                    d=2;
                }else{
                    if(dia.equals("Jueves")){
                        d=3;
                    }else{
                        if(dia.equals("Viernes")){
                            d=4;
                        }else{
                            if(dia.equals("Sábado")){
                                d=5;
                            }
                        }

                    }

                }

            }
        }
        return d;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                finish();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private static class MyWebViewClient extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }
    }

}
