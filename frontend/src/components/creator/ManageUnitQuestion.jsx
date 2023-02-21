import { Button, Grid } from "@mui/material";
import { useInView } from "framer-motion";
import { useEffect, useMemo, useRef, useState } from "react";
import { useInfiniteQuery } from "react-query";
import { useNavigate, useParams } from "react-router";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import {
  getDetailQuestionApi,
  getQuestionAnswerApi,
  getQuestionListApi,
  postUnitQuestionReplyApi,
} from "../../api/questionApi";
import { getAccessTokenSelector } from "../../atom";
import { MoveBtn } from "../../style/CommonCss";
import { CssTextField } from "./uploadCourse/Outline";

const QuestionBox = styled.div`
  background-color: #dddddd;
`;

const MoveBtnSmall = styled(MoveBtn)`
  height: 30px;
  width: 80px;
  margin-bottom: 20px;
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

    const replyQuestion = () => {
      postUnitQuestionReplyApi(accessToken, reply, questionId)
        .then(() => {
          alert("답변 등록");
        })
        .catch((err) => {
          alert(err);
        });
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
          console.log(already);
          return (
            <div key={already.answerId}>
              <span>- {already.content}</span>
              <span>&nbsp;by {already.writer.nickname}</span>
            </div>
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
