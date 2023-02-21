import { Button, Grid, MenuItem } from "@mui/material";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router";
import { useRecoilValue } from "recoil";
import { getCurriculumApi } from "../../api/courseApi";
import { changeChargeTypeApi, setUpTicketApi } from "../../api/creatorApi";
import { getAccessTokenSelector, getCreatorIdSelector } from "../../atom";
import { CREATOR_BAR_LIST } from "../../static";
import { UnderBar } from "../course/ChatComponents";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";
import SimpleCurriculumCheck from "./SimpleCurriculumCheck";
import { CssTextField } from "./uploadCourse/Outline";

export default function CourseDetailSetUp() {
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const { courseId } = useParams();
  const [curriculumList, setCurriculumList] = useState();
  const [period, setPeriod] = useState();
  const [price, setPrice] = useState();
  const [discountPrice, setDiscountPrice] = useState();
  const isCreator = useRecoilValue(getCreatorIdSelector);
  const navigate = useNavigate();

  const setUpTicket = () => {
    const data = {
      costPrice: price,
      discountedPrice: discountPrice,
      coursePeriod: period,
    };
    setUpTicketApi(accessToken, courseId, data)
      .then(() => {
        alert("설정이 되었습니다.");
      })
      .catch(() => {
        alert("err");
      });
  };

  useEffect(() => {
    if (isCreator < 0) {
      navigate(CREATOR_BAR_LIST.list[0].creator[1].url);
    }
    getCurriculumApi(courseId)
      .then(({ data }) => setCurriculumList(data.chapters))
      .catch((err) => {
        alert(err);
      });
  }, []);

  return (
    <>
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <DashboardTitleTab title="강좌 가격 설정" />
        </Grid>
        <Grid item xs={3}>
          <CssTextField
            size="small"
            fullWidth
            value={price}
            onChange={(e) => {
              setPrice(e.target.value);
            }}
            select
            label="강의 가격 정책"
            variant="outlined"
            id="cate1"
          >
            <MenuItem value={0} key={0}>
              무료
            </MenuItem>
            <MenuItem value={10000} key={1}>
              유료
            </MenuItem>
          </CssTextField>
        </Grid>
        <Grid item xs={12} />
        {price !== 0 && (
          <>
            {" "}
            <Grid item xs={3}>
              <CssTextField
                size="small"
                fullWidth
                value={period}
                onChange={(e) => {
                  setPeriod(e.target.value);
                }}
                type="number"
                label="강좌 수강 기한 설정"
                variant="outlined"
                id="cate1"
                placeholder="기본 설정은 달(month)입니다."
              />
            </Grid>
            <Grid item xs={3}>
              <CssTextField
                size="small"
                fullWidth
                value={price}
                onChange={(e) => {
                  setPrice(e.target.value);
                }}
                type="number"
                label="강의 원 가격"
                variant="outlined"
                id="cate1"
                placeholder="강좌의 원 가격을 설정하세요."
              />
            </Grid>
            <Grid item xs={3}>
              <CssTextField
                size="small"
                fullWidth
                value={discountPrice}
                onChange={(e) => {
                  setDiscountPrice(e.target.value);
                }}
                type="number"
                label="할인 가격"
                variant="outlined"
                id="cate1"
                placeholder="할인해서 판매할 가격을 설정하세요."
              />
            </Grid>
            <Grid item xs={3}>
              <Button onClick={setUpTicket}> 설정하기</Button>
            </Grid>
          </>
        )}
        <br />
        <br />
        <br />
        <br />
        <UnderBar />

        <Grid item xs={12}>
          <DashboardTitleTab title="강좌 커리큘럼 확인" />
        </Grid>

        <Grid item xs={12}>
          <SimpleCurriculumCheck curriculumList={curriculumList} />
        </Grid>
      </Grid>
    </>
  );
}
