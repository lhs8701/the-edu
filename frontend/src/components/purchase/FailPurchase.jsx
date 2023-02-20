import { useNavigate } from "react-router";
import styled from "styled-components";

const ProgressDiv = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
`;
const BtnDiv = styled.div`
  display: flex;
`;
const SuccuessP = styled.p`
  font-size: 1.5rem;
  font-weight: var(--weight-middle);
`;

const MoveBtn = styled.button`
  width: 180px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: var(--color-background);
  font-size: 1rem;
`;
export default function FailPurchase() {
  const navigate = useNavigate();
  return (
    <ProgressDiv>
      <div>
        <SuccuessP>
          시스템에 오류가 있어 결제에 실패했어요. 관리자에게 문의해주세요.
        </SuccuessP>
        <br />
        <br />
        <ProgressDiv>
          <MoveBtn
            onClick={() => {
              navigate("/");
            }}
          >
            홈으로 가기
          </MoveBtn>
        </ProgressDiv>
      </div>
    </ProgressDiv>
  );
}
