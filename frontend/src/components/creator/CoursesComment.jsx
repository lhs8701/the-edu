import { useEffect } from "react";
import { useNavigate } from "react-router";
import { useRecoilValue } from "recoil";
import { getCreatorIdSelector } from "../../atom";
import { CREATOR_BAR_LIST } from "../../static";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";

export default function CoursesComment() {
  const navigate = useNavigate();
  const isCreator = useRecoilValue(getCreatorIdSelector);

  useEffect(() => {
    if (isCreator < 0) {
      navigate(CREATOR_BAR_LIST.list[0].creator[1].url);
    }
  }, []);
  return (
    <div>
      <DashboardTitleTab title={CREATOR_BAR_LIST.list[2].list[2].name} />
    </div>
  );
}
