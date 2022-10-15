package sistemas.ing.gironza.notasunicorappultimate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class materiasAdapter extends ArrayAdapter<Materia> {

    public Context context;

    public int layautResourceid;
    public Materia datos[]=null;


    public materiasAdapter(Context context, int layautResourceid, Materia[] datos) {
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

            holder.Hora=(TextView) row.findViewById(R.id.horaPP);
            holder.nombre=(TextView) row.findViewById(R.id.nombreMaterPP);
            holder.salon=(TextView) row.findViewById(R.id.salonPP);
            holder.cod=(TextView) row.findViewById(R.id.codMPP);
            holder.card=(CardView) row.findViewById(R.id.card);


            row.setTag(holder);
        }else{
            holder=(materiaHolder) row.getTag();
        }

        Materia materia=datos[pos];

        holder.cod.setText(materia.getIdM());
        holder.nombre.setText(materia.getNombre());
        holder.nombre.setSelected(true);
        //holder.nombre.setTextColor(context.getResources().getColor(R.color.cardview_light_background));
        holder.salon.setText(materia.getSalon());
        holder.salon.setSelected(true);
        holder.Hora.setText(materia.getHoras());
        //holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.black_overlay));

        return row;
    }

    static class materiaHolder{

        TextView Hora;
        TextView nombre;
        TextView salon;
        TextView cod;
        CardView card;
    }

}
