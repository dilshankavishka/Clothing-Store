package repository.custom.Impl;

import dto.OrderDetail;
import entity.ProductEntity;
import javafx.collections.ObservableList;
import repository.custom.ProductDao;

import java.util.List;

public class ProductDaoImpl implements ProductDao {

    @Override
    public boolean save(ProductEntity productEntity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public ObservableList<ProductEntity> getAll() {
        return null;
    }

    @Override
    public boolean update(ProductEntity productEntity) {
        return false;
    }

    @Override
    public ProductEntity search(String id) {
        return null;
    }

    @Override
    public boolean updateStocks(List<OrderDetail> orderDetails) {
        return false;
    }
}
