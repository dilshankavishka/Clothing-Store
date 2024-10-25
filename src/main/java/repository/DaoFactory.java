package repository;

import repository.custom.Impl.*;
import util.DaoType;

public class DaoFactory {
    private static DaoFactory instance;
    private DaoFactory(){}

    public static DaoFactory getInstance() {
        return instance==null?instance=new DaoFactory():instance;
    }

    public <T extends SuperDao>T getServiceType(DaoType type){
        switch (type){
            case EMPLOYEE: return (T) new EmployeeDaoImpl();
            case PRODUCT: return (T) new ProductDaoImpl();
            case SUPPLIER: return (T) new SupplierDaoImpl();
            case LOGIN: return (T) new LoginDaoImpl();
            case ADMIN: return (T) new AdminLoginDaoImpl();
            case ORDER: return (T) new repository.custom.Impl.OrderDaoImpl();
            case ORDERDETAIL: return (T) new repository.custom.Impl.OrderDetailDaoImpl();
        }
        return null;
    }
}
