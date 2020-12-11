class Account {

    private volatile long balance = 0;

    public synchronized boolean withdraw(long amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }

    public synchronized void deposit(long amount) {
        // do something useful
        balance += amount;
    }

    public long getBalance() {
        return balance;
    }
}