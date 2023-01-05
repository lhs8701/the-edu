import { useState } from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import { motion, AnimatePresence } from "framer-motion";
import {
  CATE_VALUE,
  PROCESS_ACCOUNT_URL,
  PROCESS_MAIN_URL,
  COLOR,
} from "../static";
const HeadWrapper = styled.header`
  width: 100%;
  height: 65px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 25px;
  padding-top: 20px;
  position: sticky;
  top: 0;
  background-color: var(--color-background);
  z-index: 1;
`;

const TitleBox = styled.div`
  height: 100%;
  display: flex;
  align-items: center;
`;

const Title = styled.p`
  font-size: 40px;
  font-weight: var(--weight-point);
  cursor: pointer;
`;

const PointTitle = styled(Title)`
  color: var(--color-primary);
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
  height: 100%;
  display: flex;
  align-items: center;
  font-size: 20px;
  font-weight: var(--weight-middle);
  color: ${(props) =>
    props.mouse ? "var(--color-primary)" : "var(--color-text)"};
  &:hover {
    color: var(--color-primary);
  }
`;

const CateBox = styled(motion.div)`
  text-decoration: none;
  height: 100%;
  display: flex;
  align-items: center;
  font-size: 20px;
  font-weight: var(--weight-middle);
  position: relative;
  cursor: pointer;
`;

const CateTab = styled(motion.div)`
  position: absolute;
  width: 120px;
  box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.25);
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: var(--color-background);
  padding-top: 20px;
  padding-bottom: 10px;
  top: 50px;
  left: -13px;
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
  border-bottom: 1.5px solid var(--color-gray);
  &:focus {
    outline: none;
    border-bottom: 1.5px solid var(--color-primary);
  }
  &::placeholder {
    color: var(--color-gray);
    padding-left: 1px;
    font-weight: var(--weight-point);
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

const CateLink = styled(Link)`
  font-size: 18px;
  text-decoration: none;
  color: var(--color-gray);
  font-weight: var(--weight-thin);
  color: ${(props) =>
    props.mouse ? "var(--color-primary)" : "var(--color-text)"};
  &:hover {
    color: var(--color-primary);
    font-weight: var(--weight-middle);
  }
  display: ${(props) => (props.iscategoryon ? "flex" : "none")};
`;

export default function Header() {
  const [isCategoryOn, setCategoryOn] = useState(false);
  return (
    <HeadWrapper>
      <TitleBox
        onClick={() => {
          window.location.replace("/");
        }}
      >
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
              onMouseDown={() => {
                setCategoryOn(true);
              }}
              animate={{
                color: isCategoryOn ? "var(--color-primary)" : "black",
              }}
            >
              카테고리
              <CateTab
                initial={{ opacity: 0, height: 0 }}
                animate={{
                  opacity: isCategoryOn ? 1 : 0,
                  height: isCategoryOn ? "auto" : 0,
                }}
                transition={{
                  duration: 0.2,
                  type: "ease",
                }}
                onMouseEnter={() => {
                  setCategoryOn(true);
                }}
              >
                <CateLink
                  iscategoryon={isCategoryOn}
                  to={PROCESS_MAIN_URL.CATEGORIES + "/" + 0}
                >
                  전체 보기
                </CateLink>
                <br />
                {CATE_VALUE.map((e, index) => {
                  return (
                    <>
                      <CateLink
                        iscategoryon={isCategoryOn}
                        to={PROCESS_MAIN_URL.CATEGORIES + "/" + index}
                      >
                        {e}
                      </CateLink>
                      <br />
                    </>
                  );
                })}
              </CateTab>
            </CateBox>
          </NavTab>
          <NavTab>
            <NavLink to={""}>정보 공유</NavLink>
          </NavTab>
          <NavTab>
            <NavLink to={PROCESS_MAIN_URL.ROADMAP}>로드맵</NavLink>
          </NavTab>
          <NavTab>
            <NavLink to={PROCESS_MAIN_URL.EVENT}>이벤트</NavLink>
          </NavTab>
        </NavBox>
      </NavBar>
      <SearchBox>
        <SearchInput placeholder="검색어를 입력해주세요." />
      </SearchBox>
      <LoginTab>
        <LoginLink to={PROCESS_ACCOUNT_URL.LOGIN}>로그인</LoginLink>
      </LoginTab>
    </HeadWrapper>
  );
}
