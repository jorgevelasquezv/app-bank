package co.com.jorge.bank.business;

import co.com.jorge.bank.controller.dto.*;
import co.com.jorge.bank.domain.exception.BusinessException;
import co.com.jorge.bank.domain.model.Account;
import co.com.jorge.bank.domain.model.CustomerProfile;
import co.com.jorge.bank.domain.model.Loan;
import co.com.jorge.bank.domain.model.Transaction;
import co.com.jorge.bank.domain.repository.IBankRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.List;

@Service
public class BankService {

    private final IBankRepository bankRepository;

    public BankService(IBankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    private final Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer();

    public Flux<CustomerProfile> getCustomers() {
        // Caso de uso: Consultar todos los perfiles de clientes que poseen cuentas bancarias.
        return bankRepository.findAll();
    }

    public Mono<Double> getBalance(String accountId) {
        // Caso de uso: Consultar el saldo actual de una cuenta bancaria. Sino hay balance se debe tener un valor de 0.0
        return bankRepository.findByAccountId(accountId)
                .map(CustomerProfile::getAccount)
                .map(Account::getBalance)
                .defaultIfEmpty(0.0);
    }

    public Mono<String> transferMoney(TransferRequest request) {
        // Caso de uso: Transferir dinero de una cuenta a otra. Hacer llamado de otro flujo simulando el llamado
        return bankRepository.findByAccountId(request.getFromAccount())
                .zipWith(bankRepository.findByAccountId(request.getToAccount()))
                .map(tuple -> {
                    Account fromAccount = tuple.getT1().getAccount();
                    Account toAccount = tuple.getT2().getAccount();
                    if (fromAccount.getBalance() > request.getAmount()) {
                        Transaction transactionFrom = new Transaction(fromAccount.getId(), - request.getAmount());
                        tuple.getT1().getAccount().setBalance(fromAccount.getBalance() - request.getAmount());
                        tuple.getT1().getAccount().getTransactions().add(transactionFrom);
                        Transaction transactionTo = new Transaction(toAccount.getId(), request.getAmount());
                        tuple.getT2().getAccount().setBalance(toAccount.getBalance() + request.getAmount());
                        tuple.getT2().getAccount().getTransactions().add(transactionTo);
                    }
                    return List.of(tuple.getT1(), tuple.getT2());
                })
                .flatMapMany(Flux::fromIterable)
                .flatMap(bankRepository::save)
                .collectList()
                .map(c -> {
                    System.out.println(c);
                    return "Transfer completed successfully. Accounts: " + c;
                });
    }

    public Flux<Transaction> getTransactions(String accountId) {
        // Caso de uso: Consultar el historial de transacciones de una cuenta bancaria.
        return bankRepository.findByAccountId(accountId)
                .map(CustomerProfile::getAccount)
                .map(Account::getTransactions)
                .flatMapMany(Flux::fromIterable);

    }

    public Flux<Account> getAccountsByCustomerProfile(String customerId) {
        // Caso de uso: Consultar el perfil del cliente que poseen cuentas bancarias y retornar las cuentas.
        return bankRepository
                .findById(customerId)
                .map(CustomerProfile::getAccount)
                .flatMapMany(Flux::just)
                .switchIfEmpty(Flux.error(new BusinessException("Customer not found")));
    }

    public Mono<String> createAccount(CreateAccountRequest request) {
        // Caso de uso: Crear una nueva cuenta bancaria con un saldo inicial.
        // Se busca CustomerProfile por customerId si viene id en el request
        // Se crea una nueva cuenta con el saldo inicial sobre el cliente
        // si no existe se crea un Customer profile con los datos del request
        if (request.getCustomerId() == null) {
            return createCustomerProfile(request)
                    .map(customerProfile -> {
                        customerProfile.setAccount(new Account(request.getInitialBalance()));
                        return customerProfile;
                    })
                    .flatMap(bankRepository::save)
                    .map(customerProfile -> "Account created successfully with ID: " + customerProfile.getCustomerId());
        }
        return bankRepository.findById(request.getCustomerId())
                .map(customerProfile -> {
                    customerProfile.setAccount(new Account(request.getInitialBalance()));
                    return customerProfile;
                })
                .flatMap(bankRepository::save)
                .map(customerProfile -> "Account created successfully with ID: " + customerProfile.getCustomerId());
    }

    private Mono<CustomerProfile> createCustomerProfile(CreateAccountRequest request) {
        // Crear un nuevo perfil de cliente
        CustomerProfile customerProfile = new CustomerProfile(request.getName(), request.getEmail());
        return Mono.just(customerProfile);
    }

    public Mono<String> closeAccount(String accountId) {
        // Caso de uso: Cerrar una cuenta bancaria especificada. Verificar que la cuenta exista y si no existe debe retornar un error controlado
        return bankRepository.findByAccountId(accountId)
                .flatMap(customerProfile -> {
                    customerProfile.setAccount(null);
                    return bankRepository.save(customerProfile).thenReturn("Account closed successfully");
                }).switchIfEmpty(Mono.error(new BusinessException("Account not found")));
    }

    public Mono<String> updateAccount(UpdateAccountRequest request) {
        // Caso de uso: Actualizar la información de una cuenta bancaria especificada. Verificar que la cuenta exista y si no existe debe retornar un error controlado
        return bankRepository.findByAccountId(request.getAccountId())
                .map(customerProfile -> {
                    customerProfile.getAccount().setBalance(request.getNewBalance());
                    return customerProfile;
                })
                .flatMap(bankRepository::save)
                .map(account -> "Account updated successfully")
                .switchIfEmpty(Mono.error(new BusinessException("Account not found")));
    }

    public Mono<CustomerProfile> getCustomerProfile(String accountId) {
        // Caso de uso: Consultar el perfil del cliente que posee la cuenta bancaria. Obtener los valores por cada uno de los flujos y si no existe alguno debe presentar un error
        return bankRepository.findById(accountId)
                .switchIfEmpty(Mono.error(new BusinessException("Customer not found")));
    }

    public Flux<Loan> getActiveLoans(String accountId) {
        // Caso de uso: Consultar todos los préstamos activos asociados al cliente especificado.
        return bankRepository.findByAccountId(accountId)
                .map(CustomerProfile::getAccount)
                .map(Account::getLoans)
                .flatMapMany(Flux::fromIterable);
    }

    public Flux<Double> simulateInterest(String accountId) {
        double principal = 1000.00;
        double rate = 0.05;

        // Caso de uso: Simular el interés compuesto en una cuenta bancaria. Sacar un rago de 10 años y aplicar la siguiente formula = principal * Math.pow(1 + rate, year)
        return bankRepository.findByAccountId(accountId)
                .map(CustomerProfile::getAccount)
                .map(Account::getBalance)
                .flatMapMany(balance -> Flux.range(1, 10)
                        .map(year -> balance * Math.pow(1 + rate, year)));
    }

    public Mono<String> getLoanStatus(String loanId) {
        // Caso de uso: Consultar el estado de un préstamo. se debe tener un flujo balanceMono y interestRateMono. Imprimir con el formato siguiente el resultado   "Loan ID: %s, Balance: %.2f, Interest Rate: %.2f%%"
        return bankRepository.findByLoanId(loanId)
                .map(customerProfile -> customerProfile.getAccount().getLoans())
                .map(loans -> loans.stream()
                        .filter(loan -> loan.getLoanId().equals(loanId)).findFirst()
                        .orElseThrow(() ->new BusinessException("Loan not found")))
                .map(loan -> String.format("Loan ID: %s, Balance: %.2f, Interest Rate: %.2f%%",
                        loan.getLoanId(), loan.getBalance(), loan.getInterestRate()));
    }
}
