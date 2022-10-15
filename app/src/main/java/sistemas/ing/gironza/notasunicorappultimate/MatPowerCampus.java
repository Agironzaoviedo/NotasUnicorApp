package sistemas.ing.gironza.notasunicorappultimate;

public class MatPowerCampus {

    String dia;
    String id;
    String nombre,grupo,creditos,horari,lugar, nom_docente;

    public MatPowerCampus(String dia, String id, String nombre, String grupo, String creditos, String horari, String lugar, String nom_docente) {
        this.dia = dia;
        this.id = id;
        this.nombre = nombre;
        this.grupo = grupo;
        this.creditos = creditos;
        this.horari = horari;
        this.lugar = lugar;
        this.nom_docente = nom_docente;
    }
    public MatPowerCampus() {
        this.dia = "";
        this.id ="" ;
        this.nombre ="";
        this.grupo = "";
        this.creditos ="";
        this.horari = "";
        this.lugar = "";
        this.nom_docente = "";
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getCreditos() {
        return creditos;
    }

    public void setCreditos(String creditos) {
        this.creditos = creditos;
    }

    public String getHorari() {
        return horari;
    }

    public void setHorari(String horari) {
        this.horari = horari;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getNom_docente() {
        return nom_docente;
    }

    public void setNom_docente(String nom_docente) {
        this.nom_docente = nom_docente;
    }
}

