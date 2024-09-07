package co.com.jorge.bank.domain.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Transaction {
    @Id
    private String transactionId;
    private String accountId;
    private Double amount;

    public Transaction() {
        generateId();
    }

    public Transaction(String transactionId, String accountId, Double amount) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
    }

    public Transaction(String accountId, Double amount) {
        generateId();
        this.accountId = accountId;
        this.amount = amount;
    }

    private void generateId() {
        ObjectId objectId = new ObjectId();
        this.transactionId = objectId.toHexString();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", accountId='" + accountId + '\'' +
                ", amount=" + amount +
                '}';
    }
}
