import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { getServices, deleteService } from '../services/api';
import { Plus, Edit2, Trash2, Search } from 'lucide-react';

export default function Services({ addToast }) {
  const [services, setServices] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(true);

  const fetchServices = async () => {
    try {
      const res = await getServices();
      setServices(res.data);
    } catch (err) {
      console.error('Error fetching service list:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchServices();
  }, []);

  const handleDelete = async (id, name) => {
    if (window.confirm(`Are you sure you want to delete service '${name}'?`)) {
      try {
        await deleteService(id);
        addToast({
          id: Date.now(),
          title: 'Service Deleted',
          message: `Successfully deleted ${name}`,
          status: 'UP',
        });
        fetchServices();
      } catch (err) {
        console.error('Failed to delete service', err);
      }
    }
  };

  const filtered = services.filter((s) =>
    s.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
    s.url.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1.5rem' }}>
        <div>
          <h2 style={{ fontSize: '1.65rem', fontWeight: 600, color: 'var(--text-primary)', letterSpacing: '-0.02em' }}>Monitored Services</h2>
          <p style={{ color: 'var(--text-secondary)', fontSize: '0.875rem' }}>Manage target microservice endpoint configurations</p>
        </div>

        <Link to="/services/new" className="btn btn-primary">
          <Plus size={14} />
          <span>Add Service</span>
        </Link>
      </div>

      <div className="card" style={{ marginBottom: '1.25rem', padding: '0.75rem 1rem' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '0.6rem' }}>
          <Search size={16} style={{ color: 'var(--text-muted)' }} />
          <input
            type="text"
            className="form-input"
            placeholder="Search services by name or URL..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            style={{ border: 'none', background: 'transparent', height: 'auto', padding: 0 }}
          />
        </div>
      </div>

      <div className="table-container">
        <table className="data-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Service Name</th>
              <th>Endpoint URL</th>
              <th>Health Path</th>
              <th>Description</th>
              <th>Registered At</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {filtered.length === 0 ? (
              <tr>
                <td colSpan="7" style={{ textAlign: 'center', color: 'var(--text-muted)', padding: '2rem' }}>
                  No registered microservices found.
                </td>
              </tr>
            ) : (
              filtered.map((service) => (
                <tr key={service.id}>
                  <td className="font-mono">#{service.id}</td>
                  <td style={{ fontWeight: 600, color: 'var(--text-primary)' }}>
                    <Link to={`/services/${service.id}`} style={{ color: 'inherit', textDecoration: 'none' }}>
                      {service.name}
                    </Link>
                  </td>
                  <td className="font-mono" style={{ fontSize: '0.8rem' }}>{service.url}</td>
                  <td className="font-mono" style={{ fontSize: '0.8rem' }}>{service.healthEndpoint}</td>
                  <td>{service.description || '—'}</td>
                  <td style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>{new Date(service.createdAt).toLocaleDateString()}</td>
                  <td>
                    <div style={{ display: 'flex', gap: '0.4rem' }}>
                      <Link to={`/services/${service.id}/edit`} className="btn btn-secondary" style={{ padding: '0.25rem 0.5rem', fontSize: '0.75rem' }}>
                        <Edit2 size={13} />
                      </Link>
                      <button onClick={() => handleDelete(service.id, service.name)} className="btn btn-secondary" style={{ padding: '0.25rem 0.5rem', fontSize: '0.75rem', color: 'var(--status-down)' }}>
                        <Trash2 size={13} />
                      </button>
                    </div>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}
