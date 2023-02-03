import { Button, Fab, Grid, MenuItem, Select } from "@mui/material";
import { useState } from "react";
import { CATE_VALUE } from "../../static";
import { CssTextField } from "./Outline";
import AddIcon from "@mui/icons-material/Add";
import { useEffect } from "react";
import styled from "styled-components";

const PreviewImg = styled.img`
  width: 350px;
  height: 200px;
`;

export default function CourseInfoUpload({ setCourseValue }) {
  const [courseTitle, setCourseTitle] = useState("");
  const [courseDetail, setCourseDetail] = useState("");
  const [firstCategory, setFirstCategory] = useState("");
  const [secCategory, setSecCategory] = useState("");
  const [introImgCnt, setIntroImgCnt] = useState(1);
  const [coursePrice, setCoursePrice] = useState(0);
  const [thumbImg, setThumbImg] = useState();
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

  const plusIntroImg = () => {
    setIntroImgCnt((prev) => prev + 1);
  };

  const uploadThumbImg = (e) => {
    setThumbImg(e.target.files[0]);
  };

  const CategoryComponent = () => {
    return (
      <>
        <Grid item xs={3}>
          <Select
            size="small"
            fullWidth
            value={firstCategory}
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
          </Select>
        </Grid>
        <Grid item xs={3}>
          <Select
            size="small"
            fullWidth
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
          </Select>
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

  useEffect(() => {
    setCourseValue({
      title: courseTitle,
      detail: courseDetail,
      category: secCategory,
      price: coursePrice,
    });
  }, [courseTitle, courseDetail, secCategory, coursePrice, thumbImg]);

  return (
    <Grid container spacing={2}>
      <Grid item xs={6}>
        <CssTextField
          fullWidth
          type="text"
          id="title"
          name="title"
          label="강좌 제목"
          size="small"
          value={courseTitle}
          onChange={(e) => {
            setCourseTitle(e.target.value);
          }}
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
          value={courseDetail}
          onChange={(e) => {
            setCourseDetail(e.target.value);
          }}
        />
      </Grid>
      <Grid item xs={4}>
        <CssTextField
          fullWidth
          type="number"
          id="price"
          name="price"
          label="강의 가격"
          size="small"
          value={coursePrice}
          onChange={(e) => {
            setCoursePrice(e.target.value);
          }}
        />
      </Grid>
      <Grid item xs={6}></Grid>
      <Grid item xs={6}>
        <div>썸네일 이미지 (1장)</div>
        <br />
        {thumbImg ? (
          <>
            <PreviewImg src={URL.createObjectURL(thumbImg)} />
            <Button
              sx={{
                backgroundColor: "var(--color-box-gray)",
                "&:hover": {
                  backgroundColor: "#b1b1b1",
                },
              }}
              variant="contained"
              onClick={() => {
                setThumbImg();
              }}
            >
              취소
            </Button>
          </>
        ) : (
          <>
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
                onChange={uploadThumbImg}
                multiple
                type="file"
              />
            </Button>{" "}
          </>
        )}
      </Grid>

      <Grid item xs={11}>
        강의 소개 이미지 (추가가능)
      </Grid>
      <Grid item xs={1}>
        <Fab aria-label="add" size="small">
          <AddIcon onClick={plusIntroImg} />
        </Fab>
      </Grid>
      {[...Array(introImgCnt).keys()].map((cnt) => {
        return <ImgUploadComponent key={cnt} />;
      })}
    </Grid>
  );
}
