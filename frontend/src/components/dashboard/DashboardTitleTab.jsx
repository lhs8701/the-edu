import { Typography } from "@mui/material";
import styled from "styled-components";

const DashboardTitle = styled(Typography)`
  font-weight: var(--weight-point);
`;

export default function DashboardTitleTab({ title }) {
  return (
    <DashboardTitle variant="h5" mb={3}>
      {title}
    </DashboardTitle>
  );
}
