import { motion, useInView } from "framer-motion";
import styled from "styled-components";
import { faClosedCaptioning as regular } from "@fortawesome/free-regular-svg-icons";
import { useEffect, useState, useMemo } from "react";
import Swal from "sweetalert2";
import { Wrapper } from "../../style/PlayerSideBarCss";
import { Accordion, AccordionDetails, AccordionSummary } from "@mui/material";
import KeyboardArrowDownIcon from "@mui/icons-material/KeyboardArrowDown";
import { useInfiniteQuery, useQuery } from "react-query";
import {
  deleteMyQuestionApi,
  getAllMyQuestionsApi,
  getDetailQuestionApi,
  getQuestionAnswerApi,
  getQuestionListApi,
  postUnitQuestionApi,
} from "../../api/questionApi";
import { useRecoilValue } from "recoil";
import { getAccessTokenSelector } from "../../atom";
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
  height: 100px;
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
  height: 20px;
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
  height: 50vh;
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
  font-size: 0.9rem;
`;

const Tab = styled(motion.div)`
  overflow: hidden;
  white-space: nowrap;
  font-size: var(--font-size-question-any);
  text-overflow: ellipsis;
`;

const Form = styled.form`
  width: 100%;
  display: flex;
  align-items: flex-end;
  flex-direction: column;
  justify-content: flex-start;
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
  margin: 10px 0px;
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
  font-size: 0.7rem;
  color: var(--color-gray);
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const QuestionReplyTab = styled.div`
  padding: 5px 0 2px 0;
`;

const DeleteQuestionTab = styled.div`
  cursor: pointer;
  color: var(--color-red);
  font-size: 0.8rem;
  text-align: end;
  &:hover {
    color: var(--color-gray);
  }
`;

export default function UnitQuestion({ unitId }) {
  const [type, setType] = useState(false);
  const [allListExpanded, setAllListExpanded] = useState();
  const bottomRef = useRef(null);
  const isInView = useInView(bottomRef);
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const [userQuestionTitle, setUserQuestionTitle] = useState("");
  const [userQuestionContext, setQuestionContext] = useState("");
  const [section, setSection] = useState(true);

  const questionList = useInfiniteQuery(
    ["questionList", unitId],
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
        questionList.refetch();
      })
      .catch((err) => {
        alert(err);
      });
  };

  const spreadAccordian = (questionId) => {
    setSection(true);
    if (allListExpanded === questionId) {
      setAllListExpanded();
    } else {
      setAllListExpanded(questionId);
    }
  };

  useEffect(() => {
    if (isInView && questionList.hasNextPage && questionList.isSuccess) {
      questionList.fetchNextPage();
    }
  }, [isInView, questionList.data]);

  const Reply = ({ replyInfo }) => {
    return replyInfo?.map((reply) => {
      return (
        <QuestionReplyTab key={reply.answerId}>
          <QuestionContextBox>{reply.content}</QuestionContextBox>
          <QuestionContentDate>
            <div>{reply?.modifiedTime}</div>
            <div>by {reply?.writer?.nickname}</div>
          </QuestionContentDate>
        </QuestionReplyTab>
      );
    });
  };

  const Content = ({ contentInfo }) => {
    return (
      <QuestionReplyTab>
        <QuestionContextBox>{contentInfo?.content}</QuestionContextBox>
        <QuestionContentDate>
          <div>{contentInfo?.modifiedTime}</div>
          <div>by {contentInfo?.writer?.nickname}</div>
        </QuestionContentDate>
      </QuestionReplyTab>
    );
  };

  const QuestionContentComponent = ({ questionId, isMine }) => {
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

    const deleteQuestion = () => {
      if (window.confirm("질문을 삭제하시겠어요?")) {
        deleteMyQuestionApi(questionId, accessToken)
          .then(() => {
            questionList.refetch();
          })
          .catch((err) => {
            alert("삭제 불가");
          });
      }
    };

    return (
      <QuestionDiv>
        {isMine && (
          <DeleteQuestionTab onClick={deleteQuestion}>삭제</DeleteQuestionTab>
        )}
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

  const QuestionComponent = ({ question, isMine }) => {
    return (
      <QuestionTab
        TransitionProps={{ unmountOnExit: true }}
        key={question.questionId}
      >
        <QuestionInfoTab
          aria-controls="panel1a-content"
          expandIcon={<KeyboardArrowDownIcon />}
          sx={{}}
        >
          {question.title}
        </QuestionInfoTab>
        <QuestionContentComponent
          questionId={question.questionId}
          isMine={isMine}
        />
      </QuestionTab>
    );
  };

  const QuestionListComponent = ({ questions, isMine }) => {
    return questions?.map((question, idx) => {
      return <QuestionComponent question={question} isMine={isMine} />;
    });
  };

  const MyQuestionListWrapper = () => {
    const myBottomRef = useRef(null);
    const myIsInView = useInView(myBottomRef);
    const myQuestionList = useInfiniteQuery(
      ["myQuestionList", unitId],
      ({ pageParam = 0 }) => {
        return getAllMyQuestionsApi(pageParam, unitId, accessToken);
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
      () => myQuestionList?.data?.pages.flatMap((page) => page.list),
      [myQuestionList?.data?.pages]
    );

    useEffect(() => {
      if (isInView && myQuestionList.hasNextPage && myQuestionList.isSuccess) {
        myQuestionList.fetchNextPage();
      }
    }, [myIsInView, myQuestionList.data]);

    return (
      <>
        <QuestionInfoBox>
          <Tab>{questions?.length}개의 질문이 있어요.</Tab>
        </QuestionInfoBox>
        <QuestionBox>
          <QuestionListComponent questions={questions} isMine={true} />
          <BottomDiv ref={myBottomRef} />
        </QuestionBox>
      </>
    );
  };

  return (
    <Wrapper>
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
          나의 질문
        </CateTab>
      </CateBox>
      {type ? (
        <MyQuestionListWrapper />
      ) : (
        <>
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
            <QuestionBtn onClick={questionUpload}>질문하기</QuestionBtn>
          </Form>
          <br />
          <QuestionInfoBox>
            <Tab>{questions?.length}개의 질문이 있어요.</Tab>
          </QuestionInfoBox>
          <QuestionBox>
            {questions?.map((question, idx) => {
              return (
                <QuestionTab
                  TransitionProps={{ unmountOnExit: true }}
                  key={question.questionId}
                  expanded={allListExpanded === question.questionId}
                  onChange={() => {
                    spreadAccordian(question.questionId);
                  }}
                >
                  <QuestionInfoTab
                    aria-controls="panel1a-content"
                    expandIcon={<KeyboardArrowDownIcon />}
                  >
                    {question.title}
                  </QuestionInfoTab>
                  <QuestionContentComponent
                    questionId={question.questionId}
                    isMine={false}
                  />
                </QuestionTab>
              );
            })}
            <BottomDiv ref={bottomRef} />
          </QuestionBox>
        </>
      )}
    </Wrapper>
  );
}
