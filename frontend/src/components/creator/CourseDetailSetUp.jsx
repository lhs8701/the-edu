import { Button, Grid, MenuItem } from "@mui/material";
import { useEffect, useState } from "react";
import { Outlet, useNavigate, useParams } from "react-router";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { getCurriculumApi } from "../../api/courseApi";
import { changeChargeTypeApi, setUpTicketApi } from "../../api/creatorApi";
import { getAccessTokenSelector, getCreatorIdSelector } from "../../atom";
import { CREATOR_BAR_LIST } from "../../static";
import { TabTitle } from "../../style/CommonCss";
import { UnderBar } from "../course/ChatComponents";
import DashboardTitleTab from "../dashboard/DashboardTitleTab";
import Title from "../dashboard/Title";
import { CssTextField } from "./uploadCourse/Outline";

const UnitBox = styled.div`
  display: flex;
  width: 100%;
  align-items: center;
  justify-content: space-between;
  &:hover {
    background-color: var(--color-box-gray);
  }
  margin-bottom: 7px; ;
`;

const ChapterBox = styled.div`
  border: 1px solid black;
  padding: 10px 20px;
  box-sizing: border-box;
  margin-bottom: 10px;
`;

export default function CourseDetailSetUp() {
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const { courseId } = useParams();
  const [curriculumList, setCurriculumList] = useState();
  const [type, setType] = useState();
  const navigate = useNavigate();

  const changeType = () => {
    changeChargeTypeApi(accessToken, courseId, type)
      .then(() => {
        alert("설정 되었습니다.");
      })
      .catch((err) => {
        alert(err);
      });
  };

  useEffect(() => {
    getCurriculumApi(courseId)
      .then(({ data }) => setCurriculumList(data.chapters))
      .catch((err) => {
        alert(err);
      });
  }, []);

  const CoursePaidInfo = () => {
    const [period, setPeriod] = useState();
    const [price, setPrice] = useState();
    const [discountPrice, setDiscountPrice] = useState();

    const setUpTicket = () => {
      const data = {
        costPrice: price,
        discountedPrice: discountPrice,
        period: type,
      };
      setUpTicketApi(accessToken, courseId, data)
        .then(() => {
          alert("설정이 되었습니다.");
        })
        .catch(() => {
          alert("err");
        });
    };
    const isCreator = useRecoilValue(getCreatorIdSelector);

    useEffect(() => {
      if (isCreator < 0) {
        navigate(CREATOR_BAR_LIST.list[0].creator[1].url);
      }
    }, []);
    return (
      <>
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
    );
  };

  return (
    <>
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <DashboardTitleTab title="강좌 가격 정책 설정" />
        </Grid>
        <Grid item xs={3}>
          <CssTextField
            size="small"
            fullWidth
            value={type}
            onChange={(e) => {
              setType(e.target.value);
            }}
            select
            label="강의 가격 정책"
            variant="outlined"
            id="cate1"
          >
            <MenuItem value={"FREE"} key={0}>
              무료
            </MenuItem>
            <MenuItem value={"PAID"} key={1}>
              유료
            </MenuItem>
          </CssTextField>
        </Grid>
        <Grid item xs={9}>
          <Button onClick={changeType}> 설정하기</Button>
        </Grid>
        <br />
        <br />
        <br />
        <br />
        <UnderBar />

        <Grid item xs={12}>
          <DashboardTitleTab title="강좌 수강권 가격 설정" />
        </Grid>
        <CoursePaidInfo />

        <br />
        <br />
        <br />
        <br />
        <UnderBar />

        <Grid item xs={12}>
          <DashboardTitleTab title="강좌 커리큘럼 확인" />
        </Grid>
        <Grid item xs={12}>
          {curriculumList?.map((chapter, idx) => {
            return (
              <ChapterBox key={chapter.title}>
                <div>
                  {idx + 1}. {chapter?.title}
                </div>
                <br />
                {chapter?.units?.map((unit, idx) => {
                  return (
                    <UnitBox key={unit.unitId}>
                      <div>
                        {idx + 1}. {unit?.title}
                      </div>
                      <button
                        onClick={() => {
                          navigate(`${unit?.unitId}`);
                        }}
                      >
                        강좌 보기
                      </button>
                    </UnitBox>
                  );
                })}
              </ChapterBox>
            );
          })}
        </Grid>
      </Grid>
    </>
  );
}
