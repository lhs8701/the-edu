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
import { CREATOR_BAR_LIST, PROCESS_ADMIN_URL } from "../../static";
import { Accordion, AccordionDetails, AccordionSummary } from "@mui/material";

export default function CreatorListItems({ isCreator }) {
  const navigate = useNavigate();

  const ListComponent = ({ list, idx }) => {
    return (
      <ListItemButton
        onClick={() => {
          navigate(list.url);
        }}
      >
        <ListItemIcon>
          <LayersIcon />
        </ListItemIcon>
        <ListItemText primary={list.name} />
      </ListItemButton>
    );
  };

  const ArcodianDetails = ({ smallList }) => {
    return (
      <AccordionDetails
        sx={{
          p: 0,
        }}
      >
        <ListComponent list={smallList} />
      </AccordionDetails>
    );
  };

  const Arcodian = ({ list }) => {
    return (
      <div>
        <Accordion
          sx={{
            boxShadow: 0,
            borderRadius: 0,
          }}
        >
          <AccordionSummary
            expandIcon={<AssignmentIcon />}
            aria-controls="panel1a-content"
            id="panel1a-header"
            sx={{
              mb: -1,
            }}
          >
            {list.name}
          </AccordionSummary>
          {list.list.map((smallList, idx) => {
            return <ArcodianDetails smallList={smallList} />;
          })}
        </Accordion>
      </div>
    );
  };

  const listFilter = (list, idx) => {
    if (idx === 0) {
      if (isCreator) {
        return <ListComponent list={CREATOR_BAR_LIST.list[idx].creator[idx]} />;
      } else {
        return (
          <ListComponent list={CREATOR_BAR_LIST.list[idx].creator[idx + 1]} />
        );
      }
    } else if (idx === 3) {
      return <Arcodian list={list} />;
    } else {
      return <ListComponent list={list} />;
    }
  };

  return CREATOR_BAR_LIST.list.map((list, idx) => {
    return listFilter(list, idx);
  });
}
