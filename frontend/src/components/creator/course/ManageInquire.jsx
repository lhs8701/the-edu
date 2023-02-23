import { Button, Grid } from "@mui/material";
import { useState } from "react";
import { useQuery } from "react-query";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { getcourseInquiriessApi } from "../../../api/courseApi";
import { replyInquireApi, reviseInquireApi } from "../../../api/creatorApi";
import { getAccessTokenSelector } from "../../../atom";
import { CardTitle } from "../../../style/MypageComponentsCss";
import Title from "../../dashboard/Title";
import { CssTextField } from "../uploadCourse/Outline";

const InquireBox = styled.div`
  background-color: #dddddd;
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
export default function ManageInquire({ courseId }) {
  const accessToken = useRecoilValue(getAccessTokenSelector);

  const { data, isSuccess } = useQuery(
    ["courseInquiries", courseId],
    () => {
      return getcourseInquiriessApi(courseId);
    },
    {
      enabled: !!courseId,
      onSuccess: (res) => {},
      onError: () => {
        console.error("에러 발생했지롱");
      },
    }
  );

  const InquireComponent = ({ inquire }) => {
    console.log(inquire);
    const [reply, setReply] = useState();
    const replyInquire = () => {
      if (window.confirm("문의에 답글을 남기시겠습니까?")) {
        replyInquireApi(accessToken, inquire.inquiryId, reply)
          .then(() => {})
          .catch((err) => {
            alert(err);
          });
      }
    };
    const reviseInquire = (replyId) => {
      if (window.confirm("답글을 수정하시겠습니까?")) {
        reviseInquireApi(accessToken, replyId, reply)
          .then(() => {})
          .catch((err) => {
            alert(err);
          });
      }
    };

    return (
      <>
        <InquireBox>
          <b>문의 내용</b> <div>{inquire.content}</div>
        </InquireBox>
        {inquire.reply && (
          <AnswerTab key={inquire?.reply?.content}>
            <AnswerContent>{inquire?.reply?.content}</AnswerContent>
            <div style={{ fontSize: "0.9rem" }}>
              {inquire?.reply?.createdTime}에 작성됨.&nbsp;
            </div>
          </AnswerTab>
        )}

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
          {inquire.reply === null ? (
            <Button onClick={replyInquire} variant="contained">
              답글 등록
            </Button>
          ) : (
            <Button
              onClick={() => {
                reviseInquire(inquire.reply.id);
              }}
              variant="contained"
            >
              답글 수정
            </Button>
          )}
        </div>
        <br />
        <br />
      </>
    );
  };

  return (
    <Grid container xs={12}>
      {data?.map((inquire, idx) => {
        return (
          <Grid item xs={12} key={inquire.inquiryId}>
            <InquireComponent inquire={inquire} />
          </Grid>
        );
      })}
    </Grid>
  );
}
