package cat.paucasesnovescifp.cyberassembly.model;

import java.util.concurrent.Semaphore;

public class CentralSoldadura {
    private Semaphore semaforEstacions;
    private Semaphore semaforRegistreCentral;
    private boolean[] estacionsOcupades;
    private boolean[] estacionsAvariades;
    private int nombreEstacions;


    public CentralSoldadura(int nombreEstacions) {
        this.nombreEstacions = nombreEstacions;
        semaforEstacions = new Semaphore(nombreEstacions, true);
        semaforRegistreCentral = new Semaphore(1, true);
        estacionsOcupades = new boolean[nombreEstacions];
        estacionsAvariades = new boolean[nombreEstacions];
    }

    public int agafarEstacio(int idRobot) throws InterruptedException {

        while (true) {
            semaforEstacions.acquire();

            synchronized (this) {
                for (int i = 0; i < nombreEstacions; i++) {
                    if (!estacionsOcupades[i] && !estacionsAvariades[i]) {
                        estacionsOcupades[i] = true;

                        int estacioAssignada = i;
                        return estacioAssignada;
                    }
                }
            }

            semaforEstacions.release();
            Thread.sleep(50);
        }
    }

    public void alliberarEstacio(int idRobot, int estacio) {
        
        synchronized (this) {
            estacionsOcupades[estacio] = false;
        }

        semaforEstacions.release();
    }

    public void registreCentral(int id) throws InterruptedException {
        semaforRegistreCentral.acquire();

        try {
            Thread.sleep(100);

        } finally {
            semaforRegistreCentral.release();
        }
    }



    public void produirAvaria(int estacio) {
        
        synchronized (this) {
            estacionsAvariades[estacio] = true;
        }
    }

    public void repararEstacio(int estacio) {

        synchronized (this) {
            estacionsAvariades[estacio] = false;
        }
    }



    public synchronized boolean[] getEstacionsOcupades() {
        return estacionsOcupades.clone();
    }

    public synchronized boolean[] getEstacionsAvariades() {
        return estacionsAvariades.clone();
    }

}
