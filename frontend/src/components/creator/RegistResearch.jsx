import { Widget } from "@typeform/embed-react";
import { FORM_ID } from "../../AuthKey";

export default function ResearchBox() {
  return (
    <Widget
      id={FORM_ID}
      style={{ width: "100%", height: "100%" }}
      className="my-form"
    />
  );
}
