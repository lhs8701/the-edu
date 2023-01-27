import Dashboard from "../../components/admin/Dashboard";
import { Outlet, useNavigate } from "react-router";
import { useState } from "react";
import AdminLogin from "../../components/admin/AdminLogin";

export default function AdminRoot() {
  const [isLogin, setIsLogin] = useState(false);
  return <div>{isLogin ? <Dashboard /> : <AdminLogin />}</div>;
}
