package Beans;

/**
 * Created by Anderson G on 10/01/2018.
 */

public class MateriaBeans {

    private int codM,numCred;
    private String nombre, horas, salon;
    private Double corte1, corte2,corte3, meta;

    public MateriaBeans() {
        this.codM = -100;
        this.nombre="";
        this.horas="";
        this.salon="";
        this.numCred=-100;
        this.corte1 = 0.0;
        this.corte2 = 0.0;
        this.corte3 = 0.0;
        this.meta = 0.0;
    }

    public MateriaBeans(int cod, String nombre,String horas,int numCred, String salon, Double corte1, Double corte2, Double corte3, Double meta) {

        this.codM = cod;
        this.nombre=nombre;
        this.horas=horas;
        this.salon=salon;
        this.numCred=numCred;
        this.corte1 = corte1;
        this.corte2 = corte2;
        this.corte3 = corte3;
        this.meta = meta;
    }

    public int getCodM() {
        return codM;
    }

    public void setCod(int cod) {
        this.codM = cod;
    }

    public int getNumCred() {
        return numCred;
    }

    public void setNumCred(int numCred) {
        this.numCred = numCred;
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
}
