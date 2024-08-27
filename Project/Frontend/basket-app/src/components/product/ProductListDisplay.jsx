import React from 'react';
import { Row, Col } from 'antd';
import ProductItem from './ProductItem';

const ProductListDisplay = ({ products, onLikeToggle, blacklistedSellerIds }) => {
     const filteredProducts = products.filter(product => {
        const sellerId = product.seller ? product.seller.id : null;
        return !blacklistedSellerIds?.includes(sellerId);
    });

    const handleBlacklistSeller = (sellerId) => {
        console.log('Seller blacklisted:', sellerId);
    };

   
    return (
        <div className="product-container">
            <Row justify="center" gutter={[16, 16]}>
                {filteredProducts.map(product => (
                    <Col key={product.id} xs={24} sm={12} md={8} lg={6}>
                        <ProductItem
                            key={product.id}
                            tags={product.tags}
                            product={product}
                            onLikeToggle={onLikeToggle}
                            onBlacklistSeller={handleBlacklistSeller}
                        />
                    </Col>
                ))}
            </Row>
        </div>
    );
};

export default ProductListDisplay;
