package cat.paucasesnovescifp.cyberassembly.tasks;

import java.util.Random;
import cat.paucasesnovescifp.cyberassembly.model.CintaTransportadora;

public class AGVTask implements Runnable{
    private final int id;
    private int comptadorBateria;
    private final CintaTransportadora cintaTransportadora;
    private final Random random;


    public AGVTask(int id, CintaTransportadora cintaTransportadora) {
        this.id = id;
        this.cintaTransportadora = cintaTransportadora;
        comptadorBateria = 0;
        random = new Random();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                cintaTransportadora.afegirBateria(id, comptadorBateria);
                comptadorBateria++;

                Thread.sleep(random.nextInt(400) + 200);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
