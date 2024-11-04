public class cambStato {
    protected int nPkt;
    protected double tempo;

    public cambStato(int nPkt, double tempo) {
        this.nPkt = nPkt;
        this.tempo = tempo;
    }

    public int getnPkt() {
        return nPkt;
    }

    public double getTempo() {
        return tempo;
    }
}
