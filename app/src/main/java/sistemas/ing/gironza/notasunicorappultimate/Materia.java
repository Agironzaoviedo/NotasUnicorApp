package sistemas.ing.gironza.notasunicorappultimate;


import android.widget.Toast;

public class Materia {

    private String idM,nombre,horas, salon,notifTime;
    private Double corte1, corte2, corte3, meta;
    private  int creditos;

    public Materia(){
        super();
        this.idM = "";
        this.nombre = "";
        this.horas = "";
        this.salon = "";
        this.corte1 = 0.0;
        this.corte2 = 0.0;
        this.corte3 = 0.0;
        this.meta = 3.0;
        this.creditos = 0;
        notifTime="";
    }

    public Materia(String idM, String nombre, String horas, String salon,
                   Double corte1, Double corte2, Double corte3, Double meta, int creditos,String notifTime) {

        super();
        this.idM = idM;
        this.nombre = nombre;
        this.horas = horas;
        this.salon = salon;
        this.corte1 = corte1;
        this.corte2 = corte2;
        this.corte3 = corte3;
        this.meta = meta;
        this.creditos = creditos;
        this.notifTime=notifTime;
    }

    public String getNotifTime() {
        return notifTime;
    }

    public void setNotifTime(String notifTime) {
        this.notifTime = notifTime;
    }

    public String getIdM() {
        return idM;
    }

    public void setIdM(String idM) {
        this.idM = idM;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public Double getCorte1() {
        return corte1;
    }

    public void setCorte1(Double corte1) {
        this.corte1 = corte1;
    }

    public Double getCorte2() {
        return corte2;
    }

    public void setCorte2(Double corte2) {
        this.corte2 = corte2;
    }

    public Double getCorte3() {
        return corte3;
    }

    public void setCorte3(Double corte3) {
        this.corte3 = corte3;
    }

    public Double getMeta() {
        return meta;
    }

    public void setMeta(Double meta) {
        this.meta = meta;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    @Override
    public String toString() {
        return "------------------------\n" +
                "*" + nombre + "*\n" +
                "Horas: " + horas + "\n" +
                "Lugar: " + salon + "\n" +
                "Creditos: " + creditos+ "\n";
    }
}
