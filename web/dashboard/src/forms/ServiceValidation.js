export function validateServiceForm(values) {
  const errors = {};

  if (!values.name || !values.name.trim()) {
    errors.name = 'Service Name is required.';
  } else if (values.name.trim().length < 3) {
    errors.name = 'Service Name must be at least 3 characters.';
  }

  if (!values.url || !values.url.trim()) {
    errors.url = 'Service URL is required.';
  } else {
    try {
      // Allow http://user-service:9001 format in docker environment
      const dummyUrl = values.url.startsWith('http://') || values.url.startsWith('https://') 
        ? values.url 
        : `http://${values.url}`;
      new URL(dummyUrl);
    } catch (_) {
      errors.url = 'Enter a valid URL (e.g. http://payment-service:9003 or http://localhost:9001).';
    }
  }

  if (!values.healthEndpoint || !values.healthEndpoint.trim()) {
    errors.healthEndpoint = 'Health Endpoint is required.';
  } else if (!values.healthEndpoint.startsWith('/')) {
    errors.healthEndpoint = 'Health endpoint must begin with a forward slash (/) e.g. /health';
  }

  return errors;
}
