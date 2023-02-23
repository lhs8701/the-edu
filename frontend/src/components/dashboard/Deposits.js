import * as React from "react";
import Link from "@mui/material/Link";
import Typography from "@mui/material/Typography";
import Title from "./Title";

function preventDefault(event) {
  event.preventDefault();
}

export default function Deposits({ title, amount, date }) {
  return (
    <React.Fragment>
      <Title>{title}</Title>
      <br />
      <Typography component="p" variant="h4">
        {amount === null ? 0 : amount}원
      </Typography>
      <Typography color="text.secondary" sx={{ flex: 1 }}>
        {date}
      </Typography>
      {/* <div>
        <Link color="primary" href="#" onClick={preventDefault}>
          View balance
        </Link>
      </div> */}
    </React.Fragment>
  );
}
