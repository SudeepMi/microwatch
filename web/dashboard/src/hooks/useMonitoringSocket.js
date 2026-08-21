import { useEffect, useRef, useState, useCallback } from 'react';

const WS_URL = import.meta.env.VITE_WS_URL || 'ws://localhost:8080/ws/monitoring';

export function useMonitoringSocket(onMessageCallback) {
  const [isConnected, setIsConnected] = useState(false);
  const socketRef = useRef(null);

  const connect = useCallback(() => {
    try {
      const ws = new WebSocket(WS_URL);
      socketRef.current = ws;

      ws.onopen = () => {
        setIsConnected(true);
        console.log('WebSocket connected to gateway');
      };

      ws.onmessage = (event) => {
        try {
          const data = JSON.parse(event.data);
          if (onMessageCallback) {
            onMessageCallback(data);
          }
        } catch (e) {
          console.error('Error parsing WS message', e);
        }
      };

      ws.onerror = (error) => {
        console.error('WebSocket error', error);
      };

      ws.onclose = () => {
        setIsConnected(false);
        console.log('WebSocket disconnected, attempting reconnect in 3s...');
        setTimeout(connect, 3000);
      };
    } catch (err) {
      console.error('WebSocket connection failed', err);
      setTimeout(connect, 3000);
    }
  }, [onMessageCallback]);

  useEffect(() => {
    connect();
    return () => {
      if (socketRef.current) {
        socketRef.current.close();
      }
    };
  }, [connect]);

  return { isConnected };
}
