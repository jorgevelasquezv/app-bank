package co.com.jorge.bank.controller.dto;

public class CreateAccountRequest {
    private String customerId;
    private String name;
    private String email;
    private Double initialBalance;

    public CreateAccountRequest() {
    }

    public CreateAccountRequest(String customerId, String name, String email, Double initialBalance) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.initialBalance = initialBalance;
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

    public Double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(Double initialBalance) {
        this.initialBalance = initialBalance;
    }

    @Override
    public String toString() {
        return "CreateAccountRequest{" +
                "customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", initialBalance=" + initialBalance +
                '}';
    }
}
