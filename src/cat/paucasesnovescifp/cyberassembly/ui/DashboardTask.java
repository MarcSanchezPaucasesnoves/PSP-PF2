package cat.paucasesnovescifp.cyberassembly.ui;

import cat.paucasesnovescifp.cyberassembly.model.CentralSoldadura;
import cat.paucasesnovescifp.cyberassembly.model.CintaTransportadora;
import cat.paucasesnovescifp.cyberassembly.model.SupervisorCentral;

public class DashboardTask implements Runnable{
    private final CintaTransportadora cintaTransportadora;
    private final CentralSoldadura centralSoldadura;
    private final SupervisorCentral supervisorCentral;


    public DashboardTask(CintaTransportadora cintaTransportadora, CentralSoldadura centralSoldadura, SupervisorCentral supervisorCentral) {
        this.cintaTransportadora = cintaTransportadora;
        this.centralSoldadura = centralSoldadura;
        this.supervisorCentral = supervisorCentral;
    }


    public void mostrarEstat() {
        IO.println("====ESTAT CYBERASSEMBLY DASHBOARD====");
        IO.println("Alerta Superescalfament: " + (supervisorCentral.isAlertaActiva() ? "ACTIVA" : "INACTIVA") );

        boolean[] estacionsOcupades = centralSoldadura.getEstacionsOcupades();
        boolean[] estacionsAvariades = centralSoldadura.getEstacionsAvariades();
        String[] bateries = centralSoldadura.getBateriesEnProces();

        IO.println("----Estacions de Soldadura----");

        for (int i = 0; i < estacionsOcupades.length; i++) {
            String estatEstacio = estacionsAvariades[i] ? "AVARIADA" : (estacionsOcupades[i] ? "OCUPADA (" + bateries[i] + ")" : "DISPONIBLE");
            IO.println("La estació " + i + " está " + estatEstacio);
        }
        
        IO.println("");
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(2000);
                mostrarEstat();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
