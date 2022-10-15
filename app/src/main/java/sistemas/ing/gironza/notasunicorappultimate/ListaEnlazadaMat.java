package sistemas.ing.gironza.notasunicorappultimate;


public class ListaEnlazadaMat {


    private Nodo Cabeza;
    private int CantNodos;

    public ListaEnlazadaMat() {
        Cabeza = null;
        CantNodos=0;
    }

    void setCabeza(Nodo c) {
        Cabeza = c;
    }

    public Nodo getCabeza() {
        return Cabeza;
    }

    public Nodo Ultimo() {
        Nodo temp = Cabeza;
        while (temp != null) {

            if (temp.getSiguiente() == null) {
                break;

            } else {
                temp = temp.getSiguiente();
            }
        }
        return temp;
    }

    public void AgregarNodos(Nodo nuevo) {
        Nodo temp = Ultimo();
        if (temp != null) {
            temp.setSiguiente(nuevo);//El enlace del ultimo nodo apunta al nuevo nodo.
            nuevo.setAnterior(temp);//El enlace anterior del nuevo nodo apunta al último nodo.
            nuevo.setSiguiente(null);//El enlace siguiente del nuevo nodo apunta a nulo.
        } else {//Esta condición se da, en caso de que la lista este vacía y se agrega el primer nodo.
            nuevo.setAnterior(null);
            setCabeza( nuevo);
        }

    }



    public int CantMaterias() {
        CantNodos=0;
        Nodo temp = getCabeza();
        while (temp != null) {
            if (temp.getSiguiente() == null) {

                CantNodos++;

                break;
            } else {
                CantNodos++;
                temp = temp.getSiguiente();

            }
        }
        return  CantNodos;

    }
    //Llenar el vector cada dia de la ventana principal
    public Materia[] VectorMateriasPrincipal() {

        Materia [] MatPrincipal=new Materia[CantMaterias()];

        Nodo temp = getCabeza();
        int i=0;
        while (temp != null) {
            if (temp.getSiguiente() == null) {

                MatPrincipal[i]=temp.getDatoMateria();
                break;
            } else {

                MatPrincipal[i]=temp.getDatoMateria();
                i++;
                temp = temp.getSiguiente();
            }
        }
        return  MatPrincipal;

    }

    public Materia Buscar(String idM) {
        Materia Mat=null;
        Nodo temp = Cabeza;
        while (temp != null) {
            if (temp.getDatoMateria().getIdM().equals(idM)) {
                Mat=temp.getDatoMateria();
                break;
            } else {
                temp = temp.getSiguiente();
            }
        }

        return Mat;

    }

    public Materia BuscarHora(int  hora) {
        Materia Mat=new Materia();
        Nodo temp = Cabeza;
        int h;
        while (temp != null) {

            String a=temp.getDatoMateria().getHoras().charAt(1)+"";

            if(!a.equals(" ")){
                h=Integer.parseInt(temp.getDatoMateria().getHoras().charAt(0)+""+temp.getDatoMateria().getHoras().charAt(1));
            }else{
                h=Integer.parseInt(temp.getDatoMateria().getHoras().charAt(0)+"");
            }
            if(temp.getDatoMateria().getHoras().charAt(temp.getDatoMateria().getHoras().length()-2)=='p'){
                if(h!=12){
                    h+=12;
                }

            }

            if (hora<h) {
                Mat=temp.getDatoMateria();
                break;
            } else {
                temp = temp.getSiguiente();
            }
        }

        return Mat;

    }

    public Materia siguienteMateria(String idM){
        Nodo NodMat= BuscarNodo(idM);
        Materia Mat=new Materia();

        if(NodMat!=null) {
            if (NodMat.getSiguiente() != null) {
                Mat = NodMat.getSiguiente().getDatoMateria();
            }
        }
        return Mat;

    }

    public Materia anteriorMateria(String idM){
        Nodo NodMat= BuscarNodo(idM);
        Materia Mat=null;
        if (NodMat.getAnterior()!=null){
            Mat=NodMat.getAnterior().getDatoMateria();

        }
        return Mat;

    }

    public Nodo BuscarNodo(String idM) {
        Nodo Encon=null;
        Nodo temp = getCabeza();
        while (temp != null) {
            if (temp.getDatoMateria().getIdM().equals(idM)) {
                Encon=temp;
                break;
            } else {
                temp = temp.getSiguiente();
            }
        }

        return Encon;
    }

    @Override
    public String toString() {
        Nodo temp=Cabeza;
        String mostrar="";


        while (temp != null) {
            if (temp.getSiguiente()==null) {
                mostrar+=temp.getDatoMateria().getIdM()+"\n";
                mostrar+=temp.getDatoMateria().getHoras()+"\n";
                mostrar+=temp.getDatoMateria().getNombre()+"\n";
                mostrar+=temp.getDatoMateria().getSalon()+"\n";
                mostrar+=temp.getDatoMateria().getCorte1()+"\n";
                mostrar+=temp.getDatoMateria().getCorte2()+"\n";
                mostrar+=temp.getDatoMateria().getCorte3()+"\n";
                mostrar+=temp.getDatoMateria().getMeta()+"\n";
                mostrar+=temp.getDatoMateria().getCreditos()+"\n";
                mostrar+=temp.getDatoMateria().getNotifTime()+"\n";
                mostrar+="-----------------------------------\n";
                break;
            } else {
                mostrar+=temp.getDatoMateria().getIdM()+"\n";
                mostrar+=temp.getDatoMateria().getHoras()+"\n";
                mostrar+=temp.getDatoMateria().getNombre()+"\n";
                mostrar+=temp.getDatoMateria().getSalon()+"\n";
                mostrar+=temp.getDatoMateria().getCorte1()+"\n";
                mostrar+=temp.getDatoMateria().getCorte2()+"\n";
                mostrar+=temp.getDatoMateria().getCorte3()+"\n";
                mostrar+=temp.getDatoMateria().getMeta()+"\n";
                mostrar+=temp.getDatoMateria().getCreditos()+"\n";
                mostrar+=temp.getDatoMateria().getNotifTime()+"\n";
                mostrar+="-----------------------------------\n";
                temp = temp.getSiguiente();
            }
        }
        return mostrar;
    }
    Materia Vect[];
    public void Ordenar(){
        Vect=VectorMateriasPrincipal();

        int i, j;
        for (i = 0; i <= Vect.length - 1; i++) {
            for (j = i + 1; j <= Vect.length - 1; j++) {
                if (Integer.parseInt(Vect[i].getHoras().charAt(0)+"") > Integer.parseInt(Vect[j].getHoras().charAt(0)+"") && Vect[i].getHoras().charAt(7)== Vect[j].getHoras().charAt(7) ) {
                    cambiar(i, j);
                }
            }
        }



    }
    public void cambiar(int p1, int p2) {
        Materia temp;
        temp = Vect[p1];
        Vect[p1]=Vect[p2];
        Vect[p2]=temp;
    }

}
