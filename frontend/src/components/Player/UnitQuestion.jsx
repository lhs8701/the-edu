import { motion, useInView } from "framer-motion";
import styled from "styled-components";
import { faClosedCaptioning as regular } from "@fortawesome/free-regular-svg-icons";
import { useEffect, useState } from "react";
import Swal from "sweetalert2";
import { Wrapper } from "../../style/PlayerSideBarCss";
import { Accordion, AccordionDetails, AccordionSummary } from "@mui/material";
import KeyboardArrowDownIcon from "@mui/icons-material/KeyboardArrowDown";
import { useInfiniteQuery, useQuery } from "react-query";
import {
  getDetailQuestionApi,
  getQuestionAnswerApi,
  getQuestionListApi,
  postUnitQuestionApi,
} from "../../api/questionApi";
import { useRecoilValue } from "recoil";
import { getAccessTokenSelector } from "../../atom";
import { useMemo } from "react";
import { useRef } from "react";

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
  color: ${(props) =>
    props.type ? " var(--color-gray)" : "black"}; //props 활용
  border-bottom: 1px solid
    ${(props) => (props.type ? " var(--color-gray)" : "none")}; ;
`;

const SmallCateTab = styled(CateTab)`
  font-weight: var(--weight-middle);
  font-size: 0.8rem;
  padding: 5px;
  color: ${(props) =>
    props.type ? " var(--color-gray)" : "black"}; //props 활용
  border-bottom: 1px solid
    ${(props) => (props.type ? " var(--color-gray)" : "none")}; ;
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
  box-shadow: 1px 1px 1px rgb(0 0 0 / 16%), 1px 1px 7px rgb(0 0 0 / 16%);
  margin: 5px 0;
`;

const QuestionInfoTab = styled(AccordionSummary)`
  font-weight: var(--weight-middle);
  text-align: start;
`;

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

const QuestionDiv = styled(AccordionDetails)`
  margin-top: -10px;
`;

const QuestionContextBox = styled.div`
  /* border-top: 1px solid var(--color-box-gray); */
  text-align: start;
  padding: 10px 5px;
  box-sizing: border-box;
  font-size: 0.9rem;
  font-weight: var(--weight-thin);
`;

const BottomDiv = styled.div`
  width: 100%;
  height: 1px;
`;

const QuestionContentDate = styled.div`
  text-align: end;
  font-weight: var(--weight-thin);
  font-size: 0.8rem;
  color: var(--color-gray);
`;

const QuestionReplyTab = styled.div`
  padding: 5px 0 2px 0;
`;

export default function UnitQuestion({ unitId }) {
  const [type, setType] = useState(false);
  const bottomRef = useRef(null);
  const isInView = useInView(bottomRef);
  const accessToken = useRecoilValue(getAccessTokenSelector);

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

  useEffect(() => {
    if (isInView && questionList.hasNextPage && questionList.isSuccess) {
      questionList.fetchNextPage();
    }
  }, [isInView, questionList.data]);

  const QuestionUploadForm = () => {
    const [userQuestionTitle, setUserQuestionTitle] = useState("");
    const [userQuestionContext, setQuestionContext] = useState("");
    const questionUpload = (e) => {
      e.preventDefault();
      if (userQuestionTitle === "") {
        alert("제목을 입력하세요.");
        return;
      }
      if (userQuestionContext === "") {
        alert("질문을 입력하세요.");
        return;
      }
      postUnitQuestionApi(
        accessToken,
        userQuestionTitle,
        userQuestionContext,
        unitId
      )
        .then(({ data }) => {
          console.log(data);
          alert("질문이 등록 되었습니다.");
          setType(false);
        })
        .catch((err) => {
          alert(err);
        });
    };
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
          value={userQuestionContext}
          onChange={(e) => {
            setQuestionContext(e.target.value);
          }}
          placeholder="질문을 등록해주세요"
        />
        <QuestionBtn
          onClick={questionUpload}
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

  const Reply = ({ replyInfo }) => {
    return replyInfo?.map((reply) => {
      return (
        <QuestionReplyTab key={reply.answerId}>
          <QuestionContextBox>{reply.content}</QuestionContextBox>
          <QuestionContentDate>{reply?.modifiedTIme}</QuestionContentDate>
        </QuestionReplyTab>
      );
    });
  };

  const Content = ({ contentInfo }) => {
    return (
      <>
        <QuestionContextBox>{contentInfo?.content}</QuestionContextBox>
        <QuestionContentDate>{contentInfo?.modifiedTIme}</QuestionContentDate>
      </>
    );
  };

  const QuestionContentComponent = ({ questionId }) => {
    const [section, setSection] = useState(true);
    const questionContent = useQuery(
      ["questionContent", questionId],
      () => {
        return getDetailQuestionApi(questionId, accessToken);
      },
      {
        enabled: !!questionId,
        onSuccess: (res) => {},
        onError: (err) => {
          console.error("에러 발생했지롱");
        },
      }
    );

    const questionReply = useQuery(
      ["questionReply", questionId],
      () => {
        return getQuestionAnswerApi(0, questionId, accessToken);
      },
      {
        enabled: !!questionId,
        onSuccess: (res) => {},
        onError: (err) => {
          console.error("에러 발생했지롱");
        },
      }
    );

    return (
      <QuestionDiv>
        <CateBox>
          <SmallCateTab
            type={section}
            onClick={() => {
              setSection(true);
            }}
          >
            질문
          </SmallCateTab>
          <SmallCateTab
            type={!section}
            onClick={() => {
              setSection(false);
            }}
          >
            답변
          </SmallCateTab>
        </CateBox>
        {section ? (
          <Content contentInfo={questionContent?.data?.data} />
        ) : (
          <Reply replyInfo={questionReply?.data?.list} />
        )}
      </QuestionDiv>
    );
  };

  const QuestionComponent = ({ question }) => {
    return (
      <QuestionTab
        TransitionProps={{ unmountOnExit: true }}
        onClick={() => {
          // getQuestionContent(e.questionId);
          // getQuestionReply(e.questionId);
        }}
        key={question.questionId}
      >
        <QuestionInfoTab
          aria-controls="panel1a-content"
          expandIcon={<KeyboardArrowDownIcon />}
          sx={{}}
        >
          {question.title}
        </QuestionInfoTab>
        <QuestionContentComponent questionId={question.questionId} />
      </QuestionTab>
    );
  };

  const QuestionListComponent = () => {
    return questions?.map((question, idx) => {
      return <QuestionComponent question={question} />;
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
          <BottomDiv ref={bottomRef} />
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
