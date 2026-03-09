package lk.ijse.woodceylon.bo.custom;

import lk.ijse.woodceylon.bo.SuperBO;
import lk.ijse.woodceylon.dto.ProductDTO;
import java.util.List;

public interface ProductBO extends SuperBO {

    String addProduct(ProductDTO dto) throws Exception;

    String updateProduct(ProductDTO dto) throws Exception;

    String deleteProduct(int id) throws Exception;

    ProductDTO searchProduct(int id) throws Exception;

    List<ProductDTO> getAllProducts() throws Exception;

    void printReport() throws Exception;

    boolean updateStock(int productId, int qtyChange) throws Exception;

    List<ProductDTO> getLowStockItems() throws Exception;
}
