import { Typography } from "@mui/material";
import { Widget } from "@typeform/embed-react";
import { FORM_ID } from "../../AuthKey";

export default function ResearchBox() {
  return (
    <>
      <Typography variant="h4" mb={1}>
        크리에이터 신청
      </Typography>
      <Widget
        id={FORM_ID}
        style={{ width: "100%", height: "100%" }}
        className="my-form"
      />
    </>
  );
}
