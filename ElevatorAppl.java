package ait.elevator;

import ait.elevator.task.Elevator;
import ait.elevator.task.Truck;

//    Что изменилось?
//        - Добавлен второй элеватор (elevator2).
//        - Грузовики теперь выгружают зерно в два элеватора:
//        - Половина зерна идет в elevator1.
//- Половина зерна идет в elevator2.
//- Синхронизация:
//        - Используется synchronized (monitor), чтобы избежать гонки потоков при обновлении объема элеваторов.
//Этот код гарантирует, что зерно распределяется равномерно между двумя элеваторами без потерь и с эффективной синхронизацией.


public class ElevatorAppl {
    private static final int N_TRUCK = 10_000;
    private static final int N_RACES = 10;
    private static final int CAPACITY = 20;

    public static void main(String[] args) throws InterruptedException {
        Elevator elevator1 = new Elevator("V. I. Lenin");
        Elevator elevator2 = new Elevator("Karl Marx");

        Truck[] trucks = new Truck[N_TRUCK];
        for (int i = 0; i < trucks.length; i++) {
            trucks[i] = new Truck(N_RACES, CAPACITY, elevator1, elevator2);
        }

        Thread[] threads = new Thread[trucks.length];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(trucks[i]);
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }

        System.out.println("Elevator " + elevator1.getName() + " has " + elevator1.getCurrentVolume());
        System.out.println("Elevator " + elevator2.getName() + " has " + elevator2.getCurrentVolume());
    }
}


