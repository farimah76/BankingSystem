package azki.project.services.ObserveLogger;

public interface TransactionLogger {
    void logTransaction(String accountNumber,String transactionType,double amount);
}
