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
  const [selectTicket, setSelectTicket] = useState({
    id: ticketInfo[0]?.id,
    idx: 0,
  });

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
      return;
    }
    if (isAlreadyIn) {
      alert("이미 구입한 강좌입니다.");
      navigate(PROCESS_MAIN_URL.LOBBY.slice(1));
      return;
    }
    if (ticketInfo?.length === 1) {
      postItemPurchaseApi(ticketInfo[0].id, accessToken, {
        couponId: null,
        point: null,
        key: null,
        tossId: null,
        amount: null,
      })
        .then(() => {
          alert("등록이 완료되었습니다.");
          navigate(PROCESS_MAIN_URL.LOBBY.slice(0));
        })
        .catch((err) => {
          console.log(err);
          if (err.response.status === 400) {
            alert("사용 불가한 쿠폰입니다.");
          } else {
            alert("결제에 오류가 있습니다.");
          }
        });
      return;
    }
    navigate(`${PROCESS_MAIN_URL.PURCHASE}/${courseId}/${selectTicket.id}`, {
      state: {
        price: ticketInfo[selectTicket.idx].discountedPrice,
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
        <ONOFFTab>온/오프</ONOFFTab>
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
          setSelectTicket({
            id: e.target.value,
            idx: e.nativeEvent.target.options.selectedIndex,
          });
        }}
        name="purchaseOption"
      >
        {ticketInfo?.map((option) => (
          <option key={option.id} value={option.id}>
            {option?.coursePeriod?.description}
          </option>
        ))}
      </Select>
      <br />
      <PriceBox>
        <Tab>
          <PrimaryCostTab>원 가격</PrimaryCostTab>
          <PrimaryCostTab>
            {ticketInfo[selectTicket.idx].costPrice}
          </PrimaryCostTab>
        </Tab>
        <Tab>
          <DiscountTab>할인 가격</DiscountTab>
          <PrimaryCostTab>
            -{" "}
            {ticketInfo[selectTicket.idx].costPrice -
              ticketInfo[selectTicket.idx].discountedPrice}
          </PrimaryCostTab>
        </Tab>
        <PriceUnderBar />
        <Tab>
          <OwnPriceTab>실 결제 가격</OwnPriceTab>
          <PrimaryCostTab>
            {ticketInfo[selectTicket.idx].discountedPrice}
          </PrimaryCostTab>
        </Tab>
      </PriceBox>

      <PurchaseBtn onClick={goPurchase}>결제</PurchaseBtn>
    </PaymentBox>
  );
}
