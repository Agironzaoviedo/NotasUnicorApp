package sistemas.ing.gironza.notasunicorappultimate;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperConf;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperJueves;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperLunes;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperMartes;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperMiercoles;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperSabado;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.HelperViernes;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.ListaDiaActual;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.ListaJueves;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.ListaLunes;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.ListaMartes;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.ListaMiercoles;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.ListaSabado;
import static sistemas.ing.gironza.notasunicorappultimate.MainActivityNav.ListaViernes;




public class VistaGeneral extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int TODAS_DIA=1,TODOS_HORARIO=2,ACTUAL=0;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    View Vista;
    ListView Lunes, Martes,Miercoles,Jueves,Viernes,Sabado;
    Button Blunes, BMartes,BMiercoles,BJueves,BViernes,BSabado;



    public VistaGeneral() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VistaGeneral.
     */
    // TODO: Rename and change types and number of parameters
    public static VistaGeneral newInstance(String param1, String param2) {
        VistaGeneral fragment = new VistaGeneral();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    void inicializar(View v){



        Lunes = v.findViewById(R.id.LVlunes);
        Martes = v.findViewById(R.id.LVmartes);
        Miercoles=v.findViewById(R.id.LVmiercoles);
        Jueves =v.findViewById(R.id.LVjueves);
        Viernes=v.findViewById(R.id.LVviernes);
        Sabado=v.findViewById(R.id.LVsabado);
        BMartes=v.findViewById(R.id.Bmartes);
        BMiercoles=v.findViewById(R.id.Bmiercoles);
        BJueves=v.findViewById(R.id.Bjueves);
        BViernes=v.findViewById(R.id.Bviernes);
        BSabado=v.findViewById(R.id.Bsabado);
        Blunes=v.findViewById(R.id.Blunes);



    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializar(view);


        cargarListasEnlazadas();
        ListarEnPantalla();
        ColorearDia();

        //----------------Menu popUp
        Lunes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showPopupMenu(view,0);
                return true;
            }
        });
        Martes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showPopupMenu(view,1);
                return true;
            }
        });
        Miercoles.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showPopupMenu(view,2);
                return true;
            }
        });
        Jueves.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showPopupMenu(view,3);
                return true;
            }
        });
        Viernes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showPopupMenu(view,4);
                return true;
            }
        });
        Sabado.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showPopupMenu(view,5);
                return true;
            }
        });

        //----------------Abrir materia
        Lunes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OnclicDias(Lunes,view);
            }
        });

        Martes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OnclicDias(Martes,view);
            }
        });
        Miercoles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OnclicDias(Miercoles,view);
            }
        });
        Jueves.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OnclicDias(Jueves,view);
            }
        });
        Viernes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OnclicDias(Viernes,view);
            }
        });
        Sabado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OnclicDias(Sabado,view);
            }
        });

    }
    private void showPopupMenu(final View view, final int dia)
    {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_compartir, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                CompartirMateria(view,dia,item);
                return true;
            }
        });
        //dont forget to show the menu
        popupMenu.show();
    }

    private void CompartirMateria(View view, int dia, MenuItem menu) {

        TextView idM =  view.findViewById(R.id.codMPP);
        String id=idM.getText().toString(),TextoCompartir="";

        Materia actual=new Materia();
        String firma="\nBy Notas UnicorApp" +
                "\nConsigue la App aquí: https://drive.google.com/open?id=1ZtiDr7tGogY-zuMoxXsjmt2DNyU-GsoQ";

        boolean cm=false;
        switch (menu.getItemId()){
            case R.id.m_actual:
                actual= cargarMateria(id,dia);
                TextoCompartir="*"+diaCompartir+"*\n"+actual.toString();
                break;
            case R.id.todasdia:
                CargarlistaDiaActual(dia);
                Nodo temp=ListaDiaActual.getCabeza();

                String todasDia="*"+diaCompartir+"*\n";

                while (temp!=null){
                    todasDia+=temp.getDatoMateria().toString();
                    temp=temp.getSiguiente();
                }
                TextoCompartir=todasDia;

                break;
            case R.id.todosdias:
                TextoCompartir =CargarTodosDias();
                break;
            case R.id.compartirM:
                cm=true;
                break;
            case R.id.UbicarMateria:
                actual= cargarMateria(id,dia);
                BuscaryUbicar(actual);
                cm=true;
                break;


        }
        if(!cm){
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,TextoCompartir+firma);
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "Compartir horario con"));
        }


    }

    private void BuscaryUbicar(Materia actual) {
        String bloque=actual.getSalon().trim();

        try{
            String Bloque=bloque.charAt(7)+""+bloque.charAt(8);

            Intent intent= new Intent(getContext(),Web_Descarga.class);
            intent.putExtra("bloque",Integer.parseInt(Bloque.trim()));
            intent.putExtra("para","Ubicor");

            startActivity(intent);

        }catch (Exception e){
            Toast.makeText(getContext(),  "No es una ubicación válida para Ubicor", Toast.LENGTH_SHORT).show();

        }


    }

    private String CargarTodosDias() {

        String todosDias="";
        Nodo tempx=ListaLunes.getCabeza();
        if (tempx != null){
            todosDias+="\n\n"+getResources().getString(R.string.dia0)+"\n";
        }
        while (tempx!=null){
            todosDias+=tempx.getDatoMateria().toString();
            tempx=tempx.getSiguiente();
        }

        tempx=ListaMartes.getCabeza();
        if (tempx != null){
            todosDias+="\n\n"+getResources().getString(R.string.dia1)+"\n";
        }
        while (tempx!=null){
            todosDias+=tempx.getDatoMateria().toString();
            tempx=tempx.getSiguiente();
        }
        tempx=ListaMiercoles.getCabeza();
        if (tempx!=null){
            todosDias+="\n\n"+getResources().getString(R.string.dia2)+"\n";
        }
        while (tempx!=null){
            todosDias+=tempx.getDatoMateria().toString();
            tempx=tempx.getSiguiente();
        }
        tempx=ListaJueves.getCabeza();
        if (tempx != null){
            todosDias+="\n\n"+getResources().getString(R.string.dia3)+"\n";
        }
        while (tempx!=null){
            todosDias+=tempx.getDatoMateria().toString();
            tempx=tempx.getSiguiente();
        }
        tempx=ListaViernes.getCabeza();
        if (tempx != null){
            todosDias+="\n\n"+getResources().getString(R.string.dia4)+"\n";
        }
        while (tempx!=null){
            todosDias+=tempx.getDatoMateria().toString();
            tempx=tempx.getSiguiente();
        }
        tempx=ListaSabado.getCabeza();
        if (tempx != null){
            todosDias+="\n\n"+getResources().getString(R.string.dia5)+"\n";
        }
        while (tempx!=null){
            todosDias+=tempx.getDatoMateria().toString();
            tempx=tempx.getSiguiente();
        }
        return todosDias;
    }

    String diaCompartir;
    void CargarlistaDiaActual(int dia){

        switch (dia){

            case 0:
                diaCompartir=getResources().getString(R.string.dia0);
                ListaDiaActual=ListaLunes;

                break;

            case 1:
                ListaDiaActual=ListaMartes;
                diaCompartir=getResources().getString(R.string.dia1);
                break;

            case 2:
                ListaDiaActual=ListaMiercoles;
                diaCompartir=getResources().getString(R.string.dia2);
                break;
            case 3:
                ListaDiaActual=ListaJueves;
                diaCompartir=getResources().getString(R.string.dia3);

                break;
            case 4:
                ListaDiaActual=ListaViernes;
                diaCompartir=getResources().getString(R.string.dia4);
                break;
            case 5:
                ListaDiaActual=ListaSabado;
                diaCompartir=getResources().getString(R.string.dia5);
                break;

            default:


        }

    }

    Materia cargarMateria(String idM, int dia) {

        Materia materiaActual;
        switch (dia) {
            case 0:
                materiaActual = ListaLunes.Buscar(idM);
                diaCompartir=getResources().getString(R.string.dia0);
                return materiaActual;
            case 1:
                materiaActual = ListaMartes.Buscar(idM);
                diaCompartir=getResources().getString(R.string.dia1);
                return materiaActual;
            case 2:
                materiaActual = ListaMiercoles.Buscar(idM);
                diaCompartir=getResources().getString(R.string.dia2);
                return materiaActual;
            case 3:
                materiaActual = ListaJueves.Buscar(idM);
                diaCompartir=getResources().getString(R.string.dia3);
                return materiaActual;
            case 4:
                materiaActual = ListaViernes.Buscar(idM);
                diaCompartir=getResources().getString(R.string.dia4);
                return materiaActual;
            case 5:
                materiaActual = ListaSabado.Buscar(idM);
                diaCompartir=getResources().getString(R.string.dia5);
                return materiaActual;
            default:
                 materiaActual=new Materia();
                return materiaActual;
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View Vista=inflater.inflate(R.layout.fragment_vita_general_2, container, false);


        return Vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    void OnclicDias(View lista,View View2){

        Intent intent;
        TextView idM =  View2.findViewById(R.id.codMPP);
        intent=new Intent(getContext(), MainActivity.class);
        intent.putExtra("idM",idM.getText().toString());

        switch (lista.getId()){

            case R.id.LVlunes:
                ListaDiaActual=ListaLunes;
                intent.putExtra("dia",0);
                startActivity(intent);
                break;

            case R.id.LVmartes:
                ListaDiaActual=ListaMartes;
                intent.putExtra("dia",1);
                startActivity(intent);
                break;

            case R.id.LVmiercoles:
                ListaDiaActual=ListaMiercoles;
                intent.putExtra("dia",2);
                startActivity(intent);

                break;
            case R.id.LVjueves:
                ListaDiaActual=ListaJueves;
                intent.putExtra("dia",3);
                startActivity(intent);

                break;
            case R.id.LVviernes:
                ListaDiaActual=ListaViernes;
                intent.putExtra("dia",4);
                startActivity(intent);

                break;
            case R.id.LVsabado:
                ListaDiaActual=ListaSabado;
                intent.putExtra("dia",5);
                startActivity(intent);

                break;

            default:


        }

    }


    @Override
    public void onClick(View view) {
        agregarEnDia(view);
    }

    void agregarEnDia(View dia){

        Intent intent=new Intent(getContext(), EditarMateria.class);
        intent.putExtra("Texto","Agregar en Día");

        switch (dia.getId()){
            case R.id.Blunes:
                intent.putExtra("Dia",Integer.parseInt(""+0));
                break;
            case R.id.Bmartes:
                intent.putExtra("Dia",Integer.parseInt(""+1));
                break;
            case R.id.Bmiercoles:
                intent.putExtra("Dia",Integer.parseInt(""+2));
                break;
            case R.id.Bjueves:
                intent.putExtra("Dia",Integer.parseInt(""+3));
                break;
            case R.id.Bviernes:
                intent.putExtra("Dia",Integer.parseInt(""+4));
                break;
            case R.id.Bsabado:
                intent.putExtra("Dia",Integer.parseInt(""+5));
                break;
            default:
        }
        startActivity(intent);
    }

    void ListarEnPantalla(){

        inflarListas(Lunes,ListaLunes);
        inflarListas(Martes,ListaMartes);
        inflarListas(Miercoles,ListaMiercoles);
        inflarListas(Jueves,ListaJueves);
        inflarListas(Viernes,ListaViernes);
        inflarListas(Sabado,ListaSabado);

    }
    void inflarListas(ListView Dia,ListaEnlazadaMat Lista){

        if(Lista.CantMaterias()>0){
            Materia MateriasDia[] =Lista.VectorMateriasPrincipal();

            final materiasAdapter Adapter = new materiasAdapter(getContext(), R.layout.listview_item_row, MateriasDia);

            //View header = (View) getLayoutInflater().inflate(R.layout.listview_item_header, null);
            //Dia.addHeaderView(header);
            Dia.setAdapter(Adapter);
        }
    }


    public void ColorearDia(){
        Calendar Cal=Calendar.getInstance();
        int dia=Cal.getTime().getDay();

        switch (dia){
            case 0:

                break;
            case 1:

                Blunes.setTextColor(getResources().getColor(R.color.primaryDark));
                Blunes.setText("•   "+Blunes.getText()+"   •");
                break;
            case 2:

                BMartes.setTextColor(getResources().getColor(R.color.primaryDark));
                BMartes.setText("•   "+BMartes.getText()+"   •");
                break;
            case 3:

                BMiercoles.setTextColor(getResources().getColor(R.color.primaryDark));
                BMiercoles.setText("•   "+BMiercoles.getText()+"   •");
                break;
            case 4:
                BJueves.setTextColor(getResources().getColor(R.color.primaryDark));
                BJueves.setText("•   "+BJueves.getText()+"   •");
                break;
            case 5:
                BViernes.setTextColor(getResources().getColor(R.color.primaryDark));
                BViernes.setText("•   "+BViernes.getText()+"   •");
                break;
            case 6:
                BSabado.setText("•    "+BSabado.getText()+"   •");
                BSabado.setTextColor(getResources().getColor(R.color.primaryDark));
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
        HelperConf.abrir();
    }
    void cerrarDias(){
        HelperLunes.cerrar();
        HelperMartes.cerrar();
        HelperMiercoles.cerrar();
        HelperJueves.cerrar();
        HelperViernes.cerrar();
        HelperSabado.cerrar();
        HelperConf.cerrar();
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

        cerrarDias();
    }
    @Override

    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.about:


                return true;

            case R.id.quit:

                return true;


            default:

                return super.onOptionsItemSelected(item);
        }

    }


}
