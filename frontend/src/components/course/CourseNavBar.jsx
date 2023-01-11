import { useInView } from "framer-motion";
import styled from "styled-components";
import {
  StatusDisplayUnderBar,
  StatusNavBar,
  StatusNavBox,
  StatusNavTab,
} from "../../style/StatusNavBarCss";

const CourseStatusNavBar = styled(StatusNavBar)`
  width: 100%;
  height: 50px;
  font-size: var(--size-own-footer);
  font-weight: var(--weight-normal);
  position: sticky;
  top: 0px;
  background-color: var(--color-background);
`;

const CourseStatusNavBox = styled(StatusNavBox)`
  justify-content: flex-start;
`;

const CourseStatusNavTab = styled(StatusNavTab)`
  margin: 0px;
  margin-right: 75px;
  font-weight: ${(props) =>
    props.ison[0] === props.ison[1]
      ? "var(--weight-point)"
      : "var(--weight-middle)"};
`;

const TAB_STATUS = [
  { id: 0, title: "클래스 소개" },
  { id: 1, title: "커리큘럼" },
  { id: 2, title: "수강 후기" },
  { id: 3, title: "문의 사항" },
];

export default function CourseNavBar({ refArr, isTabStatus, setIsTabStatus }) {
  const moveToSection = (index) => {
    refArr[index]?.current.scrollIntoView({
      behavior: "smooth",
      block: "start",
      // inline: "start",
    });
  };

  //
  return (
    <CourseStatusNavBar>
      <CourseStatusNavBox>
        {TAB_STATUS.map((tab) => {
          return (
            <CourseStatusNavTab
              key={tab.id}
              onClick={() => {
                moveToSection(tab.id);
                setIsTabStatus(tab.id);
              }}
              ison={[isTabStatus, tab.id]}
            >
              {tab.title}
              {isTabStatus === tab?.id && (
                <StatusDisplayUnderBar layoutId="course" />
              )}
            </CourseStatusNavTab>
          );
        })}
      </CourseStatusNavBox>
    </CourseStatusNavBar>
  );
}
