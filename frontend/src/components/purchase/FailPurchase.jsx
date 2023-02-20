import { useNavigate } from "react-router";
import styled from "styled-components";
import { AlertP, CenterDiv, MoveBtn } from "../../style/CommonCss";

export default function FailPurchase() {
  const navigate = useNavigate();
  return (
    <CenterDiv>
      <div>
        <AlertP>
          시스템에 오류가 있어 결제에 실패했어요. 관리자에게 문의해주세요.
        </AlertP>
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
        </CenterDiv>
      </div>
    </CenterDiv>
  );
}
