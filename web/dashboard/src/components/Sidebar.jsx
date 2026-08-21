import React from 'react';
import { NavLink } from 'react-router-dom';
import { LayoutDashboard, Boxes, PlusCircle, Bell } from 'lucide-react';

export default function Sidebar() {
  return (
    <aside className="sidebar">
      <div className="sidebar-logo">
        <div className="sidebar-logo-icon">
          <Boxes size={18} />
        </div>
        <span>MicroWatch</span>
      </div>

      <nav className="sidebar-nav">
        <NavLink to="/dashboard" className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>
          <LayoutDashboard size={18} />
          <span>Dashboard</span>
        </NavLink>

        <NavLink to="/services" end className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>
          <Boxes size={18} />
          <span>Services</span>
        </NavLink>

        <NavLink to="/services/new" className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>
          <PlusCircle size={18} />
          <span>Register Service</span>
        </NavLink>

        <NavLink to="/notifications" className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>
          <Bell size={18} />
          <span>Notifications</span>
        </NavLink>
      </nav>
    </aside>
  );
}
