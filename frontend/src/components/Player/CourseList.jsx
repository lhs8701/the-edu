import { motion } from "framer-motion";
import React from "react";
import { useNavigate } from "react-router";
import styled from "styled-components";
import { queryClient } from "../..";
import { PROCESS_MAIN_URL } from "../../static";
import { Wrapper } from "../../style/CommonCss";
import { SideTitle, TitleBox } from "../../style/PlayerSideBarCss";

const Catalog = styled(motion.li)`
  cursor: pointer;
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
  color: ${(props) =>
    props.men === props.now
      ? "var(--color-text)"
      : "var(--color-box-gray)"}; //props 활용
  padding-left: 8px;
  padding-top: 8px;
  padding-bottom: 8px;
  background: ${(props) =>
    props.complete ? "#000000" : "var(--color-background)"}; //props 활용
`;

const ListBox = styled.ul`
  width: 100%;
  height: 100%;
  overflow: auto;
`;

const UnitTitle = styled.div`
  margin-left: 5px;
`;

const CourseList = React.memo(function CourseList({
  courseId,
  unitId,
  exitUnit,
}) {
  const curriculum = queryClient.getQueryData(["userCurriStatus", courseId]);
  const navigate = useNavigate();

  const Unit = ({ unit, idx }) => {
    return (
      <Catalog
        onClick={() => {
          exitUnit();
          navigate(
            PROCESS_MAIN_URL.PLAYER + "/" + courseId + "/" + unit.unitId
          );
          window.location.reload();
        }}
        men={unit.unitId}
        now={Number(unitId)}
        complete={unit.completed}
        key={idx}
        whileHover={{ backgroundColor: "#dfdede", color: "var(--color-text)" }}
      >
        {idx + 1}.<UnitTitle>{unit.title}</UnitTitle>
      </Catalog>
    );
  };

  const Units = ({ units }) => {
    return units?.map((unit, idx) => {
      return <Unit unit={unit} idx={idx} key={unit.unitId} />;
    });
  };

  const Chapter = ({ chapter, idx }) => {
    return (
      <>
        <TitleBox>
          <SideTitle>
            {idx + 1}.&nbsp;<div>{chapter.title}</div>
          </SideTitle>
        </TitleBox>
        <br />
        <Units units={chapter.units} />
        <br />
      </>
    );
  };

  const Chapters = ({ chapters }) => {
    return chapters?.map((chapter, idx) => {
      return <Chapter chapter={chapter} idx={idx} key={idx} />;
    });
  };

  return (
    <Wrapper>
      <ListBox>
        {curriculum?.data && <Chapters chapters={curriculum?.data?.chapters} />}
      </ListBox>
    </Wrapper>
  );
});

export default React.memo(CourseList);
