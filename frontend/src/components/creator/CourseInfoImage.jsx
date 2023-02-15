import { Button, CircularProgress, Fab, Grid } from "@mui/material";
import { useState } from "react";
import { uploadImageApi } from "../../api/creatorApi";
import { PreviewImg, ProgressBarDiv } from "./CourseInfoUpload";

import RemoveIcon from "@mui/icons-material/Remove";
import { useEffect } from "react";

export default function CourseInfoImage({
  value,
  introImgList,
  setIntroImgList,
  accessToken,
  setDummyImgUrlUpdate,
}) {
  const [isUpload, setIsUpload] = useState();
  const [imgValue, setImgValue] = useState({
    file: false,
    url: "",
  });
  const removeCnt = () => {
    setIntroImgList((current) =>
      current.filter((unit) => {
        return unit.id !== value.id;
      })
    );
  };

  const updateIntroImgList = () => {
    const prevList = introImgList;
    let keyIdx = 0;
    const chapter = introImgList.filter((chapter, idx) => {
      if (chapter.id === value.id) {
        keyIdx = idx;
        return chapter;
      }
    });
    chapter[0].url = imgValue.url; //filter의 리턴은 배열이다 주의
    prevList.splice(keyIdx, 1, chapter[0]); //이건 그 자리 인덱스를 찾아야지
    setIntroImgList(prevList);
    setDummyImgUrlUpdate((prev) => !prev);
  };

  const uploadImg = (e) => {
    setIsUpload(true);
    uploadImageApi(e.target.files[0], accessToken)
      .then(({ data }) => {
        setImgValue({ file: e.target.files[0], url: data.originalFilePath });
      })
      .catch((err) => {
        alert(err);
        setIsUpload(false);
      });
  };

  useEffect(updateIntroImgList, [imgValue]);

  return (
    <Grid item xs={10}>
      {imgValue.file ? (
        <>
          <PreviewImg src={URL.createObjectURL(imgValue?.file)} />
          <Button
            sx={{
              backgroundColor: "var(--color-box-gray)",
              "&:hover": {
                backgroundColor: "#b1b1b1",
              },
            }}
            variant="contained"
            onClick={() => {
              setIsUpload(false);
              setImgValue({
                file: false,
                url: "",
              });
            }}
          >
            취소
          </Button>
        </>
      ) : isUpload ? (
        <ProgressBarDiv>
          <CircularProgress
            sx={{
              color: "var(--color-primary)",
            }}
          />
        </ProgressBarDiv>
      ) : (
        <Button
          sx={{
            width: 300,
            height: 200,
            backgroundColor: "var(--color-box-gray)",
            "&:hover": {
              backgroundColor: "#b1b1b1",
            },
          }}
          variant="contained"
          component="label"
        >
          Upload
          <input
            hidden
            accept=".jpg, .jpeg, .png"
            onChange={uploadImg}
            multiple
            type="file"
          />
        </Button>
      )}
      <Fab aria-label="remove" size="small">
        <RemoveIcon onClick={removeCnt} />
      </Fab>
    </Grid>
  );
}
