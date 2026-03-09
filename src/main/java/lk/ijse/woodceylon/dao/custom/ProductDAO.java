package lk.ijse.woodceylon.dao.custom;

import lk.ijse.woodceylon.dao.CrudDAO;
import lk.ijse.woodceylon.entity.Product;

import java.util.List;

public interface ProductDAO extends CrudDAO<Product, Integer> {

    boolean updateStock(int productId, int qtyChange) throws Exception;

    List<Product> getLowStockItems() throws Exception;

    void printReport() throws Exception;

}
