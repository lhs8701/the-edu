import { motion } from "framer-motion";
import { useNavigate } from "react-router";
import styled from "styled-components";
import { queryClient } from "../..";
import { postMyRecordApi } from "../../api/playerApi";
import { PROCESS_MAIN_URL } from "../../static";
import { Wrapper } from "../../style/CommonCss";
import { SideTitle, TitleBox } from "../../style/PlayerSideBarCss";

const Catalog = styled(motion.li)`
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  color: ${(props) =>
    props.men === props.now
      ? "var(--color-text)"
      : "var(--color-box-gray)"}; //props 활용
  padding-left: 15px;
  height: 30px;
`;

const ListBox = styled.ul`
  width: 100%;
  height: 100%;
  overflow: auto;
`;

const SubInfoBox = styled.div`
  margin: 15px 0;
  display: flex;
  width: 100%;
  align-items: center;
  justify-content: flex-end;
  font-weight: var(--weight-thin);
`;

export default function CourseList({ courseId, unitId, exitUnit }) {
  const curriculum = queryClient.getQueryData(["userCurriStatus", courseId]);
  const navigate = useNavigate();

  const Unit = ({ unit, idx }) => {
    console.log(unit);
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
        key={idx}
        whileHover={{ backgroundColor: "#dfdede", color: "var(--color-text)" }}
        animate={{
          backgroundColor: unit.completed ? "#a8a7a7" : "transparent",
        }}
      >
        {idx + 1}. {unit.title}
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
            {idx + 1}. {chapter.title}
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
}
