import React, { useState, useEffect, useCallback } from 'react';
import { useParams, Link } from 'react-router-dom';
import { getServiceById, getServiceMetrics, getServiceEvents } from '../services/api';
import StatusBadge from '../components/StatusBadge';
import ResponseChart from '../components/ResponseChart';
import MetricCard from '../components/MetricCard';
import { ArrowLeft, Clock, Activity, Timer, CheckCircle2, Boxes } from 'lucide-react';

export default function ServiceDetails() {
  const { id } = useParams();
  const [service, setService] = useState(null);
  const [metrics, setMetrics] = useState([]);
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);

  const loadData = useCallback(async () => {
    try {
      const [svcRes, metRes, evtRes] = await Promise.all([
        getServiceById(id),
        getServiceMetrics(id, '24h'),
        getServiceEvents(id),
      ]);
      setService(svcRes.data);
      setMetrics(metRes.data);
      setEvents(evtRes.data);
    } catch (err) {
      console.error('Failed to load service details', err);
    } finally {
      setLoading(false);
    }
  }, [id]);

  useEffect(() => {
    loadData();
    const interval = setInterval(loadData, 10000);
    return () => clearInterval(interval);
  }, [loadData]);

  if (loading) {
    return <div className="card" style={{ padding: '2rem', textAlign: 'center', color: 'var(--text-muted)' }}>Loading service details...</div>;
  }

  if (!service) {
    return <div className="card" style={{ padding: '2rem', textAlign: 'center', color: 'var(--status-down)' }}>Service not found.</div>;
  }

  const latestMetric = metrics.length > 0 ? metrics[0] : null;
  const currentStatus = latestMetric ? latestMetric.status : 'UNKNOWN';
  const latestResponseTime = latestMetric ? latestMetric.responseTime : null;

  const totalChecks = metrics.length;
  const upChecks = metrics.filter((m) => m.status === 'UP').length;
  const uptime = totalChecks > 0 ? ((upChecks / totalChecks) * 100).toFixed(1) : '100.0';

  const validResponseTimes = metrics.filter((m) => m.responseTime !== null).map((m) => m.responseTime);
  const avgResponseTime = validResponseTimes.length > 0
    ? (validResponseTimes.reduce((a, b) => a + b, 0) / validResponseTimes.length).toFixed(1)
    : '0.0';

  return (
    <div>
      {/* Header Bar */}
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1.5rem' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
          <Link to="/services" className="btn btn-secondary" style={{ padding: '0.35rem 0.65rem' }}>
            <ArrowLeft size={15} />
          </Link>
          <div>
            <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
              <h2 style={{ fontSize: '1.5rem', fontWeight: 600, color: 'var(--text-primary)' }}>{service.name}</h2>
              <StatusBadge status={currentStatus} />
            </div>
            <p className="font-mono" style={{ color: 'var(--text-secondary)', fontSize: '0.8rem' }}>
              {service.url}{service.healthEndpoint}
            </p>
          </div>
        </div>

        <Link to={`/services/${id}/edit`} className="btn btn-primary">
          Edit Configuration
        </Link>
      </div>

      {/* Metrics Summary Row */}
      <div className="summary-grid" style={{ marginBottom: '1.5rem' }}>
        <MetricCard title="Current Status" value={currentStatus} icon={CheckCircle2} color={currentStatus === 'UP' ? 'var(--status-up)' : 'var(--status-down)'} />
        <MetricCard title="Latest Response" value={latestResponseTime !== null ? `${latestResponseTime} ms` : '—'} icon={Clock} color="var(--accent-blue)" />
        <MetricCard title="24h Avg Response" value={`${avgResponseTime} ms`} icon={Timer} color="var(--accent-primary)" />
        <MetricCard title="24h Uptime" value={`${uptime}%`} icon={Activity} color="var(--status-up)" />
      </div>

      {/* Historical Graph */}
      <div style={{ marginBottom: '1.5rem' }}>
        <ResponseChart data={metrics} title={`Historical Response Time for ${service.name}`} />
      </div>

      {/* Event Log Table */}
      <div className="card">
        <h3 style={{ fontSize: '0.95rem', fontWeight: 600, marginBottom: '0.85rem', color: 'var(--text-primary)' }}>Status Transition & Event Log</h3>
        {events.length === 0 ? (
          <p style={{ color: 'var(--text-muted)', fontSize: '0.85rem' }}>No status change events logged for this service.</p>
        ) : (
          <div className="table-container">
            <table className="data-table">
              <thead>
                <tr>
                  <th>Timestamp</th>
                  <th>Previous Status</th>
                  <th>New Status</th>
                  <th>Message</th>
                </tr>
              </thead>
              <tbody>
                {events.map((evt) => (
                  <tr key={evt.id}>
                    <td style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>{new Date(evt.createdAt).toLocaleString()}</td>
                    <td><StatusBadge status={evt.previousStatus} /></td>
                    <td><StatusBadge status={evt.currentStatus} /></td>
                    <td style={{ color: 'var(--text-primary)', fontWeight: 500 }}>{evt.message}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
}
