package BasedeDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.nio.charset.MalformedInputException;

import sistemas.ing.gironza.notasunicorappultimate.Configuraciones;
import sistemas.ing.gironza.notasunicorappultimate.ListaEnlazadaMat;
import sistemas.ing.gironza.notasunicorappultimate.Materia;
import sistemas.ing.gironza.notasunicorappultimate.Nodo;


/**
 * Created by Anderson G on 10/01/2018.
 */

public class SQLite_OpenHelper extends SQLiteOpenHelper {


    String  Dias[]={"MLunes","MMartes","MMiercoles","MJueves","MViernes","MSabado","Configuraciones"};

    int ver;
    Context co;
    public int dia;

    public SQLite_OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version,int dia) {
        super(context, name, factory, version);
        ver=version;
        co=context;
        this.dia=dia;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase ) {
        String query=null;
        switch (dia){

            case 0:

                query="create table MLunes(_ID integer primary key autoincrement," +
                        " idM text," +
                        " Nombre text," +
                        " Horas text," +
                        " Salon text," +
                        " Corte1 text," +
                        " Corte2 text," +
                        " Corte3 text," +
                        " Meta text," +
                        " Creditos text);";

                break;

            case 1:
                query="create table MMartes(_ID integer primary key autoincrement," +
                        " idM text," +
                        " Nombre text," +
                        " Horas text," +
                        " Salon text," +
                        " Corte1 text," +
                        " Corte2 text," +
                        " Corte3 text," +
                        " Meta text," +
                        " Creditos text," +
                        " NotifTime text);";
                break;
            case 2:
                query="create table MMiercoles(_ID integer primary key autoincrement," +
                        " idM text," +
                        " Nombre text," +
                        " Horas text," +
                        " Salon text," +
                        " Corte1 text," +
                        " Corte2 text," +
                        " Corte3 text," +
                        " Meta text," +
                        " Creditos text," +
                        " NotifTime text);";
                break;
            case 3:
                query="create table MJueves(_ID integer primary key autoincrement," +
                        " idM text," +
                        " Nombre text," +
                        " Horas text," +
                        " Salon text," +
                        " Corte1 text," +
                        " Corte2 text," +
                        " Corte3 text," +
                        " Meta text," +
                        " Creditos text," +
                        " NotifTime text);";
                break;
            case 4:
                query="create table MViernes(_ID integer primary key autoincrement," +
                        " idM text," +
                        " Nombre text," +
                        " Horas text," +
                        " Salon text," +
                        " Corte1 text," +
                        " Corte2 text," +
                        " Corte3 text," +
                        " Meta text," +
                        " Creditos text," +
                        " NotifTime text);";
                break;
            case 5:
                query="create table MSabado(_ID integer primary key autoincrement," +
                        " idM text," +
                        " Nombre text," +
                        " Horas text," +
                        " Salon text," +
                        " Corte1 text," +
                        " Corte2 text," +
                        " Corte3 text," +
                        " Meta text," +
                        " Creditos text," +
                        " NotifTime text);";
                break;
            case 6:
                query="create table Configuraciones(_ID integer primary key autoincrement," +
                        " NotificacionMat text," +
                        " TiempoNotf text," +
                        " BloqCorte1 text," +
                        " BloqCorte2 text," +
                        " BloqCorte3 text," +
                        " BloqAnimo text," +
                        " TextAnimo text," +
                        " HojaApuntes text," +
                        " DatosUltimaAct text);";
                break;

        }

        sqLiteDatabase.execSQL(query);



    }

    //METODO PARA INSERTAR REGISTROS EN LA TABLA
    public void insertarRegistros(String idM, String nombre, String horas, String salon, Double corte1, Double corte2, Double corte3, Double meta, int creditos, int dia, String NotifTime){

        if (ver==1){
            insertar1(idM, nombre, horas, salon, corte1, corte2, corte3, meta, creditos, dia, NotifTime);
        }

    }

    private void insertar1(String idM, String nombre, String horas, String salon, Double corte1, Double corte2, Double corte3, Double meta, int creditos, int dia, String NotifTime) {
        ContentValues Valores= new ContentValues(10);
        Valores.put("idM",idM);
        Valores.put("Nombre",nombre);
        Valores.put("Horas",horas);
        Valores.put("Salon",salon);
        Valores.put("Corte1",corte1.toString());
        Valores.put("Corte2",corte2.toString());
        Valores.put("Corte3",corte3.toString());
        Valores.put("Meta",meta.toString());
        Valores.put("Creditos",String.valueOf(creditos));

        this.getWritableDatabase().insert(Dias[dia],null,Valores);
    }
    private void insertar2(String idM, String nombre, String horas, String salon, Double corte1, Double corte2, Double corte3, Double meta, int creditos, int dia, String NotifTime) {
        ContentValues Valores= new ContentValues(10);
        Valores.put("idM",idM);
        Valores.put("Nombre",nombre);
        Valores.put("Horas",horas);
        Valores.put("Salon",salon);
        Valores.put("Corte1",corte1.toString());
        Valores.put("Corte2",corte2.toString());
        Valores.put("Corte3",corte3.toString());
        Valores.put("Meta",meta.toString());
        Valores.put("Creditos",String.valueOf(creditos));
        Valores.put("NotifTime",NotifTime);

        this.getWritableDatabase().insert(Dias[dia],null,Valores);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int anteriorVersion, int nuevaVersion) {

    }

    //METODO PARA ABRIR LA BASE DE DATOS
    public void abrir(){
        this.getReadableDatabase();
    }

    //METODO PARA CERRAR LA BASE DE DATOS
    public void cerrar(){
        this.close();
    }


    //METODO PARA CONSULTAR REGISTROS EN LA TABLA
    public Cursor consultar(String nombre, int pos) throws SQLException{
        Cursor mCursor=null;

        mCursor=this.getReadableDatabase().query("Materia",new String[]{"_ID","nombre","corte1","corte2",
                "corte3","meta",},"_ID like'"+pos+"' and Nombre like'"+nombre+"'",null,null,null,null);
        return mCursor;

    }
    //METODO PARA LISTAR REGISTROS EN LA TABLA
    public Cursor listarMaterias(int dia)throws SQLException{
        Cursor mCursor=null;

        mCursor=this.getReadableDatabase().query(Dias[dia],new String[]{"_ID",
                "nombre","corte1","corte2",
                "corte3","meta",},null,null,null,null,null);
        if(mCursor!=null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //METODO PARA LISTAR REGISTROS EN LA TABLA CONFIGURACIONES
    public Cursor listarConfiguracion(int dia, Context context)throws SQLException{
        Cursor mCursor=null;

        mCursor=this.getReadableDatabase().query(Dias[dia],new String[]{"_ID","NotificacionMat","TiempoNotf","BloqCorte1",
                "BloqCorte2","BloqCorte3","BloqAnimo","TextAnimo","HojaApuntes","DatosUltimaAct",},null,null,null,null,null);
        if(mCursor!=null){
            mCursor.moveToLast();
        }

        return mCursor;
    }

    public int eliminarMateria(int dia,String id){

        return this.getWritableDatabase().delete(Dias[dia],"_ID"+"=?",new  String[]{id});

    }
    public int editarDatos(String idM, String nombre, String horas, String salon, Double corte1, Double corte2, Double corte3, Double meta, int creditos, int dia, String id,String NotifTime){
        int val=0;
        if(ver==1){
            val=editarDatos1(idM, nombre, horas, salon, corte1, corte2, corte3, meta, creditos, dia, id, NotifTime);

        }
        return val;
    }
    public int editarDatos1(String idM, String nombre, String horas, String salon, Double corte1, Double corte2, Double corte3, Double meta, int creditos, int dia, String id,String NotifTime){

        ContentValues Valores= new ContentValues(9);
        Valores.put("idM",idM);
        Valores.put("Nombre",nombre);
        Valores.put("Horas",horas);
        Valores.put("Salon",salon);
        Valores.put("Corte1",corte1.toString());
        Valores.put("Corte2",corte2.toString());
        Valores.put("Corte3",corte3.toString());
        Valores.put("Meta",meta.toString());
        Valores.put("Creditos",String.valueOf(creditos));

        return this.getWritableDatabase().update(Dias[dia],Valores,"_ID"+"=?",new String[]{idM+""});
    }

    //METODO PARA LLENAR LAS LISTAS DE MAIN PRINCIPAL PARA LISTAR EN LA PANATALLA
    public void llenarLista(ListaEnlazadaMat List, int dia){

        if(ver==1){
            llenarLista1(List,dia);
        }
    }

    public void llenarLista2(ListaEnlazadaMat List, int dia){

        Cursor mCursor=null;

        Materia materia=new Materia();
        mCursor=this.getReadableDatabase().query(Dias[dia],new String[]{"_ID","idM","Nombre",
                "Horas","Salon","Corte1","Corte2","Corte3","Meta","Creditos","NotifTime",},null,null,null,null,null);

        if (mCursor.moveToFirst()){
            do{

                materia= new Materia(mCursor.getString(0),mCursor.getString(2),
                        mCursor.getString(3),mCursor.getString(4),mCursor.getDouble(5),
                        mCursor.getDouble(6),mCursor.getDouble(7),mCursor.getDouble(8),mCursor.getInt(9),mCursor.getString(10));

                Nodo Nod=new Nodo();
                Nod.setDatoMateria(materia);

                List.AgregarNodos(Nod);

            }while (mCursor.moveToNext());
        }
        mCursor.close();
        this.close();
    }
    public void llenarLista1(ListaEnlazadaMat List, int dia){

        Cursor mCursor=null;

        Materia materia;
        mCursor=this.getReadableDatabase().query(Dias[dia],new String[]{"_ID","idM","Nombre",
                "Horas","Salon","Corte1","Corte2","Corte3","Meta","Creditos",},null,null,null,null,null);

        if (mCursor.moveToFirst()){
            do{

                materia= new Materia(mCursor.getString(0),mCursor.getString(2),
                        mCursor.getString(3),mCursor.getString(4),mCursor.getDouble(5),
                        mCursor.getDouble(6),mCursor.getDouble(7),mCursor.getDouble(8),mCursor.getInt(9),"10");

                Nodo Nod=new Nodo();
                Nod.setDatoMateria(materia);

                List.AgregarNodos(Nod);

            }while (mCursor.moveToNext());
        }
        mCursor.close();
        this.close();
    }
    public Configuraciones LlenarConfiguraciones(int dia){

        Cursor mCursor;

        Configuraciones Config=new Configuraciones();
        mCursor=this.getReadableDatabase().query(Dias[dia],new String[]{"_ID","notificacionMat","tiempoNotf",
                "bloqCorte1","bloqCorte2","bloqCorte3","bloqAnimo","textAnimo","hojaApuntes","datosUltimaAct",},
                null,null,null,null,null);


        mCursor.moveToFirst();
        Config= new Configuraciones(mCursor.getString(1), mCursor.getString(2),
                mCursor.getString(3),mCursor.getString(4),mCursor.getString(5),
                mCursor.getString(6),mCursor.getString(7),mCursor.getString(8),
                mCursor.getString(9),mCursor.getInt(0));



        mCursor.close();
        this.close();

        return Config;
    }

    public int editarDatosConfiguracion(String notificacionMat, String tiempoNotf, String bloqCorte1, String bloqCorte2,
                                        String bloqCorte3, String bloqAnimo, String textAnimo, String hojaApuntes,
                                        String datosUltimaAct,int dia, int idC){

    ContentValues Valores= new ContentValues();
        Valores.put("notificacionMat",notificacionMat);
        Valores.put("tiempoNotf",tiempoNotf);
        Valores.put("bloqCorte1",bloqCorte1);
        Valores.put("bloqCorte2",bloqCorte2);
        Valores.put("bloqCorte3",bloqCorte3);
        Valores.put("bloqAnimo",bloqAnimo);
        Valores.put("textAnimo",textAnimo);
        Valores.put("hojaApuntes",hojaApuntes);
        Valores.put("datosUltimaAct",datosUltimaAct);

        return this.getWritableDatabase().update(Dias[dia],Valores,"_ID"+"=?",new String[]{idC+""});
    }
    public void insertarConfiguraciones(String notificacionMat, String tiempoNotf, String bloqCorte1, String bloqCorte2,
                                         String bloqCorte3, String bloqAnimo, String textAnimo, String hojaApuntes,
                                         String datosUltimaAct,int dia){
        ContentValues Valores= new ContentValues();
        Valores.put("notificacionMat",notificacionMat);
        Valores.put("tiempoNotf",tiempoNotf);
        Valores.put("bloqCorte1",bloqCorte1);
        Valores.put("bloqCorte2",bloqCorte2);
        Valores.put("bloqCorte3",bloqCorte3);
        Valores.put("bloqAnimo",bloqAnimo);
        Valores.put("textAnimo",textAnimo);
        Valores.put("hojaApuntes",hojaApuntes);
        Valores.put("datosUltimaAct",datosUltimaAct);

        this.getWritableDatabase().insert(Dias[dia],null,Valores);
    }

    public void Ordenar(Context context,SQLite_OpenHelper HelperD,int dia) {
        ListaEnlazadaMat Lista;

        if (HelperD != null) {
            Cursor c=HelperD.listarMaterias(dia);

            if(c!=null){

                try {
                    Lista=new ListaEnlazadaMat();
                    HelperD.llenarLista(Lista,dia);
                    Materia VecOrdenar[]= new Materia [Lista.CantMaterias()];

                    Nodo temp=Lista.getCabeza();
                    for (int i = 0; i <Lista.CantMaterias() ; i++) {
                        VecOrdenar[i]=temp.getDatoMateria();
                        temp=temp.getSiguiente();
                    }
                    ordenarBurbujaVector(VecOrdenar);

                    ReiniciarBasedeDatos(context,HelperD,dia);

                    for (Materia materia : VecOrdenar) {

                        HelperD.insertarRegistros(materia.getIdM(), materia.getNombre(),
                                materia.getHoras(), materia.getSalon(), materia.getCorte1(),
                                materia.getCorte2(), materia.getCorte3(), materia.getMeta(),
                                materia.getCreditos(), dia, materia.getNotifTime());
                    }
                    HelperD.cerrar();

                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }


    }

    private void ReiniciarBasedeDatos(Context c,SQLite_OpenHelper H,int dia) {

        switch (dia){
            case 0:
                c.deleteDatabase("BasedeDatos0");
                H= new SQLite_OpenHelper(c,"BasedeDatos0",null,1,dia);
                break;
            case 1:
                c.deleteDatabase("BasedeDatos01");
                H= new SQLite_OpenHelper(c,"BasedeDatos01",null,1,dia);
                break;
            case 2:
                c.deleteDatabase("BasedeDatos2");
                H= new SQLite_OpenHelper(c,"BasedeDatos2",null,1,dia);
                break;
            case 3:
                c.deleteDatabase("BasedeDatos3");
                H= new SQLite_OpenHelper(c,"BasedeDatos3",null,1,dia);
                break;
            case 4:
                c.deleteDatabase("BasedeDatos4");
                H= new SQLite_OpenHelper(c,"BasedeDatos4",null,1,dia);
                break;
            case 5:
                c.deleteDatabase("BasedeDatos5");
                H= new SQLite_OpenHelper(c,"BasedeDatos5",null,1,dia);
                break;

        }
        cerrar();
    }


    private void cambiar(Materia p1, Materia p2) {
        Materia temp;
        temp = p1;
        p1=p2;
        p2=temp;
    }

    private void ordenarBurbujaVector(Materia[] Vector) {

        int i, j;
        int HoraActual,HoraSig;
        for (i = 0; i <= Vector.length - 1; i++) {
            for (j = 0; j <= (Vector.length - i) - 2; j++) {

                HoraActual = Integer.parseInt((Vector[j].getHoras().charAt(0) + "" + Vector[j + 1].getHoras().charAt(1)).trim());
                HoraSig=Integer.parseInt((Vector[j].getHoras().charAt(0)+""+Vector[j+1].getHoras().charAt(1)).trim());
                if (HoraActual< HoraSig) {
                    cambiar(Vector[j],Vector[j+1]);
                }
            }
        }
    }

}
