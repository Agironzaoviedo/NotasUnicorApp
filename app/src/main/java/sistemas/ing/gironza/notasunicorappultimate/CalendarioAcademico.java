package sistemas.ing.gironza.notasunicorappultimate;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class CalendarioAcademico extends AppCompatActivity {

    Button Calsiguiente;

    ImageView img2,img1;
    Boolean camb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_academic);

        inicializar();
        //Boton atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Calsiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (camb) {
                    img2.setVisibility(View.GONE);
                    Calsiguiente.setText("Pagina 2");
                    camb = false;
                } else {
                    img2.setVisibility(View.VISIBLE);
                    Calsiguiente.setText("Pagina 1");
                    camb = true;
                }
            }
        });


    }

    private void inicializar() {
        img1=findViewById(R.id.img1);
        camb=true;
        Calsiguiente=findViewById(R.id.Calsiguiente);
        img2=findViewById(R.id.img2);

    }

    public void AbrirEnGaleria1(View v){
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.calendar2);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream
                bytes = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                b, "Title", null);

        openInGallery(path);
    }

    public void AbrirEnGaleria2(View v){

        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.calendar3);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream
                bytes = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                b, "Title", null);

        openInGallery(path);
    }
    public void openInGallery(String currentPhotoPath) {

        File f=null;
        boolean ver=false;

        try {
            f = new File(currentPhotoPath);
            ver=true;
            Toast.makeText(this, "Abriendo galeria", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ver) {
            Uri contentUri = Uri.fromFile(f);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    contentUri = Uri.parse(currentPhotoPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                contentUri = Uri.fromFile(f);
            }

            Intent galleryIntent = new Intent(Intent.ACTION_VIEW);

            galleryIntent.setDataAndType(contentUri, "image/*");
            galleryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(galleryIntent,0);
            Toast.makeText(this, "Abriendo galeria", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
