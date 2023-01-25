import { useQuery } from "react-query";
import styled from "styled-components";
import { getcourseReviewsApi } from "../../api/courseApi";
import { queryClient } from "../../App";
import { AccountSmallBtn } from "../../style/AccountComponentCss";
import { Wrapper } from "../../style/CommonCss";
import { Title } from "../../style/CourseCss";
import {
  Box,
  ChatBottom,
  ChatContextArea,
  ChatUserInfo,
} from "./ChatComponents";

const BtnBox = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: flex-end;
`;

export default function CourseReview({ courseId }) {
  const courseReviews = queryClient.getQueryData(["courseReviews", courseId]);

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
    <Wrapper>
      <Title>수강 후기</Title>
      <BtnBox>
        <AccountSmallBtn>등록</AccountSmallBtn>
      </BtnBox>
      <ReviewCreator />
    </Wrapper>
  );
}
