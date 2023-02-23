import {
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  List,
  Typography,
} from "@mui/material";
import { useState } from "react";
import AddIcon from "@mui/icons-material/Add";
import RemoveIcon from "@mui/icons-material/Remove";
import styled from "styled-components";
import { useEffect } from "react";
import { CssTextField } from "./Outline";
import Unit from "./Unit";

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

export default function Chapter({
  chapter,
  chapterList,
  setChapterList,
  currentIdx,
  currentKey,
  courseId,
}) {
  const [units, setUnits] = useState(chapter.units);
  const [clicked, setClicked] = useState(false);
  const [chapterUpdate, setChapterUpdate] = useState(false);
  const [chaptertitle, setChapterTitle] = useState("");
  const [unitCnt, setUnitCnt] = useState(1);

  const reviseChapterTitle = () => {
    const prevList = chapterList;
    let keyIdx = 0;
    const chapter = chapterList.filter((chapter, idx) => {
      if (chapter.id === currentKey) {
        keyIdx = idx;
        return chapter;
      }
    });
    chapter[0].title = chaptertitle; //filter의 리턴은 배열이다 주의
    prevList.splice(keyIdx, 1, chapter[0]); //이건 그 자리 인덱스를 찾아야지
    setChapterList(prevList);
  };

  const plusUnit = () => {
    const unit = {
      id: unitCnt,
      unitId: 0,
      title: "",
      url: "",
    };
    setUnitCnt((prev) => prev + 1);
    setUnits((prev) => [...prev, unit]);
    updateSmallListInChapter();
  };
  const removeChapter = (removeKey) => {
    setChapterList((current) =>
      current.filter((chapter) => {
        return chapter.id !== removeKey;
      })
    );
  };

  const updateSmallListInChapter = () => {
    console.log("chapter update");
    const prevList = chapterList;
    let keyIdx = 0;
    const chapter = chapterList.filter((chapter, idx) => {
      if (chapter.id === currentKey) {
        keyIdx = idx;
        return chapter;
      }
    });

    chapter[0].units = units; //filter의 리턴은 배열이다 주의
    prevList.splice(keyIdx, 1, chapter[0]); //이건 그 자리 인덱스를 찾아야지
    setChapterList(prevList);
  };

  useEffect(() => {
    if (chapterUpdate) {
      updateSmallListInChapter();
      setChapterUpdate(false);
      console.log(chapterList);
    }
  }, [units, chapterUpdate]);

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
                {currentIdx + 1}.&nbsp; {chapter.title}
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
                  챕터 제목 추가
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
                  removeChapter(currentKey);
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
                <Unit
                  key={unit.id}
                  courseId={courseId}
                  unit={unit}
                  unitIdx={unitIdx}
                  units={units}
                  setUnits={setUnits}
                  setChapterUpdate={setChapterUpdate}
                />
              );
            })}
          </List>
        </CardContent>
      </Card>
    </Box>
  );
}
