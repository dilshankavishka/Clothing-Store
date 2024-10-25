package repository.custom;

import dto.OrderDetail;
import entity.ProductEntity;
import javafx.collections.ObservableList;
import repository.CrudRepository;

import java.util.List;
import java.util.zip.CRC32;

public interface ProductDao extends CrudRepository<ProductEntity> {
    boolean updateStocks(List<OrderDetail> orderDetails);
}
