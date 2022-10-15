package sistemas.ing.gironza.notasunicorappultimate;



public class Nodo {
    
    private Materia DatoMateria;
    private Nodo siguiente,anterior;
    
    public Nodo (){
        DatoMateria=null;
        siguiente=null;
        anterior=null;
    }
    public Nodo (Materia Dat){
        DatoMateria=Dat;
        siguiente=null;
        anterior=null;
    }

    public Materia getDatoMateria() {
        return DatoMateria;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }

    public void setAnterior(Nodo ant) {
        anterior = ant;
    }

    public Nodo getAnterior() {
        return anterior;
    }

    public void setDatoMateria(Materia datoMateria) {
        DatoMateria = datoMateria;
    }
}
