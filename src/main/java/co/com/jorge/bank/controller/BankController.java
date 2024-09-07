package co.com.jorge.bank.controller;

import co.com.jorge.bank.business.BankService;
import co.com.jorge.bank.controller.dto.CreateAccountRequest;
import co.com.jorge.bank.domain.model.Account;
import co.com.jorge.bank.domain.model.CustomerProfile;
import co.com.jorge.bank.controller.dto.TransferRequest;
import co.com.jorge.bank.controller.dto.UpdateAccountRequest;
import co.com.jorge.bank.domain.model.Loan;
import co.com.jorge.bank.domain.model.Transaction;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/bank")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/customers")
    public Flux<CustomerProfile> getCustomers() {
        return bankService.getCustomers();
    }

    @GetMapping("/accounts/{accountId}/balance")
    public Mono<Double> getBalance(@PathVariable String accountId) {
        return bankService.getBalance(accountId);
    }

    @PostMapping("/accounts/transfer")
    public Mono<String> transferMoney(@RequestBody TransferRequest request) {
        return bankService.transferMoney(request);
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public Flux<Transaction> getTransactions(@PathVariable String accountId) {
        return bankService.getTransactions(accountId);
    }

    @GetMapping("/accounts/{customerId}")
    public Flux<Account> getAccounts(@PathVariable String customerId) {
        return bankService.getAccountsByCustomerProfile(customerId);
    }

    @PostMapping("/accounts/create")
    public Mono<String> createAccount(@RequestBody CreateAccountRequest request) {
        return bankService.createAccount(request);
    }

    @DeleteMapping("/accounts/{accountId}")
    public Mono<String> closeAccount(@PathVariable String accountId) {
        return bankService.closeAccount(accountId);
    }

    @PutMapping("/accounts/update")
    public Mono<String> updateAccount(@RequestBody UpdateAccountRequest request) {
        return bankService.updateAccount(request);
    }

    @GetMapping("/accounts/{accountId}/profile")
    public Mono<CustomerProfile> getCustomerProfile(@PathVariable String accountId) {
        return bankService.getCustomerProfile(accountId);
    }

    @GetMapping("/customers/{customerId}/loans")
    public Flux<Loan> getActiveLoans(@PathVariable String customerId) {
        return bankService.getActiveLoans(customerId);
    }

    @GetMapping("/accounts/{accountId}/interest")
    public Flux<Double> simulateInterest(@PathVariable String accountId) {
        return bankService.simulateInterest(accountId);
    }

    @GetMapping("/loans/{loanId}")
    public Mono<String> getLoanStatus(@PathVariable String loanId) {
        return bankService.getLoanStatus(loanId);
    }
}
