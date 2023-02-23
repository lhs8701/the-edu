import { useEffect } from "react";
import { useNavigate } from "react-router";
import { AlertP, CenterDiv, Wrapper } from "../style/CommonCss";

export default function ErrorComponent() {
  const navigate = useNavigate();
  // useEffect(() => {
  //   alert("확인되지 않은 접근입니다!");
  //   navigate(-1);
  // }, []);
  return (
    <Wrapper>
      <CenterDiv>
        <AlertP>오류가 발생했습니다. 잠시 후 시도해주세요.</AlertP>
      </CenterDiv>
    </Wrapper>
  );
}
