import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { getFormResponse } from "../../api/adminApi";

import CreatorDashboard from "../../components/creator/CreatorDashboard";
import { CREATOR_BAR_LIST } from "../../static";

export default function CreatorRoot() {
  const navigate = useNavigate();
  const isCreator = false;

  useEffect(() => {
    if (!isCreator) {
      navigate(CREATOR_BAR_LIST.list[0].creator[1].url);
    }
  }, []);

  return (
    <div>
      <CreatorDashboard isCreator={isCreator} />
    </div>
  );
}
