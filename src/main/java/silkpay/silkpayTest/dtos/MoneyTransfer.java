package silkpay.silkpayTest.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MoneyTransfer {
    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;
}
