package meoipzi.meoipzi.product.repository;

import meoipzi.meoipzi.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByOutfitId(Long outfitId);
}
