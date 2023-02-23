import { useState } from "react";
import { useRecoilValue } from "recoil";
import { getAdminLoginState } from "../../atom";
import AdminDashboard from "../../components/admin/AdminDashboard";
import AdminLogin from "../../components/admin/AdminLogin";

export default function AdminRoot() {
  const adminLoginState = useRecoilValue(getAdminLoginState);

  return <div>{adminLoginState ? <AdminDashboard /> : <AdminLogin />}</div>;
}
