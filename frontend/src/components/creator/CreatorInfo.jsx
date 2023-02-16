import { CREATOR_BAR_LIST } from "../../static";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";

export default function CreatorInfo() {
  return <DashboardTitleTab title={CREATOR_BAR_LIST.list[0].creator[0].name} />;
}
