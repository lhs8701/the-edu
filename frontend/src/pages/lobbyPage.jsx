import { useEffect, useState } from "react";
import { useQuery } from "react-query";
import { useNavigate, useParams } from "react-router";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import {
  getCurriculumStatusApi,
  getUserEnrollStatusApi,
} from "../api/courseApi";
import { getAccessTokenSelector, getLoginState } from "../atom";
import Slider from "@mui/material/Slider";
import {
  PLAYER_URL,
  PROCESS_ACCOUNT_URL,
  PROCESS_MAIN_URL,
  STATIC_URL,
} from "../static";
import CourseReviewForm from "../components/course/CourseReviewForm";
import Modal from "react-modal";
import { motion } from "framer-motion";

const LobbyWrapper = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
`;

const InfoSection = styled.div`
  cursor: pointer;
  position: relative;
  width: 31%;
  height: 100%;
`;

const CategorySection = styled.div`
  width: 69%;
`;

const ImgBox = styled.div`
  margin-top: 40px;
  width: 100%;
  height: 200px;
  overflow: hidden;
  border-radius: var(--size-border-radius);
`;

const Img = styled.img`
  width: 100%;
  height: 100%;
`;

const InfoBox = styled.div`
  box-shadow: 0 1px 1px rgb(0 0 0 / 16%), 0 2px 14px rgb(0 0 0 / 16%);
  position: absolute;
  width: 87%;
  height: 48%;
  background-color: #fffbfb;
  bottom: -18%;
  left: 6.5%;
  border-radius: var(--size-border-radius);
  padding: 15px 20px;
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-direction: column;
`;

const Tab = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
`;

const InfoTab = styled(Tab)`
  justify-content: space-between;
`;
const CourseTitle = styled.p`
  font-weight: var(--weight-middle);
  font-size: 18px;
`;
const CourseRate = styled.p``;

const CategoryBox = styled.div`
  width: 85%;
  margin: 0 auto;
  margin-bottom: 15px;
`;

const BigCategoryTab = styled.div`
  width: 100%;
  background-color: var(--color-box-primary);
  font-weight: var(--weight-middle);
  font-size: 1.5rem;
  padding: 10px 20px;
  box-sizing: border-box;
  /* box-shadow: 0 0 3px rgb(0 0 0 / 16%), 0 0px 1px rgb(0 0 0 / 16%); */
`;

const SmallCategoryTab = styled.div`
  width: 100%;
  background-color: ${({ completed }) =>
    completed ? "var(--color-box-gray)" : "var(--color-background)"};
  font-weight: var(--weight-middle);
  font-size: 1.1rem;
  padding: 10px 20px;
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: space-between;
  &:hover {
    background-color: #dcdcdc;
  }
`;

const PlayBtn = styled.button`
  border: none;
  border-radius: 10px;
  padding: 3px 10px;
  &:hover {
    scale: 1.1;
  }
`;

const ReviewBtn = styled(motion.button)`
  position: absolute;
  bottom: -150px;
  right: 35%;
  height: 40px;
  width: 80px;
  border-radius: 10px;
  background-color: var(--color-primary);
  border: none;
  font-weight: var(--weight-middle);
  font-size: 1rem;
`;

