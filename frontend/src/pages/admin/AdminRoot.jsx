import { useState } from "react";
import AdminDashboard from "../../components/admin/AdminDashboard";
import AdminLogin from "../../components/admin/AdminLogin";

export default function AdminRoot() {
  const [loginState, setLoginState] = useState({
    state: false,
    accessToken: "",
    refreshToken: "",
  });

  return (
    <div>
      {loginState.state ? (
        <AdminDashboard />
      ) : (
        <AdminLogin setLoginState={setLoginState} />
      )}
    </div>
  );
}
