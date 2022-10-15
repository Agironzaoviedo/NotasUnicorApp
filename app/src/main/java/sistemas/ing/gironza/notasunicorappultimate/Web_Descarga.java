package sistemas.ing.gironza.notasunicorappultimate;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class Web_Descarga extends AppCompatActivity {

    TextView Titulo, Subtitulo;
    String Eventos, Calificaciones,mapa;
    Button mapaCompleto;
    ProgressBar loadingProgressBar;

    Context contextoBoton;
    WebView Web;
    LinearLayout baner;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web__descarga);
        Inicializar();
        //Web = new WebView(this);


        WebSettings settings = Web.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setGeolocationEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);


        Intent inten = getIntent();
        Bundle ext = inten.getExtras();

        try {
            if (ext.getString("para").equals("Calificacion")) {
                getSupportActionBar().setTitle("Web");
                Titulo.setText("Calificaciones");
                Subtitulo.setText("Desde powercampus.unicordoba.edu.co");
                Web.loadUrl(Calificaciones);
                mapaCompleto.setVisibility(View.GONE);
                Snackbar.make(Web, "Para ver las calificaciones debes iniciar sesión",
                        Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                        .setAction("Action", null).show();

            }
            if (ext.getString("para").equals("Eventos")) {
                getSupportActionBar().setTitle("Web");
                Titulo.setText("Próximos eventos");
                Subtitulo.setText("Desde www.unicordoba.edu.co");
                Web.loadUrl(Eventos);
                mapaCompleto.setVisibility(View.GONE);
            }
            if(ext.getString("para").equals("Ubicor")) {
                getSupportActionBar().setTitle("Web");
                Titulo.setText("Ubicor ");
                mapaCompleto.setVisibility(View.VISIBLE);
                Subtitulo.setText("Desde ubicor.alvarezcristian.com");
                String Ubicor=("https://ubicor.alvarezcristian.com/bloque?id="+ext.getInt("bloque"));
                Web.loadUrl(Ubicor);
            }
            if(ext.getString("para").equals("mapa")) {
                getSupportActionBar().setTitle("Web");
                Titulo.setText("Ubicor ");
                mapaCompleto.setVisibility(View.VISIBLE);
                Subtitulo.setText("Desde ubicor.alvarezcristian.com");
                Web.loadUrl(mapa);
            }

        } catch (Exception e) {
            Web.loadUrl("http://powercampus.unicordoba.edu.co");
        }
        Web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Web= findViewById(R.id.webDes);
        //Web.setWebViewClient(new MyWebViewClient());
        //WebSettings settings=Web.getSettings();
        //settings.setJavaScriptEnabled(true);
        //Web.loadUrl(Url);

/*

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, REQUEST_CODE);

        }
*/


        Web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                loadingProgressBar.setProgress(newProgress);
                //loadingTitle.setProgress(newProgress);
                // hide the progress bar if the loading is complete
                if (newProgress == 100) {
                    loadingProgressBar.setVisibility(View.GONE);

                } else {
                    loadingProgressBar.setVisibility(View.VISIBLE);
                }
            }
        });


        mapaCompleto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Web.loadUrl(mapa);
            }
        });

    }

   /* public void EscuchaDescarga(final WebView Web){
        Web.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                //for downloading directly through download manager
                final DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.allowScanningByMediaScanner();
                //Se elimina el archivo anterior y se descarga uno nuevo, evitando que hayan mas de un archivo con el mismo nombre
                File Archivo = new File(Environment.getExternalStorageDirectory()+"/Download/HorarioEstudiantePeriodoActual.xml");
                if(Archivo.delete()){
                    Toast.makeText(Web.getContext(), "Horario anterior eliminado", Toast.LENGTH_SHORT).show();
                }

                request.setMimeType(mimetype);
                //------------------------COOKIE------------------------
                String cookies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);
                //------------------------COOKIE------------------------
                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription("Downloading file...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimetype));
                final DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);


                new Thread("Browser download") {
                    public void run() {
                        dm.enqueue(request);
                    }
                }.start();
                Toast.makeText(contextoBoton, "Descargando horario...", Toast.LENGTH_LONG).show();
            }
        });


    }
*/


    public void Inicializar() {
        loadingProgressBar = findViewById(R.id.LProgressBar);
        Web = findViewById(R.id.webDes);
        baner = findViewById(R.id.baner);
        Subtitulo = findViewById(R.id.Subtitulo);
        mapaCompleto=findViewById(R.id.mapaCompleto);
        Eventos = "https://www.unicordoba.edu.co/index.php/eventos/#tab-ddc2968e280df96e74d";
        Calificaciones = "http://powercampus.unicordoba.edu.co/Records/NotasParciales.aspx";
        mapa="https://ubicor.alvarezcristian.com/mapa";
        Titulo = findViewById(R.id.Titulo);
        contextoBoton = Web.getContext();
    }


    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;
            case R.id.actualizar:
                Web.reload();
                return true;
            case R.id.adela:
                Web.goForward();
                return true;
            case R.id.atras:
                Web.goBack();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_navegador, menu);
        return true;
    }

    //Para cuando se mentiene presionado un item de algo
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.context_menu, menu);

    }


}
