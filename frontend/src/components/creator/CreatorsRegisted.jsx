import { CREATOR_BAR_LIST } from "../../static";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";

export default function CreatorsRegisted() {
  return (
    <div>
      <DashboardTitleTab title={CREATOR_BAR_LIST.list[2].list[1].name} />
      {/* <EventTable
        cells={eventListTableCells}
        rows={eventList}
        deleteFun={deleteEvent}
        navigate={navigate}
      /> */}
    </div>
  );
}
