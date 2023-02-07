import styled from "styled-components";
import { motion } from "framer-motion";
import CourseList from "./CourseList";
import CourseInfo from "./CourseInfo";
import UnitQuestion from "./UnitQuestion";
import UnitReview from "./UnitReview";

const BarWrapper = styled.div`
  height: 100%;
  box-shadow: 0 0 0 rgb(0 0 0 / 16%), 0 1px 5px rgb(0 0 0 / 16%);
  background-color: var(--color-background);
`;

const NoticeTab = styled(motion.div)`
  width: 100%;
  height: 5%;
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  font-weight: var(--weight-middle);
`;

const Notice = styled(motion.div)`
  width: 25%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: nowrap;
  cursor: pointer;
  color: ${(props) =>
    props.men === props.now ? "var(--color-primary)" : "black"}; //props 활용
  &:hover {
    color: ${(props) =>
      props.men === props.now ? "var(--color-primary)" : "black"};
  }
  font-size: 0.9rem;
  flex: 1;
`;

const ListTab = styled(motion.div)`
  width: 100%;
  height: 95%;
  padding: 1rem 1rem;
  box-sizing: border-box;
`;

export default function PlayerSidebar({
  unitId,
  courseId,
  exitUnit,
  menu,
  setMenu,
}) {
  const MENU_LIST_NAME = ["강의 목록", "수업 자료", "질문", "평가"];
  const menuList = {
    0: <CourseList exitUnit={exitUnit} courseId={courseId} unitId={unitId} />,
    1: <CourseInfo />,
    2: <UnitQuestion unitId={unitId} />,
    3: <UnitReview unitId={unitId} />,
  };

  const MenuBar = ({ idx }) => {
    return (
      <Notice
        now={idx}
        men={menu}
        onClick={() => {
          setMenu(idx);
        }}
      >
        {MENU_LIST_NAME[idx]}
      </Notice>
    );
  };

  return (
    <BarWrapper>
      <NoticeTab>
        {[0, 1, 2, 3].map((idx) => {
          return <MenuBar idx={idx} />;
        })}
      </NoticeTab>
      <ListTab>{menuList[menu]}</ListTab>
    </BarWrapper>
  );
}
