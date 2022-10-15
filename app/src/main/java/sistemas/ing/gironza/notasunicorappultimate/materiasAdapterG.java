package sistemas.ing.gironza.notasunicorappultimate;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class materiasAdapterG extends ArrayAdapter<Materia> {

    public Context context;

    public int layautResourceid;
    String Dia;
    public Materia datos[]=null;


    public materiasAdapterG(Context context, int layautResourceid, Materia[] datos) {
        super(context, layautResourceid,datos);
        this.context=context;
        this.layautResourceid = layautResourceid;
        this.datos=datos;
    }



    @NonNull
    public View getView(int pos, View convertView, ViewGroup parent){

        View row=convertView;

        materiaHolder holder;

        if(row==null)
        {
            LayoutInflater inflater=((FragmentActivity)context).getLayoutInflater();
            row=inflater.inflate(layautResourceid,parent,false);
            holder=new materiaHolder();

            holder.nombre= row.findViewById(R.id.Gnom);
            holder.ini= row.findViewById(R.id.Ginicial);
            holder.def= row.findViewById(R.id.Gdefi);
            holder.c1= row.findViewById(R.id.Gcorte1);
            holder.c2= row.findViewById(R.id.Gcorte2);
            holder.c3= row.findViewById(R.id.Gcorte3);


            row.setTag(holder);
        }else{
            holder=(materiaHolder) row.getTag();
        }

        Materia materia=datos[pos];

        //hace referencia al dia
        holder.ini.setText(materia.getIdM());
        holder.nombre.setText(materia.getNombre());
        holder.nombre.setSelected(true);
        holder.def.setText(((materia.getCorte1()+materia.getCorte1()+materia.getCorte1())/3)+"");
        holder.c1.setText(materia.getCorte1().toString()+"");
        holder.c2.setText(materia.getCorte2().toString()+"");
        holder.c3.setText(materia.getCorte3().toString()+"");

        return row;
    }

    static class materiaHolder{

        TextView ini;
        TextView nombre;
        TextView def;
        TextView c1;
        TextView c2;
        TextView c3;

    }

}
