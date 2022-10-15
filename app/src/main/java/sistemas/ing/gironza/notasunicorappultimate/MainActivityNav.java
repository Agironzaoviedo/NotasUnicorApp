package sistemas.ing.gironza.notasunicorappultimate;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import BasedeDatos.SQLite_OpenHelper;

public class MainActivityNav extends AppCompatActivity
        implements VistaGeneral.OnFragmentInteractionListener, FragmentVistaGeneral.OnFragmentInteractionListener,
        View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    ListView Lunes, Martes, Miercoles, Jueves, Viernes, Sabado;
    Button Blunes, BMartes, BMiercoles, BJueves, BViernes, BSabado, BtnCalendar, BMasNotas, BEventos;
    TextView Nombre, Lugar, Hora, ProximaClase;
    FloatingActionButton BFABpp, BFABNotif;
    TimePicker TPhora;
    DatePicker DPdia;
    int hora, dia, min;
    Materia next;
    Boolean Notif, Actuali, vistas;
    ImageView sigi;
    private static int REQUEST_CODE = 1;
    String VersionN;
    static Configuraciones Config;
    NotificationCompat.Builder nBuilder;
    View includeVista;
    VistaGeneral FragmentVistaPrincipal;
    FragmentVistaGeneral Fragment_VistaGeneral;

    public static ListaEnlazadaMat ListaLunes = new ListaEnlazadaMat();
    public static ListaEnlazadaMat ListaMartes = new ListaEnlazadaMat();
    public static ListaEnlazadaMat ListaMiercoles = new ListaEnlazadaMat();
    public static ListaEnlazadaMat ListaJueves = new ListaEnlazadaMat();
    public static ListaEnlazadaMat ListaViernes = new ListaEnlazadaMat();
    public static ListaEnlazadaMat ListaSabado = new ListaEnlazadaMat();
    public static ListaEnlazadaMat ListaDiaActual, ListaDiaPresente = new ListaEnlazadaMat();

    public static SQLite_OpenHelper HelperLunes, HelperMartes, HelperMiercoles, HelperJueves, HelperViernes, HelperSabado, HelperConf;

    DatabaseReference DatabaseNUAP = FirebaseDatabase.getInstance().getReference();
    DatabaseReference RootChild = DatabaseNUAP.child("Texto");
    Toolbar toolbar;


    Bundle savedInstanceState2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstanceState2 = savedInstanceState;
        setContentView(R.layout.activity_main_nav);
        inicializar();

        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        crearAccesoDirectoAlInstalar(MainActivityNav.this);

        //FragmentPasoMain FramePasoM=new FragmentPasoMain();


        sigi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, REQUEST_CODE);


        HelperLunes = new SQLite_OpenHelper(this, "BasedeDatos0", null, 1, 0);
        HelperMartes = new SQLite_OpenHelper(this, "BasedeDatos01", null, 1, 1);
        HelperMiercoles = new SQLite_OpenHelper(this, "BasedeDatos2", null, 1, 2);
        HelperJueves = new SQLite_OpenHelper(this, "BasedeDatos3", null, 1, 3);
        HelperViernes = new SQLite_OpenHelper(this, "BasedeDatos4", null, 1, 4);
        HelperSabado = new SQLite_OpenHelper(this, "BasedeDatos5", null, 1, 5);
        HelperConf = new SQLite_OpenHelper(this, "BasedeDatos11", null, 1, 6);

        cargarListasEnlazadas();

        verificarActualizacion(this);
        MensajeAutor(this);
        nextClass();


        BEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten2 = new Intent(MainActivityNav.this, Web_Descarga.class);
                inten2.putExtra("para", "Eventos");
                startActivity(inten2);

            }
        });

        BtnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrearToast("Estamos trabajando en esta herramienta ;)");
                Intent intenCal = new Intent(getApplicationContext(), Calendario.class);
                startActivity(intenCal);

            }
        });

        ProximaClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextClass();
            }
        });

        BMasNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(MainActivityNav.this, MainActivity.class);
                intent2.putExtra("vacio", true);
                startActivity(intent2);
            }
        });


        BFABpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hi, You are Win!!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(MainActivityNav.this, EditarMateria.class);
                intent.putExtra("Texto", "Agregar Materia");
                startActivity(intent);
            }
        });

        BFABNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Config.NotificacionMat.equals("1")) {
                    BFABNotif.setImageResource(R.drawable.bell_off);
                    Config.setNotificacionMat("0");
                    ActualizarConfiguracion();
                    Toast.makeText(getApplicationContext(), getString(R.string.TextDesac_Notif), Toast.LENGTH_SHORT).show();
                } else {
                    BFABNotif.setImageResource(R.drawable.bell);
                    Config.setNotificacionMat("1");
                    ActualizarConfiguracion();
                    Toast.makeText(getApplicationContext(), getString(R.string.TextAct_Notif), Toast.LENGTH_SHORT).show();
                }
            }
        });

        BFABpp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        BFABpp.setVisibility(View.INVISIBLE);
                        BFABNotif.setVisibility(View.INVISIBLE);

                    }
                }, 500);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        BFABpp.setVisibility(View.VISIBLE);
                        BFABNotif.setVisibility(View.VISIBLE);

                    }
                }, 7000);

                return true;
            }
        });
        BFABNotif.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        BFABpp.setVisibility(View.INVISIBLE);
                        BFABNotif.setVisibility(View.INVISIBLE);

                    }
                }, 500);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        BFABpp.setVisibility(View.VISIBLE);


                    }
                }, 7000);


                return true;
            }
        });
        try {

            if (!Config.DatosUltimaAct.equals("2")) {
                String t = "-Mejoras en notificaciones (aún en proceso)\n\n " +
                        "-Sincronización del horario mejorada con PowerCampus\n\n " +
//                        "-Localizar en Ubicor tu salón de clases en el mapa de la Universidad (toque sostenido en la materia)\n\n " +
                        "-Visualizar los eventos culturales y actividades próximas de la Universidad\n\n " +
                        "-Ahora puedes compartir tu horario por materia, por día y completo (toque sostenido en la materia)\n\n " +
                        "-Corrección de errores\n ";
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("¡Disfruta lo nuevo de Notas UnicorApp!");
                dialog.setMessage(t);
                dialog.setCancelable(false);
                dialog.setPositiveButton("Vale", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivityNav.this, "Todo bien", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                dialog.show();
                Config.DatosUltimaAct = ("2");
                ActualizarConfiguracion();

            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        getSupportFragmentManager().beginTransaction().add(R.id.ContenedorFragments, FragmentVistaPrincipal).commit();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startService(new Intent(this, BackgroundService.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        startService(new Intent(this, BackgroundService.class));
    }

    public void ActualizarConfiguracion() {

        abrirDias();
        HelperConf.editarDatosConfiguracion(Config.NotificacionMat, Config.TiempoNotf, Config.BloqCorte1,
                Config.BloqCorte2, Config.BloqCorte3, Config.BloqAnimo, Config.TextAnimo, Config.HojaApuntes,
                Config.DatosUltimaAct, 6, Config.id);

        cerrarDias();
    }


    public void crearAccesoDirectoAlInstalar(Activity actividad) {
        SharedPreferences preferenciasapp;
        boolean aplicacioninstalada = Boolean.FALSE;

        /*
         * Compruebo si es la primera vez que se ejecuta la alicación,
         * entonces es cuando creo el acceso directo
         */
        preferenciasapp = PreferenceManager.getDefaultSharedPreferences(actividad);
        aplicacioninstalada = preferenciasapp.getBoolean("aplicacioninstalada", Boolean.FALSE);

        if (!aplicacioninstalada) {
            /*
             * Código creación acceso directo
             */
            Intent shortcutIntent = new Intent(actividad, SplashScreen.class);
            shortcutIntent.setAction(Intent.ACTION_MAIN);
            Intent intent = new Intent();
            intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getResources().getString(R.string.app_name));
            intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(actividad, R.mipmap.ic_launcher));
            intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            actividad.sendBroadcast(intent);
            Toast.makeText(this, "Acceso directo creado", Toast.LENGTH_SHORT).show();

            /*
             * Indico que ya se ha creado el acceso directo para que no se vuelva a crear mas
             */
            SharedPreferences.Editor editor = preferenciasapp.edit();
            editor.putBoolean("aplicacioninstalada", true);
            editor.apply();
        }
    }


    public void verificarActualizacion(final Context context) {

        final String[] version = {""};

        final DatabaseReference Version = FirebaseDatabase.getInstance().getReference();
        DatabaseReference RootVersion = Version.child("Version");

        RootVersion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                version[0] = dataSnapshot.getValue().toString();

                if (version[0].equals(getString(R.string.Version))) {
                    toolbar.setTitle(getString(R.string.app_name));
                } else {
                    toolbar.setTitle(getString(R.string.app_name) + "*");
                    Toast.makeText(MainActivityNav.this, "Actualización Disponible", Toast.LENGTH_SHORT).show();
                    VersionN = version[0];
                    Actuali = true;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (Actuali) {

                                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                                dialog.setTitle("¿Actualizar?");
                                dialog.setMessage("Existe una actualización más reciente de Notas UnicorApp.\n" + VersionN);
                                dialog.setCancelable(true);
                                dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                Uri uriURLApp = Uri.parse("https://drive.google.com/file/d/1ZtiDr7tGogY-zuMoxXsjmt2DNyU-GsoQ/view");
                                                Intent IntentNav = new Intent(Intent.ACTION_VIEW, uriURLApp);
                                                startActivity(IntentNav);
                                                Toast.makeText(MainActivityNav.this, "Descargando Actualización", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                );
                                dialog.setNegativeButton("Más tarde", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();

                                    }
                                });
                                if (!isFinishing()) {
                                    dialog.show();
                                }

                            }

                        }
                    }, 8000);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void MensajeAutor(final Context context) {

        final String[] Mensj = {""};

        final DatabaseReference Mensaje = FirebaseDatabase.getInstance().getReference();
        DatabaseReference RootMensaje = Mensaje.child("Texto");

        RootMensaje.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Mensj[0] = dataSnapshot.getValue().toString();

                if (!Mensj[0].equals("Notas UnicorApp Beta*")) {
                    Toast.makeText(context, "Mensaje importante de Notas UnicorApp", Toast.LENGTH_LONG).show();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("¡Mensaje importante!");
                    dialog.setMessage("" + Mensj[0]);
                    dialog.setCancelable(true);
                    dialog.setPositiveButton("Vale", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Toast.makeText(MainActivityNav.this, "Gracias por su atención", Toast.LENGTH_SHORT).show();
                                }
                            }
                    );

                    if (!isFinishing()) {
                        dialog.show();
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);


        switch (id) {
            case R.id.sinc:

                Intent inte = new Intent(MainActivityNav.this, DownloadHorario.class);
                inte.putExtra("Sinc", true);
                startActivity(inte);

                break;

            case R.id.UbicorM:

                Intent inteU = new Intent(MainActivityNav.this, Web_Descarga.class);
                inteU.putExtra("para", "mapa");
                startActivity(inteU);

                break;

            case R.id.agg:

                Intent intent = new Intent(MainActivityNav.this, EditarMateria.class);
                startActivity(intent);

                break;

            case R.id.Calcular:

                Intent intent2 = new Intent(MainActivityNav.this, MainActivity.class);
                intent2.putExtra("vacio", true);
                startActivity(intent2);

                break;

            case R.id.About:

                Intent inten = new Intent(MainActivityNav.this, AboutActivity.class);
                startActivity(inten);
                break;

            case R.id.Descargas:
                Intent inten2 = new Intent(MainActivityNav.this, Web_Descarga.class);
                inten2.putExtra("para", "Calificacion");
                startActivity(inten2);
                break;

            case R.id.nav_share:
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hola, Descarga Notas UnicorApp Aquí: https://drive.google.com/open?id=1ZtiDr7tGogY-zuMoxXsjmt2DNyU-GsoQ \n Recuerda activar en ajustes del celular la opción; permitir orígenes desconocidos");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Compartir Notas UnicorApp con"));

                break;

            case R.id.nav_send:

                Uri uriURLApp = Uri.parse("https://wa.me/573206131883?text=Hola,%20estoy%20usando%20tu%20app;%20Notas%20UnicorApp%20y%20te%20quería%20decir...");
                Intent IntentNav = new Intent(Intent.ACTION_VIEW, uriURLApp);
                startActivity(IntentNav);
                break;

            case R.id.web:

                Uri uri = Uri.parse("https://sites.google.com/view/notasunicorapp/");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
                return true;

            default:

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    void inicializar() {

        Lunes = findViewById(R.id.LVlunes);
        Martes = findViewById(R.id.LVmartes);
        Miercoles = findViewById(R.id.LVmiercoles);
        Jueves = findViewById(R.id.LVjueves);
        Viernes = findViewById(R.id.LVviernes);
        Sabado = findViewById(R.id.LVsabado);
        BMartes = findViewById(R.id.Bmartes);
        BMiercoles = findViewById(R.id.Bmiercoles);
        BJueves = findViewById(R.id.Bjueves);
        BViernes = findViewById(R.id.Bviernes);
        BSabado = findViewById(R.id.Bsabado);
        BEventos = findViewById(R.id.BEventos);
        BtnCalendar = findViewById(R.id.BCalendar);
        BMasNotas = findViewById(R.id.BMasNotas);
        BFABpp = findViewById(R.id.BFABpp);
        BFABNotif = findViewById(R.id.BFABNotif);
        Blunes = findViewById(R.id.Blunes);
        DPdia = findViewById(R.id.DPdia);
        TPhora = findViewById(R.id.TPhora);
        Nombre = findViewById(R.id.NNom);
        Lugar = findViewById(R.id.Nlugar);
        Hora = findViewById(R.id.Nhora);
        ProximaClase = findViewById(R.id.ProximaClase);
        next = new Materia();
        Notif = true;
        sigi = findViewById(R.id.sigi);
        toolbar = findViewById(R.id.toolbar);
        Actuali = false;
        vistas = true;
        FragmentVistaPrincipal = new VistaGeneral();
        Fragment_VistaGeneral = new FragmentVistaGeneral();

    }

    void abrirDias() {
        HelperLunes.abrir();
        HelperMartes.abrir();
        HelperMiercoles.abrir();
        HelperJueves.abrir();
        HelperViernes.abrir();
        HelperSabado.abrir();
        HelperConf.abrir();
    }

    void cerrarDias() {
        HelperLunes.cerrar();
        HelperMartes.cerrar();
        HelperMiercoles.cerrar();
        HelperJueves.cerrar();
        HelperViernes.cerrar();
        HelperSabado.cerrar();
        HelperConf.cerrar();
    }

    public void cargarListasEnlazadas() {

        abrirDias();

        if (HelperLunes == null) {

        } else {
            Cursor c = HelperLunes.listarMaterias(0);

            if (c != null) {

                try {
                    ListaLunes = new ListaEnlazadaMat();
                    HelperLunes.llenarLista(ListaLunes, 0);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        if (HelperMartes == null) {

        } else {
            Cursor c = HelperMartes.listarMaterias(1);

            if (c != null) {

                try {
                    ListaMartes = new ListaEnlazadaMat();
                    HelperMartes.llenarLista(ListaMartes, 1);
                } catch (Exception e) {
                    Toast.makeText(this, "se callo!!!" + e.getMessage(), Toast.LENGTH_LONG).show();
                    Toast.makeText(this, "se callo!!!" + e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }

        }
        if (HelperMiercoles == null) {

        } else {
            Cursor c = HelperMiercoles.listarMaterias(2);

            if (c != null) {

                try {
                    ListaMiercoles = new ListaEnlazadaMat();
                    HelperMiercoles.llenarLista(ListaMiercoles, 2);
                } catch (Exception e) {

                }
            }

        }
        if (HelperJueves == null) {

        } else {
            Cursor c = HelperJueves.listarMaterias(3);

            if (c != null) {

                try {
                    ListaJueves = new ListaEnlazadaMat();
                    HelperJueves.llenarLista(ListaJueves, 3);
                } catch (Exception e) {

                }
            }

        }
        if (HelperViernes == null) {

        } else {
            Cursor c = HelperViernes.listarMaterias(4);

            if (c != null) {

                try {
                    ListaViernes = new ListaEnlazadaMat();
                    HelperViernes.llenarLista(ListaViernes, 4);
                } catch (Exception e) {

                }
            }

        }
        if (HelperSabado == null) {

        } else {
            Cursor c = HelperSabado.listarMaterias(5);

            if (c != null) {

                try {
                    ListaSabado = new ListaEnlazadaMat();
                    HelperSabado.llenarLista(ListaSabado, 5);
                } catch (Exception e) {

                }
            }

        }


        if (HelperConf == null) {


        } else {
            Cursor c = HelperConf.listarConfiguracion(6, this);

            if (c != null) {

                try {
                    Config = HelperConf.LlenarConfiguraciones(6);

                } catch (Exception e) {

                }
            }

        }


        cerrarDias();
    }


    void ListarEnPantalla() {

        inflarListas(Lunes, ListaLunes);
        inflarListas(Martes, ListaMartes);
        inflarListas(Miercoles, ListaMiercoles);
        inflarListas(Jueves, ListaJueves);
        inflarListas(Viernes, ListaViernes);
        inflarListas(Sabado, ListaSabado);

    }

    void inflarListas(ListView Dia, ListaEnlazadaMat Lista) {

        if (Lista.CantMaterias() > 0) {
            Materia MateriasDia[] = Lista.VectorMateriasPrincipal();

            final materiasAdapter Adapter = new materiasAdapter(this, R.layout.listview_item_row, MateriasDia);

            //View header = (View) getLayoutInflater().inflate(R.layout.listview_item_header, null);
            //Dia.addHeaderView(header);
            Dia.setAdapter(Adapter);
        } else {

        }
    }


    void OnclicDias(View lista, View View2) {

        Intent intent;
        TextView idM = View2.findViewById(R.id.codMPP);
        intent = new Intent(MainActivityNav.this, MainActivity.class);
        intent.putExtra("idM", idM.getText().toString());

        switch (lista.getId()) {

            case R.id.LVlunes:
                ListaDiaActual = ListaLunes;
                intent.putExtra("dia", 0);
                startActivity(intent);
                break;

            case R.id.LVmartes:
                ListaDiaActual = ListaMartes;
                intent.putExtra("dia", 1);
                startActivity(intent);
                break;

            case R.id.LVmiercoles:
                ListaDiaActual = ListaMiercoles;
                intent.putExtra("dia", 2);
                startActivity(intent);

                break;
            case R.id.LVjueves:
                ListaDiaActual = ListaJueves;
                intent.putExtra("dia", 3);
                startActivity(intent);

                break;
            case R.id.LVviernes:
                ListaDiaActual = ListaViernes;
                intent.putExtra("dia", 4);
                startActivity(intent);

                break;
            case R.id.LVsabado:
                ListaDiaActual = ListaSabado;
                intent.putExtra("dia", 5);
                startActivity(intent);

                break;

            default:
                Toast.makeText(this, "Nada de nada ", Toast.LENGTH_SHORT).show();

        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_main, menu);

        return true;

    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.sinc:

                Intent inte = new Intent(MainActivityNav.this, DownloadHorario.class);
                inte.putExtra("Sinc", true);
                startActivity(inte);

                return true;


            case R.id.Vgeneral:
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (vistas) {

                    vistas = false;
                    transaction.replace(R.id.ContenedorFragments, Fragment_VistaGeneral).commit();
                } else {
                    transaction.replace(R.id.ContenedorFragments, FragmentVistaPrincipal).commit();
                    vistas = true;
                }
                return true;

            case R.id.sub:
                finish();
                return true;

            case R.id.CompHorario:
                CompartirMateria();
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void CompartirMateria() {

        String TextoCompartir = CargarTodosDias();
        String firma = "\nBy Notas UnicorApp" +
                "\nConsigue la App aquí: https://drive.google.com/open?id=1ZtiDr7tGogY-zuMoxXsjmt2DNyU-GsoQ";

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, TextoCompartir + firma);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Compartir horario con"));

    }


    private String CargarTodosDias() {

        String todosDias = "";
        Nodo tempx = ListaLunes.getCabeza();
        if (tempx != null) {
            todosDias += "\n\n" + getResources().getString(R.string.dia0) + "\n";
        }
        while (tempx != null) {
            todosDias += tempx.getDatoMateria().toString();
            tempx = tempx.getSiguiente();
        }

        tempx = ListaMartes.getCabeza();
        if (tempx != null) {
            todosDias += "\n\n" + getResources().getString(R.string.dia1) + "\n";
        }
        while (tempx != null) {
            todosDias += tempx.getDatoMateria().toString();
            tempx = tempx.getSiguiente();
        }
        tempx = ListaMiercoles.getCabeza();
        if (tempx != null) {
            todosDias += "\n\n" + getResources().getString(R.string.dia2) + "\n";
        }
        while (tempx != null) {
            todosDias += tempx.getDatoMateria().toString();
            tempx = tempx.getSiguiente();
        }
        tempx = ListaJueves.getCabeza();
        if (tempx != null) {
            todosDias += "\n\n" + getResources().getString(R.string.dia3) + "\n";
        }
        while (tempx != null) {
            todosDias += tempx.getDatoMateria().toString();
            tempx = tempx.getSiguiente();
        }
        tempx = ListaViernes.getCabeza();
        if (tempx != null) {
            todosDias += "\n\n" + getResources().getString(R.string.dia4) + "\n";
        }
        while (tempx != null) {
            todosDias += tempx.getDatoMateria().toString();
            tempx = tempx.getSiguiente();
        }
        tempx = ListaSabado.getCabeza();
        if (tempx != null) {
            todosDias += "\n\n" + getResources().getString(R.string.dia5) + "\n";
        }
        while (tempx != null) {
            todosDias += tempx.getDatoMateria().toString();
            tempx = tempx.getSiguiente();
        }
        return todosDias;
    }


    private void CrearToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.submenu:

                finish();

                return true;

            case R.id.edits:

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

    }


    @Override
    public void onClick(View view) {
        agregarEnDia(view);
    }

    void agregarEnDia(View dia) {

        Intent intent = new Intent(MainActivityNav.this, EditarMateria.class);
        intent.putExtra("Texto", "Agregar en Día");

        switch (dia.getId()) {
            case R.id.Blunes:
                intent.putExtra("Dia", Integer.parseInt("" + 0));
                break;
            case R.id.Bmartes:
                intent.putExtra("Dia", Integer.parseInt("" + 1));
                break;
            case R.id.Bmiercoles:
                intent.putExtra("Dia", Integer.parseInt("" + 2));
                break;
            case R.id.Bjueves:
                intent.putExtra("Dia", Integer.parseInt("" + 3));
                break;
            case R.id.Bviernes:
                intent.putExtra("Dia", Integer.parseInt("" + 4));
                break;
            case R.id.Bsabado:
                intent.putExtra("Dia", Integer.parseInt("" + 5));
                break;
            default:
        }
        startActivity(intent);
    }


    public void obtenerDiayHora() {
        Calendar Cal = Calendar.getInstance();
        TPhora = findViewById(R.id.TPhora);
        DPdia = findViewById(R.id.DPdia);

        hora = TPhora.getCurrentHour();
        min = TPhora.getCurrentMinute();
        dia = Cal.getTime().getDay();
        next = new Materia();
    }


    public void nextClass() {
        obtenerDiayHora();

        switch (dia) {
            case 0:

                break;
            case 1:
                next = ListaLunes.BuscarHora(hora);
                ListaDiaPresente = ListaLunes;

                break;
            case 2:
                next = ListaMartes.BuscarHora(hora);
                ListaDiaPresente = ListaMartes;

                break;
            case 3:
                next = ListaMiercoles.BuscarHora(hora);
                ListaDiaPresente = ListaMiercoles;
                break;
            case 4:
                next = ListaJueves.BuscarHora(hora);
                ListaDiaPresente = ListaJueves;

                break;
            case 5:
                next = ListaViernes.BuscarHora(hora);
                ListaDiaPresente = ListaViernes;

                break;
            case 6:
                next = ListaSabado.BuscarHora(hora);
                ListaDiaPresente = ListaSabado;

                break;

        }
        if (!next.getNombre().equals("")) {
            Nombre.setText(next.getNombre());
            Nombre.setSelected(true);
            Nombre.setMarqueeRepeatLimit(1);
            Lugar.setText(next.getSalon());
            Lugar.setSelected(true);
            Lugar.setMarqueeRepeatLimit(1);
            Hora.setVisibility(View.VISIBLE);
            Hora.setText(next.getHoras());
            CrearToast(getString(R.string.nextClass) + " " + next.getNombre());

            sigi.setImageResource(R.drawable.chevron_right_circle_outline2);
        } else {
            Toast t = Toast.makeText(this, getString(R.string.nohayproxima), Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER, 0, 0);
            t.show();
            Hora.setVisibility(View.INVISIBLE);
            sigi.setImageResource(R.drawable.chevron_right_circle_outline);
            Nombre.setText(getString(R.string.noMasClases));
            Nombre.setMarqueeRepeatLimit(1);
            Nombre.setSelected(true);
            Lugar.setText("     ( ' - ')");
            Hora.setText("");
        }

        try {
            if (Config.NotificacionMat.equals("1")) {
                BFABNotif.setImageResource(R.drawable.bell);
            } else {
                BFABNotif.setImageResource(R.drawable.bell_off);
            }
        } catch (Exception e) {


            HelperConf.insertarConfiguraciones("1", "15", "0",
                    "0", "0", "0", "", "", "0", 6);

        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {


    }
}
