package silkpay.silkpayTest.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import silkpay.silkpayTest.dtos.TransactionView;
import silkpay.silkpayTest.models.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(source = "fromAccount.id", target = "fromAccountId")
    @Mapping(source = "toAccount.id", target = "toAccountId")
    TransactionView toView(Transaction transaction);
}
