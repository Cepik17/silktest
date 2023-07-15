package silkpay.silkpayTest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import silkpay.silkpayTest.models.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account getByUserId(Long userId);

}
