import styled from "styled-components";
import { AccountSmallBtn } from "../../style/AccountComponentCss";
import { Title } from "../../style/CourseCss";
import {
  Box,
  ChatBottom,
  ChatContextArea,
  ChatUserInfo,
  UnderBar,
} from "./ChatComponents";

const ReivewWrapper = styled.div`
  width: 100%;
`;

const BtnBox = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: flex-end;
`;

export default function CourseReview() {
  const ReviewCreator = () => {
    return (
      <>
        <Box>
          <ChatUserInfo />
          <ChatContextArea />
          <ChatBottom />
        </Box>
        {/* <UnderBar /> */}
      </>
    );
  };
  return (
    <ReivewWrapper>
      <Title>수강 후기</Title>
      <BtnBox>
        <AccountSmallBtn>등록</AccountSmallBtn>
      </BtnBox>
      <ReviewCreator />
    </ReivewWrapper>
  );
}
