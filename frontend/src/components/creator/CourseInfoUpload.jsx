import { Button, Fab, Grid, MenuItem, Select } from "@mui/material";
import { useState } from "react";
import { CATE_VALUE } from "../../static";
import { CssTextField } from "./Outline";
import AddIcon from "@mui/icons-material/Add";
import { useEffect } from "react";
import styled from "styled-components";
import { uploadImageApi } from "../../api/creatorApi";
import { getAccessTokenSelector } from "../../atom";
import { useRecoilValue } from "recoil";
import CourseInfoImage from "./CourseInfoImage";
import RemoveIcon from "@mui/icons-material/Remove";
export const PreviewImg = styled.img`
  width: 350px;
  height: 200px;
`;

export default function CourseInfoUpload({ setCourseValue }) {
  const MIN_PRICE = 10000;
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const [dummyImgUrlUpdate,setDummyImgUrlUpdate] = useState(false)
  const [courseTitle, setCourseTitle] = useState("");
  const [courseDetail, setCourseDetail] = useState("");
  const [firstCategory, setFirstCategory] = useState("");
  const [secCategory, setSecCategory] = useState("");
  const [introImgCnt, setIntroImgCnt] = useState(1);
  const [coursePrice, setCoursePrice] = useState(0);
  const introImg = {
    id: 0,
    url:""
  };
  const [introImgList, setIntroImgList] = useState([introImg]);
  const [thumbImg, setThumbImg] = useState({
    file:false,url:""
  });
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
    const introImg = {
      id: introImgCnt,
      url:""
    };
    setIntroImgList((prev)=>[...prev,introImg])
    setIntroImgCnt((prev) => prev + 1);
  };


  const uploadThumbImg = (e) => {
    uploadImageApi(e.target.files[0],accessToken).then(({data})=>{setThumbImg({file:e.target.files[0],url:data.originalFilePath});}).catch((err)=>{console.log(err)})
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



  useEffect(() => {
    console.log("updateTop")
    const introImgUrlList = []
    introImgList.filter((imgVal, idx) => {
      if (String(imgVal.url) !== "") {
        introImgUrlList.push(imgVal.url)
        }
      });
      
    setCourseValue({
      title: courseTitle,
      detail: courseDetail,
      category: secCategory,
      price: coursePrice,
      thumbUrl:thumbImg.url,
      descriptionImageUrls:introImgUrlList
    });
  }, [introImgList,dummyImgUrlUpdate,courseTitle, courseDetail, secCategory, coursePrice, thumbImg]);

  console.log("top")
  console.log(introImgList)

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
          inputProps={{ min: 4, max: 10 }}
          onChange={(e) => {
            setCoursePrice(e.target.value);
          }}
        />
      </Grid>
      <Grid item xs={6}></Grid>
      <Grid item xs={6}>
        <div>썸네일 이미지 (1장)</div>
        <br />
        {thumbImg.file ? (
          <>
            <PreviewImg src={URL.createObjectURL(thumbImg?.file)} />
            <Button
              sx={{
                backgroundColor: "var(--color-box-gray)",
                "&:hover": {
                  backgroundColor: "#b1b1b1",
                },
              }}
              variant="contained"
              onClick={() => {
                setThumbImg({
                  file:false,
                  url:""
                });
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
      

      
      
      {introImgList.map((intro) => {
      return <CourseInfoImage setDummyImgUrlUpdate={setDummyImgUrlUpdate} accessToken={accessToken}introImgList={introImgList} setIntroImgList={setIntroImgList} key={intro.key} value={intro} />;
      })}
    </Grid>
  );
}
