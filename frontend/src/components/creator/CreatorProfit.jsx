import { useEffect } from "react";
import { useNavigate } from "react-router";
import { useRecoilValue } from "recoil";
import { getCreatorIdSelector } from "../../atom";
import { CREATOR_BAR_LIST } from "../../static";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";

export default function CreatorProfit() {
  const isCreator = useRecoilValue(getCreatorIdSelector);
  const navigate = useNavigate();
  useEffect(() => {
    if (isCreator < 0) {
      alert("크리에이터 권환이 없습니다.");
      navigate(CREATOR_BAR_LIST.list[0].creator[1].url);
    }
  }, []);
  return <DashboardTitleTab title={CREATOR_BAR_LIST.list[1].name} />;
}
