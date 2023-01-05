import { Outlet, useNavigate } from "react-router";
import styled from "styled-components";

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
  width: 60%;
  min-height: 75vh;
  background: teal;
`;

const TitleBox = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
`;

const Title = styled.p`
  font-size: 3rem;
  font-weight: 900;
  cursor: pointer;
`;

const PointTitle = styled(Title)`
  color: var(--color-primary);
  margin-left: 10px;
`;

const SubTitle = styled(Title)`
  font-size: 2.5rem;
  font-weight: 300;
`;

const Description = styled.p`
  color: var(--color-gray);
  margin-left: 10%;
  font-size: 1.1rem;
  font-weight: 100;
`;

export default function AccountRoot() {
  const navigate = useNavigate();
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
          <Description>교육 최강!! 다 봄 조은캠프</Description>
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
