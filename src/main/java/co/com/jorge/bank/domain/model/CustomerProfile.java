package co.com.jorge.bank.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CustomerProfile {
    @Id
    private String customerId;
    private String name;
    private String email;
    private Account account;

    public CustomerProfile() {
    }

    public CustomerProfile(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public CustomerProfile(String customerId, String name, String email, Account account) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.account = account;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "CustomerProfile{" +
                "customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", account=" + account +
                '}';
    }
}
