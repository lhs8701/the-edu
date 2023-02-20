import { Button, ListItem, ListItemText } from "@mui/material";
import { useState } from "react";
import { Outlet, useNavigate, useParams } from "react-router";
import { useRecoilValue } from "recoil";
import { createUnitsApi } from "../../../api/creatorApi";
import { getAccessTokenSelector } from "../../../atom";
import { CssTextField } from "./Outline";
import RemoveIcon from "@mui/icons-material/Remove";
import { faL } from "@fortawesome/free-solid-svg-icons";
import UnitInfoOutline from "./UnitInfoOutline";

export default function Unit({
  unit,
  unitIdx,
  setUnits,
  units,
  courseId,
  setChapterUpdate,
}) {
  const [unitTitle, setUnitTitle] = useState(unit.title);
  const [unitDescription, setUnitDescription] = useState(unit.description);
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const [infoClicked, setInfoClicked] = useState(false);
  const [videoClicked, setVideoClicked] = useState(false);
  const [fileUrl, setFileUrl] = useState(unit.url);
  const [unitId, setUnitId] = useState(unit.unitId);
  const [video, setVideo] = useState(unit.file);

  const removeUnit = () => {
    setUnits((current) =>
      current.filter((now) => {
        return now.id !== unit.id;
      })
    );
    setChapterUpdate(true);
  };

  const reviseUnit = () => {
    console.log("reviseUnit");
    console.log(unitTitle, fileUrl, video, unitDescription, unitId);
    const prevList = units;
    let keyIdx = 0;
    const reviseUnit = units.filter((now, idx) => {
      if (now.id === unit.id) {
        keyIdx = idx;
        return now;
      }
    });
    reviseUnit[0].title = unitTitle;
    reviseUnit[0].url = fileUrl;
    reviseUnit[0].unitId = unitId;
    reviseUnit[0].description = unitDescription;
    reviseUnit[0].file = video;
    prevList.splice(keyIdx, 1, reviseUnit[0]);
    setUnits(prevList);
    setChapterUpdate(true);
  };

  function reviseUnitVideo(fileUrl, video) {
    console.log("reviseUnit");
    const prevList = units;
    let keyIdx = 0;
    const reviseUnit = units.filter((now, idx) => {
      if (now.id === unit.id) {
        keyIdx = idx;
        return now;
      }
    });
    reviseUnit[0].url = fileUrl;
    reviseUnit[0].file = video;
    prevList.splice(keyIdx, 1, reviseUnit[0]);
    setUnits(prevList);
    setChapterUpdate(true);
  }

  const reviseUnitId = (unitId) => {
    console.log("reviseUnit");
    const prevList = units;
    let keyIdx = 0;
    const reviseUnit = units.filter((now, idx) => {
      if (now.id === unit.id) {
        keyIdx = idx;
        return now;
      }
    });
    reviseUnit[0].unitId = unitId;
    prevList.splice(keyIdx, 1, reviseUnit[0]);
    setUnits(prevList);
    setChapterUpdate(true);
  };

  const uploadUnit = () => {
    if (unitTitle === "") {
      alert("제목을 입력하세요");
      return;
    }
    if (unitDescription === "") {
      alert("강의 설명을 입력하세요");
      return;
    }
    if (fileUrl === "") {
      alert("영상을 입력하세요");
      return;
    }
    createUnitsApi(
      accessToken,
      courseId,
      unitTitle,
      unitDescription,
      fileUrl
    ).then(({ data }) => {
      setUnitId(data);
      reviseUnitId(data);
    });
  };

  const InfoReviseBtn = ({ infoClicked }) => {
    return infoClicked ? (
      <Button
        sx={{ color: "black" }}
        onClick={() => {
          setInfoClicked(false);
          reviseUnit();
        }}
      >
        제목 입력
      </Button>
    ) : (
      <Button
        sx={{ color: "black" }}
        onClick={() => {
          setInfoClicked(true);
        }}
      >
        제목 수정
      </Button>
    );
  };
  return (
    <>
      <ListItem>
        {infoClicked ? (
          <>
            <CssTextField
              type="text"
              unit={unitTitle}
              id="unitTitle"
              name="unitTitle"
              label="유닛 제목"
              size="small"
              onChange={(e) => {
                setUnitTitle(e.target.value);
              }}
            />
            <CssTextField
              type="text"
              unit={unitDescription}
              id="unitDescription"
              name="unitDescription"
              label="유닛 설명"
              size="small"
              onChange={(e) => {
                setUnitDescription(e.target.value);
              }}
            />
          </>
        ) : (
          <>
            <ListItemText primary={unitIdx + 1 + ". " + unitTitle} />
            <ListItemText primary={unitDescription} />
          </>
        )}
        <div>
          <InfoReviseBtn infoClicked={infoClicked} />
          {videoClicked ? (
            <Button
              sx={{ color: "black" }}
              onClick={() => {
                uploadUnit();
                setVideoClicked(false);
                reviseUnit();
              }}
            >
              영상 확인
            </Button>
          ) : (
            <Button
              sx={{ color: "black" }}
              onClick={() => {
                setVideoClicked(true);
              }}
            >
              영상 등록
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
      {videoClicked && (
        <UnitInfoOutline
          fileUrl={fileUrl}
          setFileUrl={setFileUrl}
          video={video}
          setVideo={setVideo}
          reviseUnitVideo={reviseUnitVideo}
        />
      )}
    </>
  );
}
