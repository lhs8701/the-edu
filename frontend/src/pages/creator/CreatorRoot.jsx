import { useEffect, useLayoutEffect, useState } from "react";
import { useNavigate } from "react-router";
import { useRecoilValue } from "recoil";
import { getCreatorIdSelector, getLoginState } from "../../atom";

import CreatorDashboard from "../../components/creator/CreatorDashboard";
import { CREATOR_BAR_LIST, PROCESS_ACCOUNT_URL } from "../../static";

export default function CreatorRoot() {
  const navigate = useNavigate();
  const isCreator = useRecoilValue(getCreatorIdSelector);

  const loginState = useRecoilValue(getLoginState);

  useLayoutEffect(() => {
    if (!loginState) {
      window.location.replace("/" + PROCESS_ACCOUNT_URL.LOGIN);
      alert("확인되지 않은 접근입니다.");
    }
  }, []);

  return (
    <div>
      <CreatorDashboard isCreator={isCreator} />
    </div>
  );
}
