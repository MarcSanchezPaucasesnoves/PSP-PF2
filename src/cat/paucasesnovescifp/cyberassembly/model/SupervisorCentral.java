package cat.paucasesnovescifp.cyberassembly.model;

public class SupervisorCentral {
    private boolean alertaActiva;

    
    
    public SupervisorCentral() {
        this.alertaActiva = false;
    }

    public synchronized void verificarEstat(int idRobot) throws InterruptedException {
        while (alertaActiva) {
            wait();
        }
    }

    public synchronized void activarAlerta() {
        alertaActiva = true;
    }

    public synchronized void desactivarAlerta() {
        alertaActiva = false;
        notifyAll();
    }

    
    public synchronized boolean isAlertaActiva() {
        return alertaActiva;
    }
}
