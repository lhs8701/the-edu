import { Link } from "react-router-dom";
import styled from "styled-components";

const SideBar = styled.nav`
  width: 17%;
  height: 270px;
  border-radius: 10px;
  top: 200px;
  position: sticky;
  position: -webkit-sticky;
  background: var(--color-sidebar);
  box-shadow: 0 1px 1px rgb(0 0 0 / 16%), 0 2px 10px rgb(0 0 0 / 16%);
  display: flex;
  justify-content: flex-start;
  align-items: center;
  flex-direction: column;
  padding: 15px 0;
`;

const MyLink = styled(Link)`
  text-decoration: none;
  height: 100%;
  display: flex;
  align-items: center;
  font-size: 19px;
  font-weight: var(--weight-middle);
  color: ${(props) =>
    props.mouse ? "var(--color-primary)" : "var(--color-text)"};
  &:hover {
    color: var(--color-primary);
  }
`;
const NavBox = styled.ul``;
const NavTab = styled.li`
  height: 45px;
  display: flex;
  align-items: center;
  justify-content: center;
`;

export default function MyPageSideBar() {
  return (
    <SideBar>
      <NavBox>
        <NavTab>
          <MyLink to={"own"}>나의 클래스</MyLink>
        </NavTab>
        <NavTab>
          <MyLink to={"wish"}>찜한 클래스</MyLink>
        </NavTab>
        <NavTab>
          <MyLink to={"Revise"}>개인정보 수정</MyLink>
        </NavTab>
        <NavTab>
          <MyLink to={"coupon"}>쿠폰</MyLink>
        </NavTab>
        <NavTab>
          <MyLink to={"deal"}>구매 내역</MyLink>
        </NavTab>
        <NavTab>
          <MyLink to={"withdraw"}>회원 탈퇴</MyLink>
        </NavTab>
      </NavBox>
    </SideBar>
  );
}
