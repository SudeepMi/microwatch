import React from 'react';

export default function MetricCard({ title, value, icon: Icon, color }) {
  return (
    <div className="card">
      <div className="metric-card-header">
        <span>{title}</span>
        {Icon && <Icon size={18} style={{ color: color || 'var(--text-secondary)' }} />}
      </div>
      <div className="metric-card-value">
        {value}
      </div>
    </div>
  );
}
