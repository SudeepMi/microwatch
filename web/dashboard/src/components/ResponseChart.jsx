import React from 'react';
import { ResponsiveContainer, LineChart, Line, XAxis, YAxis, Tooltip, CartesianGrid } from 'recharts';

export default function ResponseChart({ data, title = "Response Time History (ms)" }) {
  if (!data || data.length === 0) {
    return (
      <div className="card" style={{ padding: '2rem', textAlign: 'center', color: 'var(--text-muted)' }}>
        No metric history available yet.
      </div>
    );
  }

  const chartData = data.slice().reverse().map(item => ({
    time: item.checkedAt ? new Date(item.checkedAt).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' }) : '',
    responseTime: item.responseTime || 0,
    status: item.status
  }));

  return (
    <div className="card">
      <h3 style={{ fontSize: '0.95rem', fontWeight: 600, marginBottom: '1rem', color: 'var(--text-primary)' }}>
        {title}
      </h3>
      <div style={{ width: '100%', height: 250 }}>
        <ResponsiveContainer width="100%" height="100%">
          <LineChart data={chartData} margin={{ top: 10, right: 20, left: -20, bottom: 0 }}>
            <CartesianGrid strokeDasharray="3 3" stroke="#EEF0F2" vertical={false} />
            <XAxis dataKey="time" stroke="#98A2B3" fontSize={11} tickLine={false} />
            <YAxis stroke="#98A2B3" fontSize={11} tickLine={false} />
            <Tooltip
              contentStyle={{ background: '#FFFFFF', borderColor: '#E4E7EC', borderRadius: '8px', color: '#111827', boxShadow: '0 4px 12px rgba(0,0,0,0.08)' }}
              itemStyle={{ color: '#4F46E5', fontFamily: 'JetBrains Mono', fontSize: '13px' }}
            />
            <Line
              type="monotone"
              dataKey="responseTime"
              stroke="#4F46E5"
              strokeWidth={2}
              dot={{ r: 3, fill: '#4F46E5' }}
              activeDot={{ r: 5 }}
            />
          </LineChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
}
