package lk.ijse.dep.pos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailPK implements SuperEntity{
    private String orderID;
    private String itemCode;
}
