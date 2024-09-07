package co.com.jorge.bank.controller.dto;

public class UpdateAccountRequest {
    private String accountId;
    private Double newBalance;

    public UpdateAccountRequest() {
    }

    public UpdateAccountRequest(String accountId, Double newBalance) {
        this.accountId = accountId;
        this.newBalance = newBalance;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Double getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(Double newBalance) {
        this.newBalance = newBalance;
    }

    @Override
    public String toString() {
        return "UpdateAccountRequest{" +
                "accountId='" + accountId + '\'' +
                ", newBalance=" + newBalance +
                '}';
    }
}
