import { useEffect } from "react";
import { useNavigate } from "react-router";
import { useRecoilValue } from "recoil";
import { getCreatorIdSelector } from "../../atom";
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
