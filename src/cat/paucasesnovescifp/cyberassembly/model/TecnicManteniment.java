package cat.paucasesnovescifp.cyberassembly.model;

import java.util.Random;

public class TecnicManteniment implements Runnable{
    private final CentralSoldadura centralSoldadura;
    private final Random random;


    public TecnicManteniment(CentralSoldadura centralSoldadura) {
        this.centralSoldadura = centralSoldadura;
        random = new Random();
    }

    @Override
    public void run() {

        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(random.nextInt(1000) + 1000);

                boolean[] avaries = centralSoldadura.getEstacionsAvariades();

                for (int i = 0; i < avaries.length; i++) {
                    if (avaries[i]) {
                        Thread.sleep(5000);

                        centralSoldadura.repararEstacio(i);
                    }
                }
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
