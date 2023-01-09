import { useState } from "react";
import styled from "styled-components";
import CourseDetail from "../components/course/CourseDetail";
import CourseIntro from "../components/course/CourseIntro";
import CoursePayment from "../components/course/CoursePayment";

const DividerBox = styled.div`
  display: flex;
  min-height: 1180vh;
`;

export default function CoursePage() {
  return (
    <div>
      <CourseIntro />
      <DividerBox>
        <CourseDetail />
        <CoursePayment />
      </DividerBox>
    </div>
  );
}
