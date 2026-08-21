import React from 'react';

export default function StatusBadge({ status }) {
  const normalized = (status || 'UNKNOWN').toLowerCase();
  const displayStatus = (status || 'UNKNOWN').toUpperCase();

  return (
    <span className={`status-badge ${normalized}`}>
      <span className="status-dot"></span>
      {displayStatus}
    </span>
  );
}
