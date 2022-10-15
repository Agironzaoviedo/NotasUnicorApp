package sistemas.ing.gironza.notasunicorappultimate;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.Config;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperJueves;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperLunes;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperMartes;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperMiercoles;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperSabado;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperViernes;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperConf;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.ListaDiaActual;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.ListaJueves;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.ListaLunes;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.ListaMartes;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.ListaMiercoles;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.ListaSabado;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.ListaViernes;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //http://powercampus.unicordoba.edu.co/UniversidadDeCordoba/Administracion/Parametrizacion/Reportes/ReportesRegistroAcademico.aspx?path=Horario/HorarioEstudiantePeriodoActual&prPersonId=61528
    EditText nmeta, corte1, corte2, corte3, NotaFinal, Animo;
    Double Cort1, Cort2, Cort3, Definiva;
    double Respaldo, Acort1, Acort2, Acort3;
    String Recom, msjCortes;
    Button BActMeta, Bmasinfo, BactNotaFinal;
    FloatingActionButton Bant, Bsig;
    TextView Recomendacion, mensajeCortes, NombreMateria, codM, creditos, horario, salon, diadesemana;
    LinearLayout LytMasinfo;
    double cte4, aux;
    int dia, minutosNotif = 10;
    Boolean vac, a;//para ayudar a la visibilidad
    EventoTeclado tecladoOcultar;
    CheckBox Chec1, Chec2, Chec3, ChecAnim;
    ToggleButton NotifButon;
    public static Materia materiaActual = new Materia();


    @SuppressLint("CutPasteId")
    public void inicializarMainActivity() {
        a = true;
        cte4 = 3.0;
        Respaldo = cte4;
        Acort1 = Respaldo;
        Acort2 = Respaldo;
        Acort3 = Respaldo;
        Chec1 = findViewById(R.id.Chk1C);
        Chec2 = findViewById(R.id.Chk2C);
        Chec3 = findViewById(R.id.Chk3C);
        ChecAnim = findViewById(R.id.ChkAnim);
        Animo = findViewById(R.id.animo);
        corte1 = findViewById(R.id.Notacorte1);
        corte2 = findViewById(R.id.Notacorte2);
        corte3 = findViewById(R.id.Notacorte3);
        NombreMateria = findViewById(R.id.NombreMateria);
        BActMeta = findViewById(R.id.BActMeta);
        Bmasinfo = findViewById(R.id.BmasInfo);
        BactNotaFinal = findViewById(R.id.BActNotaFinal);
        corte1 = findViewById(R.id.Notacorte1);
        corte2 = findViewById(R.id.Notacorte2);
        corte3 = findViewById(R.id.Notacorte3);
        NotaFinal = findViewById(R.id.NotaDefinitiva);
        mensajeCortes = findViewById(R.id.mensajeCortes);
        Recomendacion = findViewById(R.id.mensaje);
        LytMasinfo = findViewById(R.id.LYTmasInfo);
        tecladoOcultar = new EventoTeclado();
        nmeta = findViewById(R.id.notaMeta);
        codM = findViewById(R.id.codM);
        creditos = findViewById(R.id.creditos);
        horario = findViewById(R.id.horario);
        salon = findViewById(R.id.salon);
        diadesemana = findViewById(R.id.diadesemana);
        Bant = findViewById(R.id.ant);
        Bsig = findViewById(R.id.sig);
        NotifButon = findViewById(R.id.NotifButon);

    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarMainActivity();


        Bmasinfo.setOnClickListener(this);
        BActMeta.setOnClickListener(this);
        BactNotaFinal.setOnClickListener(this);
        //Boton atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //creamos una instancia de la clase para pasarla como parametro al siguiente evento

        nmeta.setOnEditorActionListener(tecladoOcultar);//usamos el boton que se oprime teniendo el teclado abierto para q se ejecute la accion de dicho boton
        Intent inten = getIntent();
        Bundle ext = inten.getExtras();
        vac = false;
        try {
            dia = ext.getInt("dia");
            String idM = ext.getString("idM");
            vac = ext.getBoolean("vacio");

            cargarMateria(idM, dia);
            textoToast(dia);


        } catch (Exception e) {
            dia = 10;
        }
        escojerTituloDia(dia, vac);

        if (vac) {
            NombreMateria.setText("Calcular cortes");
            Bmasinfo.setVisibility(View.INVISIBLE);
            Bsig.setVisibility(View.INVISIBLE);
            Bant.setVisibility(View.INVISIBLE);
        } else {
            visibilidadAntYSig();
            setOnclickChec1(NombreMateria);
        }


        Bant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cargarMateria(ListaDiaActual.anteriorMateria(materiaActual.getIdM()).getIdM(), dia);
                visibilidadAntYSig();
                Toast.makeText(getApplicationContext(), NombreMateria.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });

        Bsig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarMateria(ListaDiaActual.siguienteMateria(materiaActual.getIdM()).getIdM(), dia);
                visibilidadAntYSig();
                Toast.makeText(getApplicationContext(), NombreMateria.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        NotifButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GuardarNotif();

            }
        });
        ActualizarNotaFinal();


    }

    public void visibilidadAntYSig() {

        if (ListaDiaActual.BuscarNodo(codM.getText() + "").getAnterior() == null) {
            Bant.setVisibility(View.GONE);
        } else {
            Bant.setVisibility(View.VISIBLE);
        }

        if (ListaDiaActual.BuscarNodo(codM.getText() + "").getSiguiente() == null) {
            Bsig.setVisibility(View.GONE);
        } else {
            Bsig.setVisibility(View.VISIBLE);
        }
    }

    void escojerTituloDia(int dia, Boolean ver) {
        if (!ver) {
            switch (dia) {
                case 0:
                    getSupportActionBar().setTitle(R.string.dia0);
                    break;
                case 1:
                    getSupportActionBar().setTitle(R.string.dia1);
                    break;
                case 2:
                    getSupportActionBar().setTitle(R.string.dia2);
                    break;
                case 3:
                    getSupportActionBar().setTitle(R.string.dia3);
                    break;
                case 4:
                    getSupportActionBar().setTitle(R.string.dia4);
                    break;
                case 5:
                    getSupportActionBar().setTitle(R.string.dia5);
                    break;
                default:
                    getSupportActionBar().setTitle(R.string.app_name);
                    break;
            }
        }

    }

    public void mostrarTextoDia(View a) {
        textoToast(dia);
    }

    void textoToast(int dia) {

        String Dia = getString(R.string.MensajediaD);

        switch (dia) {
            case 0:
                Dia = getString(R.string.Mensajedia0);
                break;
            case 1:
                Dia = getString(R.string.Mensajedia1);
                break;
            case 2:

                Dia = getString(R.string.Mensajedia2);
                break;
            case 3:

                Dia = getString(R.string.Mensajedia3);
                break;
            case 4:

                Dia = getString(R.string.Mensajedia4);
                break;
            case 5:

                Dia = getString(R.string.Mensajedia5);
                break;
            default:


        }


        Toast.makeText(this, Dia, Toast.LENGTH_SHORT).show();
    }


    public void setOnclickChec1(View v) {


        if (!Chec1.isChecked()) {
            corte1.setEnabled(true);
        } else {
            try {
                Double.parseDouble(this.corte1.getText().toString());
                corte1.setEnabled(false);
            } catch (Exception e) {
                Chec1.setChecked(false);
                Toast.makeText(getApplicationContext(), getString(R.string.camposVoid), Toast.LENGTH_SHORT).show();
            }

        }

        if (!Chec2.isChecked()) {
            corte2.setEnabled(true);
        } else {
            try {
                Double.parseDouble(this.corte2.getText().toString());
                corte2.setEnabled(false);
            } catch (Exception e) {
                Chec2.setChecked(false);
                Toast.makeText(getApplicationContext(), getString(R.string.camposVoid), Toast.LENGTH_SHORT).show();
            }
        }

        if (!Chec3.isChecked()) {
            corte3.setEnabled(true);
        } else {
            try {
                Double.parseDouble(this.corte3.getText().toString());
                corte3.setEnabled(false);
            } catch (Exception e) {
                Chec3.setChecked(false);
                Toast.makeText(getApplicationContext(), getString(R.string.camposVoid), Toast.LENGTH_SHORT).show();
            }
        }
        if (!ChecAnim.isChecked()) {
            Animo.setEnabled(true);
        } else {
            try {
                String a = Animo.getText().toString();
                Animo.setEnabled(false);
            } catch (Exception e) {
                ChecAnim.setChecked(false);
                Toast.makeText(getApplicationContext(), "¡ No hay texto ! ", Toast.LENGTH_SHORT).show();
            }

        }
        if (!vac) {
            if (Chec1.isChecked()) {
                Config.setBloqCorte1("1");
            } else {
                Config.setBloqCorte1("0");
            }

            if (Chec2.isChecked()) {
                Config.setBloqCorte2("1");
            } else {
                Config.setBloqCorte2("0");
            }

            if (Chec3.isChecked()) {
                Config.setBloqCorte3("1");
            } else {
                Config.setBloqCorte3("0");
            }

            if (ChecAnim.isChecked()) {
                Config.setBloqAnimo("1");
                Config.setTextAnimo(Animo.getText().toString());
            } else {
                Config.setTextAnimo(Animo.getText().toString());
                Config.setBloqAnimo("0");
            }
            ActualizarConfiguracion();
        }

    }

    public void ActualizarConfiguracion() {

        abrirDias();
        HelperConf.editarDatosConfiguracion(Config.NotificacionMat, Config.TiempoNotf, Config.BloqCorte1,
                Config.BloqCorte2, Config.BloqCorte3, Config.BloqAnimo, Config.TextAnimo, Config.HojaApuntes,
                Config.DatosUltimaAct, 6, Config.id);
        cerrarDias();
    }

    public void GuardarNotif() {
/*
        if (NotifButon.getText().equals("")){

            materiaActual.setNotifTime("Desact");
        }else{

            NotifButon.setChecked(true);
            NotifButon.setText(minutosNotif+" min");
            materiaActual.setNotifTime(NotifButon.getText().toString());
            minutosNotif=Integer.parseInt(NotifButon.getText().toString().charAt(0)+""+NotifButon.getText().toString().charAt(1));

        }
        minutosNotif+=10;

        if(minutosNotif==60){
            NotifButon.setChecked(false);
            minutosNotif=10;
        }else{
            materiaActual.setNotifTime(NotifButon.getText().toString());
        }
        */
        GuardarHelpers();

    }

    public void GuardarHelpers() {


        switch (dia) {
            case 0:
                HelperLunes.abrir();
                HelperLunes.editarDatos(materiaActual.getIdM(), materiaActual.getNombre(), materiaActual.getHoras(), materiaActual.getSalon(), materiaActual.getCorte1(), materiaActual.getCorte2(), materiaActual.getCorte3(), materiaActual.getMeta(), materiaActual.getCreditos(), dia, materiaActual.getIdM(), materiaActual.getNotifTime());
                HelperLunes.cerrar();
                break;

            case 1:
                HelperMartes.abrir();
                HelperMartes.editarDatos(materiaActual.getIdM(), materiaActual.getNombre(), materiaActual.getHoras(), materiaActual.getSalon(), materiaActual.getCorte1(), materiaActual.getCorte2(), materiaActual.getCorte3(), materiaActual.getMeta(), materiaActual.getCreditos(), dia, materiaActual.getIdM(), materiaActual.getNotifTime());
                HelperMartes.cerrar();
                break;

            case 2:
                HelperMiercoles.abrir();
                HelperMiercoles.editarDatos(materiaActual.getIdM(), materiaActual.getNombre(), materiaActual.getHoras(), materiaActual.getSalon(), materiaActual.getCorte1(), materiaActual.getCorte2(), materiaActual.getCorte3(), materiaActual.getMeta(), materiaActual.getCreditos(), dia, materiaActual.getIdM(), materiaActual.getNotifTime());
                HelperMiercoles.cerrar();
                break;

            case 3:
                HelperJueves.abrir();
                HelperJueves.editarDatos(materiaActual.getIdM(), materiaActual.getNombre(), materiaActual.getHoras(), materiaActual.getSalon(), materiaActual.getCorte1(), materiaActual.getCorte2(), materiaActual.getCorte3(), materiaActual.getMeta(), materiaActual.getCreditos(), dia, materiaActual.getIdM(), materiaActual.getNotifTime());
                HelperJueves.cerrar();
                break;

            case 4:
                HelperViernes.abrir();
                HelperViernes.editarDatos(materiaActual.getIdM(), materiaActual.getNombre(), materiaActual.getHoras(), materiaActual.getSalon(), materiaActual.getCorte1(), materiaActual.getCorte2(), materiaActual.getCorte3(), materiaActual.getMeta(), materiaActual.getCreditos(), dia, materiaActual.getIdM(), materiaActual.getNotifTime());
                HelperViernes.cerrar();
                break;

            case 5:
                HelperSabado.abrir();
                HelperSabado.editarDatos(materiaActual.getIdM(), materiaActual.getNombre(), materiaActual.getHoras(), materiaActual.getSalon(), materiaActual.getCorte1(), materiaActual.getCorte2(), materiaActual.getCorte3(), materiaActual.getMeta(), materiaActual.getCreditos(), dia, materiaActual.getIdM(), materiaActual.getNotifTime());
                HelperSabado.cerrar();
                break;

        }

    }

    //GUARDAR EN BASE DE DATOS  <----------------------------
    public void Guardar_datos() {

        Double corteN1 = Double.parseDouble(this.corte1.getText().toString());
        Double corteN2 = Double.parseDouble(this.corte2.getText().toString());
        Double corteN3 = Double.parseDouble(this.corte3.getText().toString());
        Double meta = getMeta();
        materiaActual.setCorte1(corteN1);
        materiaActual.setCorte2(corteN2);
        materiaActual.setCorte3(corteN3);
        materiaActual.setMeta(meta);
        GuardarHelpers();
    }


    class EventoTeclado implements Button.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

            if (i == EditorInfo.IME_ACTION_NEXT) {
                onClick(BActMeta);
            }
            return false;
        }
    }


    public double getMeta() {
        double gm;

        try {
            gm = Double.valueOf(nmeta.getText() + "");
        } catch (Exception e) {
            nmeta.setText("" + 3.0);
            gm = Double.valueOf(nmeta.getText() + "");
        }

        return gm;
    }

    void cargarMateria(String idM, int dia) {


        String Dia = "";

        switch (dia) {
            case 0:
                materiaActual = ListaLunes.Buscar(idM);
                Dia = getString(R.string.dia0);
                break;
            case 1:
                materiaActual = ListaMartes.Buscar(idM);
                Dia = getString(R.string.dia1);
                break;
            case 2:
                materiaActual = ListaMiercoles.Buscar(idM);
                Dia = getString(R.string.dia2);
                break;
            case 3:
                materiaActual = ListaJueves.Buscar(idM);
                Dia = getString(R.string.dia3);
                break;
            case 4:
                materiaActual = ListaViernes.Buscar(idM);
                Dia = getString(R.string.dia4);
                break;
            case 5:
                materiaActual = ListaSabado.Buscar(idM);
                Dia = getString(R.string.dia5);
                break;
            default:
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();

        }


        NombreMateria.setText(materiaActual.getNombre());
        corte1.setText(materiaActual.getCorte1().toString());
        corte2.setText(materiaActual.getCorte2().toString());
        corte3.setText(materiaActual.getCorte3().toString());
        nmeta.setText(materiaActual.getMeta().toString());
        salon.setText(materiaActual.getSalon());
        if (Config.getBloqCorte1().equals("1")) {
            Chec1.setChecked(true);
        } else {
            Chec1.setChecked(false);
        }
        if (Config.getBloqCorte2().equals("1")) {
            Chec2.setChecked(true);
        } else {
            Chec2.setChecked(false);
        }
        if (Config.getBloqCorte3().equals("1")) {
            Chec3.setChecked(true);
        } else {
            Chec3.setChecked(false);
        }
        if (Config.getBloqAnimo().equals("1")) {
            ChecAnim.setChecked(true);
        } else {
            ChecAnim.setChecked(false);
        }
        Animo.setText(Config.getTextAnimo() + "");
        creditos.setText(String.valueOf(materiaActual.getCreditos()));
        horario.setText(materiaActual.getHoras());
        codM.setText(materiaActual.getIdM());
        Respaldo = materiaActual.getMeta();//Respaldo para la meta
        diadesemana.setText(Dia);
        if (materiaActual.getNotifTime() == null) {
            NotifButon.setText(minutosNotif + " min");
        } else {
            NotifButon.setText(materiaActual.getNotifTime());
        }


    }

    @Override
    public void onClick(View v) {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);//para cerrar el teclado virtual despues de introducir algo y presionar un boton
        inputMethodManager.hideSoftInputFromWindow(BActMeta.getWindowToken(), 0);//le pasamos por parametros una vista que accione el ocultamiento del teclado

        switch (v.getId()) {
            case R.id.BActMeta:
                //Si cambia la meta se cambiaran todos los valores de los cortes
                ActualizarMeta();

                break;

            case R.id.BmasInfo:

                if (a) {
                    LytMasinfo.setVisibility(View.VISIBLE);
                    Bmasinfo.setText(getString(R.string.menosDetalles));

                    a = false;
                } else {

                    LytMasinfo.setVisibility(View.GONE);
                    Bmasinfo.setText(getString(R.string.masDetalles));

                    a = true;
                }

                break;

            case R.id.BActNotaFinal:

                ActualizarNotaFinal();

                break;


        }
    }

    private void BuscaryUbicar(Materia actual) {
        String bloque = actual.getSalon().trim();

        try {
            String Bloque = bloque.charAt(7) + "" + bloque.charAt(8);

            Intent intent = new Intent(this, Web_Descarga.class);
            intent.putExtra("bloque", Integer.parseInt(Bloque.trim()));
            intent.putExtra("para", "Ubicor");

            startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(this, "No es una ubicación válida para Ubicor", Toast.LENGTH_SHORT).show();

        }


    }


    private void ActualizarNotaFinal() {

        boolean ready;
        final Double SubR = Respaldo;
        if (Respaldo != getMeta()) {

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage(getString(R.string.MensajeMetaModif));
            dialog.setCancelable(false);
            dialog.setPositiveButton(getString(android.R.string.yes), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            ActualizarMeta();
                        }
                    }
            );
            dialog.setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                    nmeta.setText("" + SubR);
                    Respaldo = SubR;
                    ActualizarNotaFinal();

                }
            });
            dialog.show();
        }

        try {//Verificacion de campos vacios

            Cort1 = Double.parseDouble(corte1.getText() + "");
            Cort2 = Double.parseDouble(corte2.getText() + "");
            Cort3 = Double.parseDouble(corte3.getText() + "");
            ready = true;
        } catch (Exception e) {
            Cort1 = Acort1;
            Cort2 = Acort2;
            Cort3 = Acort3;
            ready = false;
            Toast.makeText(getApplicationContext(), getString(R.string.camposVoid), Toast.LENGTH_SHORT).show();
        }

        Double Resul;
        String notaOriginal;
        String textoNota;

        if ((Chec1.isChecked()) && (!Chec2.isChecked()) && (!Chec3.isChecked()))//Si el primero se modifico y los demas siguieron iguales
        {

            aux = ((getMeta() * 3) - Cort1) / 2;

            notaOriginal = String.valueOf(aux);

            textoNota = notaOriginal.length() > 4 ? notaOriginal.substring(0, 4) : notaOriginal;

            corte2.setText(textoNota);
            corte3.setText(textoNota);

            Cort2 = Double.parseDouble(corte2.getText() + "");
            Cort3 = Double.parseDouble(corte3.getText() + "");

            Resul = ((Cort1 + Cort2 + Cort3) / 3);

            notaOriginal = String.valueOf(Resul);
            textoNota = notaOriginal.length() > 6 ? notaOriginal.substring(0, 5) : notaOriginal;
            Definiva = Double.parseDouble(textoNota);
            NotaFinal.setText(textoNota);
            Acort1 = Cort1;//Como se modifico el corte1 este va a ser el siguiente anterior
            Acort2 = Cort2;//Como tambien se modificaron los demas cortes..
            Acort3 = Cort3;//actualizamos lo anteriores de los siguientes
            mensajeCortes.setText(" 2do y 3cer corte. Mínimo para la meta ");
            if ((Cort2 > 5)) {
                Recomendacion.setText("#Se recomienda sacar una nota Muy alta#");
            } else {
                Recomendacion.setText("#Muy Bien... Meta en Proceso #");
            }
            Respaldo = getMeta();
            Recom = Recomendacion.getText().toString();
            msjCortes = mensajeCortes.getText().toString();

        } else {
            if ((!Chec1.isChecked()) && (Chec2.isChecked()) && (!Chec3.isChecked())) //si el segundo se modifico y los demas siguen iguales
            {

                aux = ((getMeta() * 3) - Cort2) / 2;

                notaOriginal = String.valueOf(aux);

                textoNota = notaOriginal.length() > 4 ? notaOriginal.substring(0, 4) : notaOriginal;

                corte1.setText(textoNota);
                corte3.setText(textoNota);

                Cort1 = Double.parseDouble(corte1.getText() + "");
                Cort3 = Double.parseDouble(corte3.getText() + "");

                Resul = ((Cort1 + Cort2 + Cort3) / 3);

                notaOriginal = String.valueOf(Resul);
                textoNota = notaOriginal.length() > 6 ? notaOriginal.substring(0, 5) : notaOriginal;
                Definiva = Double.parseDouble(textoNota);
                NotaFinal.setText(textoNota);
                Acort1 = Cort1;
                Acort2 = Cort2;//Como se modifico el corte2 este va a ser el siguiente anterior
                Acort3 = Cort3;//Como tambien se modificaron los demas cortes..actualizamos lo anteriores de los siguientes
                mensajeCortes.setText(" 1er y 3cer corte. Mínimo para la meta ");
                if (Cort1 > 5) {
                    Recomendacion.setText("#Se recomienda sacar una nota Muy alta#");
                } else {
                    Recomendacion.setText("#Muy Bien... Meta en Proceso #");
                }
                Respaldo = getMeta();
                Recom = Recomendacion.getText().toString();
                msjCortes = mensajeCortes.getText().toString();

            } else {

                if ((!Chec1.isChecked()) && (!Chec2.isChecked()) && (Chec3.isChecked()))//si el tercero se modifico y los demas siguen iguales
                {

                    aux = ((getMeta() * 3) - Cort3) / 2;

                    notaOriginal = String.valueOf(aux);

                    textoNota = notaOriginal.length() > 4 ? notaOriginal.substring(0, 4) : notaOriginal;

                    corte1.setText(textoNota);
                    corte2.setText(textoNota);

                    Cort1 = Double.parseDouble(corte1.getText() + "");
                    Cort2 = Double.parseDouble(corte2.getText() + "");

                    Resul = ((Cort1 + Cort2 + Cort3) / 3);

                    notaOriginal = String.valueOf(Resul);
                    textoNota = notaOriginal.length() > 6 ? notaOriginal.substring(0, 5) : notaOriginal;
                    Definiva = Double.parseDouble(textoNota);
                    NotaFinal.setText(textoNota);
                    Acort1 = Cort1;//Como tambien se modificaron los demas cortes..actualizamos lo anteriores de los siguientes
                    Acort2 = Cort2;
                    Acort3 = Cort3;//Como se modifico el corte3 este va a ser el siguiente anterior
                    mensajeCortes.setText(" 1er y 2do corte. Mínimo para la meta ");
                    if (Cort1 > 5) {
                        Recomendacion.setText("#Se recomienda sacar una nota Muy alta#");
                    } else {
                        Recomendacion.setText("#Muy Bien... Meta en Proceso #");
                    }
                    Respaldo = getMeta();
                    corte1.setText(textoNota);
                    corte2.setText(textoNota);
                    Recom = Recomendacion.getText().toString();
                    msjCortes = mensajeCortes.getText().toString();

                } else {

                    if ((Chec1.isChecked()) && (Chec2.isChecked()) && (!Chec3.isChecked()))  //si se modificaron primero y segundo y los demas siguen iguales
                    {

                        aux = ((getMeta() * 3) - (Cort1 + Cort2));

                        notaOriginal = String.valueOf(aux);
                        textoNota = notaOriginal.length() > 4 ? notaOriginal.substring(0, 4) : notaOriginal;

                        corte3.setText(textoNota);


                        Cort3 = Double.parseDouble(corte3.getText() + "");

                        Resul = ((Cort1 + Cort2 + Cort3) / 3);

                        notaOriginal = String.valueOf(Resul);
                        textoNota = notaOriginal.length() > 6 ? notaOriginal.substring(0, 5) : notaOriginal;
                        Definiva = Double.parseDouble(textoNota);
                        NotaFinal.setText(textoNota);
                        Acort1 = Cort1;
                        Acort2 = Cort2;
                        Acort3 = Cort3;
                        mensajeCortes.setText(" 3cer corte. Mínimo para la meta ");
                        if (Cort3 > 5) {
                            Recomendacion.setText("#Se recomienda sacar una nota Muy alta#");
                        } else {
                            Recomendacion.setText("#Muy Bien... Meta en Proceso #");
                        }
                        Respaldo = getMeta();
                        Recom = Recomendacion.getText().toString();
                        msjCortes = mensajeCortes.getText().toString();

                    } else {


                        if ((!Chec1.isChecked()) && (Chec2.isChecked()) && (Chec3.isChecked()))  //si se modificaron segundo y tercero y los demas siguen iguales
                        {
                            aux = ((getMeta() * 3) - (Cort2 + Cort3));

                            notaOriginal = String.valueOf(aux);
                            textoNota = notaOriginal.length() > 4 ? notaOriginal.substring(0, 4) : notaOriginal;

                            corte1.setText(textoNota);


                            Cort1 = Double.parseDouble(corte1.getText() + "");

                            Resul = ((Cort1 + Cort2 + Cort3) / 3);

                            notaOriginal = String.valueOf(Resul);
                            textoNota = notaOriginal.length() > 6 ? notaOriginal.substring(0, 5) : notaOriginal;
                            Definiva = Double.parseDouble(textoNota);
                            NotaFinal.setText(textoNota);
                            Acort1 = Cort1;
                            Acort2 = Cort2;
                            Acort3 = Cort3;
                            mensajeCortes.setText(" 1er corte. Mínimo para la meta ");
                            if (Cort1 > 5) {
                                Recomendacion.setText("#Se recomienda sacar una nota Muy alta#");
                            } else {
                                Recomendacion.setText("#Muy Bien... Meta en Proceso #");
                            }
                            Respaldo = getMeta();
                            Recom = Recomendacion.getText().toString();
                            msjCortes = mensajeCortes.getText().toString();

                        } else {

                            if ((Chec1.isChecked()) && (!Chec2.isChecked()) && (Chec3.isChecked()))  //si se modificaron primero y tercero y los demas siguen iguales
                            {
                                aux = ((getMeta() * 3) - (Cort1 + Cort3));

                                notaOriginal = String.valueOf(aux);
                                textoNota = notaOriginal.length() > 4 ? notaOriginal.substring(0, 4) : notaOriginal;

                                corte2.setText(textoNota);

                                Cort2 = Double.parseDouble(corte2.getText() + "");

                                Resul = ((Cort1 + Cort2 + Cort3) / 3);

                                notaOriginal = String.valueOf(Resul);
                                textoNota = notaOriginal.length() > 6 ? notaOriginal.substring(0, 5) : notaOriginal;
                                Definiva = Double.parseDouble(textoNota);
                                NotaFinal.setText(textoNota);
                                Acort1 = Cort1;
                                Acort2 = Cort2;
                                Acort3 = Cort3;
                                mensajeCortes.setText(" 2do corte. Mínimo para la meta ");
                                if (Cort2 > 5) {
                                    Recomendacion.setText("#Se recomienda sacar una nota Muy alta#");
                                } else {
                                    Recomendacion.setText("#Muy Bien... Meta en Proceso #");
                                }
                                Respaldo = getMeta();
                                Recom = Recomendacion.getText().toString();
                                msjCortes = mensajeCortes.getText().toString();

                            } else {


                                if ((Chec1.isChecked()) && (Chec2.isChecked()) && (Chec3.isChecked()))  //si se modificaron los tres
                                {

                                    Resul = ((Cort1 + Cort2 + Cort3) / 3);

                                    notaOriginal = String.valueOf(Resul);
                                    textoNota = notaOriginal.length() > 6 ? notaOriginal.substring(0, 5) : notaOriginal;
                                    Definiva = Double.parseDouble(textoNota);
                                    NotaFinal.setText(textoNota);
                                    Acort1 = Cort1;
                                    Acort2 = Cort2;
                                    Acort3 = Cort3;
                                    mensajeCortes.setText("# Todos los cortes se han actualizado #");
                                    if (Resul >= getMeta()) {
                                        Recomendacion.setText("#Felicidades...Meta Superada!#");
                                    } else {

                                        if (Resul < getMeta() && Resul >= 3) {
                                            Recomendacion.setText("#Felicidades...Pasaste!#");
                                        } else {
                                            if (Resul < 3)
                                                Recomendacion.setText("#Mejoraremos para la proxima#");
                                        }

                                    }
                                    Respaldo = getMeta();
                                    Recom = Recomendacion.getText().toString();
                                    msjCortes = mensajeCortes.getText().toString();

                                } else {

                                    if ((!Chec1.isChecked()) && (!Chec2.isChecked()) && (!Chec3.isChecked())) {

                                        corte1.setText(String.valueOf(getMeta()));
                                        corte2.setText(String.valueOf(getMeta()));
                                        corte3.setText(String.valueOf(getMeta()));

                                        float Resultado = (float) (getMeta());

                                        NotaFinal.setText(String.valueOf(Resultado));
                                        Definiva = Double.parseDouble(Resultado + "");
                                        Respaldo = getMeta();
                                        Recomendacion.setText("# Muy bien... Meta en proceso #");
                                        Acort1 = Respaldo;//los anteriores cortes se actualizan cuando actualizas la meta
                                        Acort2 = Respaldo;
                                        Acort3 = Respaldo;
                                        nmeta.setText(Respaldo + "");
                                        mensajeCortes.setText(" 1er, 2do y 3cer corte. Mínimo para la meta ");

                                        Toast.makeText(getApplicationContext(), "No ha seleccionado ningun corte", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getApplicationContext(), "Cortes actualizados con su meta", Toast.LENGTH_SHORT).show();

                                        Recom = Recomendacion.getText().toString();
                                        msjCortes = mensajeCortes.getText().toString();
                                    }

                                }

                            }

                        }
                    }

                }

            }


        }
        if (!vac) {
            if (ready) {
                Guardar_datos();
            }

        }

    }

    private void ActualizarMeta() {
        if (getMeta() > 5 || getMeta() < 3) {
            Toast.makeText(getApplicationContext(), getString(R.string.rangoMeta), Toast.LENGTH_LONG).show();
            nmeta.setText("" + Respaldo);
        } else {

            if (Respaldo != getMeta()) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle(getString(R.string.updateValores));
                dialog.setMessage(getString(R.string.avisodeCortes));
                dialog.setCancelable(false);
                dialog.setPositiveButton(getString(android.R.string.yes), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (!Chec1.isChecked()) {
                                    corte1.setText(String.valueOf(getMeta()));
                                    Acort1 = Respaldo;
                                }
                                if (!Chec2.isChecked()) {
                                    corte2.setText(String.valueOf(getMeta()));
                                    Acort2 = Respaldo;
                                }
                                if (!Chec3.isChecked()) {
                                    corte3.setText(String.valueOf(getMeta()));
                                    Acort3 = Respaldo;
                                }

                                Respaldo = getMeta();

                                nmeta.setText(Respaldo + "");
                                ActualizarNotaFinal();

                            }
                        }
                );
                dialog.setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                        nmeta.setText("" + Respaldo);
                        Toast.makeText(getApplicationContext(), (getString(R.string.sinCambios)), Toast.LENGTH_SHORT).show();
                        //ActualizarNotaFinal();

                    }
                });
                dialog.show();


            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.mismaMeta), Toast.LENGTH_SHORT).show();
            }

        }
    }


    //:::::::::::::::::::::::::::::::::::::::::

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        if (!vac) {
            inflater.inflate(R.menu.context_menu, menu);
        }


        return true;

    }

    void buscarEliminar(int dia, String idM) {
        int conf = 0;
        switch (dia) {
            case 0:
                conf = HelperLunes.eliminarMateria(dia, idM);
                break;
            case 1:
                conf = HelperMartes.eliminarMateria(dia, idM);
                break;
            case 2:
                conf = HelperMiercoles.eliminarMateria(dia, idM);
                break;
            case 3:
                conf = HelperJueves.eliminarMateria(dia, idM);
                break;
            case 4:
                conf = HelperViernes.eliminarMateria(dia, idM);
                break;
            case 5:
                conf = HelperSabado.eliminarMateria(dia, idM);
                break;

            default:
        }

        if (conf != -1) {
            Toast.makeText(MainActivity.this, getString(R.string.eliminada), Toast.LENGTH_LONG).show();
        }

    }

    void abrirDias() {
        HelperLunes.abrir();
        HelperMartes.abrir();
        HelperMiercoles.abrir();
        HelperJueves.abrir();
        HelperViernes.abrir();
        HelperSabado.abrir();
    }

    void cerrarDias() {
        HelperLunes.cerrar();
        HelperMartes.cerrar();
        HelperMiercoles.cerrar();
        HelperJueves.cerrar();
        HelperViernes.cerrar();
        HelperSabado.cerrar();
    }


    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                finish();
                return true;

            case R.id.submenuDel:

                abrirDias();
                buscarEliminar(dia, codM.getText().toString());
                cerrarDias();

                Intent i = new Intent(MainActivity.this, MainActivityNav.class);
                startActivity(i);

                finish();
                return true;

            case R.id.edits:

                Intent intent = new Intent(MainActivity.this, EditarMateria.class);
                intent.putExtra("Texto", "Editar Materia");
                intent.putExtra("Dia", Integer.parseInt("" + dia));
                startActivity(intent);
                return true;

            case R.id.ubicor:
                BuscaryUbicar(materiaActual);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Para cuando se mentiene presionado un item de algo
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.context_menu, menu);

    }


    @Override

    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.about:


                return true;

            case R.id.quit:

                finish();

                return true;


            default:

                return super.onOptionsItemSelected(item);
        }

    }

    //:::::::::::::::::::::::::::::::::::::::::

}

