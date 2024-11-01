public class MMcSystem {
    public static void main(String[] args) {

        double t = 0; // tempo attuale in secondi
        
        // inizializzo il sistema
        MMC sistema = new MMC(8, 5, 3); // lamba, mu, numero servitori

        // inizializzo i servitori
        for (int codiceServer = 1; codiceServer <= sistema.getC(); codiceServer++) {
            sistema.addServer(codiceServer);
        }

        //riempimento iniziale dei server con pacchetti e relativi tempi di servizio
        for (Server s : sistema.getServitori()) {
            double tempoArrivoPkt = t + sistema.randomExponentialLam(); // tempo arrivo 1° pacchetto
            Pkt pkt = new Pkt(s.getCodServer(), tempoArrivoPkt); //per ogni server creo un pacchetto
            sistema.addPkt(pkt); //aggiungo il pacchetto alla lista dei pacchetti
            double tempoServizioPkt = tempoArrivoPkt + sistema.randomExponentialMu(); // tempo di servizio del pacchetto
            pkt.setTempoServizio(tempoServizioPkt); // setto il tempo di servizio del pacchetto
            s.setServer(tempoServizioPkt, pkt.getCodPkt()); // tempo in cui il server è occupato da quel pacchetto
        }

        double tempoArrivoPkt = (System.currentTimeMillis() / 1000) + sistema.randomExponentialLam(); // tempo arrivo 4° pacchetto
        Pkt pkt = new Pkt(sistema.getC() + 1, tempoArrivoPkt);
        sistema.addPkt(pkt); // 4^ pacchetto aggiunto alla lista dei pacchetti

        while (t < 5) { // durante Tobs
            double minTime = tempoArrivoPkt; 
            for(Server s : sistema.getServitori()) { // scorro i server per trovare quello con il servizio minore
                if(s.getTempoServizio() < minTime) { // se il tempo di servizio del server è minore del minimo
                    minTime = s.getTempoServizio(); //troviamo il min tra i tempi di servizio 
                }
            }

            t = minTime; // tempo attuale è il minimo tra i tempi di servizio e il tempo di arrivo del pacchetto

            if(tempoArrivoPkt == t) { // se t = tempoArrivo del pacchetto

                if(sistema.getServerPieni() == sistema.getC()) { // se tutti i server sono pieni
                    sistema.getCoda().add(pkt); // server pieni, pkt in coda
                    pkt.setTempoEntrataCoda(t); // il pkt entra in coda al tempo t = minTime
                } else { // c'è almeno un server libero
                    for(Server s : sistema.getServitori()){
                        if(s.getTempoServizio() == 0) {
                            double tempoServizioPkt = t + sistema.randomExponentialMu(); // tempo di servizio del pacchetto
                            pkt.setTempoServizio(tempoServizioPkt); // setto il tempo di servizio del pacchetto
                            s.setServer(tempoServizioPkt, pkt.getCodPkt()); //diamo al servitore vuoto il pacchetto da servire
                            break; // esco dal ciclo    
                        }
                    }
                }
                tempoArrivoPkt = t + sistema.randomExponentialLam(); //arrivo 5° pkt
                pkt = new Pkt(sistema.getPacchetti().size() + 1, tempoArrivoPkt);
                sistema.addPkt(pkt); //aggiungo il pacchetto alla lista dei pacchetti
                
            
            } else { // t = tempoServizio
                for(Server s : sistema.getServitori()) {
                    if(s.getTempoServizio() == t) { // se il tempo di servizio del server è uguale al tempo attuale
                        for(Pkt p : sistema.getPacchetti()) {
                            if(p.getTempoServizio() == t) { // se il tempo di servizio del pacchetto è uguale al tempo attuale
                                s.resetTempoServizio(); // libero il server
                                break; // smetto di scorrere i pkt
                            }
                        }
                        if(sistema.getCoda().size() > 0) {
                            Pkt pktInCoda = sistema.getCoda().get(0); // prendo il primo pkt in coda
                            double tempoServizioPkt = t + sistema.randomExponentialMu(); // tempo di servizio del pacchetto
                            pktInCoda.setTempoServizio(tempoServizioPkt); // setto il tempo di servizio del pacchetto
                            s.setServer(tempoServizioPkt, pktInCoda.getCodPkt()); //diamo al servitore vuoto il pacchetto da servire che è in coda
                            sistema.getCoda().remove(0); // rimuovo il pkt dalla coda
                        }
                    }
                }
            }
            // if (serverPieni == sistema.getC()) { // se tutti i server sono pieni
            //     System.out.println("Server pieni, pacchetto in coda");
            //     sistema.addCoda(pkt); // ultimo arrivato va in coda
            //     pkt.setTempoAttesa((System.currentTimeMillis() / 1000) - tempoArrivoPkt); // setto tempo attesa pacchetto
            //     System.out.println("Pkt numero " + pkt.getCodPkt() + " in coda al tempo " + pkt.getTempoArrivo());

            // } else { // c'è almeno un server libero
            //     double TempoAttesaMax = 0; // inizializzo tempo attesa massimo
            //     Iterator<Pkt> iterator = sistema.getCoda().iterator(); // scorro la coda
            //     while (iterator.hasNext()) { // finchè ci sono elementi nella coda
            //         Pkt currentPkt = iterator.next(); // guardo pacchetto corrente
            //         if (currentPkt.getTempoAttesa() > TempoAttesaMax) { // se il tempo di attesa del pacchetto corrente
            //                                                             // è maggiore del tempo di attesa massimo
            //             TempoAttesaMax = currentPkt.getTempoAttesa(); // diventa il nuovo tempo di attesa massimo
            //         }
            //     }
            //     iterator = sistema.getCoda().iterator(); // reset iterator to start of the queue
            //     while (iterator.hasNext()) { // iterate again to find and remove the packet with max wait time
            //         Pkt currentPkt = iterator.next();
            //         if (currentPkt.getTempoAttesa() == TempoAttesaMax) {
            //             t = System.currentTimeMillis() / 1000.0; // aggiorno il tempo a questo istante
            //             currentPkt.setTempoServizio(t + sistema.randomExponentialMu()); // tempo di servizio del pacchetto pari a adesso più esponenziale
            //             System.out.println("Pkt numero " + currentPkt.getCodPkt() + " servito al tempo " + currentPkt.getTempoServizio());
            //             serverPieni--; // libero un server
            //             System.out.println("Server liberi al momento: " + (sistema.getC() - serverPieni));
            //             iterator.remove(); // rimuovo dalla coda il pacchetto servito
            //             System.out.println("Pkt numero " + currentPkt.getCodPkt() + " rimosso dalla coda");
            //             break;
            //         }
            //     }
            // }
        }
    }

}
