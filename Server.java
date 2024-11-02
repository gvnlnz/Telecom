public class Server {
    protected int codServer;
    protected Pkt pkt;

    public Server (int codServer, Pkt pkt) {
        this.codServer = codServer;
        this.pkt = pkt;
    }

    public int getCodServer() {
        return codServer;
    }

    public double getTempoServizio() {
        return pkt.getTempoServizio();
    }

    public void setPkt(Pkt pkt) {
        this.pkt = pkt;
    }

    public void reset() {
            Pkt pktNull = new Pkt(0, 0, 0, 0);
            this.pkt = pktNull;
    }

    public Pkt getPkt() {
        return pkt;
    }


    // public String toString() {
    //     return "Server: " + codServer + "; Tempo di servizio: " + tempoServizio + "; Codice pacchetto: " + codPkt;
    // }
}
