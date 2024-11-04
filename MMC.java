import java.util.*;

public class MMC {
    public int lam, mu, c; 
    protected List<Pkt> coda, pktServiti;
    protected List<Server> servitori;
    protected List<cambStato> cambiamentiStato;
    protected List<cambStato> cambiamentiStatoCoda;

    public MMC(int lam, int mu, int c) {
        this.lam = lam;
        this.mu = mu;
        this.c = c;
        coda = new LinkedList<Pkt>();
        servitori = new LinkedList<Server>();
        pktServiti = new LinkedList<Pkt>();
        cambiamentiStato = new LinkedList<cambStato>();
        cambiamentiStatoCoda = new LinkedList<cambStato>();
    }
    //parametri del sistema MMC
    public int getLam() {
        return lam;
    }

    public int getMu() {
        return mu;
    }

    public int getC() {
        return c;
    }

    //ottenere la coda
    public List<Pkt> getCoda() {
        return coda;
    }

    //aggiungere un pacchetto alla coda
    public void addCoda(Pkt pkt) {
        coda.add(pkt);
    }

    //mi restituisce la lista dei server
    public List<Server> getServitori() {
        return servitori;
    }

    //aggiungo server alla lista servitori (utilizzata solo nell'inizializzazione)
    public void addServer(int codServer, Pkt pkt) {
        servitori.add(new Server(codServer, pkt));
    }


    //funzione che calcola l'esponenziale random per gli arrivi
    public double randomExponentialLam() {
        Random rand = new Random();
        return Math.log(1 - rand.nextDouble()) / (-this.lam);
    }
    
    public double randomExponentialMu() {
        Random rand = new Random();
        return Math.log(1 - rand.nextDouble()) / (-this.mu);
    }

    public void addPktServiti(Pkt pkt) {
        pktServiti.add(pkt);
    }

    public void addCambiamentiStato(int nPkt, double tempo) {
        cambiamentiStato.add(new cambStato(nPkt, tempo));
    }

    public void addCambiamentiStatoCoda(int nPkt, double tempo) {
        cambiamentiStatoCoda.add(new cambStato(nPkt, tempo));
    }

    public double getLs(){
        double num = 0;
        double ultimoCambiamento = 0;
        for(cambStato cs : cambiamentiStato) {
            double t = cs.getTempo();
            int n = cs.getnPkt();
            num += n * (t - ultimoCambiamento);
            ultimoCambiamento = t;
        }
        if(ultimoCambiamento == 0){
            return 0;
        }
        else{
            return num / ultimoCambiamento;
        }
    }

    public double getLq(){
        double num = 0;
        double ultimoCambiamento = 0;
        for(cambStato cs : cambiamentiStatoCoda) {
            double t = cs.getTempo();
            double n = cs.getnPkt();
            num += n * (t - ultimoCambiamento);
            ultimoCambiamento = t;
        }
        if(ultimoCambiamento == 0){
            return 0;
        }
        else{
            return num / ultimoCambiamento;
        }
    }

    public double getWs(){
        double tot = 0;
        for(Pkt pkt : pktServiti) {
            tot += pkt.getTempoServizio() - pkt.getTempoArrivo();
        }
        return tot / pktServiti.size();
    }

    public double getWq(){
        double tot = 0;
        for(Pkt pkt : pktServiti) {
            tot += pkt.getTempoCoda();
        }
        return tot / pktServiti.size();
    }
}
