import React, { useState, useEffect } from 'react';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import {
    Box,
    Grid,
    MenuItem,
    Select,
    IconButton, Alert, AlertTitle, Stack
} from "@mui/material";
import axios from 'axios';

const TaskPage = () => {
    const [dailyLimit, setDailyLimit] = useState(0);
    const [limitSet, setLimitSet] = useState(false);
    const [tasks, setTasks] = useState([]);
    const [tasksSubmitted, setTasksSubmitted] = useState(false);
    const [savedTasks, setSavedTasks] = useState([]);
    const [savedTasksFetched, setSavedTasksFetched] = useState(false);
    const [categoryOptions, setCategoryOptions] = useState([]);
    const [selectedCategories, setSelectedCategories] = useState(tasks.map(() => ''));
    const [showSuccessAlert, setShowSuccessAlert] = useState(false);

    useEffect(() => {
        axios.get('http://localhost:8080/daily-limits')
            .then(response => {
                setDailyLimit(response.data.taskLimit);
                setTasks(new Array(response.data.taskLimit).fill(null).map(() => ({
                    description: '',
                    categoryId: null, // can be null for now
                    date: new Date().toISOString().split('T')[0], // current date - YYYY-MM-DD
                    startTime: null,
                    endTime: null,
                    status: 'Pending'
                })));
                setLimitSet(true);
                //already saved tasks:
                axios.get('http://localhost:8080/tasks/byDate')
                    .then(savedTasksResponse => {
                        setSavedTasks(savedTasksResponse.data);
                        setSavedTasksFetched(true);
                    })
                    .catch(error => {
                        console.error('Error fetching saved tasks', error);
                    });
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

    useEffect(() => {
        // Fetch category options from the backend
        axios.get('http://localhost:8080/categories')
            .then(response => {
                setCategoryOptions(response.data);
            })
            .catch(error => {
                console.error('Error fetching category options', error);
            });
    }, []);


    const handleLimitSubmit = () => {
        axios.post('http://localhost:8080/daily-limits', { taskLimit: dailyLimit })
            .then(response => {
                setTasks(new Array(response.data.taskLimit).fill(null).map(() => ({
                    description: '',
                    categoryId: null,
                    date: new Date().toISOString().split('T')[0], // Current date
                    startTime: null,
                    endTime: null,
                    status: 'Pending'
                })));
                setLimitSet(true);
            })
            .catch(error => console.error('Error setting daily limit', error));
    };

    const handleTaskChange = (index, event) => {
        const newTasks = [...tasks];
        newTasks[index] = { ...newTasks[index], description: event.target.value };
        setTasks(newTasks);
    };
    const handleCategoryChange = (index, event) => {
        const newTasks = [...tasks];
        newTasks[index].categoryId = event.target.value;
        setTasks(newTasks);
    };

    const closeSuccessAlert = () => {
        setShowSuccessAlert(false);
    };

    const submitTasks = async () => {
        // backend integration for submitting tasks
        for (const task of tasks) {
            // Check if the description is non-empty and not just whitespace
            if (task.description && task.description.trim()) {
                try {
                    // First, create or update the task
                    const response = await axios.post('http://localhost:8080/tasks', task);
                    const createdTask = response.data;
                    console.log('Task submitted successfully', createdTask);
                    setShowSuccessAlert(true);
                    // Assign the category to the task
                    if (task.categoryId) {
                        await axios.patch(`http://localhost:8080/tasks/${createdTask.id}/category`, null, {
                            params: { categoryId: task.categoryId }
                        });
                        console.log('Category assigned to task successfully');
                    }
                    setShowSuccessAlert(true);
                    setTimeout(closeSuccessAlert, 2000);

                } catch (error) {
                    console.error('Error submitting task or assigning category', error);
                    if (error.response) {
                        console.error('Response Status:', error.response.status);
                        console.error('Response Data:', error.response.data);
                    }
                }
            } else {
                console.log('Skipping task with empty or whitespace-only description');
            }
        }
        setTasksSubmitted(true);
    };


    return (
        <Box className={"page-container"}>
            <Grid>
                {!limitSet && (
                    <div style={{display: 'flex', flexDirection: 'column', alignItems: 'flex-start'}}>
                        <p>How many tasks do you want to accomplish today?</p>
                        <TextField
                            type="number"
                            value={dailyLimit}
                            onChange={(e) => setDailyLimit(e.target.value)}
                            variant="outlined"
                        />
                        <Button style={{marginTop: '10px'}} variant="contained" onClick={handleLimitSubmit}>
                            Set Goal
                        </Button>
                    </div>
                )}
                {limitSet && tasks.map((task, index) => (
                    <div key={index} style={{display: 'flex', alignItems: 'center'}}>
                        <TextField
                            key={index}
                            label={`Task ${index + 1}`}
                            value={task?.description || ''}
                            onChange={(e) => handleTaskChange(index, e)}
                            variant="outlined"
                            fullWidth
                            margin="normal"
                            InputProps={{
                                readOnly: tasksSubmitted,
                            }}
                            style={{paddingRight: '16px'}}
                        />

                        <Select
                            value={task.categoryId || ''}
                            onChange={(e) => handleCategoryChange(index, e)}
                            style={{marginTop: '9px'}}
                        >
                            {categoryOptions.map((category) => (
                                <MenuItem key={category.id} value={category.id}>{category.name}</MenuItem>
                            ))}
                        </Select>
                    </div>
                ))}
                {limitSet && tasks.length > 0 && (
                    <Button variant="contained" color="primary" onClick={submitTasks}>
                        save Tasks
                    </Button>
                )}
                {showSuccessAlert && (
                    <Stack spacing={2} sx={{ position: 'fixed', bottom: 16, right: 16 }}>
                        <Alert severity="success"  onClose={closeSuccessAlert} autoHideDuration={3000}>
                            Tasks saved successfully
                        </Alert>
                    </Stack>
                )}
            </Grid>
        </Box>
    );
};

export default TaskPage;
