public class MMcSystem {
    public static void main(String[] args) {

        double t = 0; // tempo attuale in secondi

        //dati statistiche
        int pktArrivati = 0; // numero di pacchetti 
        // int nPktServiti = 0; // numero di pacchetti serviti      
        // double Ws = 0; // tempo medio trascorso in sistema
        // double WsTot = 0; // somma di tutt i itempi di permanenza nel sistema
        int pktSistema = 0; // numero di pacchetti nel sistema
        // int cambiamentiStato = 0;
        
        // inizializzo il sistema
        MMC sistema = new MMC(8, 5, 3); // lamba, mu, numero servitori

        // inizializzo i servitori
        for (int codiceServer = 1; codiceServer <= sistema.getC(); codiceServer++) {
            Pkt pkt = new Pkt(0, 0, 0, 0); // pacchetto vuoto
            sistema.addServer(codiceServer, pkt); // aggiungo il server alla lista dei servitori
        }

        for (Server s : sistema.getServitori()) {
            double tempoArrivoPkt = t + sistema.randomExponentialLam(); // tempo arrivo 1° pacchetto
            double tempoServizioPkt = tempoArrivoPkt + sistema.randomExponentialMu(); // tempo di servizio del pacchetto
            Pkt pkt = new Pkt(s.getCodServer(), tempoArrivoPkt, tempoServizioPkt, 0); //per ogni server creo un pacchetto
            s.setPkt(pkt); //diamo al servitore il pacchetto da servire
            
            //s.setServer(tempoServizioPkt, pkt.getCodPkt()); // tempo in cui il server è occupato da quel pacchetto
        }

        double tempoArrivoPkt = t + sistema.randomExponentialLam(); // tempo arrivo 4° pacchetto


        int serverPieni = sistema.getC(); // server pieni inizialmente = c

        while (t < 60) { // finchè il tempo attuale è minore di 5 secondi
            double minTime = tempoArrivoPkt; 
            for(Server s : sistema.getServitori()) { // scorro i server per trovare quello con il servizio minore
                if(s.getTempoServizio() < minTime && s.getTempoServizio() != 0) { // se il tempo di servizio del server è minore del minimo
                    minTime = s.getTempoServizio(); //troviamo il min tra i tempi di servizio 
                }
            }

            t = minTime; // tempo attuale è il minimo tra i tempi di servizio e il tempo di arrivo del pacchetto

            if(tempoArrivoPkt == t) { // se t = tempoArrivo del pacchetto
                pktArrivati++; // incremento il numero di pacchetti arrivati
                Pkt pkt = new Pkt(sistema.getC() + pktArrivati, tempoArrivoPkt, 0, 0); // creo un nuovo pacchetto
                serverPieni = 0; // server pieni = 0
                for(Server s : sistema.getServitori()) { // per ogni server
                    if(s.getTempoServizio() > 0) // se il server è pieno
                        serverPieni++; // incremento il numero di server pieni
                }
                pktSistema = serverPieni + sistema.getCoda().size(); // numero di pacchetti nel sistema
                sistema.addCambiamentiStato(pktSistema, t);

                if(serverPieni == sistema.getC()) { // se tutti i server sono pieni
                    sistema.getCoda().add(pkt); // server pieni, pkt in coda
                    sistema.addCambiamentiStatoCoda(sistema.getCoda().size(), t);
                } else { // c'è almeno un server libero
                    for(Server s : sistema.getServitori()){
                        if(s.getTempoServizio() == 0) {
                            double tempoServizioPkt = t + sistema.randomExponentialMu(); // tempo di servizio del pacchetto
                            pkt.setTempoServizio(tempoServizioPkt); // setto il tempo di servizio del pacchetto
                            s.setPkt(pkt); //diamo al servitore vuoto il pacchetto da servire
                            break; // esco dal ciclo    
                        }
                    }
                }
                tempoArrivoPkt = t + sistema.randomExponentialLam(); //arrivo 5° pkt
            
            } else { // t = tempoServizio
                for(Server s : sistema.getServitori()) {
                    if (s.getTempoServizio() == t){     // se il tempo di servizio del server è uguale al tempo attuale
                        sistema.addPktServiti(s.getPkt());  // aggiungo il pacchetto servito alla lista dei pacchetti serviti
                        s.reset(); // libero il server
                        serverPieni--;
                        pktSistema = serverPieni + sistema.getCoda().size(); // numero di pacchetti nel sistema
                        sistema.addCambiamentiStato(pktSistema, t);

                        if(sistema.getCoda().size() > 0) {
                            Pkt pktInCoda = sistema.getCoda().get(0); // prendo il primo pkt in coda
                            double tempoServizioPkt = t + sistema.randomExponentialMu(); // tempo di servizio del pacchetto
                            pktInCoda.setTempoServizio(tempoServizioPkt); // setto il tempo di servizio del pacchetto
                            pktInCoda.setTempoCoda(t - pktInCoda.getTempoArrivo()); // tempo trascorso in coda
                            s.setPkt(pktInCoda); //diamo al servitore vuoto il pacchetto da servire che è in coda
                            serverPieni++; // incremento il numero di server pieni
                            sistema.getCoda().remove(0); // rimuovo il pkt dalla coda
                            sistema.addCambiamentiStatoCoda(sistema.getCoda().size(), t);
                        }
                        break;
                    }
                }
            }
            
        }
        //System.out.println("Pacchetti arrivati: " + pktArrivati);
        System.out.println("Ls: " + sistema.getLs());
        System.out.println("Lq: " + sistema.getLq());
        System.out.println("Ws: " + sistema.getWs());
        System.out.println("Wq: " + sistema.getWq());
    }

}
