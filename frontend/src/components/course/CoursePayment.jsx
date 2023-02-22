import { useNavigate } from "react-router";
import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHeart as alreadyHeart } from "@fortawesome/free-solid-svg-icons";
import { faHeart } from "@fortawesome/free-regular-svg-icons";
import { PROCESS_MAIN_URL } from "../../static";
import {
  DiscountTab,
  ONOFFTab,
  OwnPriceTab,
  PaymentBox,
  PriceBox,
  PriceUnderBar,
  PrimaryCostTab,
  PurchaseBtn,
  Select,
} from "../../style/CourseCss";
import { useEffect, useLayoutEffect, useState } from "react";
import {
  courseWishApi,
  courseWishCheckApi,
  getUserEnrollStatusApi,
} from "../../api/courseApi";
import {
  getAccessTokenSelector,
  getLoginState,
  getMemberIdSelector,
} from "../../atom";
import { useRecoilValue } from "recoil";
import { queryClient } from "../..";
import { postItemPurchaseApi } from "../../api/orderApi";

const IconBox = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;
const Title = styled.h1`
  font-size: 18px;
  font-weight: var(--weight-point);
`;
const Teacher = styled.h2`
  width: 100%;
  font-size: 15px;
`;
const Tab = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;
const Icon = styled(FontAwesomeIcon)`
  width: 1.5rem;
  height: 1.5rem;
  color: var(--color-primary);
  cursor: pointer;
  &:hover {
    scale: 1.2;
  }
  &:active {
    scale: 0.8;
  }
`;

export default function CoursePayment({
  title,
  teacher,
  purchaseOption,
  courseId,
  ticketInfo,
}) {
  const navigate = useNavigate();
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const memberId = useRecoilValue(getMemberIdSelector);
  const loginState = useRecoilValue(getLoginState);
  const [isWishState, setIsWishState] = useState(false);
  const [isAlreadyIn, setIsAlereadyIn] = useState(false);
  const [isWishPushState, setIsWishPushState] = useState();
  const [selectTicket, setSelectTicket] = useState(ticketInfo?.id);

  useLayoutEffect(() => {
    if (loginState) {
      courseWishCheckApi(courseId, accessToken)
        .then(({ data }) => {
          if (data.code === -7001) {
            setIsWishState(false);
          } else {
            if (data) {
              setIsWishState(true);
            } else {
              setIsWishState(false);
            }
          }
        })
        .catch((err) => {
          setIsWishState(false);
        });
    }
  }, []);

  useEffect(() => {
    if (isWishState) {
      setIsWishPushState(true);
    } else {
      setIsWishPushState(false);
    }
  }, [isWishState]);

  const pushWish = () => {
    if (loginState) {
      setIsWishPushState((prev) => !prev);
      courseWishApi(courseId, accessToken);
      if (
        queryClient?.getQueryData({ queryKey: ["wishCourseList", memberId] })
      ) {
        queryClient.refetchQueries({ queryKey: ["wishCourseList", memberId] });
      }
    } else {
      alert("로그인이 필요해요!");
    }
  };

  const goPurchase = () => {
    if (!loginState) {
      alert("로그인이 필요해요!");
      navigate("/account/login");
      return;
    }
    if (isAlreadyIn) {
      alert("이미 구입한 강좌입니다.");
      navigate(PROCESS_MAIN_URL.LOBBY.slice(1));
      return;
    }
    if (ticketInfo?.costPrice === 0) {
      postItemPurchaseApi(ticketInfo.id, accessToken, {
        couponId: null,
        point: null,
        key: null,
        tossId: null,
        amount: null,
      })
        .then(() => {
          alert("등록이 완료되었습니다.");
          navigate(PROCESS_MAIN_URL.LOBBY.slice(1));
        })
        .catch((err) => {
          if (err.response.status === 400) {
            alert("이용 불가입니다.");
          } else {
            alert("결제에 오류가 있습니다.");
          }
        });
      return;
    }
    navigate(`${PROCESS_MAIN_URL.PURCHASE}/${courseId}/${selectTicket}`, {
      state: {
        price: ticketInfo.discountedPrice,
      },
    });
  };

  const checkUserEnroll = () => {
    if (loginState) {
      getUserEnrollStatusApi(accessToken, courseId)
        .then(({ data }) => {
          if (data) {
            setIsAlereadyIn(true);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };

  useEffect(checkUserEnroll, []);

  return (
    <PaymentBox>
      <IconBox>
        <ONOFFTab>온라인</ONOFFTab>
        {isWishPushState ? (
          <Icon onClick={pushWish} icon={alreadyHeart} />
        ) : (
          <Icon onClick={pushWish} icon={faHeart} />
        )}
      </IconBox>
      <Title>{title}</Title>
      <Teacher>{teacher} 강사</Teacher>
      <Select
        value={selectTicket.id}
        onChange={(e) => {
          setSelectTicket(e.target.value);
        }}
        name="purchaseOption"
      >
        <option key={ticketInfo.id} value={ticketInfo.id}>
          {ticketInfo?.costPrice === 0
            ? "무료 강좌"
            : ticketInfo?.coursePeriod === null
            ? "영구 수강권"
            : ticketInfo?.coursePeriod + "개월 수강권"}
        </option>
      </Select>
      <br />
      <PriceBox>
        <Tab>
          <PrimaryCostTab>원 가격</PrimaryCostTab>
          <PrimaryCostTab>{ticketInfo?.costPrice}</PrimaryCostTab>
        </Tab>
        <Tab>
          <DiscountTab>할인 가격</DiscountTab>
          <PrimaryCostTab>
            - {ticketInfo?.costPrice - ticketInfo?.discountedPrice}
          </PrimaryCostTab>
        </Tab>
        <PriceUnderBar />
        <Tab>
          <OwnPriceTab>실 결제 가격</OwnPriceTab>
          <PrimaryCostTab>{ticketInfo?.discountedPrice}</PrimaryCostTab>
        </Tab>
      </PriceBox>

      <PurchaseBtn onClick={goPurchase}>결제</PurchaseBtn>
    </PaymentBox>
  );
}
