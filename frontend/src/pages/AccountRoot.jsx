import { Outlet } from "react-router";
import styled from "styled-components";

const Wrapper = styled.div`
  width: 1100px;
  min-height: 110vh;
  margin: 0 auto;
  justify-content: center;
`;

export default function AccountRoot() {
  return (
    <>
      <Wrapper>
        sdfdf
        <Outlet />
      </Wrapper>
    </>
  );
}
