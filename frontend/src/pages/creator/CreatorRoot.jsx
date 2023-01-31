import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { getFormResponse } from "../../api/adminApi";

import CreatorDashboard from "../../components/creator/CreatorDashboard";
import { PROCESS_CREATOR_URL } from "../../static";

export default function CreatorRoot() {
  const navigate = useNavigate();
  const [loginState, setLoginState] = useState({
    state: false,
    accessToken: "",
    refreshToken: "",
  });
  //   if (loginState) {
  //     navigate(PROCESS_CREATOR_URL.REGIST);
  //   }

  return (
    <div>
      <CreatorDashboard loginState={loginState} />
      {/* <ResearchBox /> */}
    </div>
  );
}
