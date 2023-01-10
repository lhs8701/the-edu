import styled from "styled-components";
import { Title } from "../../style/CourseCss";
import { ChatBottom, ChatContextArea, ChatUserInfo } from "./ChatComponents";

const ReivewWrapper = styled.div`
  width: 100%;
`;

const ReviewBtn = styled.button`
  width: 70px;
  height: 35px;
  background-color: var(--color-primary);
  border: none;
  border-radius: var(--size-border-radius);
  font-weight: var(--weight-point);
  font-size: 16px;
  &:hover {
    color: var(--color-background);
  }
`;

const BtnBox = styled.div`
  width: 95%;
  display: flex;
  align-items: center;
  justify-content: flex-end;
`;

const ReviewBox = styled.div`
  width: 100%;
  margin: 30px 0px;
`;

export default function CourseReview() {
  const ReviewCreator = () => {
    return (
      <>
        <ReviewBox>
          <ChatUserInfo />
          <ChatContextArea />
          <ChatBottom />
        </ReviewBox>
        <ReviewBox>
          <ChatUserInfo />
          <ChatContextArea />
          <ChatBottom />
        </ReviewBox>
      </>
    );
  };
  return (
    <ReivewWrapper>
      <Title>수강 후기</Title>
      <BtnBox>
        <ReviewBtn>등록</ReviewBtn>
      </BtnBox>
      <ReviewCreator />
    </ReivewWrapper>
  );
}
