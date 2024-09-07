package co.com.jorge.bank.domain.repository;

import co.com.jorge.bank.domain.model.CustomerProfile;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IBankRepository extends ReactiveCrudRepository<CustomerProfile, String> {

    Mono<CustomerProfile> findByAccountId(String accountId);

    Mono<CustomerProfile> findByLoanId(String loanId);
}
