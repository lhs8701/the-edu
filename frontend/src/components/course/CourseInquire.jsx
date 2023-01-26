import { useState } from "react";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { postcourseInquiriessApi } from "../../api/courseApi";
import { queryClient } from "../../index";
import { getAccessTokenSelector, getMemberIdSelector } from "../../atom";
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
  const [textValue, setTextValue] = useState("");
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const memberId = useRecoilValue(getMemberIdSelector);
  const courseInquiries = queryClient.getQueryData([
    "courseInquiries",
    courseId,
  ]);

  const handleSetValue = (e) => {
    setTextValue(e.target.value);
  };

  const handleSetTab = (e) => {
    if (e.keyCode === 9) {
      e.preventDefault();
      let val = e.target.value;
      let start = e.target.selectionStart;
      let end = e.target.selectionEnd;
      e.target.value = val.substring(0, start) + "\t" + val.substring(end);
      e.target.selectionStart = e.target.selectionEnd = start + 1;
      handleSetValue(e);
      return false; //  prevent focus
    }
  };

  async function enrollInquire() {
    try {
      const { data } = await postcourseInquiriessApi(
        accessToken,
        courseId,
        textValue,
        memberId
      );
      console.log(data);
      if (data) {
        window.location.reload();
      }
    } catch (err) {
      alert(err);
    }
  }

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
      <InputTextArea
        value={textValue}
        onChange={(e) => handleSetValue(e)}
        onKeyDown={(e) => handleSetTab(e)}
        placeholder="강의에 대한 궁금증을 적어주세요!"
      />
      <InputBox>
        <AccountSmallBtn onClick={enrollInquire}>등록</AccountSmallBtn>
      </InputBox>
      <Inquires />
    </InquireWrapper>
  );
}
