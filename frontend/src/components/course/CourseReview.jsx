import { useQuery } from "react-query";
import styled from "styled-components";
import { getcourseReviewsApi } from "../../api/courseApi";
import { queryClient } from "../../index";
import { AccountSmallBtn } from "../../style/AccountComponentCss";
import { Wrapper } from "../../style/CommonCss";
import { Title } from "../../style/CourseCss";
import {
  Box,
  ChatBottom,
  ChatContextArea,
  ChatUserInfo,
  UnderBar,
} from "./ChatComponents";
import CourseReviewForm from "./CourseReviewForm";
import Modal from "react-modal";
import { useState } from "react";

const BtnBox = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: flex-end;
`;

export default function CourseReview({ courseId }) {
  const courseReviews = queryClient.getQueryData(["courseReviews", courseId]);

  const Review = ({ userInfo }) => {
    return (
      <>
        <Box>
          <ChatUserInfo writer={userInfo?.writer} />
          <ChatContextArea content={userInfo?.content} />
          {/* <ChatBottom /> */}
        </Box>
        <UnderBar />
      </>
    );
  };
  const Reviews = () => {
    return courseReviews?.map((review) => {
      return <Review key={review.reviewId} userInfo={review} />;
    });
  };

  return (
    <Wrapper>
      <Title>수강 후기</Title>
      <br />
      <br />
      <Reviews />
    </Wrapper>
  );
}
