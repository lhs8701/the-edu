import * as React from "react";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import ListSubheader from "@mui/material/ListSubheader";
import DashboardIcon from "@mui/icons-material/Dashboard";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import PeopleIcon from "@mui/icons-material/People";
import BarChartIcon from "@mui/icons-material/BarChart";
import LayersIcon from "@mui/icons-material/Layers";
import AssignmentIcon from "@mui/icons-material/Assignment";
import { useNavigate } from "react-router";
import { PROCESS_ADMIN_URL } from "../../static";
import { Accordion, AccordionDetails, AccordionSummary } from "@mui/material";

export default function AdminListItems() {
  const navigate = useNavigate();

  const ListComponent = ({ path, title }) => {
    return (
      <ListItemButton
        onClick={() => {
          navigate(path);
        }}
      >
        <ListItemText primary={title} />
      </ListItemButton>
    );
  };

  return (
    <React.Fragment>
      <ListItemButton
        onClick={() => {
          navigate(PROCESS_ADMIN_URL.DASHBOARD);
        }}
      >
        <ListItemIcon>
          <LayersIcon />
        </ListItemIcon>
        <ListItemText primary="대시보드" />
      </ListItemButton>

      <ListItemButton
        onClick={() => {
          navigate(PROCESS_ADMIN_URL.CREATORS);
        }}
      >
        <ListItemIcon>
          <DashboardIcon />
        </ListItemIcon>
        <ListItemText primary="크리에이터 관리" />
      </ListItemButton>
      <ListItemButton
        onClick={() => {
          navigate(PROCESS_ADMIN_URL.COURSES);
        }}
      >
        <ListItemIcon>
          <AssignmentIcon />
        </ListItemIcon>
        <ListItemText primary="강좌 관리" />
      </ListItemButton>
      <ListItemButton
        onClick={() => {
          navigate(PROCESS_ADMIN_URL.USERS);
        }}
      >
        <ListItemIcon>
          <PeopleIcon />
        </ListItemIcon>
        <ListItemText primary="회원 관리" />
      </ListItemButton>
      <ListItemButton
        onClick={() => {
          navigate(PROCESS_ADMIN_URL.PROFIT);
        }}
      >
        <ListItemIcon>
          <BarChartIcon />
        </ListItemIcon>
        <ListItemText primary="수익" />
      </ListItemButton>
      <div>
        <Accordion sx={{ m: 0 }}>
          <AccordionSummary
            expandIcon={<AssignmentIcon />}
            aria-controls="panel1a-content"
            id="panel1a-header"
          >
            <ListComponent
              path={PROCESS_ADMIN_URL.CREATORS}
              title="크리에이터 관리"
            />
          </AccordionSummary>
          <AccordionDetails sx={{ m: 0 }}>
            <h1>크리에이터 신청 목록</h1>
          </AccordionDetails>
        </Accordion>
        <Accordion>
          <AccordionSummary
            aria-controls="panel2a-content"
            id="panel2a-header"
          ></AccordionSummary>
          <AccordionDetails sx={{ m: 0 }}></AccordionDetails>
        </Accordion>
      </div>
    </React.Fragment>
  );
}
