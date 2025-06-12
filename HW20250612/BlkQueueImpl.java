//                           Задача 1
//
//        Реализация очереди блокировки для примера производитель-потребитель
//
//        • Найдите класс ait.mediation.BlkQueueImpl и напишите реализацию всех методов, отмеченных как //TODO
//         (удалив код, вызывающий в данный момент исключения).
//        • Запустите проект и убедитесь, что у вас правильно работает пример «Производитель-потребитель».

//        Советы по домашнему заданию:
//
//        • Класс BlkQueueImpl будет реализовывать блокирующую очередь, которая работает как Message Box в классной
//        работе, где push-post, pop-get и количество сообщений в очереди <= maxSize (вместо 1 в Message Box). Логика
//        блокировки такая же, как и в Message Box:
//        ◦ Блокирует операцию pop() для потребителей (читателей), пока она пуста.
//        ◦ Блокирует операцию push() производителей (писателей), пока он заполнен (количество элементов maxSize).
//        • Используйте LinkedList как внутреннюю реализацию очереди в классе BlkQueue.
//        • Изучите примеры MessageBox из классной, чтобы реализовать правильную логику синхронизации.

// =================================================================================

//        Вот реализация класса BlkQueueImpl, использующего ReentrantLock и Condition для синхронизации.
//        Этот код реализует блокирующую очередь с ограниченным размером, используя ReentrantLock для
//        управления доступом к очереди и Condition для ожидания, когда очередь станет доступной. Теперь ваш
//        класс BlkQueueImpl полностью функционален!

// =================================================================================

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlkQueueImpl<T> implements BlkQueue<T> {
    private final Queue<T> queue;
    private final int maxSize;
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();

    public BlkQueueImpl(int maxSize) {
        this.maxSize = maxSize;
        this.queue = new LinkedList<>();
    }

    @Override
    public void push(T message) {
        lock.lock();
        try {
            while (queue.size() >= maxSize) {
                notFull.await();
            }
            queue.add(message);
            notEmpty.signal();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public T pop() {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                notEmpty.await();
            }
            T message = queue.poll();
            notFull.signal();
            return message;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            lock.unlock();
        }
    }
}






