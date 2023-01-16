import { motion } from "framer-motion";
import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCircle,
  faStar as emptyStar,
} from "@fortawesome/free-regular-svg-icons";
import {
  faStar,
  faStarHalfStroke,
  faCircle as circle,
} from "@fortawesome/free-solid-svg-icons";
import { useEffect, useState } from "react";
import Swal from "sweetalert2";
import { SideTitle, Wrapper } from "../../style/PlayerSideBarCss";

const ReviewIconTab = styled.div`
  width: 100%;
  padding: 10px 0px;
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  justify-content: center;
  flex-wrap: wrap;
  svg {
    &:nth-child(1) {
      height: 3rem;
    }
    &:nth-child(2) {
      height: 2.6rem;
    }
    &:nth-child(3) {
      height: 2.2rem;
    }
    &:nth-child(4) {
      height: 2.6rem;
    }
    &:nth-child(5) {
      height: 3rem;
    }
  }
  @media screen and (min-width: 1024px) {
    svg {
      &:nth-child(1) {
        height: 2rem;
        width: 2rem;
      }
      &:nth-child(2) {
        height: 1.8rem;
        width: 1.8rem;
      }
      &:nth-child(3) {
        height: 1.6rem;
        width: 1.6rem;
      }
      &:nth-child(4) {
        height: 1.8rem;
        width: 1.8rem;
      }
      &:nth-child(5) {
        height: 2rem;
        width: 2rem;
      }
    }
  }
`;

const SideTitleAlignCenter = styled(SideTitle)`
  text-align: center;
`;

const DivTab = styled(ReviewIconTab)`
  width: 80%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin: 0 auto;
  @media screen and (min-width: 520px) {
    width: 40%;
  }
`;

const BtnTab = styled.div`
  margin-top: 15px;
  width: 90%;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const Icon = styled(FontAwesomeIcon)`
  width: 2.8rem;
  height: 2.8rem;
  color: rgba(0, 0, 0, 0.5);
  cursor: pointer;
  margin: 0 10px;
  @media screen and (min-width: 1024px) {
    margin: 0 5px;
  }
`;

const Star = styled(FontAwesomeIcon)`
  width: 2.8rem;
  height: 2.8rem;
  color: rgba(0, 0, 0, 0.5);
  @media screen and (min-width: 1024px) {
    width: 2.2rem;
    height: 2.2rem;
  }
`;

const Form = styled.div`
  margin-top: 30px;
  display: flex;
  flex-direction: column;
  align-items: center;

  width: 100%;
`;

const Input = styled.textarea`
  width: 90%;
  height: 10vh;
`;

const StartTab = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-wrap: wrap;
  margin-bottom: 25px;
  padding-top: 15px;
`;

export default function UnitReview() {
  // const videoVal = useRecoilValue(VideoAtom);
  const [check, setCheck] = useState(null);
  const [reviewC, setReviewC] = useState("");
  const reviewChecker = (e) => {
    setCheck(e);
  };

  const [starList, setStarList] = useState({
    starCnt: 0,
    isHalf: false,
  });

  // const fetchReview = () => {
  //   axios
  //     .post(
  //       `${STATICURL}/front/units/${queryList.unitId}/rating`,
  //       { comment: reviewC, score: Number(check) },
  //       {
  //         headers: {
  //           "X-AUTH-TOKEN": accessToken,
  //           "Content-Type": "application/json",
  //           "Access-Control-Allow-Credentials": true,
  //           "Access-Control-Allow-Origin": "*",
  //         },
  //       }
  //     )
  //     .then((res) => {
  //       if (res.status === 200) {
  //         Swal.fire({
  //           icon: "success",
  //           title: "감사합니다",
  //           text: "강의 품질 개선에 참고하겠습니다.",
  //           confirmButtonText: "확인",
  //         });
  //       } else {
  //         alert("알 수없는 오류가 발생했습니다.");
  //       }
  //     })
  //     .catch((err) => {
  //       if (err.response.status === 409) {
  //         setOverlappingVal(true);
  //       } else {
  //         alert(err);
  //       }
  //     });
  // };

  // const goReview = (e) => {
  //   e.preventDefault();
  //   //강의를 80퍼 이상 봐야지 보낼 수 있게
  //   if (check === null) {
  //     Swal.fire({
  //       icon: "error",
  //       title: "아직 평가를 못해요!",
  //       text: "별점을 부탁드릴게요.",
  //       confirmButtonText: "확인",
  //     });
  //   } else {
  //     if (videoVal.playedSec < videoVal.duration * (8 / 10)) {
  //       Swal.fire({
  //         icon: "error",
  //         title: "아직 평가를 못해요!",
  //         text: "강의를 80% 이상 수강하신 뒤 평가가 가능합니다.",
  //         confirmButtonText: "확인",
  //       });
  //     } else {
  //       fetchReview();
  //     }
  //   }
  // };

  const makeRateStarList = () => {
    // const rate = unitInfo?.rating?.score;
    // var starCnt = 5;
    // var isStarHalf = true;
    // if (rate !== 5) {
    //   starCnt = parseInt(rate % 5);
    //   if (Number((rate - starCnt)?.toFixed(1)) < 0.5) {
    //     isStarHalf = false;
    //   }
    // }
    // setStarList({
    //   starCnt: starCnt,
    //   isHalf: isStarHalf,
    // });
  };

  const checkIsHalf = () => {
    if (starList.isStarHalf) {
      setStarList((prev) => ({
        ...prev,
        isHalf: false,
      }));
      return true;
    }
    return false;
  };

  useEffect(makeRateStarList, []);

  const UnitStarRate = () => {
    return (
      <StartTab>
        {[...Array(starList?.starCnt)?.keys()]?.map((e) => {
          return <Star icon={faStar} />;
        })}
        {[...Array(5 - starList?.starCnt)?.keys()]?.map((e) => {
          if (checkIsHalf()) {
            return <Star icon={faStarHalfStroke} />;
          }
          return <Star icon={emptyStar} />;
        })}
        {/* <SideTitle>unitInfo?.rating?.score?.toFixed(1)</SideTitle> */}
        <SideTitle>3.3</SideTitle>
      </StartTab>
    );
  };

  return (
    <Wrapper>
      <UnitStarRate />
      <SideTitleAlignCenter>강의에 점수를 주세요.</SideTitleAlignCenter>
      <ReviewIconTab>
        {[1, 2, 3, 4, 5].map((e, idx) => {
          if (check && check === idx + 1) {
            return (
              <Icon
                onClick={() => {
                  reviewChecker(idx + 1);
                }}
                key={e}
                id={e}
                icon={circle}
              ></Icon>
            );
          } else {
            return (
              <Icon
                onClick={() => {
                  reviewChecker(idx + 1);
                }}
                key={e}
                id={e}
                icon={faCircle}
              ></Icon>
            );
          }
        })}
      </ReviewIconTab>
      <DivTab>
        <div>불만족</div>
        <div></div>
        <div></div>
        <div></div>
        <div>만족</div>
      </DivTab>
      <Form>
        <Input
          value={reviewC}
          onChange={(e) => {
            setReviewC(e.target.value);
          }}
          placeholder="개선사항을 적어주세요!"
        />
        <BtnTab>
          <div />
          <button
            whileHover={{ scale: 1.1 }}
            whileTap={{ scale: 1 }}
            // onClick={goReview}
          >
            보내기
          </button>
        </BtnTab>
      </Form>
    </Wrapper>
  );
}
