import { AnimatePresence, motion } from "framer-motion";
import styled from "styled-components";
import { faClosedCaptioning as regular } from "@fortawesome/free-regular-svg-icons";
import { useEffect, useState } from "react";
import Swal from "sweetalert2";
import { Wrapper } from "../../style/PlayerSideBarCss";
import { Accordion, AccordionDetails, AccordionSummary } from "@mui/material";
import KeyboardArrowDownIcon from "@mui/icons-material/KeyboardArrowDown";

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
  justify-content: center;
  flex-direction: column;
  margin: 0 auto;
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

const QuestionInfoTab = styled(AccordionSummary)`
  background-color: teal;
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

const QuestionDiv = styled(AccordionDetails)``;

const QuestionContextBox = styled.div`
  text-align: start;
`;

export default function UnitQuestion() {
  const [type, setType] = useState(false);
  const [nowQ, setNowQ] = useState([1, 2, 3]);
  const [qTitle, setQTitle] = useState("");
  const [questionContext, setQuestionContext] = useState("");

  const [visible, setVisible] = useState(true);
  const [questionContent, setQuestionContent] = useState("");
  const [questionReply, setQuestionReply] = useState("");

  // async function questionDown() {
  //   try {
  //     const res = await axios.get(
  //       `${STATICURL}/front/unit/${queryList.unitId}/questions`
  //     );
  //     setQuestionVal(res.data);
  //   } catch (error) {
  //     if (error.response.status === 409) {
  //       setOverlappingVal(true);
  //     } else {
  //       alert(error);
  //     }
  //   }
  // }

  // const questionUpload = (e) => {
  //   e.preventDefault();
  //   if (qTitle === "" || questionContext === "") {
  //     alert("질문을 입력하세요.");
  //   } else {
  //     if (videoVal.playing === true) {
  //       setVideoVal({ ...videoVal, playing: false });
  //     }
  //     const lecTime = Math.trunc(videoVal.playedSec / 60); // 시간을 단계로 나눠

  //     axios
  //       .post(
  //         `${STATICURL}/front/unit/${queryList.unitId}/questions`,
  //         {
  //           content: questionContext,
  //           title: qTitle,
  //           timeline: lecTime,
  //         },
  //         {
  //           headers: {
  //             "X-AUTH-TOKEN": accessToken,
  //             "Content-Type": "application/json",
  //             "Access-Control-Allow-Credentials": true,
  //             "Access-Control-Allow-Origin": "*",
  //           },
  //         }
  //       )
  //       .then((response) => {
  //         if (response.status === 200) {
  //           questionDown();
  //           Swal.fire({
  //             icon: "success",
  //             title: "등록 완료",
  //             text: "곧 강사님이 답변을 주실거에요!",
  //             confirmButtonText: "확인",
  //           }).then((result) => {
  //             if (result.isConfirmed) {
  //               setVideoVal({ ...videoVal, playing: true });
  //             }
  //           });
  //         }
  //       })
  //       .catch((error) => {
  //         if (error.response.status === 409) {
  //           setOverlappingVal(true);
  //         } else {
  //           alert(error);
  //         }
  //       });
  //   }
  // };

  // async function getQuestionContent(questionId) {
  //   try {
  //     const res = await axios.get(`${STATICURL}/front/questions/${questionId}`);
  //     setQuestionContent(res.data);
  //   } catch (error) {
  //     if (error.response.status === 409) {
  //       setOverlappingVal(true);
  //     } else {
  //       alert(error);
  //     }
  //   }
  // }

  // async function getQuestionReply(questionId) {
  //   try {
  //     const res = await axios.get(
  //       `${STATICURL}/front/questions/${questionId}/answers`
  //     );
  //     setQuestionReply(res.data);
  //   } catch (error) {
  //     if (error.response.status === 409) {
  //       setOverlappingVal(true);
  //     } else {
  //       alert(error);
  //     }
  //   }
  // }

  // const uploadReply = (e) => {
  //   e.preventDefault();
  //   if (isReply === "") {
  //     alert("답변을 입력하세요.");
  //   } else {
  //     if (videoVal.playing === true) {
  //       setVideoVal({ ...videoVal, playing: false });
  //     }
  //     const lecTime = Math.trunc(videoVal.playedSec / 60); // 시간을 단계로 나눠

  //     axios
  //       .post(
  //         `${STATICURL}/front/questions/${queryList.unitId}/answers`,
  //         {
  //           content: q,
  //           title: qTitle,
  //           timeline: lecTime,
  //         },
  //         {
  //           headers: {
  //             "Content-Type": "application/json",
  //             "X-AUTH-TOKEN": accessToken,
  //             "Access-Control-Allow-Credentials": true,
  //             "Access-Control-Allow-Origin": "*",
  //           },
  //         }
  //       )
  //       .then((response) => {})
  //       .catch((error) => {
  //         if (error.response.status === 409) {
  //           setOverlappingVal(true);
  //         } else {
  //           alert(error);
  //         }
  //       });
  //   }
  // };

  // useEffect(questionChecker, [videoTimeVal, questionVal]);

  const QuestionUploadForm = () => {
    return (
      <Form>
        <TitleInputBox>
          <TitleInput
            type="text"
            required
            value={qTitle}
            onChange={(e) => {
              setQTitle(e.target.value);
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
    return nowQ?.map((question, idx) => {
      return (
        <QuestionTab
          onClick={() => {
            // getQuestionContent(e.questionId);
            // getQuestionReply(e.questionId);
          }}
          key={idx}
        >
          <QuestionInfoTab
            aria-controls="panel1a-content"
            expandIcon={<KeyboardArrowDownIcon />}
            sx={{
              mb: -1,
            }}
          >
            {question.title} 답변:{question.replyCount}
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
          <Tab>{nowQ?.length}개의 질문이 있어요.</Tab>
        </QuestionInfoBox>
        <QuestionBox>
          {/* <AnimatePresence>
            {clicked ? (
              <Overlay
                initial={{ backgroundColor: "rgba(0,0,0,0)" }}
                animate={{
                  display: ["none", "default"],
                  zIndex: "1",
                }}
                exit={{
                  backgroundColor: "rgba(0,0,0,0)",
                }}
              >
                <QuestionCard layoutId={clicked}>
                  <QBox>
                    <div>{nowQ[clicked - 1].title}</div>
                    <Div
                      onClick={() => {
                        setClicked(null);
                        setVisible(false);
                        setTimeout(() => {
                          setVisible(true);
                        }, 300);
                      }}
                    >
                      나가기
                    </Div>
                  </QBox>
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
                  {!qData ? (
                    <ReplyBox>{questionReply}</ReplyBox>
                  ) : (
                    <ReplyBox>
                      {questionContent.content}
                      <div>
                    답변
                    <input
                      type="text"
                      placeholder="답변을 해주세요"
                      required
                      value={isReply}
                      onChange={(e) => {
                        setIsReply(e.target.value);
                      }}
                    />
                    <button onClick={uploadReply}>등록</button>
                  </div>
                    </ReplyBox>
                  )}
                </QuestionCard>
              </Overlay>
            ) : null}
          </AnimatePresence> */}
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
