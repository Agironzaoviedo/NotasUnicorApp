package sistemas.ing.gironza.notasunicorappultimate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

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




public class FragmentVistaGeneral extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    View Vista;
    ListView Lista;
    TextView TPGA,TPsem,TCred;
    EditText EPGA,EPsem,Gpga;
    int Cred=0;
    String text;

    public FragmentVistaGeneral() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentVistaGeneral.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentVistaGeneral newInstance(String param1, String param2) {
        FragmentVistaGeneral fragment = new FragmentVistaGeneral();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    void inicializar(View v){

        Lista = v.findViewById(R.id.GLista);
        TPGA=v.findViewById(R.id.GtPga);
        TPsem=v.findViewById(R.id.GtPsem);
        TCred=v.findViewById(R.id.GtotalCre);
        EPGA=v.findViewById(R.id.Gpga);
        EPsem=v.findViewById(R.id.GPsem);
        text=EPGA.getText().toString();

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

        ListarEnPantalla(view);

        TPGA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearToast("Promedio General Acumulado");
            }
        });


        TPsem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearToast("Promedio del semestre actual");
            }
        });

        EPGA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingresarPGA(EPGA);
            }
        });

        EPsem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        SharedPreferences preferenciasapp;
        boolean haydatos = Boolean.FALSE;

        preferenciasapp = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        haydatos = preferenciasapp.getBoolean("haydatos", Boolean.FALSE);


        if(!haydatos){
            SharedPreferences.Editor editor = preferenciasapp.edit();
            editor.putFloat("PGA", 3);
            editor.putFloat("PSEM",3);
            editor.putBoolean("haydatos",true);
            editor.apply();
        }else {
            float pga=preferenciasapp.getFloat("PGA",3);
            float psem=preferenciasapp.getFloat("PSEM",3);

            EPGA.setText(pga+"");
            EPsem.setText(psem+"");
        }


    }

    private String ingresarPGA(final EditText v) {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Cambiar valor");
        alertDialog.setMessage("Ingrese mínimo 2 dígitos Ej: (4.0)");

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("Editar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        if(input.getText().toString().length()==3) {

                            text=input.getText().toString();
                            SharedPreferences preferenciasapp;
                            preferenciasapp = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

                            SharedPreferences.Editor editor = preferenciasapp.edit();
                            editor.putFloat("PGA",Float.parseFloat(text));
                            editor.apply();
                            v.setText(text);

                        }else{
                            CrearToast("No se cambió");
                        }

                    }
                });

        alertDialog.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        CrearToast("No se cambió");
                    }
                });

        alertDialog.show();

        return text;

    }

    private void CrearToast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Vista=inflater.inflate(R.layout.fragment_fragment_vista_general, container, false);


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
                Toast.makeText(getContext(),"Nada de nada ", Toast.LENGTH_SHORT).show();

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

    void ListarEnPantalla(View v){

        ListaEnlazadaMat ListaTodo=new ListaEnlazadaMat();

        Nodo temp=ListaLunes.getCabeza();
        Nodo nuevo;

        while (temp!=null){
            temp.getDatoMateria().setIdM("Lunes");
            nuevo=new Nodo(temp.getDatoMateria());
            ListaTodo.AgregarNodos(nuevo);
            temp=temp.getSiguiente();
        }

        temp=ListaMartes.getCabeza();

        while (temp!=null){
            temp.getDatoMateria().setIdM("Martes");
            nuevo=new Nodo(temp.getDatoMateria());
            ListaTodo.AgregarNodos(nuevo);
            temp=temp.getSiguiente();
        }

        temp=ListaMiercoles.getCabeza();

        while (temp!=null){
            temp.getDatoMateria().setIdM("Miercoles");
            nuevo=new Nodo(temp.getDatoMateria());
            ListaTodo.AgregarNodos(nuevo);
            temp=temp.getSiguiente();
        }

        temp=ListaJueves.getCabeza();

        while (temp!=null){
            temp.getDatoMateria().setIdM("Jueves");
            nuevo=new Nodo(temp.getDatoMateria());
            ListaTodo.AgregarNodos(nuevo);
            temp=temp.getSiguiente();
        }

        temp=ListaViernes.getCabeza();

        while (temp!=null){
            temp.getDatoMateria().setIdM("Viernes");
            nuevo=new Nodo(temp.getDatoMateria());
            ListaTodo.AgregarNodos(nuevo);
            temp=temp.getSiguiente();
        }

        temp=ListaSabado.getCabeza();

        while (temp!=null){
            temp.getDatoMateria().setIdM("Sabado");
            nuevo=new Nodo(temp.getDatoMateria());
            ListaTodo.AgregarNodos(nuevo);
            temp=temp.getSiguiente();
        }

        Materia [] VectorAllDay=ListaTodo.VectorMateriasPrincipal();

        definirPromSemestre(VectorAllDay);


        inflarListaGeneral(VectorAllDay);

        TCred.setText(Cred+"");
    }

    public void definirPromSemestre(Materia [] VectorAllDay){

        double suma,puntos,sumapuntos=0,sumacred=0, total;

        for (int i = 0; i < VectorAllDay.length; i++) {

            suma=VectorAllDay[i].getCorte1()+VectorAllDay[i].getCorte1()+VectorAllDay[i].getCorte1();
            suma/=3;
            puntos=suma*VectorAllDay[i].getCreditos();

            sumacred+=VectorAllDay[i].getCreditos();

            sumapuntos+=puntos;
        }

        total=sumapuntos/sumacred;

        String t=total+"";
        EPsem.setText(t);

        SharedPreferences preferenciasapp;
        preferenciasapp = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        SharedPreferences.Editor editor = preferenciasapp.edit();
        editor.putFloat("PSEM",Float.parseFloat(t));
        editor.apply();

    }


    void inflarListaGeneral( Materia[] MateriasDia){

        int cred=0;
        boolean conf;
            //Para contar los creditos
        String nom[]=new String[MateriasDia.length];
        int n=0;

        for (int i = 0; i <MateriasDia.length ; i++) {

            if(i==0) {
                conf = false;
            }else {
                conf = false;
                for (int j = 0; j < n; j++) {

                    if (nom[j].equals(MateriasDia[i].getNombre())) {

                        conf = true;
                    }

                }
            }
            if(!conf){
                if(MateriasDia[i].getCreditos()!=0){
                    cred+=MateriasDia[i].getCreditos();
                    nom[n] = MateriasDia[i].getNombre();
                    n++;
                }

            }
        }
        Cred=cred;
        //--
        materiasAdapterG Adapter = new materiasAdapterG(getContext(), R.layout.listview_item_general, MateriasDia);
        Lista.setAdapter(Adapter);

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

                    Log.v(": ", e.getMessage());
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

}
