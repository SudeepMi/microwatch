import React from 'react';
import { Wifi, WifiOff } from 'lucide-react';

export default function Navbar({ title, isWsConnected }) {
  return (
    <header className="header">
      <h1 className="header-title">{title}</h1>
      <div className="header-actions">
        <div 
          className="status-badge" 
          style={{ 
            background: isWsConnected ? '#ECFDF3' : '#FFFBEB', 
            color: isWsConnected ? '#16A34A' : '#D97706',
            border: '1px solid var(--border-subtle)',
            fontSize: '0.75rem',
            padding: '0.2rem 0.6rem'
          }}
        >
          {isWsConnected ? <Wifi size={13} /> : <WifiOff size={13} />}
          <span>{isWsConnected ? 'Socket Live' : 'Disconnected'}</span>
        </div>
      </div>
    </header>
  );
}
