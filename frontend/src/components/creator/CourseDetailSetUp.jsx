import { Button, Grid, MenuItem } from "@mui/material";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router";
import { useRecoilValue } from "recoil";
import { getCourseTicketsApi, getCurriculumApi } from "../../api/courseApi";
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
  const [policy, setPolicy] = useState(true);
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
    getCourseTicketsApi(courseId)
      .then(({ data }) => {
        console.log(data);
        if (data.costPrice === 0) {
          setPolicy(false);
        }
        setPrice(data.costPrice);
        setPeriod(data.coursePeriod);
        setDiscountPrice(data.discountedPrice);
      })
      .catch((err) => {
        alert(err);
      });
    getCurriculumApi(courseId)
      .then(({ data }) => setCurriculumList(data.chapters))
      .catch((err) => {
        alert(err);
      });
  }, []);
  useEffect(() => {
    if (!policy) {
      setPrice(0);
      setPeriod(0);
      setDiscountPrice(0);
    }
  }, [policy]);
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
            value={policy}
            onChange={(e) => {
              setPolicy(e.target.value);
            }}
            select
            label="강의 가격 정책"
            variant="outlined"
            id="cate1"
          >
            <MenuItem value={false} key={0}>
              무료
            </MenuItem>
            <MenuItem value={true} key={1}>
              유료
            </MenuItem>
          </CssTextField>
        </Grid>
        <Grid item xs={12} />
        {policy && (
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
                  if (e.target.value > price) {
                    alert(
                      "원 가격보다 높은 가격은 할인가격으로 설정할 수 없습니다."
                    );
                    return;
                  }
                  setDiscountPrice(e.target.value);
                }}
                type="number"
                label="할인 가격"
                variant="outlined"
                id="cate1"
                placeholder="할인해서 판매할 가격을 설정하세요."
              />
            </Grid>
            <div>
              <br />
              &nbsp;&nbsp;&nbsp;&nbsp;- 강좌 수강 시한을 영구로 설정하고
              싶으시다면 0을 입력하시면 됩니다.
            </div>
          </>
        )}

        <Grid item xs={12}>
          <Button variant="contained" onClick={setUpTicket}>
            {" "}
            설정하기
          </Button>
        </Grid>
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
