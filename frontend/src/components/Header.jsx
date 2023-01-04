import { useState } from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import { motion, AnimatePresence } from "framer-motion";
const HeadWrapper = styled.header`
  width: 100%;
  height: 65px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 25px;
  padding-top: 20px;
`;

const TitleBox = styled.div`
  height: 100%;
  display: flex;
  align-items: center;
`;

const Title = styled.p`
  font-size: 40px;
  font-weight: 900;
`;

const PointTitle = styled(Title)`
  color: #ffbf00;
  margin-left: 10px;
`;

const NavBar = styled.nav`
  height: 100%;
`;
const NavBox = styled.ul`
  display: flex;
  height: 100%;
  &:first-child {
    li:first-child {
      padding-right: 27px;
    }
  }
`;
const NavTab = styled.li`
  height: 100%;
  padding: 0px 20px;
`;

const NavLink = styled(Link)`
  text-decoration: none;
  color: black;
  height: 100%;
  display: flex;
  align-items: center;
  font-size: 20px;
  font-weight: 500;
  color: ${(props) => (props.mouse ? "#ffbf00" : "black")};
  &:hover {
    color: #ffbf00;
  }
`;

const CateBox = styled(motion.div)`
  text-decoration: none;
  height: 100%;
  display: flex;
  align-items: center;
  font-size: 20px;
  font-weight: 500;
  position: relative;
  cursor: pointer;
`;

const CateTab = styled(motion.div)`
  position: absolute;
  width: 100px;
  box-shadow: 4px 4px 4px rgba(0, 0, 0, 0.25);
`;

const SearchBox = styled.div`
  margin-left: 150px;
  height: 100%;
  display: flex;
  align-items: center;
`;

const SearchInput = styled.input`
  width: 250px;
  height: 30px;
  border: none;
  border-bottom: 1.5px solid #747272;
  &:focus {
    outline: none;
    border-bottom: 1.5px solid #ffbf00;
  }
  &::placeholder {
    color: #d5d5d5;
    padding-left: 8px;
    font-weight: 600;
  }
`;

const LoginTab = styled.div`
  height: 100%;
  display: flex;
  align-items: center;
`;

const LoginLink = styled(NavLink)`
  font-size: 18px;
`;

export default function Header() {
  const [isCategoryOn, setCategoryOn] = useState(false);
  return (
    <HeadWrapper>
      <TitleBox>
        <Title>다</Title>
        <PointTitle> 봄</PointTitle>
      </TitleBox>
      <NavBar>
        <NavBox>
          <NavTab>
            <CateBox
              onMouseEnter={() => {
                setCategoryOn(true);
              }}
              onMouseLeave={() => {
                setCategoryOn(false);
              }}
              animate={{
                color: isCategoryOn ? "#ffbf00" : "black",
              }}
            >
              카테고리
            </CateBox>
            <CateTab
              initial={{ opacity: 0, height: 0 }}
              animate={{
                opacity: isCategoryOn ? 1 : 0,
                height: isCategoryOn ? "500px" : 0,
              }}
              transition={{
                duration: 0.2,
                type: "ease",
              }}
              onMouseEnter={() => {
                setCategoryOn(true);
              }}
              onMouseLeave={() => {
                setCategoryOn(false);
              }}
            >
              {["프로그래밍", "자격증", "외국어", "재테크"].map((e) => {
                return <div>{e}</div>;
              })}
            </CateTab>
          </NavTab>
          <NavTab>
            <NavLink to={""}>정보 공유</NavLink>
          </NavTab>
          <NavTab>
            <NavLink to={"roadmap"}>로드맵</NavLink>
          </NavTab>
          <NavTab>
            <NavLink to={"event"}>이벤트</NavLink>
          </NavTab>
        </NavBox>
      </NavBar>
      <SearchBox>
        <SearchInput placeholder="검색어를 입력해주세요." />
      </SearchBox>
      <LoginTab>
        <LoginLink to={"login"}>로그인</LoginLink>
      </LoginTab>
    </HeadWrapper>
  );
}
