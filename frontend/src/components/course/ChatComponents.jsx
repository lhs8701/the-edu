import styled from "styled-components";
import { STATIC_URL } from "../../static";

const UserImg = styled.img`
  width: 100%;
  height: 100%;
`;

const ImgBox = styled.div`
  width: 50px;
  height: 50px;
  overflow: hidden;
  border-radius: 50%;
  margin-right: 20px;
`;

const UserInfoBox = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const InfoTab = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const UserName = styled.p`
  font-weight: var(--weight-thin);
  color: var(--color-gray);
  font-size: 1.1rem;
`;

const UserRateTab = styled.div`
  display: flex;
`;

const Rate = styled.p`
  color: var(--color-gray);
  font-weight: var(--weight-thin);
`;

const Date = styled.p`
  font-weight: var(--weight-thin);
  color: var(--color-gray);
`;

const Context = styled.p`
  overflow: visible;
  white-space: normal; /* 넘치면 줄바꿈 */
  word-wrap: break-word; /* 긴 단어를 분리해서 줄바꿈해라 */
  width: 100%;
  height: 100%;
  word-break: keep-all; /*  break-all;  끊음 */
  line-height: 20px;
`;

const ContextTab = styled.div`
  margin-top: 15px;
  width: 100%;
  min-height: 50px;
  display: flex;
  align-items: center;
`;

const BottomBox = styled.div`
  margin: 0 auto;
  width: 95%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: 15px;
`;

const Reply = styled.button`
  border: none;
  background-color: transparent;
`;

const HeartTab = styled.div`
  display: flex;
  align-items: center;
`;

const HearTCnt = styled.p`
  font-weight: var(--weight-thin);
`;

export const UnderBar = styled.div`
  width: 100%;
  height: 1.4px;
  background-color: #c8c8c8;
`;

export const ChatUserInfo = ({ writer, rate }) => {
  return (
    <UserInfoBox>
      <InfoTab>
        <ImgBox>
          <UserImg src={STATIC_URL + writer?.profileImage?.smallFilePath} />
        </ImgBox>
        <UserName>{writer?.nickname}</UserName>
        <UserRateTab>
          {rate === "" ? (
            <Rate>&nbsp;</Rate>
          ) : (
            <>
              &nbsp; 별 <Rate>&nbsp;5.0</Rate>
            </>
          )}
        </UserRateTab>
      </InfoTab>
      <Date>2023.02.03</Date>
    </UserInfoBox>
  );
};

export const ChatContextArea = ({ content }) => {
  return (
    <ContextTab>
      <Context>{content}</Context>
    </ContextTab>
  );
};

export const ChatBottom = () => {
  return (
    <BottomBox>
      <Reply>답글달기</Reply>
      <HeartTab>
        하트&nbsp;<HearTCnt>3</HearTCnt>
      </HeartTab>
    </BottomBox>
  );
};

export const InputTextArea = styled.textarea`
  width: 100%;
  min-height: 100px;
  border: none;
  background-color: #f1efef;
  border-radius: var(--size-border-radius);
  font-size: 20px;
  &:focus {
    outline: 2px solid black;
  }
  &::placeholder {
    color: var(--color-gray);
    padding-left: 5px;
    padding-top: 5px;
    font-weight: var(--weight-normal);
  }
`;

export const Box = styled.div`
  width: 100%;
  margin: 32px 0px;
`;
