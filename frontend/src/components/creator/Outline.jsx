import {
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  List,
  ListItem,
  ListItemText,
  Tab,
  Tabs,
  TextField,
  Typography,
} from "@mui/material";
import { useState } from "react";
import AddIcon from "@mui/icons-material/Add";
import RemoveIcon from "@mui/icons-material/Remove";
import styled from "styled-components";
import { CREATOR_BAR_LIST } from "../../static";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";
import { useEffect } from "react";
import { Outlet, useNavigate, useParams } from "react-router";
import { getAccessTokenSelector } from "../../atom";
import { useRecoilValue } from "recoil";
import CourseInfoUpload from "./CourseInfoUpload";
import {
  createCourseApi,
  createCourseCurriculumApi,
  createUnitsApi,
} from "../../api/creatorApi";
import Chapter from "./Chapter";

const BtnDiv = styled.div`
  display: Flex;
  width: 100%;
  align-items: center;
  justify-content: space-between;
  margin-top: 25px;
`;

const CardDiv = styled(BtnDiv)`
  margin: 0;
`;

export const CssTextField = styled(TextField)({
  "& label.Mui-focused": {
    color: "var(--color-primary)",
  },
  "& .MuiInput-underline:after": {
    borderBottomColor: "var(--color-primary)",
  },
  "& .MuiOutlinedInput-root": {
    "& fieldset": {},
    "&:hover fieldset": {
      borderColor: "var(--color-primary)",
    },
    "&.Mui-focused fieldset": {
      borderColor: "var(--color-primary)",
    },
  },
});

export default function Outline() {
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const [courseValue, setCourseValue] = useState({});
  const [tabVal, setTabVal] = useState(1);
  const [chapterCnt, setChapterCnt] = useState(1);
  const navigate = useNavigate();
  const chapter = {
    title: "제목 없음",
    id: 0,
    units: [
      {
        id: 0,
        unitId: 0,
      },
    ],
  };
  const [chapterList, setChapterList] = useState([chapter]);
  const [courseId, setCourseId] = useState();

  const plusChapter = () => {
    const chapter = {
      title: "제목 없음",
      id: chapterCnt,
      units: [
        {
          id: 0,
          unitId: 0,
        },
      ],
    };
    setChapterList((prev) => [...prev, chapter]);
    setChapterCnt((prev) => prev + 1);
  };

  const uploadCourse = (e) => {
    e.preventDefault();
    createCourseApi(accessToken, courseValue)
      .then(({ data }) => {
        alert("강좌가 등록되었습니다.");
        setCourseId(data.id);
        setTabVal(1);
      })
      .catch((err) => {
        console.log(err);
        alert("err");
      });
  };

  const uploadCurriculum = (e) => {
    e.preventDefault();
    createCourseCurriculumApi(accessToken, courseId, courseValue)
      .then(() => {
        alert("강좌가 등록되었습니다.");
        navigate(CREATOR_BAR_LIST.list[2].list[0].url);
      })
      .catch((err) => {
        console.log(err);
        alert("err");
      });
  };

  const UPLOAD_VALUE = [
    { fun: uploadCourse, str: "강좌 등록" },
    { fun: uploadCurriculum, str: "커리큘럼 등록" },
  ];

  const ChapterPlusComponent = () => {
    return (
      <Button
        onClick={() => {
          plusChapter();
        }}
        variant="text"
        size="large"
        sx={{ color: "black" }}
      >
        챕터 추가
        <AddIcon />
      </Button>
    );
  };

  const DetailComponent = () => {
    return (
      <>
        <ChapterPlusComponent />
        {chapterList.map((chapter, idx) => {
          return (
            <Chapter
              chapterList={chapterList}
              setChapterList={setChapterList}
              key={chapter.id}
              currentIdx={idx}
              currentKey={chapter.id}
              chapter={chapter}
            />
          );
        })}
      </>
    );
  };

  const TabComponent = () => {
    return (
      <Tabs value={tabVal} centered>
        <Tab disabled label="강좌 정보" />
        <Tab disabled label="강좌 커리큘럼" />
      </Tabs>
    );
  };

  const UploadContentComponent = () => {
    return tabVal === 0 ? (
      <CourseInfoUpload setCourseValue={setCourseValue} />
    ) : (
      <DetailComponent />
    );
  };

  return (
    <>
      <DashboardTitleTab title={CREATOR_BAR_LIST.list[2].list[2].name} />
      <Box mb={2}>
        <TabComponent />
      </Box>
      <Box component="form">
        <UploadContentComponent />
        <BtnDiv>
          <Button
            type="submit"
            variant="contained"
            size="large"
            onClick={UPLOAD_VALUE[tabVal].fun}
          >
            {UPLOAD_VALUE[tabVal].str}
          </Button>
        </BtnDiv>
      </Box>
    </>
  );
}
