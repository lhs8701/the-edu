import { Button, Grid } from "@mui/material";
import { useInView } from "framer-motion";
import { useEffect, useMemo, useRef, useState } from "react";
import { useInfiniteQuery } from "react-query";
import { useNavigate, useParams } from "react-router";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import {
  deleteUnitQuestionReplyApi,
  getDetailQuestionApi,
  getQuestionAnswerApi,
  getQuestionListApi,
  patchUnitQuestionReplyApi,
  postUnitQuestionReplyApi,
} from "../../../api/questionApi";
import { getAccessTokenSelector } from "../../../atom";
import { MoveBtn } from "../../../style/CommonCss";
import { CssTextField } from "../uploadCourse/Outline";

const QuestionBox = styled.div`
  background-color: #dddddd;
`;

const MoveBtnSmall = styled(MoveBtn)`
  height: 30px;
  width: 80px;
  margin-bottom: 20px;
`;

const AnswerTab = styled.div`
  width: 100%;
  margin: 10px 0px;
  border: 1px solid var(--color-box-gray);
  padding: 5px 10px;
  box-sizing: border-box;
`;

const AnswerContent = styled.div`
  width: 99%;
  text-align: start;
  overflow: auto;
  margin-bottom: 10px;
`;

export default function ManageUnitQuestion() {
  const { unitId } = useParams();
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const bottomRef = useRef(null);
  const isInView = useInView(bottomRef);
  const navigate = useNavigate();

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

  useEffect(() => {
    if (isInView && questionList.hasNextPage && questionList.isSuccess) {
      questionList.fetchNextPage();
    }
  }, [isInView, questionList.data]);

  const QuestionComponent = ({ questionId }) => {
    const [reply, setReply] = useState();
    const [content, setContent] = useState();
    const [alreadyReply, setAlreadyReply] = useState();

    const deleteReply = (answerId) => {
      if (window.confirm("해당 답변을 삭제하시겠습니까?")) {
        deleteUnitQuestionReplyApi(accessToken, answerId)
          .then(() => {
            alert("답변 삭제");
          })
          .catch((err) => {
            alert(err);
          });
      }
    };
    const reviseReply = () => {
      if (window.confirm("해당 답변을 수정하시겠습니까?")) {
        patchUnitQuestionReplyApi(accessToken, reply, questionId)
          .then(() => {
            alert("답변 수정");
          })
          .catch((err) => {
            alert(err);
          });
      }
    };

    const replyQuestion = () => {
      if (window.confirm("해당 답변을 등록하시겠습니까?")) {
        postUnitQuestionReplyApi(accessToken, reply, questionId)
          .then(() => {
            alert("답변 등록");
          })
          .catch((err) => {
            alert(err);
          });
      }
    };

    useEffect(() => {
      getDetailQuestionApi(questionId, accessToken)
        .then(({ data }) => {
          setContent(data);
        })
        .catch((err) => {
          alert(err);
        });
      getQuestionAnswerApi(0, questionId, accessToken)
        .then(({ list }) => {
          setAlreadyReply(list);
        })
        .catch((err) => {
          alert(err);
        });
    }, []);

    return (
      <>
        <QuestionBox>
          <b>질문</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <span>by {content?.writer.nickname}</span>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <span>{content?.createdTime}</span>
          <div>{content?.content}</div>
        </QuestionBox>
        <div
          style={{
            display: "flex",
            alignItems: "flex-start",
            justifyContent: "space-between",
          }}
        >
          <CssTextField
            sx={{ width: "90%;" }}
            type="text"
            id="title"
            name="title"
            size="small"
            value={reply}
            multiline
            rows={3}
            onChange={(e) => {
              setReply(e.target.value);
            }}
            placeholder="답글을 달아주세요."
          />
          <Button onClick={replyQuestion} variant="contained">
            답글 달기
          </Button>
        </div>
        <div>답변</div>

        {alreadyReply?.map((already) => {
          return (
            <AnswerTab key={already.answerId}>
              <AnswerContent>{already.content}</AnswerContent>
              <div style={{ fontSize: "0.9rem" }}>
                {already.createdTime}에 작성됨.&nbsp;
                <Button
                  onClick={() => {
                    deleteReply(already.answerId);
                  }}
                >
                  삭제
                </Button>
              </div>
            </AnswerTab>
          );
        })}
        <br />
        <br />
      </>
    );
  };

  return (
    <Grid container xs={12}>
      <MoveBtnSmall
        onClick={() => {
          navigate(-1);
        }}
      >
        뒤로가기
      </MoveBtnSmall>

      {questions?.map((question, idx) => {
        return (
          <Grid item xs={12} key={question.questionId}>
            <QuestionComponent questionId={question.questionId} />
          </Grid>
        );
      })}
    </Grid>
  );
}
