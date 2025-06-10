package ait.elevator.task;

import ait.elevator.task.Elevator;

public class Truck implements Runnable {
    private final static Object monitor = new Object();
    private int nRaces;
    private int capacity;
    private Elevator elevator1;
    private Elevator elevator2;

    public Truck(int nRaces, int capacity, Elevator elevator1, Elevator elevator2) {
        this.nRaces = nRaces;
        this.capacity = capacity;
        this.elevator1 = elevator1;
        this.elevator2 = elevator2;
    }

    @Override
    public void run() {
        for (int i = 0; i < nRaces; i++) {
            synchronized (monitor) {
                int halfCapacity = capacity / 2;
                elevator1.add(halfCapacity);
                elevator2.add(halfCapacity);
            }
        }
    }
}