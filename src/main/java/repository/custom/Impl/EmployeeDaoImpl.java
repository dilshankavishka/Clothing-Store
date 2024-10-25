package repository.custom.Impl;

import entity.EmployeeEntity;
import javafx.collections.ObservableList;
import repository.custom.EmployeeDao;

public class EmployeeDaoImpl implements EmployeeDao {
    @Override
    public boolean save(EmployeeEntity employeeEntity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public ObservableList<EmployeeEntity> getAll() {
        return null;
    }

    @Override
    public boolean update(EmployeeEntity employeeEntity) {
        return false;
    }

    @Override
    public EmployeeEntity search(String id) {
        return null;
    }
}
