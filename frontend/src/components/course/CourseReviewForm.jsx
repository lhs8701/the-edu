import { useState } from "react";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { postCourseReivewApi } from "../../api/courseApi";
import { getAccessTokenSelector } from "../../atom";
import { AccountSmallBtn } from "../../style/AccountComponentCss";
import { TabTitle, Wrapper } from "../../style/CommonCss";
import { InputTextArea } from "./ChatComponents";

const Title = styled(TabTitle)`
  margin: 0;
`;

const Input = styled(InputTextArea)`
  height: 80%;
  width: 100%;
`;

const Div = styled.div`
  display: flex;
  justify-content: flex-end;
  align-items: center;
`;

export default function CourseReviewForm({ courseId, setIsModalOpen }) {
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const [content, setContent] = useState("");

  const uploadReview = () => {
    if (content === "") {
      alert("리뷰를 작성해주세요.");
      return;
    }
    if (window.confirm("해당 강좌에 대한 리뷰를 작성하시겠습니까?")) {
      postCourseReivewApi(courseId, accessToken, content, 10)
        .then(() => {
          setIsModalOpen(false);
        })
        .catch((err) => {
          if (err.response.status === 403) {
            alert("이미 후기를 작성하셨습니다.");
          } else {
            alert(err);
          }
        });
    }
  };

  return (
    <Wrapper>
      <Title>수강 후기 작성하기</Title>

      <Div>
        <AccountSmallBtn onClick={uploadReview}>등록</AccountSmallBtn>
      </Div>

      <br />
      <Input value={content} onChange={(e) => setContent(e.target.value)} />
    </Wrapper>
  );
}
