import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { useRecoilValue } from "recoil";
import { getCreatorIdSelector } from "../../atom";

import CreatorDashboard from "../../components/creator/CreatorDashboard";
import { CREATOR_BAR_LIST } from "../../static";

export default function CreatorRoot() {
  const navigate = useNavigate();
  const isCreator = useRecoilValue(getCreatorIdSelector);

  useEffect(() => {
    if (isCreator < 0) {
      navigate(CREATOR_BAR_LIST.list[0].creator[1].url);
    }
  }, []);

  return (
    <div>
      <CreatorDashboard isCreator={isCreator} />
    </div>
  );
}
