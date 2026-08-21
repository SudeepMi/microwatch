import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import ServiceForm from '../forms/ServiceForm';
import { createService } from '../services/api';

export default function AddService({ addToast }) {
  const [isSubmitting, setIsSubmitting] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (formData) => {
    setIsSubmitting(true);
    try {
      await createService(formData);
      addToast({
        id: Date.now(),
        title: 'Service Registered',
        message: `Successfully registered service ${formData.name}`,
        status: 'UP',
      });
      navigate('/services');
    } catch (err) {
      console.error('Registration failed:', err);
      const msg = err.response?.data?.message || 'Failed to register service';
      alert(`Error: ${msg}`);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div style={{ paddingTop: '1rem' }}>
      <ServiceForm onSubmit={handleSubmit} isSubmitting={isSubmitting} title="Register New Microservice" />
    </div>
  );
}
