import React, { useEffect, useState } from 'react';
import { Table, Button, Popconfirm, message, Modal, Form, Input } from 'antd';
import axiosInstance from '../axiosConfig/AxiosInstance';

const UserList = () => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [editingUser, setEditingUser] = useState(null);
  const [form] = Form.useForm();

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await axiosInstance.get('/users');
        setUsers(response.data.data);
      } catch (error) {
        message.error('Failed to load users');
      } finally {
        setLoading(false);
      }
    };
    
    fetchUsers();
  }, []);

  const handleDeleteUser = async (id) => {
    try {
      await axiosInstance.delete(`/users/${id}`);
      setUsers(prevUsers => prevUsers.filter(user => user.id !== id));
      message.success('User deleted successfully');
    } catch (error) {
      message.error('Failed to delete user');
      console.error('Error deleting user:', error);
    }
  };

  const handleEditClick = (user) => {
    setEditingUser(user);
    form.setFieldsValue({
      name: user.name,
      surname: user.surname,
      email: user.email,
      password: '',  
    });
  };

  const handleEditSubmit = async (values) => {
    const { name, surname, password, email } = values;
    try {
      await axiosInstance.put(`/users/${editingUser.id}`, { name, surname, password, email });
      setUsers(prevUsers => prevUsers.map(user => user.id === editingUser.id ? { ...user, name, surname, email } : user));
      message.success('User updated successfully');
      setEditingUser(null);
      form.resetFields();
    } catch (error) {
      message.error('Failed to update user');
      console.error('Error updating user:', error);
    }
  };

  const columns = [
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
    },
    {
      title: 'Name',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: 'Surname',
      dataIndex: 'surname',
      key: 'surname',
    },
    {
      title: 'Email',
      dataIndex: 'email',
      key: 'email',
    },
    {
      title: 'Operations',
      key: 'operations',
      render: (_, record) => (
        <div>
          <Button type="link" onClick={() => handleEditClick(record)}>Edit</Button>
          <Popconfirm
            title="Are you sure you want to delete this user?"
            onConfirm={() => handleDeleteUser(record.id)}
            okText="Yes"
            cancelText="No"
          >
            <Button type="link">Delete</Button>
          </Popconfirm>
        </div>
      ),
    },
  ];

  return (
    <div>
      <Table
        dataSource={users}
        columns={columns}
        loading={loading}
        rowKey="id"
      />
      <Modal
        title="Edit User"
        visible={!!editingUser}
        onCancel={() => setEditingUser(null)}
        footer={null}
      >
        <Form
          form={form}
          layout="vertical"
          onFinish={handleEditSubmit}
        >
          <Form.Item
            name="email"
            label="Email"
            rules={[{ required: true, message: 'Please input the email!' }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="name"
            label="Name"
            rules={[{ required: true, message: 'Please input the name!' }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="surname"
            label="Surname"
            rules={[{ required: true, message: 'Please input the surname!' }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="password"
            label="Password"
            rules={[
              { required: true, message: 'Please input the password!' },
              { min: 4, max: 12, message: 'Password must be between 4 and 12 characters.' },
            ]}
          >
            <Input.Password />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit">Update</Button>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default UserList;
