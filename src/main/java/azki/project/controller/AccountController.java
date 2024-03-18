package azki.project.controller;

import azki.project.ProjectApplication;
import azki.project.model.Account;
import azki.project.services.AccountService;
import azki.project.services.ObserveLogger.TransactionLogger;
import azki.project.services.ObserveLogger.impl.TransactionLoggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(ProjectApplication.class);
    TransactionLogger transactionLogger=new TransactionLoggerImpl("transaction.txt");
    Account account=new Account(transactionLogger.toString());
    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public Account createAccount(@RequestBody Account account) {
        logger.info("createAccount from AccountController");
        return accountService.createAccount(account);
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id) {
        logger.info("getAccount from AccountController");
        return accountService.getAccount(id).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @PostMapping("/deposit")
    public ResponseEntity<Account> deposit(@RequestParam String accountNumber,@RequestParam double amount){
        Account account=accountService.deposit(accountNumber,amount);
        if(account!=null){
            logger.info("deposit done");
            return ResponseEntity.ok(account);
        }
        logger.info("deposit not found");
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/withdraw")
    public Account withdraw(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        logger.info("withdraw from AccountController");
        return accountService.withdraw(id, amount);
    }
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam String fromAccountNumber,@RequestParam String toAccountNumber,@RequestParam double amount){
        boolean sucsess=accountService.transfer(fromAccountNumber,toAccountNumber,amount);
        if (sucsess){
            logger.info("Transfer successful");
            return ResponseEntity.ok("Transfer successful");
        }
        logger.info("Transfer fail");
        return ResponseEntity.badRequest().body("Transfer fail");
    }
    @GetMapping("/balance")
    public double getBalance(@RequestParam String accountNumber){
        logger.info("getBalance done");
        return accountService.getBalance(accountNumber);
    }
    }