import React, { useEffect, useState } from 'react';
import { PieChart } from '@mui/x-charts';
import axiosInstance from '../axiosConfig/AxiosInstance';

const TagChart = () => {
    const [tagData, setTagData] = useState([]);

    useEffect(() => {
        const fetchTagUsage = async () => {
            try {
                const response = await axiosInstance.get('/tags/usage');
                setTagData(response.data.data); 
            } catch (error) {
                console.error('Error fetching tag usage statistics:', error);
            }
        };

        fetchTagUsage();
    }, []);

    return (
        <PieChart
            series={[
                {
                    data: tagData.map(tag => ({
                        id: tag.label, 
                        value: tag.value,
                        label: tag.label
                    }))
                }
            ]}
            width={500}
            height={250}
        />
    );
};

export default TagChart;
