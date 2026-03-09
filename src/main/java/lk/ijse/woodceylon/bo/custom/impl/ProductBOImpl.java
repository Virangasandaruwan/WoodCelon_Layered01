package lk.ijse.woodceylon.bo.custom.impl;

import lk.ijse.woodceylon.bo.custom.ProductBO;
import lk.ijse.woodceylon.dao.DAOFactory;
import lk.ijse.woodceylon.dao.custom.ProductDAO;
import lk.ijse.woodceylon.dto.ProductDTO;
import lk.ijse.woodceylon.entity.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductBOImpl implements ProductBO {

    private final ProductDAO productDAO =
            (ProductDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PRODUCT);

    @Override
    public String addProduct(ProductDTO dto) throws Exception {
        Product product = new Product(
                dto.getProductId(),
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getSupplierId(),
                dto.getQty()
        );
        boolean isSaved = productDAO.save(product);
        return isSaved ? "Product Added Successfully" : "Product Add Failed";
    }

    @Override
    public String updateProduct(ProductDTO dto) throws Exception {
        Product product = new Product(
                dto.getProductId(),
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getSupplierId(),
                dto.getQty()
        );
        boolean updated = productDAO.update(product);
        return updated ? "Product Updated Successfully" : "Product Update Failed";
    }

    @Override
    public String deleteProduct(int productId) throws Exception {
        boolean deleted = productDAO.delete(productId);
        return deleted ? "Product Deleted Successfully" : "Product Delete Failed";
    }

    @Override
    public ArrayList<ProductDTO> getAllProducts() throws Exception {
        List<Product> allProducts = productDAO.getAll();

        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        for (Product p : allProducts) {
            productDTOS.add(new ProductDTO(
                    p.getProductId(),
                    p.getName(),
                    p.getDescription(),
                    p.getPrice(),
                    p.getSupplierId(),
                    p.getQty()
            ));
        }
        return productDTOS;
    }

    @Override
    public ProductDTO searchProduct(int productId) throws Exception {
        Product p = productDAO.search(productId);
        if (p != null) {
            return new ProductDTO(
                    p.getProductId(),
                    p.getName(),
                    p.getDescription(),
                    p.getPrice(),
                    p.getSupplierId(),
                    p.getQty()
            );
        }
        return null;
    }

    @Override
    public void printReport() throws Exception {
        productDAO.printReport();
    }

    @Override
    public boolean updateStock(int productId, int qtyChange) throws Exception {
        return productDAO.updateStock(productId, qtyChange);
    }

    @Override
    public List<ProductDTO> getLowStockItems() throws Exception {
        List<Product> lowStockProducts = productDAO.getLowStockItems();

        List<ProductDTO> lowStockDTOs = new ArrayList<>();
        for (Product p : lowStockProducts) {
            lowStockDTOs.add(new ProductDTO(
                    p.getProductId(),
                    p.getName(),
                    p.getDescription(),
                    p.getPrice(),
                    p.getSupplierId(),
                    p.getQty()
            ));
        }
        return lowStockDTOs;
    }
}