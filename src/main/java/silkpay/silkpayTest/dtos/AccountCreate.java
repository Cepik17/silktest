package silkpay.silkpayTest.dtos;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreate {
    private Long userId;
    private BigDecimal initialBalance;

}
