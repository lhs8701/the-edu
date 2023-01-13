import { motion } from "framer-motion";
import styled from "styled-components";
import { SideTitle, TitleBox } from "../../style/PlayerSideBarCss";

const Wrapper = styled.div`
  height: 100%;
  width: 100%;
`;

const Catalog = styled(motion.li)`
  height: 30px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  /* color: ${(props) =>
    props.men === props.now ? "#a8a7a7" : "black"}; //props 활용 */
  margin: 10px 0px;
`;

const Tab = styled.div`
  margin-left: 25px;
`;

const ListBox = styled.ul`
  width: 100%;
  height: 92%;
  background-color: teal;
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

export default function CourseList() {
  return (
    <Wrapper>
      <TitleBox>
        <SideTitle>&lt;courseInfo.title&gt;</SideTitle>
        <SubInfoBox>
          <h2>courseInfo.subtitle</h2>
          {/* <h3>{courseInfo.instructor}</h3> */}
        </SubInfoBox>
        <SideTitle>unitInfo.title</SideTitle>
      </TitleBox>
      <ListBox>
        {/* {courseList?.map((e, idx) => {
          return (
            <Catalog
              onClick={() => {
                getAnotherCourseUnit(e.unitId);
              }}
              men={e.unitId}
              now={unitInfo.unitId}
              key={idx}
              whileHover={{ backgroundColor: "#dfdede" }}
              animate={{
                color: e.completed ? "#a8a7a7" : "black",
                backgroundColor:
                  e.unitId === unitInfo.unitId ? "#a8a7a7" : "transparent",
              }}
            >
              <Tab>
                {idx}. {e.title}
              </Tab>
            </Catalog>
          );
        })} */}
      </ListBox>
    </Wrapper>
  );
}
