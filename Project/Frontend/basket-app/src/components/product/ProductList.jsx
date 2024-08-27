import React, { useEffect, useState } from 'react';
import axiosInstance from '../axiosConfig/AxiosInstance';
import ProductListDisplay from './ProductListDisplay';
import { Select, Input, Space } from 'antd';
import axios from 'axios';

const { Option } = Select;

const ProductList = () => {
    const [products, setProducts] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [selectedTags, setSelectedTags] = useState([]);
    const [error, setError] = useState(null);
    const [tags, setTags] = useState([]);

    const fetchTags = async () => {
        try {
            const response = await axios.get('http://localhost:8080/v1/tags');
            setTags(response.data.data);
        } catch (error) {
            console.error('Error fetching tags:', error);
        }
    };

    const handleLike = async (productId, likedByUser) => {
        try {
            const response = await axiosInstance.post(`/products/${likedByUser ? 'unlike' : 'like'}/${productId}`);
            console.log(response.data.data);
            if(response.data.code === 'CONFLICT')
                await axiosInstance.post(`/products/unlike/${productId}`);
            fetchProducts(); //refresh
        } catch (error) {
            console.error('Error liking/unliking product:', error.response ? error.response.data : error.message);
        }
    };
    
    const fetchProducts = async () => {
        try {
            const params = {};
            if (selectedTags.length > 0) {
                params.tagTypes = selectedTags.join(',');
            }
            if (searchTerm) {
                params.name = searchTerm;
            }
    
            const response = await axiosInstance.get('http://localhost:8080/v1/products/search', { params });
            if (response.data && Array.isArray(response.data.content)) {
                setProducts(response.data.content);
            } else {
                throw new Error('Unexpected response data format');
            }
        } catch (error) {
            console.error('Error fetching products:', error);
            setError('Failed to load products');
        }
    };
    

    useEffect(() => {
        fetchTags();  // sayısınca fetch
    }, []);

    useEffect(() => {
        fetchProducts();  
    }, [searchTerm, selectedTags]); // değiştikçe fetch

    const handleSearchChange = (e) => {
        setSearchTerm(e.target.value);
    };

    const handleTagChange = (value) => {
        setSelectedTags(value);
    };

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <div>
            <div style={{ flex: 1, margin: '20px' }}>
                <Space direction="vertical" style={{ width: '100%' }}>
                    <Input
                        type="text"
                        placeholder="Search products"
                        value={searchTerm}
                        onChange={handleSearchChange}
                    />
                    <Select
                        mode="multiple"
                        placeholder="Select tags"
                        value={selectedTags}
                        onChange={handleTagChange}
                        style={{ width: '100%' }}
                    >
                        {tags.map(tag => (
                            <Option key={tag.id} value={tag.tagType}>
                                {tag.name}
                            </Option>
                        ))}
                    </Select>
                </Space>
            </div>
            <div style={{ flex: 2 }}>
                {products.length === 0 ? (
                    <p>No products found.</p>
                ) : (
                    <ProductListDisplay products={products} onLikeToggle={handleLike}/>
                )}
            </div>
        </div>
    );
};


export default ProductList;
