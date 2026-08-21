import React, { useState, useEffect, useCallback } from 'react';
import { Link } from 'react-router-dom';
import MetricCard from '../components/MetricCard';
import ServiceCard from '../components/ServiceCard';
import ResponseChart from '../components/ResponseChart';
import StatusBadge from '../components/StatusBadge';
import { getDashboardSummary, getLatestMetrics, getRecentEvents, simulateFailure, simulateRecovery } from '../services/api';
import { useMonitoringSocket } from '../hooks/useMonitoringSocket';
import { Boxes, CheckCircle2, AlertCircle, Timer, Activity, RefreshCw, Plus, Play } from 'lucide-react';

export default function Dashboard({ addToast, setWsConnected }) {
  const [summary, setSummary] = useState({
    totalServices: 0,
    healthyServices: 0,
    downServices: 0,
    averageResponseTime: 0,
    overallUptime: 100,
  });

  const [services, setServices] = useState([]);
  const [recentEvents, setRecentEvents] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchData = useCallback(async () => {
    try {
      const [sumRes, metRes, evtRes] = await Promise.all([
        getDashboardSummary(),
        getLatestMetrics(),
        getRecentEvents(),
      ]);

      setSummary(sumRes.data);
      setServices(metRes.data);
      setRecentEvents(evtRes.data);
    } catch (err) {
      console.error('Error fetching dashboard data:', err);
    } finally {
      setLoading(false);
    }
  }, []);

  const handleWsMessage = useCallback((event) => {
    if (event.type === 'STATUS_CHANGE') {
      addToast({
        id: Date.now(),
        title: `Service Alert: ${event.service}`,
        message: `Status changed to ${event.status}`,
        status: event.status,
      });

      fetchData();
    }
  }, [addToast, fetchData]);

  const { isConnected } = useMonitoringSocket(handleWsMessage);

  useEffect(() => {
    setWsConnected(isConnected);
  }, [isConnected, setWsConnected]);

  useEffect(() => {
    fetchData();
    const interval = setInterval(fetchData, 10000);
    return () => clearInterval(interval);
  }, [fetchData]);

  const handleSimulateFailure = async (port, name) => {
    try {
      await simulateFailure(port);
      addToast({
        id: Date.now(),
        title: 'Simulation Activated',
        message: `Simulated failure on ${name}`,
        status: 'DOWN',
      });
      fetchData();
    } catch (err) {
      console.error('Failure simulation failed', err);
    }
  };

  const handleSimulateRecovery = async (port, name) => {
    try {
      await simulateRecovery(port);
      addToast({
        id: Date.now(),
        title: 'Simulation Recovered',
        message: `Simulated recovery on ${name}`,
        status: 'UP',
      });
      fetchData();
    } catch (err) {
      console.error('Recovery simulation failed', err);
    }
  };

  return (
    <div>
      {/* Page Header Bar */}
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1.5rem' }}>
        <div>
          <h2 style={{ fontSize: '1.65rem', fontWeight: 600, color: 'var(--text-primary)', letterSpacing: '-0.02em' }}>Monitoring Overview</h2>
          <p style={{ color: 'var(--text-secondary)', fontSize: '0.875rem' }}>Real-time health of your distributed services</p>
        </div>

        <div style={{ display: 'flex', gap: '0.75rem' }}>
          <button onClick={fetchData} className="btn btn-secondary">
            <RefreshCw size={14} />
            <span>Refresh</span>
          </button>
          <Link to="/services/new" className="btn btn-primary">
            <Plus size={14} />
            <span>Add Service</span>
          </Link>
        </div>
      </div>

      {/* 4 Primary Metric Cards */}
      <div className="summary-grid">
        <MetricCard title="Total Services" value={summary.totalServices} icon={Boxes} color="var(--accent-primary)" />
        <MetricCard title="Healthy Services" value={summary.healthyServices} icon={CheckCircle2} color="var(--status-up)" />
        <MetricCard title="Down Services" value={summary.downServices} icon={AlertCircle} color="var(--status-down)" />
        <MetricCard title="Avg Response Time" value={`${summary.averageResponseTime} ms`} icon={Timer} color="var(--accent-blue)" />
      </div>

      {/* Academic Simulation Control Panel */}
      <div className="card" style={{ marginBottom: '1.75rem', background: '#FFFFFF', borderColor: '#E4E7EC' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '0.75rem' }}>
          <Play size={16} style={{ color: 'var(--accent-primary)' }} />
          <h3 style={{ fontSize: '0.95rem', fontWeight: 600, color: 'var(--text-primary)' }}>Failure & Recovery Simulator (Academic Demo)</h3>
        </div>
        <p style={{ fontSize: '0.85rem', color: 'var(--text-secondary)', marginBottom: '0.85rem' }}>
          Trigger simulated failure or recovery states to test real-time monitoring alerts, database events, WebSockets, and Android push notifications.
        </p>

        <div style={{ display: 'flex', flexWrap: 'wrap', gap: '0.5rem' }}>
          <button onClick={() => handleSimulateFailure(9003, 'Payment Service')} className="btn btn-secondary" style={{ color: 'var(--status-down)', fontSize: '0.8rem' }}>
            Fail Payment (:9003)
          </button>
          <button onClick={() => handleSimulateRecovery(9003, 'Payment Service')} className="btn btn-secondary" style={{ color: 'var(--status-up)', fontSize: '0.8rem' }}>
            Recover Payment (:9003)
          </button>

          <button onClick={() => handleSimulateFailure(9001, 'User Service')} className="btn btn-secondary" style={{ color: 'var(--status-down)', fontSize: '0.8rem' }}>
            Fail User (:9001)
          </button>
          <button onClick={() => handleSimulateRecovery(9001, 'User Service')} className="btn btn-secondary" style={{ color: 'var(--status-up)', fontSize: '0.8rem' }}>
            Recover User (:9001)
          </button>
        </div>
      </div>

      {/* Hero Component: Service Health Table */}
      <div style={{ marginBottom: '1.75rem' }}>
        <h3 style={{ fontSize: '1rem', fontWeight: 600, marginBottom: '0.75rem', color: 'var(--text-primary)' }}>Service Health</h3>
        
        <div className="table-container">
          <table className="data-table">
            <thead>
              <tr>
                <th>Service</th>
                <th>Status</th>
                <th>Endpoint URL</th>
                <th>Response Time</th>
                <th>Last Checked</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {services.length === 0 ? (
                <tr>
                  <td colSpan="6" style={{ textAlign: 'center', color: 'var(--text-muted)', padding: '2rem' }}>
                    No services registered. Add a microservice to begin monitoring.
                  </td>
                </tr>
              ) : (
                services.map((svc) => (
                  <tr key={svc.serviceId || svc.name}>
                    <td style={{ fontWeight: 600, color: 'var(--text-primary)' }}>
                      <Link to={`/services/${svc.serviceId || svc.id}`} style={{ color: 'inherit', textDecoration: 'none' }}>
                        {svc.name}
                      </Link>
                    </td>
                    <td><StatusBadge status={svc.status} /></td>
                    <td className="font-mono" style={{ fontSize: '0.8rem' }}>{svc.url}{svc.healthEndpoint || '/health'}</td>
                    <td className="font-mono" style={{ fontWeight: 500, color: 'var(--text-primary)' }}>
                      {svc.responseTime !== null && svc.responseTime !== undefined ? `${svc.responseTime} ms` : '—'}
                    </td>
                    <td style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>
                      {svc.checkedAt ? new Date(svc.checkedAt).toLocaleTimeString() : 'Just now'}
                    </td>
                    <td>
                      <Link to={`/services/${svc.serviceId || svc.id}`} className="btn btn-secondary" style={{ padding: '0.25rem 0.55rem', fontSize: '0.75rem' }}>
                        Details
                      </Link>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>

      {/* Response Time Chart & Recent Events Grid */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(340px, 1fr))', gap: '1.25rem' }}>
        <ResponseChart data={services} title="Response Time Performance" />

        <div className="card">
          <h3 style={{ fontSize: '0.95rem', fontWeight: 600, marginBottom: '0.85rem', color: 'var(--text-primary)' }}>Recent System Events</h3>
          {recentEvents.length === 0 ? (
            <p style={{ color: 'var(--text-muted)', fontSize: '0.85rem' }}>No status change events logged yet.</p>
          ) : (
            <div style={{ display: 'flex', flexDirection: 'column', gap: '0.6rem', maxHeight: '240px', overflowY: 'auto' }}>
              {recentEvents.map((evt) => (
                <div key={evt.id} style={{ padding: '0.6rem 0.8rem', background: '#F9FAFB', borderRadius: '6px', border: '1px solid var(--border-subtle)' }}>
                  <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '0.2rem' }}>
                    <span style={{ fontWeight: 500, fontSize: '0.85rem', color: 'var(--text-primary)' }}>{evt.message}</span>
                    <StatusBadge status={evt.currentStatus} />
                  </div>
                  <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>
                    {new Date(evt.createdAt).toLocaleString()}
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
