import { useEffect } from "react";
import { ADMIN_BAR_LIST } from "../../static";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";
import { BasicTable } from "./BasicTable";

export default function EventList() {
  const getEvents = () => {
    const eventList = [];
  };
  useEffect(getEvents, []);
  return (
    <div>
      <DashboardTitleTab title={ADMIN_BAR_LIST.list[4].list[0].name} />
      <BasicTable />
    </div>
  );
}
