import { ADMIN_BAR_LIST } from "../../../static";
import DashboardTitleTab from "../../dashboard/DashboardTitleTab";

export default function DetailEvent() {
  return (
    <div>
      <DashboardTitleTab title={ADMIN_BAR_LIST.list[4].list[1].name} />
    </div>
  );
}
