import java.time.*;

public class Prova {
    public static void main(String[] args) {
        // while (true) {
            try {
                MMC mmc = new MMC(8, 3, 3);
                int Tobs = 60;
                double time = 0;
                while( time < Tobs) {
                    System.out.println("t = " + time);
                    System.out.println("t arrotondato = " + time);
                    Thread.sleep(1000); // aspetta un secondo prima di stampare di nuovo
                    time = LocalTime.now().toSecondOfDay() % 60 + mmc.randomExponentialLam();
                    time = Math.round(time * 1000.0) / 1000.0;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        // }
    }
}