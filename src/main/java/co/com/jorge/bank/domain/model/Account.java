package co.com.jorge.bank.domain.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Account {
    @Id
    private String id;
    private List<Transaction> transactions;
    private List<Loan> loans;
    private Double balance;

    public Account() {
        generateId();
    }

    public Account(Double balance) {
        generateId();
        this.balance = balance;
        transactions = List.of();
        loans = List.of();
    }

    public Account(String id, List<Transaction> transactions, List<Loan> loans, Double balance) {
        this.id = id;
        this.transactions = transactions;
        this.loans = loans;
        this.balance = balance;
    }

    private void generateId() {
        ObjectId objectId = new ObjectId();
        this.id = objectId.toHexString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", transactions=" + transactions +
                ", loans=" + loans +
                ", balance=" + balance +
                '}';
    }
}
