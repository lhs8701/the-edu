import styled from "styled-components";
import { AccountSmallBtn } from "../../style/AccountComponentCss";
import { Title } from "../../style/CourseCss";
import {
  Box,
  ChatContextArea,
  ChatUserInfo,
  InputTextArea,
  UnderBar,
} from "./ChatComponents";

const InquireWrapper = styled.div`
  width: 100%;
`;

const InputBox = styled.div`
  margin-top: 20px;
  display: flex;
  align-items: center;
  justify-content: flex-end;
`;

export default function CourseInquire() {
  const Inquires = () => {
    return (
      <>
        <Box>
          <ChatUserInfo />
          <ChatContextArea />
        </Box>
        <UnderBar />
      </>
    );
  };

  return (
    <InquireWrapper>
      <Title>문의 사항</Title>
      <br />
      <br />
      <InputTextArea placeholder="강의에 대한 궁금증을 적어주세요!" />
      <InputBox>
        <AccountSmallBtn>등록</AccountSmallBtn>
      </InputBox>
      <Inquires />
    </InquireWrapper>
  );
}
