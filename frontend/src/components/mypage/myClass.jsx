import styled from "styled-components";
import {
  MyPageBox,
  MyPageContentBox,
  MyPageTitle,
} from "../../style/MypageComponentsCss";

const StatusNavBar = styled.nav`
  width: 100%;
  height: 35px;
  font-size: var(--size-own-nav);
  font-weight: var(--weight-normal);
`;

const StatusNavBox = styled.ul`
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
`;

const StatusNavTab = styled.li`
  color: ${(props) =>
    props.mouse ? "var(--color-primary)" : "var(--color-gray)"};
  height: 100%;
  margin: 0 30px;
  cursor: pointer;
  border-bottom: 2px solid var(--color-primary);
  display: flex;
  justify-content: center;
  align-items: center;
`;

const MyListBox = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-row-gap: 40px;
  width: 100%;
  align-items: center;
  justify-items: center;
  margin-bottom: 50px;
`;

export default function MyClass() {
  return (
    <MyPageBox>
      <MyPageTitle>나의 클래스</MyPageTitle>
      <MyPageContentBox>
        <StatusNavBar>
          <StatusNavBox>
            <StatusNavTab>전체보기</StatusNavTab>
            <StatusNavTab>덜 봄</StatusNavTab>
            <StatusNavTab>다 봄</StatusNavTab>
          </StatusNavBox>
        </StatusNavBar>
      </MyPageContentBox>
    </MyPageBox>
  );
}
