import { motion } from "framer-motion";
import styled from "styled-components";
import { faClosedCaptioning as regular } from "@fortawesome/free-regular-svg-icons";
import { useEffect, useState } from "react";
import Swal from "sweetalert2";
import { Wrapper } from "../../style/PlayerSideBarCss";
import { Accordion, AccordionDetails, AccordionSummary } from "@mui/material";
import KeyboardArrowDownIcon from "@mui/icons-material/KeyboardArrowDown";
import { useInfiniteQuery } from "react-query";
import { getQuestionListApi } from "../../api/questionApi";
import { useRecoilValue } from "recoil";
import { getAccessTokenSelector } from "../../atom";
import { useMemo } from "react";

const CateBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-around;
  width: 100%;
  margin-bottom: 13px;
`;

const CateTab = styled(motion.div)`
  font-weight: var(--weight-point);
  padding: 10px;
  text-align: center;
  cursor: pointer;
  color: ${(props) => (props.type ? " #a8a7a7" : "black")}; //props 활용
  border-bottom: 1px solid ${(props) => (props.type ? " #a8a7a7" : "none")}; ;
`;

const Input = styled.textarea`
  height: 350px;
  width: 100%;
  background-color: #efefef;
  &:focus {
    background-color: white;
    border: 0.5px soild black;
  }
  margin: 10px 0;
`;

const TitleInput = styled.input`
  width: 100%;
  height: 30px;
  border: none;
  border-bottom: 1px solid white;
  background-color: #efefef;
  &:focus {
    background-color: white;
    border: 0.5px soild black;
  }
`;

const QuestionInfoBox = styled.div`
  width: 100%;
  margin-bottom: 20px;
  color: rgba(0, 0, 0, 0.7);
  display: flex;
  align-items: center;
  justify-content: flex-end;
`;

const QuestionBox = styled(motion.div)`
  position: relative;
  overflow: auto;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  flex-direction: column;
  margin: 0 auto;
  height: 70vh;

  /* @media screen and (max-height: 90vh) and (min-height: 617px) {
    height: 30vh;
  } */
`;

const QuestionTab = styled(Accordion)`
  box-sizing: border-box;
  width: 100%;
  text-align: center;
  background-color: white;
  box-shadow: 0 1px 1px rgb(0 0 0 / 16%), 0px 1px 7px rgb(0 0 0 / 16%);
  margin: 5px 0;
`;

const QuestionInfoTab = styled(AccordionSummary)``;

const Tab = styled(motion.div)`
  overflow: hidden;
  white-space: nowrap;
  font-size: var(--font-size-question-any);
  text-overflow: ellipsis;
  /* display: ${(props) => (props.visible ? "default" : "none")}; */
`;

const Form = styled.form`
  width: 100%;
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: center;
  height: 40%;
`;

const TitleInputBox = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: flex-start;
`;

const QuestionBtn = styled(motion.button)`
  cursor: pointer;
`;

const QuestionDiv = styled(AccordionDetails)``;

const QuestionContextBox = styled.div`
  text-align: start;
`;

export default function UnitQuestion({ unitId }) {
  const [type, setType] = useState(false);
  const [nowQ, setNowQ] = useState([1, 2, 3, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3]);
  const [userQuestionTitle, setUserQuestionTitle] = useState("");
  const [questionContext, setQuestionContext] = useState("");
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const [visible, setVisible] = useState(true);
  const [questionContent, setQuestionContent] = useState("");

  const questionList = useInfiniteQuery(
    ["getSearchList", unitId],
    ({ pageParam = 0 }) => {
      return getQuestionListApi(pageParam, unitId, accessToken);
    },
    {
      retry: 1,
      retryDelay: 5000,
      onSuccess: (res) => {
        if (res.pages[0].code === -7001) {
          //query를 더이상 호출하지 않게 하자
        }
      },
      onError: () => {
        console.error("에러 발생했지롱");
      },
      getNextPageParam: (lastPage, allPages) => {
        if (Number(lastPage.totalPage) === 0) {
          return undefined;
        }
        if (lastPage.totalPage === lastPage.page + 1) {
          return undefined;
        }
        return lastPage.page + 1;
      },
    }
  );
  const questions = useMemo(
    () => questionList?.data?.pages.flatMap((page) => page.list),
    [questionList?.data?.pages]
  );

  // const questionUpload = (e) => {
  //   e.preventDefault();
  //   if (userQuestionTitle =setUserQuestionTitlequestionContext === "") {
  //     alert("질문을 입력하세요.");
  //   } else {
  //     if (videoVal.playing === true) {
  //       setVideoVal({ ...videoVal, playing: false });
  //     }
  //     const lecTime = Math.trunc(videoVal.playedSec / 60); // 시간을 단계로 나눠

  // };

  const QuestionUploadForm = () => {
    return (
      <Form>
        <TitleInputBox>
          <TitleInput
            type="text"
            required
            value={userQuestionTitle}
            onChange={(e) => {
              setUserQuestionTitle(e.target.value);
            }}
            id="title"
            placeholder="제목을 입력해주세요"
          />
        </TitleInputBox>
        <Input
          required
          value={questionContext}
          onChange={(e) => {
            setQuestionContext(e.target.value);
          }}
          placeholder="질문을 등록해주세요"
        />
        <QuestionBtn
          // onClick={questionUpload}
          whileHover={{ scale: 1.1 }}
          whileTap={{ scale: 1 }}
        >
          질문하기
        </QuestionBtn>
      </Form>
    );
  };

  const QuestionPathTab = () => {
    return (
      <CateBox>
        <CateTab
          type={!type}
          onClick={() => {
            setType(false);
          }}
          whileHover={{ y: -5 }}
          whileTap={{ y: 0 }}
        >
          등록된 질문들
        </CateTab>
        <CateTab
          type={type}
          onClick={() => {
            setType(true);
          }}
          whileHover={{ y: -5 }}
          whileTap={{ y: 0 }}
        >
          질문 등록
        </CateTab>
      </CateBox>
    );
  };

  const QuestionContentComponent = () => {
    const [qData, setQData] = useState(true);
    return (
      <QuestionDiv>
        <CateBox>
          <CateTab
            type={qData}
            onClick={() => {
              setQData(true);
            }}
          >
            질문
          </CateTab>
          <CateTab
            type={!qData}
            onClick={() => {
              setQData(false);
            }}
          >
            답변
          </CateTab>
        </CateBox>
        <QuestionContextBox>머시기머시기</QuestionContextBox>
      </QuestionDiv>
    );
  };

  const QuestionListComponent = () => {
    return questions?.map((question, idx) => {
      return (
        <QuestionTab
          onClick={() => {
            // getQuestionContent(e.questionId);
            // getQuestionReply(e.questionId);
          }}
          key={question.questionId}
        >
          <QuestionInfoTab
            aria-controls="panel1a-content"
            expandIcon={<KeyboardArrowDownIcon />}
            sx={{
              mb: -1,
            }}
          >
            <div>{question.title}</div>
            <div>- {question.writer}</div>
          </QuestionInfoTab>
          <QuestionContentComponent />
        </QuestionTab>
      );
    });
  };

  const QuestionListWrapper = () => {
    return (
      <>
        <QuestionInfoBox>
          <Tab>{questions?.length}개의 질문이 있어요.</Tab>
        </QuestionInfoBox>
        <QuestionBox>
          <QuestionListComponent />
        </QuestionBox>
      </>
    );
  };
  return (
    <Wrapper>
      <QuestionPathTab />
      {type ? <QuestionUploadForm /> : <QuestionListWrapper />}
    </Wrapper>
  );
}
