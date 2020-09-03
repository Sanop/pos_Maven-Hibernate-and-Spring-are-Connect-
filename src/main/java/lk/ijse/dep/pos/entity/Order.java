package lk.ijse.dep.pos.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "`Order`")
@ToString(exclude = "orderDetails")
public class Order implements SuperEntity {
    @Id
    private String id;
    private Date date;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "customerId",referencedColumnName = "id",nullable = false)
    private Customer customerId;
    @OneToMany(mappedBy = "order",cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private List<OrderDetail> orderDetails;

    public Order(String id, Date date, Customer customerId) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
    }

    public Order(String id, Date date, Customer customerId, List<OrderDetail> orderDetails) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setOrder(this);
        }
        this.orderDetails = orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setOrder(this);
        }
        this.orderDetails = orderDetails;
    }


    public void addOrderDetail(OrderDetail orderDetail){
        orderDetail.setOrder(this);
        List<OrderDetail> orderDetails = this.getOrderDetails();
        orderDetails.add(orderDetail);
    }
}
