import { Button } from "@mui/material";
import { useNavigate } from "react-router";
import DashboardTitleTab from "../../dashboard/DashboardTitleTab";

export default function ReviseActivateCourse() {
  const navigate = useNavigate();
  return (
    <div>
      <DashboardTitleTab title="활성된 강좌 상세 관리하기" />
      <Button>강좌 정지하기</Button>
      <Button>강좌 상세 보기</Button>
    </div>
  );
}
