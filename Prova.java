public class Prova {
    public static void main(String[] args) {
        while (true) {
            long currentTimeMillis = System.currentTimeMillis() / 1000; // ottieni il tempo attuale in millisecondi e convertilo in secondi
            long seconds = currentTimeMillis  % 60; // ottieni i secondi all'interno di un minuto
            System.out.println(seconds);
            try {
                Thread.sleep(1000); // aspetta un secondo prima di stampare di nuovo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}