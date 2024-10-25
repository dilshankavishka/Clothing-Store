package repository.custom.Impl;

import entity.LoginEntity;
import javafx.collections.ObservableList;
import repository.custom.LoginDao;

public class LoginDaoImpl implements LoginDao {
    @Override
    public boolean save(LoginEntity loginEntity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public ObservableList<LoginEntity> getAll() {
        return null;
    }

    @Override
    public boolean update(LoginEntity loginEntity) {
        return false;
    }

    @Override
    public LoginEntity search(String id) {
        return null;
    }
}
