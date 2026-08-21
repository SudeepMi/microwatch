import React from 'react';
import { Link } from 'react-router-dom';
import StatusBadge from './StatusBadge';
import { ArrowRight } from 'lucide-react';

export default function ServiceCard({ service }) {
  return (
    <div className="card" style={{ display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
      <div>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '0.75rem' }}>
          <div>
            <h3 style={{ fontSize: '1rem', fontWeight: 600, color: 'var(--text-primary)' }}>{service.name}</h3>
            <span className="font-mono" style={{ fontSize: '0.75rem', color: 'var(--text-secondary)' }}>
              {service.url}{service.healthEndpoint || '/health'}
            </span>
          </div>
          <StatusBadge status={service.status} />
        </div>

        <p style={{ fontSize: '0.85rem', color: 'var(--text-secondary)', marginBottom: '1rem' }}>
          {service.description || 'No description provided.'}
        </p>
      </div>

      <div style={{ borderTop: '1px solid var(--border-subtle)', paddingTop: '0.75rem', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <div style={{ color: 'var(--text-secondary)', fontSize: '0.8rem' }}>
          Response: <span className="font-mono" style={{ fontWeight: 600, color: 'var(--text-primary)' }}>{service.responseTime !== null && service.responseTime !== undefined ? `${service.responseTime} ms` : '—'}</span>
        </div>

        <Link to={`/services/${service.serviceId || service.id}`} className="btn btn-secondary" style={{ padding: '0.35rem 0.65rem', fontSize: '0.75rem' }}>
          <span>Details</span>
          <ArrowRight size={13} />
        </Link>
      </div>
    </div>
  );
}
