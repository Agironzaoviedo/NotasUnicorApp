package sistemas.ing.gironza.notasunicorappultimate;


import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class AboutActivity extends AppCompatActivity {

    ImageView unicorapp;
    MediaPlayer Sound;
    ImageView Esc;
    int i=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //Boton atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Esc=findViewById(R.id.escudo);

        Esc.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Sound.start();
                return false;
            }
        });

        Sound=MediaPlayer.create(this,R.raw.carefreeapp);

        unicorapp= findViewById(R.id.unicorapp);
        unicorapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.gironza.notasunicorapp");
                Intent launchIntent2 = getPackageManager().getLaunchIntentForPackage("sistemas.ing.gironza.notasunicorappultimate");
                if (launchIntent2 != null) {
                    startActivity(launchIntent2);
                }else{

                    if(launchIntent!=null){

                        startActivity(launchIntent);//null pointer check in case package name was not found
                    }else{
                        Toast.makeText(getApplicationContext(), "No tiene Notas UnicorApp instalado", Toast.LENGTH_SHORT).show();
                    }

                }
                System.exit(0);
                onDestroy();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                Intent a=new Intent(getApplicationContext(),MainActivityNav.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(a);
                finish();
                return true;

            case R.id.politicas:

                Uri uri = Uri.parse("https://sites.google.com/view/notasunicorapp-privacy-policy/");
                Intent inte = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(inte);
                return true;

            case R.id.UltimaActu:

                Uri uriURLApp = Uri.parse("https://drive.google.com/file/d/1ZtiDr7tGogY-zuMoxXsjmt2DNyU-GsoQ/view");
                Intent IntentNav = new Intent(Intent.ACTION_VIEW, uriURLApp);
                startActivity(IntentNav);
                Toast.makeText(this, "Descargando Actualización", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.subWifi:
                Intent intent=new Intent(getApplicationContext(),ListaTrucos.class);
                startActivity(intent);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_top_secret, menu);

        return true;

    }

}