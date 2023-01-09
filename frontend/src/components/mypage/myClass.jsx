import { motion } from "framer-motion";
import { useState } from "react";
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

const StatusDisplayUnderBar = styled(motion.div)`
  width: 100%;
  height: 2px;
  background-color: var(--color-primary);
  position: absolute;
  bottom: 0px;
`;

const StatusNavTab = styled.li`
  color: ${(props) =>
    props.ison[0] === props.ison[1]
      ? "var(--color-text)"
      : "var(--color-gray)"};
  font-weight: ${(props) =>
    props.ison[0] === props.ison[1]
      ? "var(--weight-point)"
      : "var(--weight-thin)"};
  height: 100%;
  margin: 0 30px;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
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

const TAB_STATUS = [
  {
    id: 0,
    title: "전체 보기",
  },
  {
    id: 1,
    title: "덜 봄",
  },
  {
    id: 2,
    title: "다 봄",
  },
];

export default function MyClass() {
  const [isTabStatus, setIsTabStatus] = useState(0);

  return (
    <MyPageBox>
      <MyPageTitle>나의 클래스</MyPageTitle>
      <MyPageContentBox>
        <StatusNavBar>
          <StatusNavBox>
            {TAB_STATUS.map((tab) => {
              return (
                <>
                  <StatusNavTab
                    key={tab.id}
                    onClick={() => {
                      setIsTabStatus(tab.id);
                    }}
                    ison={[isTabStatus, tab.id]}
                  >
                    {tab.title}
                    {isTabStatus === tab?.id && (
                      <StatusDisplayUnderBar layoutId />
                    )}
                  </StatusNavTab>
                </>
              );
            })}
          </StatusNavBox>
        </StatusNavBar>
      </MyPageContentBox>
    </MyPageBox>
  );
}
