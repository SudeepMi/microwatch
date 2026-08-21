import React, { useState, useEffect } from 'react';
import { getNotifications, markNotificationAsRead } from '../services/api';
import { Check, AlertCircle, CheckCircle2 } from 'lucide-react';

export default function Notifications({ addToast }) {
  const [notifications, setNotifications] = useState([]);
  const [filter, setFilter] = useState('ALL');
  const [loading, setLoading] = useState(true);

  const fetchNotifications = async () => {
    try {
      const res = await getNotifications();
      setNotifications(res.data);
    } catch (err) {
      console.error('Failed to load notifications:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchNotifications();
    const interval = setInterval(fetchNotifications, 10000);
    return () => clearInterval(interval);
  }, []);

  const handleMarkAsRead = async (id) => {
    try {
      await markNotificationAsRead(id);
      setNotifications((prev) =>
        prev.map((n) => (n.id === id ? { ...n, isRead: true } : n))
      );
    } catch (err) {
      console.error('Failed to mark read', err);
    }
  };

  const filtered = notifications.filter((n) => {
    if (filter === 'UNREAD') return !n.isRead;
    if (filter === 'DOWN') return n.notificationType === 'SERVICE_DOWN';
    if (filter === 'RECOVERED') return n.notificationType === 'SERVICE_RECOVERED';
    return true;
  });

  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1.5rem' }}>
        <div>
          <h2 style={{ fontSize: '1.65rem', fontWeight: 600, color: 'var(--text-primary)', letterSpacing: '-0.02em' }}>Notifications</h2>
          <p style={{ color: 'var(--text-secondary)', fontSize: '0.875rem' }}>Audit trail of failure alerts and recovery events</p>
        </div>

        <div style={{ display: 'flex', gap: '0.4rem' }}>
          <button onClick={() => setFilter('ALL')} className={`btn ${filter === 'ALL' ? 'btn-primary' : 'btn-secondary'}`} style={{ fontSize: '0.8rem', padding: '0.35rem 0.7rem' }}>
            All ({notifications.length})
          </button>
          <button onClick={() => setFilter('UNREAD')} className={`btn ${filter === 'UNREAD' ? 'btn-primary' : 'btn-secondary'}`} style={{ fontSize: '0.8rem', padding: '0.35rem 0.7rem' }}>
            Unread ({notifications.filter((n) => !n.isRead).length})
          </button>
          <button onClick={() => setFilter('DOWN')} className={`btn ${filter === 'DOWN' ? 'btn-primary' : 'btn-secondary'}`} style={{ fontSize: '0.8rem', padding: '0.35rem 0.7rem' }}>
            Alerts
          </button>
        </div>
      </div>

      <div style={{ display: 'flex', flexDirection: 'column', gap: '0.75rem' }}>
        {filtered.length === 0 ? (
          <div className="card" style={{ padding: '2rem', textAlign: 'center', color: 'var(--text-muted)' }}>
            No notifications found matching filter.
          </div>
        ) : (
          filtered.map((n) => (
            <div
              key={n.id}
              className="card"
              style={{
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'space-between',
                borderLeft: n.notificationType === 'SERVICE_DOWN' ? '3px solid var(--status-down)' : '3px solid var(--status-up)',
                opacity: n.isRead ? 0.7 : 1,
                padding: '1rem 1.25rem'
              }}
            >
              <div style={{ display: 'flex', alignItems: 'center', gap: '0.85rem' }}>
                {n.notificationType === 'SERVICE_DOWN' ? (
                  <AlertCircle size={20} style={{ color: 'var(--status-down)' }} />
                ) : (
                  <CheckCircle2 size={20} style={{ color: 'var(--status-up)' }} />
                )}

                <div>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '0.6rem' }}>
                    <h4 style={{ fontSize: '0.95rem', fontWeight: 600, color: 'var(--text-primary)' }}>{n.title}</h4>
                    {!n.isRead && (
                      <span style={{ fontSize: '0.7rem', padding: '0.1rem 0.4rem', borderRadius: '4px', background: 'var(--accent-active-bg)', color: 'var(--accent-hover)', fontWeight: 600 }}>NEW</span>
                    )}
                  </div>
                  <p style={{ fontSize: '0.85rem', color: 'var(--text-secondary)', margin: '0.15rem 0' }}>{n.message}</p>
                  <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>{new Date(n.createdAt).toLocaleString()}</span>
                </div>
              </div>

              {!n.isRead && (
                <button onClick={() => handleMarkAsRead(n.id)} className="btn btn-secondary" style={{ padding: '0.35rem 0.7rem', fontSize: '0.75rem' }}>
                  <Check size={13} />
                  <span>Mark Read</span>
                </button>
              )}
            </div>
          ))
        )}
      </div>
    </div>
  );
}
