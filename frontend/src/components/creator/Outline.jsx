import {
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  CardMedia,
  Checkbox,
  Container,
  Fab,
  FormControl,
  FormControlLabel,
  Grid,
  Input,
  InputLabel,
  List,
  ListItem,
  ListItemButton,
  ListItemText,
  MenuItem,
  NativeSelect,
  Paper,
  Select,
  Tab,
  Tabs,
  TextField,
  Typography,
} from "@mui/material";
import { useState } from "react";
import AddIcon from "@mui/icons-material/Add";
import RemoveIcon from "@mui/icons-material/Remove";
import styled from "styled-components";
import { CATE_VALUE, CREATOR_BAR_LIST } from "../../static";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";
import { useEffect } from "react";
import { Outlet, useNavigate, useParams } from "react-router";
import { getAccessTokenSelector } from "../../atom";
import { useRecoilValue } from "recoil";
import CourseInfoUpload from "./CourseInfoUpload";

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
  const [tabVal, setTabVal] = useState(0);
  const [chapterCnt, setChapterCnt] = useState(1);
  const navigate = useNavigate();
  const chapter = {
    title: "제목 없음",
    id: 0,
    smallList: [
      {
        title: "제목없음",
        id: 0,
        url: "",
      },
    ],
  };
  const [chapterList, setChapterList] = useState([chapter]);

  const plusChapter = () => {
    const chapter = {
      title: "제목 없음",
      id: chapterCnt,
      smallList: [
        {
          title: "제목없음",
          id: 0,
          url: "",
        },
      ],
    };
    setChapterList((prev) => [...prev, chapter]);
    setChapterCnt((prev) => prev + 1);
  };

  const removeChapter = (removeKey) => {
    setChapterList((current) =>
      current.filter((chapter) => {
        return chapter.id !== removeKey;
      })
    );
  };

  const handleChange = (event, newValue) => {
    setTabVal(newValue);
  };

  const uploadCourse = (e) => {
    e.preventDefault();
    console.log(chapterList);
    console.log(courseValue)
    console.log("제출");
  };

  const SmallListComponent = ({ value, unitIdx, setUnits, units }) => {
    const [unittitle, setUnitTitle] = useState("");
    const [clicked, setClicked] = useState(false);
    const { unitnumber } = useParams();
    const [file, setFile] = useState(value.url);

    const removeUnit = () => {
      setUnits((current) =>
        current.filter((unit) => {
          return unit.id !== value.id;
        })
      );
    };

    const reviseUnitTitle = () => {
      const prevList = units;
      let keyIdx = 0;
      const unit = units.filter((chapter, idx) => {
        if (chapter.id === value.id) {
          keyIdx = idx;
          return chapter;
        }
      });
      unit[0].title = unittitle;
      prevList.splice(keyIdx, 1, unit[0]);
      setUnits(prevList);
    };

    const reviseUnitUrl = () => {
      const prevList = units;
      let keyIdx = 0;
      const unit = units.filter((chapter, idx) => {
        if (chapter.id === value.id) {
          keyIdx = idx;
          return chapter;
        }
      });
      unit[0].url = file;
      prevList.splice(keyIdx, 1, unit[0]);
      setUnits(prevList);
    };

    return (
      <>
        <ListItem sx={{ width: "100%" }}>
          {clicked ? (
            <CssTextField
              type="text"
              value={unittitle}
              id="unitTitle"
              name="unitTitle"
              label="유닛 제목"
              size="small"
              onChange={(e) => {
                setUnitTitle(e.target.value);
              }}
            />
          ) : (
            <ListItemText primary={unitIdx + ". " + value.title} />
          )}
          <div>
            {clicked ? (
              <Button
                sx={{ color: "black" }}
                onClick={() => {
                  console.log(file);
                  setClicked(false);
                  reviseUnitTitle();
                }}
              >
                확인
              </Button>
            ) : (
              <Button
                sx={{ color: "black" }}
                onClick={() => {
                  console.log();
                  setClicked(true);
                }}
              >
                제목 수정
              </Button>
            )}
            {Number(unitnumber) === Number(value.id) ? (
              <Button
                sx={{ color: "black" }}
                onClick={() => {
                  navigate("");
                  reviseUnitUrl();
                }}
              >
                확인
              </Button>
            ) : (
              <Button
                sx={{ color: "black" }}
                onClick={() => {
                  navigate(`${value.id}`);
                }}
              >
                영상 추가
              </Button>
            )}

            <Button
              onClick={() => {
                removeUnit();
              }}
              variant="text"
              size="large"
              sx={{ color: "black" }}
            >
              <RemoveIcon />
            </Button>
          </div>
        </ListItem>
        {Number(unitnumber) === Number(value.id) && (
          <Outlet context={[file, setFile]} />
        )}
      </>
    );
  };

  const ChapterCard = ({ chapter, nowIdx, nowKey }) => {
    const [units, setUnits] = useState(chapter.smallList);
    const [clicked, setClicked] = useState(false);
    const [chaptertitle, setChapterTitle] = useState("");
    const [unitCnt, setUnitCnt] = useState(1);

    const reviseChapterTitle = () => {
      const prevList = chapterList;
      let keyIdx = 0;
      const chapter = chapterList.filter((chapter, idx) => {
        if (chapter.id === nowKey) {
          keyIdx = idx;
          return chapter;
        }
      });
      chapter[0].title = chaptertitle; //filter의 리턴은 배열이다 주의
      prevList.splice(keyIdx, 1, chapter[0]); //이건 그 자리 인덱스를 찾아야지
      setChapterList(prevList);
    };

    const plusUnit = () => {
      const smallChapter = {
        title: "제목 없음",
        id: unitCnt,
        url: "",
      };
      setUnitCnt((prev) => prev + 1);
      setUnits((prev) => [...prev, smallChapter]);
      updateSmallListInChapter();
    };

    const updateSmallListInChapter = () => {
      console.log(0)
      const prevList = chapterList;
      let keyIdx = 0;
      const chapter = chapterList.filter((chapter, idx) => {
        if (chapter.id === nowKey) {
          keyIdx = idx;
          return chapter;
        }
      });
      chapter[0].smallList = units; //filter의 리턴은 배열이다 주의
      prevList.splice(keyIdx, 1, chapter[0]); //이건 그 자리 인덱스를 찾아야지
      setChapterList(prevList);
    };

    useEffect(updateSmallListInChapter, [units]);

    return (
      <Box mt={2}>
        <Card>
          <CardContent>
            <CardDiv>
              {clicked ? (
                <CssTextField
                  type="text"
                  value={chaptertitle}
                  id="chapterTitle"
                  name="chapterTitle"
                  label="챕터 제목"
                  size="small"
                  onChange={(e) => {
                    setChapterTitle(e.target.value);
                  }}
                />
              ) : (
                <Typography
                  sx={{ fontWeight: "var(--weight-point)" }}
                  variant="h5"
                  component="div"
                >
                  {nowIdx + 1}.&nbsp; {chapter.title}
                </Typography>
              )}

              <CardActions>
                {clicked ? (
                  <Button
                    sx={{ color: "black" }}
                    onClick={() => {
                      setClicked(false);
                      reviseChapterTitle();
                    }}
                  >
                    확인
                  </Button>
                ) : (
                  <Button
                    sx={{ color: "black" }}
                    onClick={() => {
                      setClicked(true);
                    }}
                  >
                    제목 수정
                  </Button>
                )}

                <Button
                  onClick={plusUnit}
                  variant="text"
                  size="large"
                  sx={{ color: "black" }}
                >
                  <AddIcon />
                </Button>
                <Button
                  onClick={() => {
                    removeChapter(nowKey);
                  }}
                  variant="text"
                  size="large"
                  sx={{ color: "black" }}
                >
                  <RemoveIcon />
                </Button>
              </CardActions>
            </CardDiv>
            <List dense={true}>
              {units.map((unit, unitIdx) => {
                return (
                  <SmallListComponent
                    units={units}
                    setUnits={setUnits}
                    key={unit.id}
                    unitIdx={unitIdx}
                    value={unit}
                  />
                );
              })}
            </List>
          </CardContent>
        </Card>
      </Box>
    );
  };

  const ChapterPlusComponent = () => {
    return (
      <>
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
      </>
    );
  };

  const DetailComponent = () => {
    return (
      <>
        <ChapterPlusComponent />
        {chapterList.map((chapter, idx) => {
          return (
            <ChapterCard
              key={chapter.id}
              nowIdx={idx}
              nowKey={chapter.id}
              chapter={chapter}
            />
          );
        })}
      </>
    );
  };

  const TabComponent = () => {
    return (
      <Tabs value={tabVal} onChange={handleChange} centered>
        <Tab label="강의 정보" />
        <Tab label="강의 세부 내용" />
      </Tabs>
    );
  };
  
  return (
    <>
      <DashboardTitleTab title={CREATOR_BAR_LIST.list[2].list[2].name} />
      <Box mb={2}>
        <TabComponent />
      </Box>
      <Box component="form">
        {tabVal === 0 ? (
          <CourseInfoUpload setCourseValue={setCourseValue} />
        ) : (
          <DetailComponent />
        )}
        <BtnDiv>
          <Button
            type="submit"
            variant="contained"
            size="large"
            onClick={uploadCourse}
          >
            강좌 등록
          </Button>
        </BtnDiv>
      </Box>
    </>
  );
}
