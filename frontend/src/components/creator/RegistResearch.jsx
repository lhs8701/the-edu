import { Widget } from "@typeform/embed-react";
import { FORM_ID } from "../../AuthKey";
import { CREATOR_BAR_LIST } from "../../static";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";

export default function ResearchBox() {
  return (
    <>
      <DashboardTitleTab title={CREATOR_BAR_LIST.list[0].creator[1].name} />
      <Widget
        id={FORM_ID}
        style={{ width: "100%", height: "100%" }}
        className="my-form"
      />
    </>
  );
}
