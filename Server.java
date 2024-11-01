public class Server {
    protected int codServer;
    protected double tempoServizio; // tempo nel quale il server non può ricevere altri pacchetti
    protected int codPkt;

    public Server (int codServer) {
        this.codServer = codServer;
        tempoServizio = 0;
        codPkt = 0;
    }

    public int getCodServer() {
        return codServer;
    }

    public double getTempoServizio() {
        return tempoServizio;
    }

    public void setServer(double tempoServizioPkt, int codPkt) {
        this.tempoServizio = tempoServizioPkt;
        this.codPkt = codPkt;
    }

    public void resetTempoServizio() {
        this.tempoServizio = 0;
        this.codPkt = 0;
    }

    public int getCodPkt() {
        return codPkt;
    }

    public void setCodPkt(int codPkt) {
        this.codPkt = codPkt;
    }


    public String toString() {
        return "Server: " + codServer + "; Tempo di servizio: " + tempoServizio + "; Codice pacchetto: " + codPkt;
    }
}
