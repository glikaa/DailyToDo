import React from 'react';
import { Bar, LinearScale  } from 'react-chartjs-2';

const TaskStatisticsChart = ({ statisticsData }) => {
    const categories = statisticsData.map((item) => item.categoryId);
    const totalCompletedTasks = statisticsData.map((item) => item.totalCompletedTasks);
    const totalUncompletedTasks = statisticsData.map((item) => item.totalUncompletedTasks);


    const data = {
        labels: categories,
        datasets: [
            {
                label: 'Completed Tasks',
                data: totalCompletedTasks,
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
            },
            {
                label: 'Uncompleted Tasks',
                data: totalUncompletedTasks,
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
            },
        ],
    };

    const config = {
        type: 'bar',
        data: data,
        options:{
            scales: {
                y: {
                    type: 'linear',
                    beginAtZero: true,
                },
            },
        }

    };

    return (
        <div>
            <h2>Task Statistics</h2>
            <Bar data={data} options={config} />
        </div>
    );
};

export default TaskStatisticsChart;
