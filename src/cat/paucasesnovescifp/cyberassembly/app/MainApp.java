package cat.paucasesnovescifp.cyberassembly.app;

import java.util.ArrayList;
import java.util.List;

import cat.paucasesnovescifp.cyberassembly.model.CentralSoldadura;
import cat.paucasesnovescifp.cyberassembly.model.CintaTransportadora;
import cat.paucasesnovescifp.cyberassembly.model.SupervisorCentral;
import cat.paucasesnovescifp.cyberassembly.model.TecnicManteniment;
import cat.paucasesnovescifp.cyberassembly.tasks.AGVTask;
import cat.paucasesnovescifp.cyberassembly.tasks.RobotTask;
import cat.paucasesnovescifp.cyberassembly.tasks.SensorAvariesTask;
import cat.paucasesnovescifp.cyberassembly.ui.DashboardTask;

public class MainApp {
    public static void main(String[] args) {

        int nAgvs;
        int nRobots;
        int nEstacions;
        int capacitatCinta;

        while (true) {
            try {
                nAgvs = Integer.parseInt(IO.readln("Digues el nombre d'AGVs: "));
                nRobots = Integer.parseInt(IO.readln("Digues el nombre de robots: "));
                nEstacions = Integer.parseInt(IO.readln("Digues el nombre de estacions: "));
                capacitatCinta = Integer.parseInt(IO.readln("Digues la capaictat de la cinta: "));
                break;

            } catch (NumberFormatException e) {
                IO.println();
                IO.println("Has introduit un nombre malament!!!");
                IO.println();
            }
        }




        CintaTransportadora cintaTransportadora = new CintaTransportadora(capacitatCinta);
        CentralSoldadura centralSoldadura = new CentralSoldadura(nEstacions);
        SupervisorCentral supervisorCentral = new SupervisorCentral();
        supervisorCentral.activarAlerta();




        List<Thread> llistaFils = new ArrayList<>();

        for (int i = 0; i < nAgvs; i++) {
            Thread agv = new Thread(new AGVTask(i + 1, cintaTransportadora));
            llistaFils.add(agv);
            agv.start();
        }

        for (int i = 0; i < nRobots; i++) {
            Thread robot = new Thread(new RobotTask(i + 1, cintaTransportadora, centralSoldadura, supervisorCentral));
            llistaFils.add(robot);
            robot.start();
        }






        Thread sensorAvaries = new Thread(new SensorAvariesTask(centralSoldadura));
        sensorAvaries.setDaemon(true);
        sensorAvaries.start();

        Thread tecnicManteniment = new Thread(new TecnicManteniment(centralSoldadura));
        llistaFils.add(tecnicManteniment);
        tecnicManteniment.start();


        Thread dashboard = new Thread(new DashboardTask(cintaTransportadora, centralSoldadura, supervisorCentral));
        dashboard.setDaemon(true);
        dashboard.start();




        IO.println();
        IO.println("Execució durant 20 segons");
        IO.println();

        try {
            Thread.sleep(10000);
            supervisorCentral.desactivarAlerta();
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        




        for (Thread thread : llistaFils) {
            thread.interrupt();
        }

        for (Thread thread : llistaFils) {

            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }



        IO.println("===RESUM FINAL===");
        IO.println("AGVs emprats: " + nAgvs);
        IO.println("Robots emprats: " + nRobots);
        IO.println("Estacions de soldadura emprades: " + nEstacions);
        IO.println("Capacitat màxima de la cinta transportadora: " + capacitatCinta);
        IO.println("Total de bateries fabricades pels AGVs: " + cintaTransportadora.getTotalBateriesFabricades());
        IO.println("Total de soldadures registrades: " + centralSoldadura.getTotalSoldadures());
        IO.println("Total d'avaries registrades: " + centralSoldadura.getTotalAvaries());

    }

}
