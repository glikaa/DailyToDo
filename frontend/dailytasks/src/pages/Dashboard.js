import React, {useState, useEffect} from "react";
import axios from 'axios';
import {Box, Grid} from "@mui/material";
import { Bar } from 'react-chartjs-2';
import TaskStatisticsChart from '../components/Statistics.js'
const Dashboard = () => {
    const [taskStatistics, setTaskStatistics] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/task-statistics')
            .then(response => {
                setTaskStatistics(response.data);
            })
            .catch(error => {
                console.error('Error fetching task statistics', error);
            });
    }, []);

    return (
        <Box className={"page-container"}>
            <Grid>
                <div>
                    <h2>Task Statistics</h2>
                </div>
            </Grid>
        </Box>

    );
};

export default Dashboard;