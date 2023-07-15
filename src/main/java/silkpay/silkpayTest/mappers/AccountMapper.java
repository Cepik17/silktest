package silkpay.silkpayTest.mappers;

import org.mapstruct.Mapper;
import silkpay.silkpayTest.dtos.AccountView;
import silkpay.silkpayTest.models.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountView toView(Account account);
}
