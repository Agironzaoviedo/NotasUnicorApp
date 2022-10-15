package sistemas.ing.gironza.notasunicorappultimate;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListaTrucos extends AppCompatActivity {

    ClipData clip;
    ClipboardManager clipboard;
    TextView textTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_trucos);

        //Boton atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        textTitulo = findViewById(R.id.textTitulo);
        textTitulo.setText(getString(R.string.claves_de_wifi_unicor));


        ListView lista = findViewById(R.id.listatrucos);
        String[] list = new String[]{"Admin ✔", "Visitantes ✔", "Student ✔", "Administrativo Unicor -"};
        final ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, list);

        lista.setAdapter(Adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                Toast t;

                switch (i) {
                    case 0:
                        clip = ClipData.newPlainText("text", "NetAdmin2018*");
                        t = Toast.makeText(ListaTrucos.this, "Clave de Admin copiada", Toast.LENGTH_SHORT);
                        t.setGravity(0, 0, 1);
                        t.show();
                        break;
                    case 1:
                        clip = ClipData.newPlainText("text", "RedVisitor2019*");
                        t = Toast.makeText(ListaTrucos.this, "Clave de Visitantes copiada", Toast.LENGTH_SHORT);
                        t.setGravity(0, 0, 1);
                        t.show();
                        break;
                    case 2:
                        clip = ClipData.newPlainText("text", "NetPosgrado2019*");
                        t = Toast.makeText(ListaTrucos.this, "Clave de Student copiada", Toast.LENGTH_SHORT);
                        t.setGravity(0, 0, 1);
                        t.show();
                        break;
                    case 3:
                        clip = ClipData.newPlainText("text", "15025259");
                        t = Toast.makeText(ListaTrucos.this, "Clave de Admin Unicor copiada", Toast.LENGTH_SHORT);
                        t.setGravity(0, 0, 1);
                        t.show();
                        Toast.makeText(ListaTrucos.this, "La misma para usuario y contraseña", Toast.LENGTH_LONG).show();
                        break;

                }

                clipboard.setPrimaryClip(clip);

            }
        });


        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                return false;
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                Intent a = new Intent(getApplicationContext(), MainActivityNav.class);
                startActivity(a);

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
