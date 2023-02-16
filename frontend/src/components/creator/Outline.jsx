import { Box, Button, Tab, Tabs, TextField } from "@mui/material";
import { useState } from "react";
import AddIcon from "@mui/icons-material/Add";

import styled from "styled-components";
import { CREATOR_BAR_LIST } from "../../static";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";
import { useEffect } from "react";
import { useNavigate } from "react-router";

import { getAccessTokenSelector } from "../../atom";
import { useRecoilValue } from "recoil";
import CourseInfoUpload from "./CourseInfoUpload";
import {
  createCourseApi,
  createCourseCurriculumApi,
} from "../../api/creatorApi";
import Chapter from "./Chapter";

const BtnDiv = styled.div`
  display: Flex;
  width: 100%;
  align-items: center;
  justify-content: space-between;
  margin-top: 25px;
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
  const [tabVal, setTabVal] = useState(0);
  const [chapterCnt, setChapterCnt] = useState(1);
  const navigate = useNavigate();
  const chapter = {
    title: "",
    id: 0,
    units: [
      {
        id: 0,
        unitId: 0,
        title: "",
        url: "",
        description: "",
        file: "",
      },
    ],
  };
  const [chapterList, setChapterList] = useState([chapter]);
  const [courseId, setCourseId] = useState();

  // window.addEventListener("beforeunload", (event) => {
  //   // 표준에 따라 기본 동작 방지
  //   event.preventDefault();
  //   // Chrome에서는 returnValue 설정이 필요함
  //   event.returnValue = "";
  //   alert("fdddd");
  // });

  const plusChapter = () => {
    const chapter = {
      title: "",
      id: chapterCnt,
      units: [
        {
          id: 0,
          unitId: 0,
          title: "",
          url: "",
          description: "",
        },
      ],
    };
    setChapterList((prev) => [...prev, chapter]);
    setChapterCnt((prev) => prev + 1);
  };

  const uploadCourse = (e) => {
    e.preventDefault();
    if (window.confirm("강좌 정보를 입력하시겠습니까?")) {
      createCourseApi(accessToken, courseValue)
        .then(({ data }) => {
          alert("강좌가 등록되었습니다.");
          setCourseId(data);
          setTabVal(1);
        })
        .catch((err) => {
          console.log(err);
          alert("err");
        });
    }
  };

  const curriUploadFilter = (chapterList) => {
    const uploadCurriList = [];
    chapterList.map((chapter) => {
      const uploadUnits = [];
      chapter.units.filter((unit) => {
        if (String(unit.title) !== "") {
          uploadUnits.push({ unitId: unit.unitId });
        }
      });
      if (String(chapter.title) !== "") {
        uploadCurriList.push({ title: chapter.title, units: uploadUnits });
      }
    });
    return uploadCurriList;
  };

  const uploadCurriculum = (e) => {
    e.preventDefault();
    if (window.confirm("강좌 커리큘럼을 입력하시겠습니까?")) {
      const uploadCurriList = curriUploadFilter(chapterList);
      createCourseCurriculumApi(accessToken, courseId, uploadCurriList)
        .then(() => {
          alert("강좌가 등록되었습니다.");
          navigate(CREATOR_BAR_LIST.list[2].list[0].url);
        })
        .catch((err) => {
          console.log(err);
          alert("err");
        });
    }
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

  const CurriculumComponent = () => {
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
              courseId={courseId}
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

  useEffect(() => {
    const preventUnload = (event) => {
      // NOTE: This message isn't used in modern browsers, but is required
      const message = "Sure you want to leave?";
      event.preventDefault();
      event.returnValue = message;
    };

    window.addEventListener("beforeunload", preventUnload);

    return () => {
      window.removeEventListener("beforeunload", preventUnload);
    };
  }, []);

  return (
    <>
      <DashboardTitleTab title={CREATOR_BAR_LIST.list[2].list[1].name} />
      <Box mb={2}>
        <TabComponent />
      </Box>
      <Box component="form">
        {tabVal === 0 ? (
          <CourseInfoUpload setCourseValue={setCourseValue} />
        ) : (
          <CurriculumComponent />
        )}
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
