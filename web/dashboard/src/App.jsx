import React, { useState, useCallback } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Sidebar from './components/Sidebar';
import Navbar from './components/Navbar';
import ToastNotification from './components/ToastNotification';
import Dashboard from './pages/Dashboard';
import Services from './pages/Services';
import AddService from './pages/AddService';
import EditService from './pages/EditService';
import ServiceDetails from './pages/ServiceDetails';
import Notifications from './pages/Notifications';

export default function App() {
  const [toasts, setToasts] = useState([]);
  const [isWsConnected, setIsWsConnected] = useState(false);

  const addToast = useCallback((toast) => {
    setToasts((prev) => [...prev, toast]);
    setTimeout(() => {
      setToasts((prev) => prev.filter((t) => t.id !== toast.id));
    }, 5000);
  }, []);

  const removeToast = useCallback((id) => {
    setToasts((prev) => prev.filter((t) => t.id !== id));
  }, []);

  return (
    <Router>
      <div className="app-container">
        <Sidebar />

        <div className="main-wrapper">
          <Navbar title="MicroWatch Monitor Platform" isWsConnected={isWsConnected} />

          <main className="page-body">
            <Routes>
              <Route path="/" element={<Navigate to="/dashboard" replace />} />
              <Route path="/dashboard" element={<Dashboard addToast={addToast} setWsConnected={setIsWsConnected} />} />
              <Route path="/services" element={<Services addToast={addToast} />} />
              <Route path="/services/new" element={<AddService addToast={addToast} />} />
              <Route path="/services/:id" element={<ServiceDetails />} />
              <Route path="/services/:id/edit" element={<EditService addToast={addToast} />} />
              <Route path="/notifications" element={<Notifications addToast={addToast} />} />
            </Routes>
          </main>
        </div>

        <ToastNotification toasts={toasts} onClose={removeToast} />
      </div>
    </Router>
  );
}
