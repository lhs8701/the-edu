import { useNavigate } from "react-router";
import styled from "styled-components";
import { PROCESS_ACCOUNT_URL } from "../../static";
import { AlertP, CenterDiv, MoveBtn } from "../../style/CommonCss";

export default function EmailCert() {
  const navigate = useNavigate();
  return (
    <div>
      <CenterDiv>
        <AlertP>이메일 인증이 성공했어요. 🤣</AlertP>
      </CenterDiv>
      <br />
      <br />
      <br />
      <br />
      <br />

      <br />
      <br />
      <br />
      <CenterDiv>
        <MoveBtn
          onClick={() => {
            navigate("/");
          }}
        >
          홈으로 가기
        </MoveBtn>
        &nbsp;
        <MoveBtn
          onClick={() => {
            navigate("/" + PROCESS_ACCOUNT_URL.LOGIN, {
              state: { state: "certification" },
            });
          }}
        >
          로그인 하기
        </MoveBtn>
      </CenterDiv>
    </div>
  );
}
