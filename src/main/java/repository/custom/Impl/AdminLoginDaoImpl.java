package repository.custom.Impl;

import entity.AdminLoginEntity;
import javafx.collections.ObservableList;
import repository.custom.AdminLoginDao;

public class AdminLoginDaoImpl implements AdminLoginDao {

    @Override
    public boolean save(AdminLoginEntity adminLoginEntity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public ObservableList<AdminLoginEntity> getAll() {
        return null;
    }

    @Override
    public boolean update(AdminLoginEntity adminLoginEntity) {
        return false;
    }

    @Override
    public AdminLoginEntity search(String id) {
        return null;
    }
}
