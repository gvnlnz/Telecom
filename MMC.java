import java.util.*;

public class MMC {
    public int lam, mu, c; 
    protected List<Pkt> coda, pktServiti;
    protected List<Server> servitori;

    public MMC(int lam, int mu, int c) {
        this.lam = lam;
        this.mu = mu;
        this.c = c;
        coda = new LinkedList<Pkt>();
        servitori = new LinkedList<Server>();
        pktServiti = new LinkedList<Pkt>();
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
}
