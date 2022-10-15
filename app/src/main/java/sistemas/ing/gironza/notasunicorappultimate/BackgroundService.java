package sistemas.ing.gironza.notasunicorappultimate;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import BasedeDatos.SQLite_OpenHelper;

import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.ListaDiaPresente;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.ListaJueves;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.ListaLunes;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.ListaMartes;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.ListaMiercoles;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.ListaSabado;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.ListaViernes;


/* Add declaration of this service into the AndroidManifest.xml inside application tag*/

public class BackgroundService extends Service {

    static SQLite_OpenHelper HelperLunes,HelperMartes,HelperMiercoles,HelperJueves,HelperViernes,HelperSabado,HelperConf;
    private static final String TAG = "BackgroundService";
    private int hora,dia,min,seg,cont=0;
    private Materia next;
    private boolean am=false,MostrarActua=true;
    NotificationCompat.Builder nBuilder;
    String VersN;
    private Configuraciones Config;


    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "onBind()" );
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("NUAPP", "Notas UnicorApp", importance);
            channel.setDescription("Canal para la app Notas UnicorApp ");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }


    @Override
    public void onCreate() {
        super.onCreate();

        inicilizar();
        if(MostrarActua){
            verificarActualizacion(getApplication().getApplicationContext());
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
/*
                if(hora>=22){
                    am=true;
                    if(cont>27){
                        am=false;
                        inicilizar();
                    }else{
                        cont++;
                    }
                }
*/
                if(hora==22){
                    am=true;

                }else{
                    if(hora==5){
                        am=false;
                        inicilizar();
                    }

                }
                if(!am && Config.NotificacionMat.equals("1")){

                    GenerarNotificaciones();
                }

                onCreate();

            }
        },8000);

        //480000
    }

    public void verificarActualizacion(final Context context) {

        final String[] version = {""};


        final DatabaseReference Version= FirebaseDatabase.getInstance().getReference();
        DatabaseReference RootVersion=Version.child("Version");

        RootVersion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                version[0] =dataSnapshot.getValue().toString();
                VersN=version[0];
                if(!version[0].equals(getString(R.string.Version))){

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent inten =new Intent(context.getApplicationContext(), Web_Descarga.class);
                            inten.putExtra("NotActualizacion",true);
                            PendingIntent Pintent=
                                    PendingIntent.getActivity(context.getApplicationContext(),0,inten,PendingIntent.FLAG_UPDATE_CURRENT);

                            Intent inten2 =new Intent(context.getApplicationContext(), MainActivityNav.class);
                            PendingIntent Pintent2=
                                    PendingIntent.getActivity(context.getApplicationContext(),0,inten2,PendingIntent.FLAG_UPDATE_CURRENT);

                            Uri uriSonido = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


                            nBuilder= new NotificationCompat.Builder(context,"NUAPP")
                                    .setSmallIcon(R.drawable.notificacionnuap)
                                    .setLargeIcon(((BitmapDrawable)getResources().getDrawable(R.drawable.bell)).getBitmap())
                                    .setColor(context.getResources().getColor(R.color.colorVerdeApp))
                                    .setSubText(("  ( ' - ')"))
                                    .setBadgeIconType(R.drawable.notificacionnuap)
                                    .setContentTitle("¡Última Actualización!")
                                    .setVibrate(new long[] { 1000, 1000, 3300, 1000, 1000 })
                                    .setSound(uriSonido)
                                    .setPriority(Notification.PRIORITY_HIGH)
                                    .setLights(Color.GREEN, 1, 0)
                                    .setTicker("¡Última Actualización!")
                                    .setAutoCancel(true)
                                    .setContentText("Disfruta su nueva "+VersN+" ✓");
                            nBuilder.addAction(R.drawable.descarga, "Actualizar ahora",Pintent );


                            nBuilder.setContentIntent(Pintent2);


                            NotificationManager NotiManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                            NotiManager.notify(2,nBuilder.build());
                            MostrarActua=false;

                        }
                    },8000);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicilizar() {
        obtenerDiayHora();
        createNotificationChannel();
        HelperLunes= new SQLite_OpenHelper(this,"BasedeDatos0",null,1,0);
        HelperMartes= new SQLite_OpenHelper(this,"BasedeDatos01",null,1,1);
        HelperMiercoles= new SQLite_OpenHelper(this,"BasedeDatos2",null,1,2);
        HelperJueves= new SQLite_OpenHelper(this,"BasedeDatos3",null,1,3);
        HelperViernes= new SQLite_OpenHelper(this,"BasedeDatos4",null,1,4);
        HelperSabado= new SQLite_OpenHelper(this,"BasedeDatos5",null,1,5);
        HelperConf=new SQLite_OpenHelper(this,"BasedeDatos11",null,1,11);
        cont=0;
        cargarListasEnlazadas();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        inicilizar();
        onCreate();

        //Toast.makeText(this, "servicio iniciado", Toast.LENGTH_SHORT).show();
        return Service.START_STICKY;
    }

    public IBinder onUnBind(Intent arg0) {
        Log.i(TAG, "onUnBind()");
        return null;
    }

    public void onStop() {
        Log.i(TAG, "onStop()");
    }
    public void onPause() {
        Log.i(TAG, "onPause()");
    }
    @Override
    public void onDestroy() {
        Log.i(TAG, "onCreate() , service stopped...");
    }

    @Override
    public void onLowMemory() {
        Log.i(TAG, "onLowMemory()");
    }



    private void GenerarNotificaciones() {

        String nombre,lugar;
        int retrazo;
        int HoraAcMili,HoraMatMili,diferencia;
        nextClass();
        Nodo Ldia=ListaDiaPresente.BuscarNodo(next.getIdM());

        while (Ldia!=null){
            nombre=Ldia.getDatoMateria().getNombre();
            lugar=Ldia.getDatoMateria().getSalon();
            if(!Ldia.getDatoMateria().getNotifTime().equals("Desact") || !Ldia.getDatoMateria().getNotifTime().equals("")){


                retrazo=Integer.parseInt(Ldia.getDatoMateria().getNotifTime().charAt(0)+""+Ldia.getDatoMateria().getNotifTime().charAt(1));
                obtenerDiayHora();

                if(hora>12){
                    hora=hora-12;
                }


                HoraAcMili=(hora*60*60*1000)+((min)*60*1000)+(seg*1000);

                if(Ldia.getDatoMateria().getHoras().charAt(2)=='a'){
                    HoraMatMili=Integer.parseInt(Ldia.getDatoMateria().getHoras().charAt(0)+"");

                    HoraMatMili*=60*60*1000;
                }else{
                    HoraMatMili=Integer.parseInt(Ldia.getDatoMateria().getHoras().charAt(0)+""+Ldia.getDatoMateria().getHoras().charAt(1));
                    HoraMatMili*=60*60*1000;
                }

                diferencia=HoraMatMili-HoraAcMili;
                int difMin=(((diferencia/60))/1000);

                if(!(difMin<=retrazo)){

                    CrearNotificacion(nombre,(retrazo),diferencia,lugar);
                }
                if(!(difMin<=15)){

                    CrearNotificacion(nombre,(15),diferencia,lugar);
                }

            }
            Ldia=Ldia.getSiguiente();
        }
    }

    private void CrearNotificacion(final String nomMat, final int retrazo, long diferencia,String lugar) {

        long RetrazoF=(diferencia-(retrazo*60*1000));

        Intent AlarmIntent = new Intent(BackgroundService.this, AlarmReciever.class);
        AlarmIntent.putExtra("Nombre",nomMat);
        AlarmIntent.putExtra("Lugar",lugar);
        AlarmIntent.putExtra("Retrazo",retrazo);

        AlarmManager AlmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent Sender = PendingIntent.getBroadcast(this, 0, AlarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlmMgr.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() +RetrazoF, Sender);

    }

    public void obtenerDiayHora(){
        Calendar Cal=Calendar.getInstance();
        hora=Cal.getTime().getHours();
        min=Cal.getTime().getMinutes();
        seg=Cal.getTime().getSeconds();
        dia=Cal.getTime().getDay();
        next=new Materia();
    }

    public void nextClass(){
        obtenerDiayHora();

        switch (dia){
            case 0:

                break;
            case 1:
                next=ListaLunes.BuscarHora(hora);
                ListaDiaPresente=ListaLunes;
                break;
            case 2:
                next=ListaMartes.BuscarHora(hora);
                ListaDiaPresente=ListaMartes;
                break;
            case 3:
                next=ListaMiercoles.BuscarHora(hora);
                ListaDiaPresente=ListaMiercoles;
                break;
            case 4:
                next=ListaJueves.BuscarHora(hora);
                ListaDiaPresente=ListaJueves;
                break;
            case 5:
                next=ListaViernes.BuscarHora(hora);
                ListaDiaPresente=ListaViernes;
                break;
            case 6:
                next=ListaSabado.BuscarHora(hora);
                ListaDiaPresente=ListaSabado;
                break;

        }

    }



    void abrirDias(){
        HelperLunes.abrir();
        HelperMartes.abrir();
        HelperMiercoles.abrir();
        HelperJueves.abrir();
        HelperViernes.abrir();
        HelperSabado.abrir();
    }
    void cerrarDias(){
        HelperLunes.cerrar();
        HelperMartes.cerrar();
        HelperMiercoles.cerrar();
        HelperJueves.cerrar();
        HelperViernes.cerrar();
        HelperSabado.cerrar();
    }

    public void cargarListasEnlazadas(){

        abrirDias();

        if(HelperLunes==null){

        }else{
            Cursor c=HelperLunes.listarMaterias(0);

            if(c!=null){

                try {
                    ListaLunes=new ListaEnlazadaMat();
                    HelperLunes.llenarLista(ListaLunes,0);
                }catch (SQLException e){
                    Toast.makeText(this, "Error en lunes", Toast.LENGTH_SHORT).show();
                    Log.v("DiaMartes: ", e.getMessage());
                }
            }

        }
        if(HelperMartes==null){

        }else{
            Cursor c=HelperMartes.listarMaterias(1);

            if(c!=null){

                try {
                    ListaMartes=new ListaEnlazadaMat();
                    HelperMartes.llenarLista(ListaMartes,1);
                }catch (Exception e){
                    Toast.makeText(this, "se callo!!!"+e.getMessage(), Toast.LENGTH_LONG).show();
                    Toast.makeText(this, "se callo!!!"+e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }

        }
        if(HelperMiercoles==null){

        }else{
            Cursor c=HelperMiercoles.listarMaterias(2);

            if(c!=null){

                try {
                    ListaMiercoles=new ListaEnlazadaMat();
                    HelperMiercoles.llenarLista(ListaMiercoles,2);
                }catch (Exception e){

                }
            }

        }
        if(HelperJueves==null){

        }else{
            Cursor c=HelperJueves.listarMaterias(3);

            if(c!=null){

                try {
                    ListaJueves=new ListaEnlazadaMat();
                    HelperJueves.llenarLista(ListaJueves,3);
                }catch (Exception e){

                }
            }

        }
        if(HelperViernes==null){

        }else{
            Cursor c=HelperViernes.listarMaterias(4);

            if(c!=null){

                try {
                    ListaViernes=new ListaEnlazadaMat();
                    HelperViernes.llenarLista(ListaViernes,4);
                }catch (Exception e){

                }
            }

        }
        if(HelperSabado==null){

        }else{
            Cursor c=HelperSabado.listarMaterias(5);

            if(c!=null){

                try {
                    ListaSabado=new ListaEnlazadaMat();
                    HelperSabado.llenarLista(ListaSabado,5);
                }catch (Exception e){

                }
            }

        }


        if(HelperConf==null){


        }else{
            Cursor c=HelperConf.listarConfiguracion(6,this);

            if(c!=null){

                try {
                    Config=HelperConf.LlenarConfiguraciones(6);

                }catch (Exception e){
                    Config=new Configuraciones();
                }
            }else{
                Toast.makeText(this, "Está vacio configuracion", Toast.LENGTH_SHORT).show();
            }

        }

        cerrarDias();
    }

}
