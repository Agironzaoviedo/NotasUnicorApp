package sistemas.ing.gironza.notasunicorappultimate;

public class Configuraciones {


    String NotificacionMat,TiempoNotf ,BloqCorte1,BloqCorte2 ,BloqCorte3 ,BloqAnimo,TextAnimo,HojaApuntes,DatosUltimaAct;
    int id;

    public Configuraciones(String notificacionMat, String tiempoNotf, String bloqCorte1, String bloqCorte2, String bloqCorte3, String bloqAnimo, String textAnimo, String hojaApuntes, String datosUltimaAct, int id) {
        NotificacionMat = notificacionMat;
        TiempoNotf = tiempoNotf;
        BloqCorte1 = bloqCorte1;
        BloqCorte2 = bloqCorte2;
        BloqCorte3 = bloqCorte3;
        BloqAnimo = bloqAnimo;
        TextAnimo = textAnimo;
        HojaApuntes = hojaApuntes;
        DatosUltimaAct = datosUltimaAct;
        this.id=id;
    }

    public Configuraciones() {
        NotificacionMat = "aaaa";
        TiempoNotf = "";
        BloqCorte1 = "";
        BloqCorte2 = "";
        BloqCorte3 = "";
        BloqAnimo = "";
        TextAnimo = "";
        HojaApuntes = "";
        DatosUltimaAct = "";
        this.id=0;
    }

    public String getNotificacionMat() {
        return NotificacionMat;
    }

    public void setNotificacionMat(String notificacionMat) {
        NotificacionMat = notificacionMat;
    }

    public String getTiempoNotf() {
        return TiempoNotf;
    }

    public void setTiempoNotf(String tiempoNotf) {
        TiempoNotf = tiempoNotf;
    }

    public String getBloqCorte1() {
        return BloqCorte1;
    }

    public void setBloqCorte1(String bloqCorte1) {
        BloqCorte1 = bloqCorte1;
    }

    public String getBloqCorte2() {
        return BloqCorte2;
    }

    public void setBloqCorte2(String bloqCorte2) {
        BloqCorte2 = bloqCorte2;
    }

    public String getBloqCorte3() {
        return BloqCorte3;
    }

    public void setBloqCorte3(String bloqCorte3) {
        BloqCorte3 = bloqCorte3;
    }

    public String getBloqAnimo() {
        return BloqAnimo;
    }

    public void setBloqAnimo(String bloqAnimo) {
        BloqAnimo = bloqAnimo;
    }

    public String getTextAnimo() {
        return TextAnimo;
    }

    public void setTextAnimo(String textAnimo) {
        TextAnimo = textAnimo;
    }

    public String getHojaApuntes() {
        return HojaApuntes;
    }

    public void setHojaApuntes(String hojaApuntes) {
        HojaApuntes = hojaApuntes;
    }

    public String getDatosUltimaAct() {
        return DatosUltimaAct;
    }

    public void setDatosUltimaAct(String datosUltimaAct) {
        DatosUltimaAct = datosUltimaAct;
    }
}
