package azki.project.services;

import azki.project.model.Account;
import azki.project.model.Transaction;
import azki.project.model.TransferBalanceRequest;
import azki.project.repository.AccountRepository;
import azki.project.repository.TransactionRepository;
import azki.project.services.ObserveLogger.TransactionLogger;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    TransactionLogger transactionLogger;
    private Map<String,Account> accounts;

    public AccountService(TransactionLogger transactionLogger) {
        accounts=new HashMap<>();
        this.transactionLogger = transactionLogger;
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Optional<Account> getAccount(Long id) {
        return accountRepository.findById(id);
    }

    public Account deposit(String accountNumber, double amount) {
        Account account = getAccount(Long.valueOf(accountNumber)).orElseThrow(() -> new RuntimeException("Account not found"));
        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    public Account withdraw(Long id, double amount) {
        Account account = getAccount(id).orElseThrow(() -> new RuntimeException("Account not found"));
        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }
        account.setBalance(account.getBalance() - amount);
        return accountRepository.save(account);
    }

    public boolean transfer(String fromAccountNumber,String toAccountNumber,double amount){
        Account fromAccount=accountRepository.findByAccountNumberEquals(fromAccountNumber);
        Account toAccount=accountRepository.findByAccountNumberEquals(toAccountNumber);
        if(fromAccount!=null && toAccount!=null && fromAccount.getBalance()>=amount){
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance()+ amount);
            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);
            return true;
        }
        return false;
    }

    public double getBalance(String accountNumber){
        Account account=accountRepository.findByAccountNumberEquals(accountNumber);
        return account!=null ? account.getBalance() : -1;
    }

}
