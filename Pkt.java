public class Pkt {
    protected int codPkt;
    protected double tempoArrivo, tempoServizio, tempoEntrataCoda, tempoEntrataServer;

    public Pkt(int codPkt, double tempoArrivo) {
        this.codPkt = codPkt;
        this.tempoArrivo = tempoArrivo;
        tempoServizio = 0;
        tempoEntrataCoda = 0;
        tempoEntrataServer = 0;
    }

    public int getCodPkt() {
        return codPkt;
    }

    public double getTempoArrivo() {
        return tempoArrivo;
    }

    public void setTempoArrivo(double tempoArrivo) {
        this.tempoArrivo = tempoArrivo;
    }

    public double getTempoServizio() {
        return tempoServizio;
    }

    public void setTempoServizio(double tempoServizio) {
        this.tempoServizio = tempoServizio;
    }

    public double getTempoEntrataCoda() {
        return tempoEntrataCoda;
    }

    public void setTempoEntrataCoda(double tempoEntrataCoda) {
        this.tempoEntrataCoda = tempoEntrataCoda;
    }

    public double getTempoEntrataServer() {
        return tempoEntrataServer;
    }

    public void setTempoEntrataServer(double tempoEntrataServer) {
        this.tempoEntrataServer = tempoEntrataServer;
    }

    public double getDifferenza() {
        return tempoServizio - tempoArrivo;
    }

    public String toString() {
        return "Cod: " + codPkt + "; Tempo di arrivo: " + tempoArrivo + "; Tempo di servizio: " + tempoServizio + "; " + getDifferenza();
    }
}
