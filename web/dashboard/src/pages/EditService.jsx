import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import ServiceForm from '../forms/ServiceForm';
import { getServiceById, updateService } from '../services/api';

export default function EditService({ addToast }) {
  const { id } = useParams();
  const navigate = useNavigate();
  const [initialValues, setInitialValues] = useState(null);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function loadService() {
      try {
        const res = await getServiceById(id);
        setInitialValues(res.data);
      } catch (err) {
        console.error('Failed to load service details', err);
      } finally {
        setLoading(false);
      }
    }
    loadService();
  }, [id]);

  const handleSubmit = async (formData) => {
    setIsSubmitting(true);
    try {
      await updateService(id, formData);
      addToast({
        id: Date.now(),
        title: 'Service Updated',
        message: `Successfully updated service ${formData.name}`,
        status: 'UP',
      });
      navigate('/services');
    } catch (err) {
      console.error('Update failed:', err);
      const msg = err.response?.data?.message || 'Failed to update service';
      alert(`Error: ${msg}`);
    } finally {
      setIsSubmitting(false);
    }
  };

  if (loading) {
    return <div className="card" style={{ textAlign: 'center', padding: '2rem' }}>Loading service data...</div>;
  }

  return (
    <div style={{ paddingTop: '1rem' }}>
      <ServiceForm initialValues={initialValues} onSubmit={handleSubmit} isSubmitting={isSubmitting} title={`Edit Service #${id}`} />
    </div>
  );
}
