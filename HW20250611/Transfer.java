
// Чтобы исправить этот баг, можно:
//        - Упорядочить блокировки – всегда блокировать ресурсы в одном порядке.
//        - Использовать tryLock из ReentrantLock – позволяет избежать бесконечного ожидания.
//        - Применить глобальный блокировочный объект – один общий монитор для всех переводов.
// Пример исправленного кода с упорядоченной блокировкой:


import Bank.Account;

public class Transfer implements Runnable {
    private final Account from;
    private final Account to;
    private final int amount;

    public Transfer(Account from, Account to, int amount, int amount1) {
        this.from = from;
        this.to = to;
        this.amount = amount1;
    }

    @Override
    public void run() {
        Account first = from.getAccNumber() < to.getAccNumber() ? from : to;
        Account second = from.getAccNumber() < to.getAccNumber() ? to : from;

        synchronized (first) {
            synchronized (second) {
                from.debit(amount);
                to.credit(amount);
            }
        }
    }
}