package cat.paucasesnovescifp.cyberassembly.model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CintaTransportadora {
    private final ReentrantLock lock;
    private final int capacitatMaxima;
    private final Queue<String> buffer;
    private final Condition cintaNoPlena;
    private final Condition cintaNoBuida;
    private int totalBateriesFabricades = 0;

    public CintaTransportadora(int capacitatMaxima) {
        lock = new ReentrantLock();
        this.capacitatMaxima = capacitatMaxima;
        buffer = new LinkedList<>();
        cintaNoPlena = lock.newCondition();
        cintaNoBuida = lock.newCondition();
    }

    public void afegirBateria(int idAgv, int comptador) throws InterruptedException {
        lock.lock();
        try {

            while (buffer.size() == capacitatMaxima) {
                cintaNoPlena.await();
            }

            String bateria = "BateriaAVG" + idAgv + "N" + comptador;
            buffer.offer(bateria);
            totalBateriesFabricades++;
            cintaNoBuida.signal();

        } finally {
            lock.unlock();
        }
    }

    public String agafarBateria(int idRobot) throws InterruptedException {
        lock.lock();
        try {

            while (buffer.isEmpty()) {
                cintaNoBuida.await();
            }

            String bateria = buffer.poll();
            cintaNoPlena.signal();
            return bateria;

        } finally {
            lock.unlock();
        }
    }

    
    public synchronized int getTotalBateriesFabricades() {
        return totalBateriesFabricades;
    }
}
