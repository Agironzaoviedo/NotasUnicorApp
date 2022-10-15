package sistemas.ing.gironza.notasunicorappultimate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;


public class Calendario extends AppCompatActivity {

    CalendarView Calendario;
    Button BCaAcad;
    ListView ListaCalendario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        inicializar();
        //Boton atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        getSupportActionBar().setTitle("CALENDARIOS");

        BCaAcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),CalendarioAcademico.class);
                startActivity(i);

            }
        });
        Calendario.startLayoutAnimation();
        Calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Toast.makeText(Calendario.this, "Día "+i2+"- Mes "+i1+"- Año "+i, Toast.LENGTH_SHORT).show();
            }
        });


        String[] list= new String[]{"Lunes","Martes","Miercoles","Jueves", "viernes"};
        final ArrayAdapter<String> Adapter= new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,list);
        ListaCalendario.setAdapter(Adapter);

    }

    private void inicializar() {
        Calendario=findViewById(R.id.Calendario);
        BCaAcad=findViewById(R.id.BCaAcad);
        ListaCalendario=findViewById(R.id.ListaCalendario);
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
