import React, { useState, useEffect } from 'react';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import { Box, Grid } from "@mui/material";
import axios from 'axios';

const TaskPage = () => {
    const [dailyLimit, setDailyLimit] = useState(0);
    const [limitSet, setLimitSet] = useState(false);
    const [tasks, setTasks] = useState([]);
    const [tasksSubmitted, setTasksSubmitted] = useState(false);


    useEffect(() => {
        axios.get('http://localhost:8080/daily-limits')
            .then(response => {
                setDailyLimit(response.data.taskLimit);
                setTasks(new Array(response.data.taskLimit).fill(null).map(() => ({
                    description: '',
                    categoryId: null, // can be null for new
                    date: new Date().toISOString().split('T')[0], // current date - YYYY-MM-DD
                    startTime: null,
                    endTime: null,
                    status: 'Pending'
                })));
                setLimitSet(true);
            })
            .catch(error => {
                if (error.response && error.response.status === 404) {
                    // no limit for today
                    setLimitSet(false);
                } else {
                    console.error('No limit for today', error);
                }
            });

    }, []);


    const handleLimitSubmit = () => {
        axios.post('http://localhost:8080/daily-limits', { taskLimit: dailyLimit })
            .then(response => {
                setTasks(new Array(response.data.taskLimit).fill(''));
                setLimitSet(true);
            })
            .catch(error => console.error('Error setting daily limit', error));
    };

    const handleTaskChange = (index, field, value) => {
        const updatedTasks = tasks.map((task, i) =>
            i === index ? { ...task, [field]: value } : task
        );
        setTasks(updatedTasks);
    };

    const submitTasks = () => {
        // backend integration for submitting tasks
        tasks.forEach(task => {
            axios.post('http://localhost:8080/tasks', task)
                .then(response => {
                    console.log('Task submitted successfully', response.data);
                    console.log(tasks);
                })
                .catch(error => {
                    console.error('Error submitting task', error);
                });
        });
        setTasksSubmitted(true);
    };

    return (
        <Box className={"page-container"}>
            <Grid>
                {!limitSet && (
                    <div>
                        <p>How many tasks do you want to accomplish today?</p>
                        <TextField
                            label="set goal"
                            type="number"
                            value={dailyLimit}
                            onChange={(e) => setDailyLimit(e.target.value)}
                            variant="outlined"
                        />
                        <Button variant="contained" onClick={handleLimitSubmit}>
                            Set Limit
                        </Button>
                    </div>
                )}
                <p>Your goals for the day: </p>
                {limitSet && tasks.map((task, index) => (
                    <TextField
                        key={index}
                        label={`Task ${index + 1}`}
                        value={task.description}
                        onChange={(e) => handleTaskChange(index, e)}
                        variant="outlined"
                        fullWidth
                        margin="normal"
                    />
                ))}
                {limitSet && tasks.length > 0 && (
                    <Button variant="contained" color="primary" onClick={submitTasks}>
                        save Tasks
                    </Button>
                )}
            </Grid>
        </Box>
    );
};

export default TaskPage;
