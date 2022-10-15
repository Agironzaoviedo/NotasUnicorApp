package sistemas.ing.gironza.notasunicorappultimate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static sistemas.ing.gironza.notasunicorappultimate.MainActivity.materiaActual;

import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperJueves;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperLunes;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperMartes;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperMiercoles;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperSabado;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperViernes;

public class EditarMateria extends AppCompatActivity {

    Button GuardarEd;
    Spinner spinDia,Spampm;
    TextView Titulo;
    AutoCompleteTextView desde, hasta;
    EditText NombreMateria, nmeta, cort1, cort2, cort3,EditSalon,EditCredit;
    String TextoTitulo;
    Materia Nueva_Edit;
    int dia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_materia);

        inicializar();

        String []horas={"1","2","3","4","5","6","7","8","9","10","11","12"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,horas);
        desde.setAdapter(adapter);
        hasta.setAdapter(adapter);
        desde.setSelected(true);

        Intent inten =getIntent();
        Bundle ext= inten.getExtras();
        //Boton atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        try {
            TextoTitulo= ext.getString("Texto");

            Titulo.setText(TextoTitulo+"");
            dia=ext.getInt("Dia");

        }catch (Exception e){
            e.printStackTrace();
        }

        if(Titulo.getText().toString().equals("Editar Materia")){
            MostrarEditar();
            Titulo.setText(getString(R.string.EditMateria));
        }else{

            if(Titulo.getText().toString().equals("Agregar en Día")){
                Titulo.setText(getString(R.string.AddEnDia));
                spinDia.setSelection(dia);
                spinDia.setEnabled(false);

            }else{
                Titulo.setText(getString(R.string.AddMateria));
            }
        }

        Toast T=Toast.makeText(getApplicationContext(),getString(R.string.LleneAllCampos),Toast.LENGTH_LONG);
        T.setGravity(0,1,1);
        T.show();

        GuardarEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Guardar_datos()) {

                    if (TextoTitulo.equals("Editar Materia")) {
                        Toast.makeText(EditarMateria.this, getString(R.string.ExitoEdit), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditarMateria.this,  getString(R.string.ExitoAdd)+" "+ spinDia.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    }

                    //---------------
                    Intent a=new Intent(getApplicationContext(),MainActivityNav.class);
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(a);
                    finish();
                    //---------------

                }
            }
        });
    }

    public void MostrarEditar(){

        cort1.setText(materiaActual.getCorte1().toString());
        cort2.setText(materiaActual.getCorte2().toString());
        cort3.setText(materiaActual.getCorte3().toString());
        NombreMateria.setText(materiaActual.getNombre().toString());
        String Hors=materiaActual.getHoras(),des="",has="";

        spinDia.setSelection(dia);
        spinDia.setEnabled(false);
        int next=0;
        Boolean a=false;
        String ampm="";

        for (int i = 0; i <Hors.length() ; i++) {

            if(next>=3){
                ampm+=Hors.charAt(i);

            }else {
                if((Hors.charAt(i)!=' ') || (Hors.charAt(i)=='a')){

                    if( Hors.charAt(i)=='a'){
                        a=true;

                    }else {
                        if(a){
                            has+=Hors.charAt(i);
                        }else {
                            des+=Hors.charAt(i);
                        }
                    }

                }else {

                    next++;
                }
            }
        }
        desde.setText(des);
        hasta.setText(has);
        if (ampm.equals("am")){
            Spampm.setSelection(0);
        }else {
            Spampm.setSelection(1);
        }

        EditCredit.setText(materiaActual.getCreditos()+"");
        nmeta.setText(materiaActual.getMeta().toString());
        EditSalon.setText(materiaActual.getSalon());




    }

    public boolean Guardar_datos(){
        boolean Verif;

        Nueva_Edit=new Materia();
        try {

            Nueva_Edit.setCorte1(Double.parseDouble(cort1.getText().toString()));
            Nueva_Edit.setCorte2(Double.parseDouble(cort2.getText().toString()));
            Nueva_Edit.setCorte3(Double.parseDouble(cort3.getText().toString()));
            Nueva_Edit.setNombre(NombreMateria.getText().toString());
            String Horas = (desde.getText().toString() + " a " + hasta.getText().toString() + " " + Spampm.getSelectedItem().toString());
            Nueva_Edit.setHoras(Horas);
            Nueva_Edit.setCreditos(Integer.parseInt(EditCredit.getText().toString()));
            Nueva_Edit.setMeta(Double.parseDouble(nmeta.getText().toString()));
            Nueva_Edit.setSalon(EditSalon.getText().toString());
            Nueva_Edit.setNotifTime(15+"");

            Verif=true;
        }catch(Exception e){
            Verif=false;
        }
        if (Verif) {
            if (TextoTitulo.equals(getString(R.string.EditMateria))) {
                Nueva_Edit.setIdM(materiaActual.getIdM());

                editarMateria();
            } else {
                agregarMateria();
            }
        }else{
            Toast.makeText(this, getString(R.string.errorSave), Toast.LENGTH_SHORT).show();
            Toast t=Toast.makeText(this, getString(R.string.camposVoid), Toast.LENGTH_SHORT);
            t.setGravity(0,1,1);
            t.show();
        }
        return Verif;
    }

    void editarMateria(){

        int dia=spinDia.getSelectedItemPosition();

        switch (dia){
            case 0:
                HelperLunes.abrir();
                HelperLunes.editarDatos(Nueva_Edit.getIdM(),Nueva_Edit.getNombre(),Nueva_Edit.getHoras(),Nueva_Edit.getSalon(),Nueva_Edit.getCorte1(),Nueva_Edit.getCorte2(),Nueva_Edit.getCorte3(),Nueva_Edit.getMeta(),Nueva_Edit.getCreditos(),dia,Nueva_Edit.getIdM(),Nueva_Edit.getNotifTime());
                HelperLunes.cerrar();
                break;

            case 1:
                HelperMartes.abrir();
                HelperMartes.editarDatos(Nueva_Edit.getIdM(),Nueva_Edit.getNombre(),Nueva_Edit.getHoras(),Nueva_Edit.getSalon(),Nueva_Edit.getCorte1(),Nueva_Edit.getCorte2(),Nueva_Edit.getCorte3(),Nueva_Edit.getMeta(),Nueva_Edit.getCreditos(),dia,Nueva_Edit.getIdM(),Nueva_Edit.getNotifTime());
                HelperMartes.cerrar();
                break;

            case 2:
                HelperMiercoles.abrir();
                HelperMiercoles.editarDatos(Nueva_Edit.getIdM(),Nueva_Edit.getNombre(),Nueva_Edit.getHoras(),Nueva_Edit.getSalon(),Nueva_Edit.getCorte1(),Nueva_Edit.getCorte2(),Nueva_Edit.getCorte3(),Nueva_Edit.getMeta(),Nueva_Edit.getCreditos(),dia,Nueva_Edit.getIdM(),Nueva_Edit.getNotifTime());
                HelperMiercoles.cerrar();
                break;

            case 3:
                HelperJueves.abrir();
                HelperJueves.editarDatos(Nueva_Edit.getIdM(),Nueva_Edit.getNombre(),Nueva_Edit.getHoras(),Nueva_Edit.getSalon(),Nueva_Edit.getCorte1(),Nueva_Edit.getCorte2(),Nueva_Edit.getCorte3(),Nueva_Edit.getMeta(),Nueva_Edit.getCreditos(),dia,Nueva_Edit.getIdM(),Nueva_Edit.getNotifTime());
                HelperJueves.cerrar();
                break;

            case 4:
                HelperViernes.abrir();
                HelperViernes.editarDatos(Nueva_Edit.getIdM(),Nueva_Edit.getNombre(),Nueva_Edit.getHoras(),Nueva_Edit.getSalon(),Nueva_Edit.getCorte1(),Nueva_Edit.getCorte2(),Nueva_Edit.getCorte3(),Nueva_Edit.getMeta(),Nueva_Edit.getCreditos(),dia,Nueva_Edit.getIdM(),Nueva_Edit.getNotifTime());
                HelperViernes.cerrar();
                break;

            case 5:
                HelperSabado.abrir();
                HelperSabado.editarDatos(Nueva_Edit.getIdM(),Nueva_Edit.getNombre(),Nueva_Edit.getHoras(),Nueva_Edit.getSalon(),Nueva_Edit.getCorte1(),Nueva_Edit.getCorte2(),Nueva_Edit.getCorte3(),Nueva_Edit.getMeta(),Nueva_Edit.getCreditos(),dia,Nueva_Edit.getIdM(),Nueva_Edit.getNotifTime());
                HelperSabado.cerrar();
                break;

        }


    }

    void agregarMateria(){

        int dia=spinDia.getSelectedItemPosition();
        switch (dia){
            case 0:
                HelperLunes.abrir();
                HelperLunes.insertarRegistros(Nueva_Edit.getIdM(),Nueva_Edit.getNombre(),Nueva_Edit.getHoras(),Nueva_Edit.getSalon(),Nueva_Edit.getCorte1(),Nueva_Edit.getCorte2(),Nueva_Edit.getCorte3(),Nueva_Edit.getMeta(),Nueva_Edit.getCreditos(),dia,Nueva_Edit.getNotifTime());
                //HelperLunes.Ordenar(this,HelperLunes,dia);
                HelperLunes.cerrar();
                break;

            case 1:
                HelperMartes.abrir();
                HelperMartes.insertarRegistros(Nueva_Edit.getIdM(),Nueva_Edit.getNombre(),Nueva_Edit.getHoras(),Nueva_Edit.getSalon(),Nueva_Edit.getCorte1(),Nueva_Edit.getCorte2(),Nueva_Edit.getCorte3(),Nueva_Edit.getMeta(),Nueva_Edit.getCreditos(),dia,Nueva_Edit.getNotifTime());
                HelperMartes.Ordenar(this,HelperMartes,dia);
                HelperMartes.cerrar();
                break;

            case 2:
                HelperMiercoles.abrir();
                HelperMiercoles.insertarRegistros(Nueva_Edit.getIdM(),Nueva_Edit.getNombre(),Nueva_Edit.getHoras(),Nueva_Edit.getSalon(),Nueva_Edit.getCorte1(),Nueva_Edit.getCorte2(),Nueva_Edit.getCorte3(),Nueva_Edit.getMeta(),Nueva_Edit.getCreditos(),dia,Nueva_Edit.getNotifTime());
                //HelperMiercoles.Ordenar(this,HelperMiercoles,dia);
                HelperMiercoles.cerrar();
                break;

            case 3:
                HelperJueves.abrir();
                HelperJueves.insertarRegistros(Nueva_Edit.getIdM(),Nueva_Edit.getNombre(),Nueva_Edit.getHoras(),Nueva_Edit.getSalon(),Nueva_Edit.getCorte1(),Nueva_Edit.getCorte2(),Nueva_Edit.getCorte3(),Nueva_Edit.getMeta(),Nueva_Edit.getCreditos(),dia,Nueva_Edit.getNotifTime());
                //HelperJueves.Ordenar(this,HelperJueves,dia);
                HelperJueves.cerrar();
                break;

            case 4:
                HelperViernes.abrir();
                HelperViernes.insertarRegistros(Nueva_Edit.getIdM(),Nueva_Edit.getNombre(),Nueva_Edit.getHoras(),Nueva_Edit.getSalon(),Nueva_Edit.getCorte1(),Nueva_Edit.getCorte2(),Nueva_Edit.getCorte3(),Nueva_Edit.getMeta(),Nueva_Edit.getCreditos(),dia,Nueva_Edit.getNotifTime());
                // HelperViernes.Ordenar(this,HelperViernes,dia);
                HelperViernes.cerrar();
                break;

            case 5:
                HelperSabado.abrir();
                HelperSabado.insertarRegistros(Nueva_Edit.getIdM(),Nueva_Edit.getNombre(),Nueva_Edit.getHoras(),Nueva_Edit.getSalon(),Nueva_Edit.getCorte1(),Nueva_Edit.getCorte2(),Nueva_Edit.getCorte3(),Nueva_Edit.getMeta(),Nueva_Edit.getCreditos(),dia,Nueva_Edit.getNotifTime());
                //HelperSabado.Ordenar(this,HelperSabado,dia);
                HelperSabado.cerrar();
                break;

        }


    }



    public void inicializar(){

        spinDia=findViewById(R.id.SpinDia);
        NombreMateria = findViewById(R.id.EditNombre);
        nmeta = findViewById(R.id.EditMeta);
        cort1 = findViewById(R.id.EditCort1);
        cort2 = findViewById(R.id.EditCort2);
        cort3 = findViewById(R.id.EditCort3);
        GuardarEd= findViewById(R.id.EditBtnGuardar);
        EditSalon=findViewById(R.id.EditSalon);
        EditCredit=findViewById(R.id.EditCredit);
        TextoTitulo="";
        Titulo=findViewById(R.id.Titulo);
        desde=findViewById(R.id.desde);
        hasta=findViewById(R.id.hasta);
        Spampm=findViewById(R.id.ampm);

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_materia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                finish();
                return true;
            case R.id.guardar_edit:
                Guardar_datos();
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
