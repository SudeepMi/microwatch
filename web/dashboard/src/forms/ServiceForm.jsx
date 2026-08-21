import React, { useState, useEffect } from 'react';
import { validateServiceForm } from './ServiceValidation';
import { Save, ArrowLeft } from 'lucide-react';
import { Link } from 'react-router-dom';

export default function ServiceForm({ initialValues, onSubmit, isSubmitting, title = "Register Service" }) {
  const [formData, setFormData] = useState({
    name: '',
    url: '',
    healthEndpoint: '/health',
    description: '',
  });

  const [errors, setErrors] = useState({});
  const [touched, setTouched] = useState({});

  useEffect(() => {
    if (initialValues) {
      setFormData({
        name: initialValues.name || '',
        url: initialValues.url || '',
        healthEndpoint: initialValues.healthEndpoint || '/health',
        description: initialValues.description || '',
      });
    }
  }, [initialValues]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    const updated = { ...formData, [name]: value };
    setFormData(updated);
    
    if (touched[name]) {
      const validationErrors = validateServiceForm(updated);
      setErrors(validationErrors);
    }
  };

  const handleBlur = (e) => {
    const { name } = e.target;
    setTouched({ ...touched, [name]: true });
    const validationErrors = validateServiceForm(formData);
    setErrors(validationErrors);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setTouched({
      name: true,
      url: true,
      healthEndpoint: true,
      description: true,
    });

    const validationErrors = validateServiceForm(formData);
    setErrors(validationErrors);

    if (Object.keys(validationErrors).length === 0) {
      onSubmit(formData);
    }
  };

  return (
    <div className="card" style={{ maxWidth: '600px', margin: '0 auto' }}>
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '1.5rem' }}>
        <h2 style={{ fontSize: '1.15rem', fontWeight: 600, color: 'var(--text-primary)' }}>{title}</h2>
        <Link to="/services" className="btn btn-secondary" style={{ padding: '0.35rem 0.7rem', fontSize: '0.8rem' }}>
          <ArrowLeft size={14} />
          <span>Back</span>
        </Link>
      </div>

      <form onSubmit={handleSubmit} noValidate>
        <div className="form-group">
          <label className="form-label" htmlFor="service-name">Service name</label>
          <input
            id="service-name"
            type="text"
            name="name"
            className="form-input"
            placeholder="payment-service"
            value={formData.name}
            onChange={handleChange}
            onBlur={handleBlur}
          />
          {errors.name && <div className="form-error">{errors.name}</div>}
        </div>

        <div className="form-group">
          <label className="form-label" htmlFor="service-url">Service URL</label>
          <input
            id="service-url"
            type="text"
            name="url"
            className="form-input font-mono"
            placeholder="http://payment-service:9003"
            value={formData.url}
            onChange={handleChange}
            onBlur={handleBlur}
          />
          {errors.url && <div className="form-error">{errors.url}</div>}
        </div>

        <div className="form-group">
          <label className="form-label" htmlFor="health-endpoint">Health endpoint</label>
          <input
            id="health-endpoint"
            type="text"
            name="healthEndpoint"
            className="form-input font-mono"
            placeholder="/health"
            value={formData.healthEndpoint}
            onChange={handleChange}
            onBlur={handleBlur}
          />
          {errors.healthEndpoint && <div className="form-error">{errors.healthEndpoint}</div>}
        </div>

        <div className="form-group">
          <label className="form-label" htmlFor="service-description">Description (Optional)</label>
          <textarea
            id="service-description"
            name="description"
            rows="3"
            className="form-textarea"
            placeholder="Payment processing microservice..."
            value={formData.description}
            onChange={handleChange}
          ></textarea>
        </div>

        <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '0.75rem', marginTop: '1.75rem' }}>
          <Link to="/services" className="btn btn-secondary">
            Cancel
          </Link>
          <button type="submit" className="btn btn-primary" disabled={isSubmitting}>
            <Save size={15} />
            <span>{isSubmitting ? 'Saving...' : 'Add Service'}</span>
          </button>
        </div>
      </form>
    </div>
  );
}
