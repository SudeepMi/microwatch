import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Services CRUD APIs
export const getServices = () => api.get('/api/v1/services');
export const getServiceById = (id) => api.get(`/api/v1/services/${id}`);
export const createService = (data) => api.post('/api/v1/services', data);
export const updateService = (id, data) => api.put(`/api/v1/services/${id}`, data);
export const deleteService = (id) => api.delete(`/api/v1/services/${id}`);

// Dashboard & Monitoring APIs
export const getDashboardSummary = () => api.get('/api/v1/dashboard/summary');
export const getLatestMetrics = () => api.get('/api/v1/metrics/latest');
export const getServiceMetrics = (id, period = '') => api.get(`/api/v1/services/${id}/metrics${period ? `?period=${period}` : ''}`);
export const getServiceEvents = (id) => api.get(`/api/v1/services/${id}/events`);
export const getRecentEvents = () => api.get('/api/v1/events/recent');

// Notifications APIs
export const getNotifications = () => api.get('/api/v1/notifications');
export const markNotificationAsRead = (id) => api.put(`/api/v1/notifications/${id}/read`);

// Failure / Recovery Simulation Helper (calls Gateway / Demo service admin)
export const simulateFailure = (port) => axios.post(`http://localhost:${port}/admin/failure`);
export const simulateRecovery = (port) => axios.post(`http://localhost:${port}/admin/recover`);

export default api;
