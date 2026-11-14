package cat.paucasesnovescifp.cyberassembly.tasks;

import java.util.Random;

import cat.paucasesnovescifp.cyberassembly.model.CentralSoldadura;
import cat.paucasesnovescifp.cyberassembly.model.CintaTransportadora;
import cat.paucasesnovescifp.cyberassembly.model.SupervisorCentral;

public class RobotTask implements Runnable{
    private final int id;
    private final CintaTransportadora cintaTransportadora;
    private final CentralSoldadura centralSoldadura;
    private final SupervisorCentral supervisorCentral;
    private final Random random;


    public RobotTask(int id, CintaTransportadora cintaTransportadora, CentralSoldadura centralSoldadura, SupervisorCentral supervisorCentral) {
        this.id = id;
        this.cintaTransportadora = cintaTransportadora;
        this.centralSoldadura = centralSoldadura;
        this.supervisorCentral = supervisorCentral;
        random = new Random();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                supervisorCentral.verificarEstat(id);

                String bateria = cintaTransportadora.agafarBateria(id);

                int estacioAssignada = centralSoldadura.agafarEstacio(id);

                centralSoldadura.assignarBateriaEstacio(estacioAssignada, bateria);

                Thread.sleep(random.nextInt(2000) + 1000);

                centralSoldadura.alliberarEstacio(id, estacioAssignada);

                centralSoldadura.registreCentral(id);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
