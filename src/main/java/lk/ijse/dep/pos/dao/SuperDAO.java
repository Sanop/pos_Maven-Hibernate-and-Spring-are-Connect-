package lk.ijse.dep.pos.dao;

import lk.ijse.dep.pos.entity.SuperEntity;
import org.hibernate.Session;

import java.io.Serializable;

public interface SuperDAO <T extends SuperEntity,ID extends Serializable>{
    public void setSession(Session session);
}
