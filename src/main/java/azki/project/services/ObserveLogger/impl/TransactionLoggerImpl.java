package azki.project.services.ObserveLogger.impl;

import azki.project.services.ObserveLogger.TransactionLogger;
import org.springframework.stereotype.Service;

import javax.imageio.IIOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
@Service
public class TransactionLoggerImpl implements TransactionLogger {
    private BufferedWriter writer;

    public TransactionLoggerImpl(String fileName) {
       try {
           writer=new BufferedWriter(new FileWriter(fileName));
       }
       catch (IOException e){
           e.printStackTrace();
       }
    }

    @Override
    public void logTransaction(String accountNumber, String transactionType, double amount) {
        try {
          writer.write("Account number is: " + accountNumber + ",Transaction Type is :" +transactionType +"Amount is: " + amount);
          writer.newLine();
          writer.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
