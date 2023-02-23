import { useEffect, useLayoutEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router";
import { useRecoilValue } from "recoil";
import { getCreatorIdSelector, getLoginState } from "../../atom";

import CreatorDashboard from "../../components/creator/CreatorDashboard";
import { CREATOR_BAR_LIST, PROCESS_ACCOUNT_URL } from "../../static";

export default function CreatorRoot() {
  const isCreator = useRecoilValue(getCreatorIdSelector);
  const navigate = useNavigate();
  const loginState = useRecoilValue(getLoginState);
  const { pathname } = useLocation();
  console.log(pathname);
  if (isCreator < 0 && pathname !== "/creator/register") {
    alert("크리에이터 권한이 없습니다.");
    navigate(CREATOR_BAR_LIST.list[0].creator[1].url);
  }

  useLayoutEffect(() => {
    if (!loginState) {
      alert("확인되지 않은 접근입니다.");
      window.location.replace("/" + PROCESS_ACCOUNT_URL.LOGIN);
    }
  }, []);

  return (
    <div>
      <CreatorDashboard isCreator={isCreator} />
    </div>
  );
}
