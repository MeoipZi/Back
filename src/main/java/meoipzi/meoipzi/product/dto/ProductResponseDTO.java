package meoipzi.meoipzi.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import meoipzi.meoipzi.product.domain.Product;

@Getter
@NoArgsConstructor
public class ProductResponseDTO {
    private String imgUrl;
    private String shopName;
    private String title;
    private Long price;
    private String shopUrl;
    private boolean scrapOrNot;

    public ProductResponseDTO(Product product){
        this.imgUrl =product.getImgUrl();
        this.shopName =product.getShopName();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.shopUrl = product.getShopUrl();
        this.scrapOrNot = false;
    }

    public void setScrapOrNot(boolean scrapOrNot) {
        this.scrapOrNot = scrapOrNot;
    }
}