export default function LobbyPage() {
  const loginState = useRecoilValue(getLoginState);
  const { courseId } = useParams();
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const navigate = useNavigate();
  const [isModalOpen, setIsModalOpen] = useState(false);

  const { data } = useQuery(
    ["userCurriStatus", courseId],
    () => {
      return getCurriculumStatusApi(accessToken, courseId);
    },
    {
      enabled: !!courseId,
      onSuccess: (res) => {},
      onError: () => {
        console.error("에러 발생했지롱");
      },
    }
  );

  const playUnit = (unitId) => {
    window.open(
      `${PLAYER_URL}/${courseId}/${unitId}`, // 나중에 the edu 도메인으로 변경해야함
      "the-edu 플레이어",
      "location=no,status=no,scrollbars=no"
    );
  };

  const checkUserEnroll = () => {
    getUserEnrollStatusApi(accessToken, courseId)
      .then(({ data }) => {
        if (!data) {
          alert("부적절한 접근입니다!");
          navigate("/");
        }
      })
      .catch((err) => {
        console.log(err);
        // alert("Error");
        // navigate("/");
      });
  };

  useEffect(checkUserEnroll, []);

  const InFo = ({ courseInfo }) => {
    return (
      <>
        <ImgBox
          onClick={() => {
            navigate(PROCESS_MAIN_URL.COURSES + "/" + courseId);
          }}
        >
          <Img src={STATIC_URL + courseInfo?.thumbnailImage?.smallFilePath} />
        </ImgBox>
        <InfoBox>
          <InfoTab>
            <CourseTitle>{courseInfo?.title}</CourseTitle>
            <CourseRate>- {courseInfo?.instructor}</CourseRate>
          </InfoTab>
          <Tab>
            <Slider
              valueLabelDisplay="off"
              disabledSwap
              min={0}
              max={100}
              value={
                (courseInfo?.completedUnits / courseInfo?.entireUnits) * 100
              }
              sx={{
                height: 8,
                color: "var(--color-red)",
                "& .MuiSlider-thumb": {
                  display: "none",
                },
                "& .MuiSlider-rail": {
                  color: "var(--color-box-gray)",
                },
              }}
            ></Slider>
          </Tab>
          <Tab>
            {(
              (courseInfo?.completedUnits / courseInfo?.entireUnits) *
              100
            ).toFixed()}
            %
          </Tab>
        </InfoBox>
      </>
    );
  };

  const Unit = ({ unitInfo, idx }) => {
    return (
      <SmallCategoryTab completed={unitInfo?.completed}>
        &nbsp;&nbsp;&nbsp;{idx + 1}. &nbsp;
        {unitInfo?.title}
        <PlayBtn onClick={() => playUnit(unitInfo?.unitId)}>재생</PlayBtn>
      </SmallCategoryTab>
    );
  };

  const SmallCategories = ({ smallCurri }) => {
    return smallCurri.map((unit, idx) => {
      return <Unit unitInfo={unit} idx={idx} key={unit?.unitId} />;
    });
  };

  const Category = ({ bigCurri, idx }) => {
    return (
      <CategoryBox>
        <BigCategoryTab>
          {idx + 1}. &nbsp;{bigCurri?.title}
        </BigCategoryTab>
        <SmallCategories smallCurri={bigCurri?.units} />
      </CategoryBox>
    );
  };

  const Categories = ({ curriculum }) => {
    return curriculum?.map((bigCurri, idx) => {
      return <Category key={idx} bigCurri={bigCurri} idx={idx} />;
    });
  };

  if (!loginState) {
    alert("로그인 하세요.");
    window.location.replace(PROCESS_ACCOUNT_URL.LOGIN);
  }

  return (
    <LobbyWrapper>
      <InfoSection>
        <InFo courseInfo={data?.data?.courseStatus} />
        <ReviewBtn
          onClick={() => {
            setIsModalOpen(true);
          }}
        >
          리뷰 등록
        </ReviewBtn>
      </InfoSection>

      <CategorySection>
        <br />
        <br />
        <br />
        <br />
        <br />
        <Categories curriculum={data?.data?.chapters} />
      </CategorySection>
      <Modal
        isOpen={isModalOpen}
        ariaHideApp={false}
        onRequestClose={() => setIsModalOpen(false)}
        style={{
          overlay: {
            position: "fixed",
            top: 0,
            left: 0,
            backgroundColor: "rgba(0, 0, 0, 0.2)",
            width: "100%",
            height: "100%",
          },
          content: {
            overflow: "hidden",
            width: "35%",
            height: "75%",
            top: "10%",
            left: "32.5%",
          },
        }}
      >
        {
          <CourseReviewForm
            setIsModalOpen={setIsModalOpen}
            courseId={courseId}
          />
        }
      </Modal>
    </LobbyWrapper>
  );
}
