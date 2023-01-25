import styled from "styled-components";
import { queryClient } from "../../App";
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

export default function CourseInquire({ courseId }) {
  const courseInquiries = queryClient.getQueryData([
    "courseInquiries",
    courseId,
  ]);

  const Inquires = () => {
    return courseInquiries?.map((inquire) => {
      return <Inquire key={inquire.inquiryId} userInfo={inquire} />;
    });
  };

  const Inquire = ({ userInfo }) => {
    return (
      <>
        <Box>
          <ChatUserInfo writer={userInfo?.writer} rate={""} />
          <ChatContextArea content={userInfo?.content} />
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
