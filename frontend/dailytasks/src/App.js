import logo from './logo.svg';
import './App.css';
import {BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './global.css'
import TaskPage from './pages/TaskPage.js'
import Dashboard from './pages/Dashboard'
function App() {
  return (
      <Router>
        <Routes>
            <Route path="/" element={<TaskPage/>} />
          <Route path="/dashboard" element={<Dashboard/>} />
        </Routes>
      </Router>
  );
}

export default App;
