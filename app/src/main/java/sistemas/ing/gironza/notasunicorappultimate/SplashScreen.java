package sistemas.ing.gironza.notasunicorappultimate;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SplashScreen extends Activity {

    ImageView Logo;
    MediaPlayer Sound;
    boolean Lanzar=true;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        i=new Intent(SplashScreen.this,MainActivityNav.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Sound= MediaPlayer.create(this,R.raw.carefreeapp);
        Logo=findViewById(R.id.LogoInicio);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if(Lanzar){


                    startActivity(i);
                    finish();
                }


            }
        },3500);

        Logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(i);
                Sound.start();
                finish();

            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Lanzar=false;
    }
}
