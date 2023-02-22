import { Button, Grid } from "@mui/material";
import { useState } from "react";
import { useQuery } from "react-query";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { getcourseInquiriessApi } from "../../../api/courseApi";
import { getAccessTokenSelector } from "../../../atom";
import { CardTitle } from "../../../style/MypageComponentsCss";
import Title from "../../dashboard/Title";
import { CssTextField } from "../uploadCourse/Outline";

const InquireBox = styled.div`
  background-color: #dddddd;
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
    const [reply, setReply] = useState();
    const replyInquire = () => {};
    return (
      <>
        <InquireBox>
          <b>문의 내용</b> <div>{inquire.content}</div>
        </InquireBox>

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
          <Button onClick={replyInquire} variant="contained">
            답글 달기
          </Button>
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
          <Grid item xs={12} key={inquire.id}>
            <InquireComponent inquire={inquire} />
          </Grid>
        );
      })}
    </Grid>
  );
}
