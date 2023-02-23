import { useEffect } from "react";
import { useNavigate } from "react-router";
import { useRecoilValue } from "recoil";
import { getCreatorIdSelector } from "../../../atom";
import { CREATOR_BAR_LIST } from "../../../static";
import DashboardTitleTab from "../../dashboard/DashboardTitleTab";

export default function CoursesInquires() {
  const isCreator = useRecoilValue(getCreatorIdSelector);

  return <DashboardTitleTab title={CREATOR_BAR_LIST.list[2].list[3].name} />;
}
