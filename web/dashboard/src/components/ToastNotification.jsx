import React from 'react';
import { AlertCircle, CheckCircle2, Bell, X } from 'lucide-react';

export default function ToastNotification({ toasts, onClose }) {
  if (!toasts || toasts.length === 0) return null;

  return (
    <div className="toast-container">
      {toasts.map((toast) => (
        <div key={toast.id} className="toast">
          {toast.status === 'DOWN' ? (
            <AlertCircle size={18} style={{ color: 'var(--status-down)' }} />
          ) : toast.status === 'UP' ? (
            <CheckCircle2 size={18} style={{ color: 'var(--status-up)' }} />
          ) : (
            <Bell size={18} style={{ color: 'var(--accent-primary)' }} />
          )}

          <div style={{ flex: 1 }}>
            <div style={{ fontWeight: 600, fontSize: '0.85rem' }}>{toast.title || 'Status Alert'}</div>
            <div style={{ fontSize: '0.8rem', color: 'var(--text-secondary)' }}>{toast.message}</div>
          </div>

          <button
            onClick={() => onClose(toast.id)}
            style={{ background: 'none', border: 'none', color: 'var(--text-muted)', cursor: 'pointer' }}
          >
            <X size={14} />
          </button>
        </div>
      ))}
    </div>
  );
}
