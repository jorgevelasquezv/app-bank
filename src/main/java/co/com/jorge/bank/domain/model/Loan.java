package co.com.jorge.bank.domain.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Loan {
    @Id
    private String loanId;
    private Double balance;
    private Double interestRate;

    public Loan() {
        generateId();
    }

    public Loan(String loanId, Double balance, Double interestRate) {
        this.loanId = loanId;
        this.balance = balance;
        this.interestRate = interestRate;
    }

    private void generateId() {
        ObjectId objectId = new ObjectId();
        this.loanId = objectId.toHexString();
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanId='" + loanId + '\'' +
                ", balance=" + balance +
                ", interestRate=" + interestRate +
                '}';
    }
}
