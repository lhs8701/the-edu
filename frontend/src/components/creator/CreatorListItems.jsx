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
import { PROCESS_CREATOR_URL } from "../../static";

export default function CreatorListItems({ isCreator }) {
  const navigate = useNavigate();

  return (
    <React.Fragment>
      {isCreator ? (
        <ListItemButton
          onClick={() => {
            navigate(PROCESS_CREATOR_URL.INFO);
          }}
        >
          <ListItemIcon>
            <LayersIcon />
          </ListItemIcon>
          <ListItemText primary="크리에이터 정보" />
        </ListItemButton>
      ) : (
        <ListItemButton
          onClick={() => {
            navigate(PROCESS_CREATOR_URL.REGIST);
          }}
        >
          <ListItemIcon>
            <LayersIcon />
          </ListItemIcon>
          <ListItemText primary="크리에이터 신청" />
        </ListItemButton>
      )}

      <ListItemButton
        onClick={() => {
          navigate(PROCESS_CREATOR_URL.DASHBOARD);
        }}
      >
        <ListItemIcon>
          <DashboardIcon />
        </ListItemIcon>
        <ListItemText primary="대시보드" />
      </ListItemButton>
      <ListItemButton
        onClick={() => {
          navigate(PROCESS_CREATOR_URL.COURSES);
        }}
      >
        <ListItemIcon>
          <AssignmentIcon />
        </ListItemIcon>
        <ListItemText primary="강좌 내역" />
      </ListItemButton>
      <ListItemButton
        onClick={() => {
          navigate(PROCESS_CREATOR_URL.COMMENT);
        }}
      >
        <ListItemIcon>
          <PeopleIcon />
        </ListItemIcon>
        <ListItemText primary="강좌 댓글 관리" />
      </ListItemButton>
      <ListItemButton
        onClick={() => {
          navigate(PROCESS_CREATOR_URL.PROFIT);
        }}
      >
        <ListItemIcon>
          <BarChartIcon />
        </ListItemIcon>
        <ListItemText primary="수익" />
      </ListItemButton>
    </React.Fragment>
  );
}
