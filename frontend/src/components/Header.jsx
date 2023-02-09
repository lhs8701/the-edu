import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";
import styled from "styled-components";
import { motion } from "framer-motion";
import {
  CATE_VALUE,
  CREATOR_BAR_LIST,
  PROCESS_ACCOUNT_URL,
  PROCESS_CREATOR_URL,
  PROCESS_MAIN_URL,
} from "../static";
import {
  getAccessTokenSelector,
  getIsBasicSelector,
  getIsKakaoSelector,
  getKakaoAuthTokenSelector,
  getLoginState,
  getRefreshTokenSelector,
  LoginState,
} from "../atom";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { basicLogout, kakaoLogout } from "../api/authApi";
import { queryClient } from "../index";

const HeadWrapper = styled.header`
  width: 100%;
  height: 65px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 25px;
  padding-top: 20px;
  /* position: sticky;
  top: 0; */
  background-color: var(--color-background);
  z-index: 3;
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
  display: flex;
  align-items: center;
`;

const Icon = styled(FontAwesomeIcon)`
  position: absolute;
  width: 1.5rem;
  height: 1.5rem;
  color: var(--color-text);
  cursor: pointer;
  &:hover {
    scale: 1.2;
  }
  &:active {
    scale: 0.8;
  }
  right: 0;
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
  height: 70%;
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
  box-shadow: 0 1px 1px rgb(0 0 0 / 16%), 0 2px 14px rgb(0 0 0 / 16%);
  border-radius: var(--size-border-radius);
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: var(--color-background);
  padding-top: 20px;
  padding-bottom: 10px;
  top: 45px;
  left: -13px;
  z-index: 10;
`;

const SearchBox = styled.div`
  margin-left: 150px;
  height: 100%;
  display: flex;
  align-items: center;
  position: relative;
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
  position: relative;
`;

const UserTab = styled.div`
  position: absolute;
  width: auto;
  top: -5px;
  right: 8px;
  display: flex;
  white-space: nowrap;
`;

const UserLink = styled(NavLink)`
  font-size: 12px;
  margin-right: 15px;
  font-weight: var(--weight-normal);
`;

const LoginLink = styled(NavLink)`
  font-size: 18px;
`;

const LogoutBtn = styled.button`
  border: none;
  display: flex;
  align-items: center;
  font-weight: var(--weight-middle);
  color: ${(props) =>
    props.mouse ? "var(--color-primary)" : "var(--color-text)"};
  &:hover {
    color: var(--color-primary);
  }
  font-size: 18px;
  background-color: transparent;
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
  display: ${(props) => (props.iscategoryon === "true" ? "flex" : "none")};
`;

export default function Header() {
  const [isCategoryOn, setCategoryOn] = useState("false");
  const loginState = useRecoilValue(getLoginState);
  const setIsLoggedIn = useSetRecoilState(LoginState);
  const accessToken = useRecoilValue(getAccessTokenSelector);
  const refreshToken = useRecoilValue(getRefreshTokenSelector);
  const isKakaoState = useRecoilValue(getIsKakaoSelector);
  const isBasicState = useRecoilValue(getIsBasicSelector);
  const socialToken = useRecoilValue(getKakaoAuthTokenSelector);
  const [isSearchKeyword, setIsSearchKeyword] = useState("");
  const navigate = useNavigate();

  const logOut = () => {
    if (isKakaoState) {
      kakaoLogout(accessToken, socialToken);
    } else if (isBasicState) {
      basicLogout(accessToken, refreshToken);
    }
    setIsLoggedIn({
      state: false,
      isKakao: false,
      isBasic: false,
      memberId: 0,
      accessToken: "",
      refreshToken: "",
    });
    queryClient.clear();
    navigate("/");
  };

  const goSearch = () => {
    if (isSearchKeyword.length !== 0) {
      navigate(`${PROCESS_MAIN_URL.SEARCH}/${isSearchKeyword}`);
    } else {
      alert("검색어를 입력하세요!");
    }
  };

  const keyUpHandler = (e) => {
    var keyCode = e.keyCode ? e.keyCode : e.which;
    if (Number(keyCode) === 13) {
      goSearch();
    }
  };

  return (
    <HeadWrapper>
      <TitleBox
        onClick={() => {
          window.location.replace("/");
        }}
      >
        <Title>The</Title>
        <PointTitle>Edu</PointTitle>
      </TitleBox>
      <NavBar>
        <NavBox>
          <NavTab>
            <CateBox
              onMouseEnter={() => {
                setCategoryOn("true");
              }}
              onMouseLeave={() => {
                setCategoryOn("false");
              }}
              onMouseDown={() => {
                setCategoryOn("true");
              }}
              animate={{
                color:
                  isCategoryOn === "true" ? "var(--color-primary)" : "black",
              }}
            >
              카테고리
              {isCategoryOn === "true" && (
                <CateTab
                  initial={{ opacity: 0, height: 0 }}
                  animate={{
                    opacity: isCategoryOn === "true" ? 1 : 0,
                    height: isCategoryOn === "true" ? "auto" : 0,
                  }}
                  transition={{
                    duration: 0.2,
                    type: "ease",
                  }}
                  onMouseEnter={() => {
                    setCategoryOn("true");
                  }}
                >
                  <CateLink
                    iscategoryon={isCategoryOn}
                    to={PROCESS_MAIN_URL.CATEGORIES + "/" + 0 + "/" + 0}
                  >
                    전체 보기
                  </CateLink>
                  <br />
                  {CATE_VALUE.map((e, index) => {
                    return (
                      <span key={index}>
                        <CateLink
                          iscategoryon={isCategoryOn}
                          to={PROCESS_MAIN_URL.CATEGORIES + "/" + e.id + "/1"} //전체보기가 없으므로 1로 이동
                          preventScrollReset={true}
                        >
                          {e.big}
                        </CateLink>
                        <br />
                      </span>
                    );
                  })}
                </CateTab>
              )}
            </CateBox>
          </NavTab>
          <NavTab>
            <NavLink isValid={[0, 1]} to={""} preventScrollReset={true}>
              정보 공유
            </NavLink>
          </NavTab>
          <NavTab>
            <NavLink to={PROCESS_MAIN_URL.ROADMAP} preventScrollReset={true}>
              로드맵
            </NavLink>
          </NavTab>
          <NavTab>
            <NavLink to={PROCESS_MAIN_URL.EVENT} preventScrollReset={true}>
              이벤트
            </NavLink>
          </NavTab>
        </NavBox>
      </NavBar>
      <SearchBox>
        <SearchInput
          onKeyUp={keyUpHandler}
          value={isSearchKeyword}
          onChange={(e) => {
            setIsSearchKeyword(e.target.value);
          }}
          placeholder="검색어를 입력해주세요."
        />

        <Icon onClick={goSearch} icon={faMagnifyingGlass} />
      </SearchBox>
      {loginState ? (
        <LoginTab>
          <LogoutBtn onClick={logOut}>로그아웃</LogoutBtn>
          <UserTab>
            <UserLink to={CREATOR_BAR_LIST.list[0].creator[1].url}>
              크리에이터
            </UserLink>
            <UserLink to={PROCESS_MAIN_URL.MYPAGE.DEFAULT}>마이페이지</UserLink>
          </UserTab>
        </LoginTab>
      ) : (
        <LoginTab>
          <LoginLink to={PROCESS_ACCOUNT_URL.LOGIN} preventScrollReset={true}>
            로그인
          </LoginLink>
        </LoginTab>
      )}
    </HeadWrapper>
  );
}
