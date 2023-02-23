import { useEffect } from "react";
import { useNavigate } from "react-router";
import { useRecoilValue } from "recoil";
import { getCreatorIdSelector } from "../../../atom";
import { CREATOR_BAR_LIST } from "../../../static";
import DashboardTitleTab from "../../dashboard/DashboardTitleTab";

export default function CoursesInquires() {
  const isCreator = useRecoilValue(getCreatorIdSelector);
  const navigate = useNavigate();
  useEffect(() => {
    if (isCreator < 0) {
      navigate(CREATOR_BAR_LIST.list[0].creator[1].url);
    }
  }, []);
  return <DashboardTitleTab title={CREATOR_BAR_LIST.list[2].list[3].name} />;
}
