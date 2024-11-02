public class Pkt {
    protected int codPkt;
    protected double tempoArrivo, tempoServizio, tempoCoda;

    public Pkt(int codPkt, double tempoArrivo, double tempoServizio, double tempoCoda) {
        this.codPkt = codPkt;
        this.tempoArrivo = tempoArrivo;
        this.tempoServizio = tempoServizio;
        this.tempoCoda = tempoCoda;
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

    public double getTempoCoda() {
        return tempoCoda;
    }

    public void setTempoCoda(double tempoCoda) {
        this.tempoCoda = tempoCoda;
    }

    public String toString() {
        return "Cod: " + codPkt + "; Tempo di arrivo: " + tempoArrivo + "; Tempo di servizio: " + tempoServizio;
    }
}
