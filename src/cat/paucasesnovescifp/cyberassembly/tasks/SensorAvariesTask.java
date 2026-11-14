package cat.paucasesnovescifp.cyberassembly.tasks;

import java.util.Random;
import cat.paucasesnovescifp.cyberassembly.model.CentralSoldadura;

public class SensorAvariesTask implements Runnable{
    private final CentralSoldadura centralSoldadura;
    private final Random random;


    public SensorAvariesTask(CentralSoldadura centralSoldadura) {
        this.centralSoldadura = centralSoldadura;
        random = new Random();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(random.nextInt(8000) + 2000);

                int estacioAleatoria = random.nextInt(centralSoldadura.getEstacionsOcupades().length);

                centralSoldadura.produirAvaria(estacioAleatoria);

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
}
