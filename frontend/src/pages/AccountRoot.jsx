import { Outlet, useLocation, useNavigate } from "react-router";
import { Link } from "react-router-dom";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { getLoginState } from "../atom";

const Wrapper = styled.div`
  width: 1100px;
  min-height: 100vh;
  margin: 0 auto;
  justify-content: center;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const AccountBox = styled.div`
  margin-top: 50px;
  margin-bottom: 50px;
  width: 60%;
  border: 3px solid var(--color-box-gray);
  border-radius: var(--size-border-radius);
  box-shadow: 0 0px 1px rgb(0 0 0 / 16%), 0 1px 7px rgb(0 0 0 / 16%);
  padding: 50px;
`;

const TitleBox = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  cursor: pointer;
`;

const Title = styled.p`
  font-size: 3rem;
  font-weight: var(--weight-point);
  cursor: pointer;
`;

const PointTitle = styled(Title)`
  color: var(--color-primary);
  margin-left: 10px;
`;

const SubTitle = styled(Title)`
  font-size: 2.5rem;
  font-weight: var(--weight-middle);
`;

export default function AccountRoot() {
  const loginState = useRecoilValue(getLoginState);
  const navigate = useNavigate();
  const { state } = useLocation();

  if (loginState & (state?.state === "certification")) {
    navigate("/");
  } else if (loginState) {
    navigate(-1);
  }
  console.log(state?.state === "certification");
  return (
    <>
      <Wrapper>
        <AccountBox>
          <TitleBox
            onClick={() => {
              navigate("/");
            }}
          >
            <Title>다 </Title>
            <PointTitle>봄</PointTitle>
            <SubTitle>&nbsp; - The Edu</SubTitle>
          </TitleBox>
          <br />
          <br />

          <br />
          <br />
          <br />
          <br />
          <Outlet />
        </AccountBox>
      </Wrapper>
    </>
  );
}
