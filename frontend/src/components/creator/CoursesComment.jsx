import { Box, Tab, Tabs } from "@mui/material";
import { useEffect, useState } from "react";
import { useQuery } from "react-query";
import { useLocation, useNavigate, useParams } from "react-router";
import { useRecoilValue } from "recoil";
import { getCurriculumApi } from "../../api/courseApi";
import { getCreatorIdSelector } from "../../atom";
import { CREATOR_BAR_LIST } from "../../static";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";
import ManageInquire from "./ManageInquire";
import SimpleCurriculumCheck from "./SimpleCurriculumCheck";

export default function CoursesComment() {
  const navigate = useNavigate();
  const isCreator = useRecoilValue(getCreatorIdSelector);
  const [tabVal, setTabVal] = useState(0);
  const { courseId } = useParams();
  const { state } = useLocation();

  useEffect(() => {
    if (isCreator < 0) {
      navigate(CREATOR_BAR_LIST.list[0].creator[1].url);
    }
  }, []);

  const curriculum = useQuery(
    ["courseCurriculum", courseId],
    () => {
      return getCurriculumApi(courseId);
    },
    {
      enabled: !!courseId,
      onSuccess: (res) => {},
      onError: () => {
        console.error("에러 발생했지롱");
      },
    }
  );

  return (
    <div>
      <DashboardTitleTab title={state + "강좌 댓글 관리"} />
      <Box mb={2}>
        <Tabs
          value={tabVal}
          onChange={(e, val) => {
            setTabVal(val);
          }}
          centered
        >
          <Tab value={0} label="강의 질문 관리" />
          <Tab value={1} label="강좌 문의 관리" />
        </Tabs>
      </Box>
      <Box>
        {tabVal === 1 ? (
          <ManageInquire courseId={courseId} />
        ) : (
          <SimpleCurriculumCheck
            curriculumList={curriculum?.data?.data?.chapters}
          />
        )}
      </Box>
    </div>
  );
}
