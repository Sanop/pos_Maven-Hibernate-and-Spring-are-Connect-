package lk.ijse.dep.pos.dao.custom;

import lk.ijse.dep.pos.dao.SuperDAO;
import lk.ijse.dep.pos.entity.CustomEntity;

import java.util.List;

public interface QueryDAO extends SuperDAO {
    CustomEntity getOrderDetail(String id)throws Exception;

    CustomEntity getOrderDetail2(String id)throws Exception;

    List<CustomEntity> getAllOrderDetail()throws Exception;

    List<CustomEntity> SearchAllOrderDetail(String key)throws Exception;
}
