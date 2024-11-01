import java.util.*;

public class MMC {
    public int lam, mu, c; 
    protected List<Pkt> pacchetti, coda;
    protected List<Server> servitori;

    public MMC(int lam, int mu, int c) {
        this.lam = lam;
        this.mu = mu;
        this.c = c;
        pacchetti = new LinkedList<Pkt>();
        coda = new LinkedList<Pkt>();
        servitori = new LinkedList<Server>();
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

    //restituisce lista pacchetti
    public List<Pkt> getPacchetti() {
        return pacchetti;
    }

    //aggiungo pacchetto alla lista pacchetti
    public void addPkt(Pkt pkt) { // quando un pkt arriva, non so nè se aspetterà nè quando verrà servito
        pacchetti.add(pkt);
    }

    public int getNumPkt() {
        if(pacchetti == null)
            return 0;
        return pacchetti.size();
    }

    //mi restituisce la lista dei server
    public List<Server> getServitori() {
        return servitori;
    }

    public int getServerPieni() {
        int serverPieni = 0;
        for(Server s : servitori) {
            if(s.getTempoServizio() > 0)
                serverPieni++;
        }
        return serverPieni;
    }

    //aggiungo server alla lista servitori (utilizzata solo nell'inizializzazione)
    public void addServer(int codServer) {
        servitori.add(new Server(codServer));
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
}
