import React, { useState, useEffect } from 'react';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import {Box, FormControl, Grid, InputLabel, MenuItem, Select, Chip, Autocomplete, OutlinedInput} from "@mui/material";
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
                setTasks(new Array(response.data.taskLimit).fill(''));
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
                    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start'}}>
                        <p>How many tasks do you want to accomplish today?</p>
                        <TextField
                            type="number"
                            value={dailyLimit}
                            onChange={(e) => setDailyLimit(e.target.value)}
                            variant="outlined"
                        />
                        <Button style={{marginTop:'10px'}} variant="contained" onClick={handleLimitSubmit}>
                            Set Goal
                        </Button>
                    </div>
                )}
                {limitSet && tasks.map((task, index) => (
                    <div key={index} style={{ display: 'flex', alignItems: 'center' }}>
                        <TextField
                            key={index}
                            label={`Task ${index + 1}`}
                            value={savedTasksFetched ? savedTasks[index].description : task.description}
                            onChange={(e) => handleTaskChange(index, e)}
                            variant="outlined"
                            fullWidth
                            margin="normal"
                            InputProps={{
                                readOnly: tasksSubmitted || savedTasksFetched ,
                            }}
                        />

                        <FormControl sx={{ m: 1, width: 300 }}>
                            <InputLabel id={`category-label-${index}`}>Category</InputLabel>
                            <Select
                                labelId={`category-label-${index}`}
                                id={`category-select-${index}`}
                                multiple
                                value={savedTasksFetched && Array.isArray(savedTasks[index].categoryId) ? savedTasks[index].categoryId : []}
                                onChange={(e) => handleCategoryChange(index, e)}
                                input={<OutlinedInput id={`category-outlined-${index}`} label="Category" />}
                                renderValue={(selected) => (
                                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
                                        {categoryOptions.filter(category => selected.includes(category.id)).map((category) => (
                                            <Chip key={category.id} label={category.name} />
                                        ))}
                                    </Box>
                                )}
                            >
                                {categoryOptions.map((category) => (
                                    <MenuItem key={category.id} value={category.id}>
                                        {category.name}
                                    </MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                    </div>
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
