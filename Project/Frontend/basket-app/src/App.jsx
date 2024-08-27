import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import { Layout, Menu } from 'antd';
import { HomeOutlined, AppstoreOutlined, UserOutlined, PlusOutlined, TeamOutlined, HeartOutlined, LogoutOutlined, LoginOutlined, FormOutlined } from '@ant-design/icons';
import HomePage from './components/home/HomePage';
import ProductPage from './components/product/ProductList';
import AddProductPage from './components/product/ProductForm';
import UserPage from './components/user/UserList';
import ProfilePage from './components/Profile/ProfilePage';
import FavoriteProductsPage from './components/product/ProductFavorites';
import LoginPage from './components/authentication/LoginPage';
import RegisterPage from './components/authentication/RegisterPage';
import LogoutPage from './components/authentication/LogoutPage';
import logo from './assets/basketLogo.png';
import { UserProvider } from './components/context/UserContext';
import ProfileComponent from './components/Profile/ProfileComponent';
import SellerRegisterForm from './components/authentication/SellerRegisterForm';
import UserRegisterForm from './components/authentication/UserRegisterForm';

const { Header, Content, Footer } = Layout;

const App = () => {
  const [user, setUser] = useState(null);
  const [loggedIn, setLoggedIn] = useState(false);

  useEffect(() => {
    const storedUser = JSON.parse(localStorage.getItem('user'));

    if (storedUser && storedUser.accessToken) {
      setUser(storedUser);
      setLoggedIn(true);
    } else {
      setUser(null);
      setLoggedIn(false);
    }
  }, []);

  const menuItems = [
    { label: <Link to="/">Home</Link>, key: '/', icon: <HomeOutlined /> },
    { label: <Link to="/products">Products</Link>, key: '/products', icon: <AppstoreOutlined /> },
    ...(loggedIn ? [
      user?.role === 'SELLER' && { label: <Link to="/add-product">Add Product</Link>, key: '/add-product', icon: <PlusOutlined /> },
      user?.role === 'ADMIN' && { label: <Link to="/users">Users</Link>, key: '/users', icon: <TeamOutlined /> },
      { label: <Link to="/profile">Profile</Link>, key: '/profile', icon: <UserOutlined /> },
      { label: <Link to="/favorite-products">Favorites</Link>, key: '/favorite-products', icon: <HeartOutlined /> },
      { label: <Link to="/logout">Logout</Link>, key: '/logout', icon: <LogoutOutlined /> },
    ] : [
      { label: <Link to="/login">Login</Link>, key: '/login', icon: <LoginOutlined /> },
      { label: <Link to="/register">Register</Link>, key: '/register', icon: <FormOutlined /> },
    ])
  ].filter(Boolean);

  return (
    <Router>   
      <UserProvider>
      <Layout>
        <Header style={{ display: 'flex', alignItems: 'center', background: '#001529' }}>
          <div className="demo-logo" style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', flex: 1, marginTop: '20px'}}>
            <Link to="/">
              <img src={logo} alt="Logo" style={{ height: 70, width: 140 }} />
            </Link>
          </div>
          <Menu
            theme="dark"
            mode="horizontal"
            defaultSelectedKeys={['/']}
            items={menuItems}
            style={{ flex: 1, minWidth: 0 }}
          />
        </Header>
        <Content style={{ padding: '0 48px', marginTop: '50px'}}>
          <div style={{ background: '#fff', minHeight: 280, padding: 24}}>
              <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/products" element={<ProductPage />} />
                <Route path="/add-product" element={<AddProductPage />} />
                <Route path="/users" element={<UserPage />} />
                <Route path="/profile" element={<ProfilePage />} />
                <Route path="/profile/:id" element={<ProfileComponent />} />
                <Route path="/favorite-products" element={<FavoriteProductsPage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />
                <Route path="/register/seller" element={<SellerRegisterForm />} />
                <Route path="/register/user" element={<UserRegisterForm />} />
                <Route path="/logout" element={<LogoutPage />} />
              </Routes>
          </div>
        </Content>
        <Footer style={{ textAlign: 'center' }}>
          ©{new Date().getFullYear()} Created by Beyza🙃
        </Footer>
      </Layout>
      </UserProvider>
    </Router>
  );
};

export default App;
