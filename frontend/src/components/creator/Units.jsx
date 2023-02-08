import { Button, ListItem, ListItemText } from "@mui/material";
import { useState } from "react";
import { Outlet, useNavigate, useParams } from "react-router";
import { useRecoilValue } from "recoil";
import { createUnitsApi } from "../../api/creatorApi";
import { getAccessTokenSelector } from "../../atom";
import { CssTextField } from "./Outline";
import RemoveIcon from "@mui/icons-material/Remove";

export default function Units({ value, unitIdx, setUnits, units, courseId }) {
  const [unittitle, setUnitTitle] = useState("");
  const [unitDescription, setUnitDescription] = useState("");
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const [clicked, setClicked] = useState(false);
  const { unitnumber } = useParams();
  const [fileUrl, setFileUrl] = useState(value?.url);
  const [unitId, setUnitId] = useState(value.unitId);
  const navigate = useNavigate();

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
    unit[0].url = fileUrl;
    prevList.splice(keyIdx, 1, unit[0]);
    setUnits(prevList);
  };

  const uploadUnit = () => {
    createUnitsApi(accessToken, courseId, unittitle, unitDescription, fileUrl);
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
          <ListItemText primary={unitIdx + 1 + ". " + unittitle} />
        )}
        <div>
          {clicked ? (
            <Button
              sx={{ color: "black" }}
              onClick={() => {
                setClicked(false);
                // reviseUnitTitle();
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
          {Number(unitnumber) === Number(value.id) ? (
            <Button
              sx={{ color: "black" }}
              onClick={() => {
                navigate("");
                // reviseUnitUrl();
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
        <Outlet context={[fileUrl, setFileUrl]} />
      )}
    </>
  );
}
