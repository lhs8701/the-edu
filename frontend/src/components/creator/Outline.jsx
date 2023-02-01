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

const UploadForm = styled(Box)`
  width: 100%;
`;

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

const FlexDiv = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-start;
  width: 100%;
`;

const CssTextField = styled(TextField)({
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
  const [firstCategory, setFirstCategory] = useState("");
  const [secCategory, setSecCategory] = useState("");
  const [tabVal, setTabVal] = useState(0);
  const [chapterCnt, setChapterCnt] = useState(1);
  const [introImgVal, setIntroImgVal] = useState(1);
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

  const categoryFilter = () => {
    const value = CATE_VALUE.filter((val) => {
      if (firstCategory === val.big) {
        return val;
      }
    });

    const returnVal = value[0]?.smallList.filter((val, idx) => {
      if (idx !== 0) {
        return val;
      }
    });
    return returnVal;
  };

  const handleChange = (event, newValue) => {
    setTabVal(newValue);
  };

  const plusIntroImg = () => {
    setIntroImgVal((prev) => prev + 1);
  };

  const uploadCourse = (e) => {
    e.preventDefault();
    console.log(chapterList);
    console.log("제출");
  };

  const CategoryComponent = () => {
    return (
      <>
        <Grid item xs={3}>
          <CssTextField
            size="small"
            fullWidth
            value={firstCategory}
            select
            onChange={(e) => {
              setFirstCategory(e.target.value);
            }}
            label="카테고리1"
            variant="outlined"
            id="cate1"
          >
            {CATE_VALUE.map((e) => {
              return (
                <MenuItem value={e.big} key={e.id}>
                  {e.big}
                </MenuItem>
              );
            })}
          </CssTextField>
        </Grid>
        <Grid item xs={3}>
          <CssTextField
            size="small"
            fullWidth
            select
            value={secCategory}
            onChange={(e) => {
              setSecCategory(e.target.value);
            }}
            label="카테고리2"
            variant="outlined"
          >
            {categoryFilter()?.map((small) => {
              return (
                <MenuItem value={small.title} key={small.id}>
                  {small.title}
                </MenuItem>
              );
            })}
          </CssTextField>
        </Grid>
      </>
    );
  };

  const ImgUploadComponent = () => {
    return (
      <Grid item xs={10}>
        <CssTextField
          fullWidth
          type="file"
          id="thumImg"
          name="thumImg1"
          inputProps={{ accept: ".jpg, .jpeg, .png" }}
          size="small"
        />
      </Grid>
    );
  };

  const InfoComponent = () => {
    return (
      <FormControl component="fieldset" variant="standard">
        <Grid container spacing={2}>
          <Grid item xs={6}>
            <CssTextField
              autoFocus
              fullWidth
              type="text"
              id="title"
              name="title"
              label="강좌 제목"
              size="small"
            />
          </Grid>
          <CategoryComponent />
          <Grid item xs={12}>
            <CssTextField
              fullWidth
              multiline
              type="text"
              id="detail"
              name="detail"
              rows={4}
              label="강의 세부 내용"
              size="small"
            />
          </Grid>
          <Grid item xs={6}>
            <div>썸네일 이미지 (1장)</div>
            <br />
            <CssTextField
              fullWidth
              type="file"
              id="thumImg"
              name="thumImg1"
              inputProps={{ accept: ".jpg, .jpeg, .png" }}
              size="small"
            />
          </Grid>
          <Grid item xs={11}>
            강의 소개 이미지 (추가가능)
          </Grid>
          <Grid item xs={1}>
            <Fab aria-label="add" size="small">
              <AddIcon onClick={plusIntroImg} />
            </Fab>
          </Grid>
          {[...Array(introImgVal).keys()].map((cnt) => {
            return <ImgUploadComponent key={cnt} />;
          })}
        </Grid>
      </FormControl>
    );
  };

  const SmallListComponent = ({ value, unitIdx, setUnits, units }) => {
    const [unittitle, setUnitTitle] = useState("");
    const [clicked, setClicked] = useState(false);

    const removeUnit = () => {
      setUnits((current) =>
        current.filter((unit) => {
          console.log(unit, value.id);
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

    return (
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
                setClicked(false);
                reviseUnitTitle(unitIdx);
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
                <Typography variant="h5" component="div">
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

  const ChapterPlushComponent = () => {
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
        <ChapterPlushComponent />
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
      <UploadForm component="form">
        {tabVal === 0 ? <InfoComponent /> : <DetailComponent />}
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
      </UploadForm>
    </>
  );
}
