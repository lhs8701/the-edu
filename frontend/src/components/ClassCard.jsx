import { motion } from "framer-motion";
import { useState } from "react";
import { useNavigate } from "react-router";
import styled from "styled-components";

const ClassTab = styled.div`
  width: 240px;
  height: 200px;
  box-shadow: 0 0px 1px rgb(0 0 0 / 16%), 0 1px 7px rgb(0 0 0 / 16%);
  border-radius: var(--size-border-radius);
  cursor: pointer;
  overflow: hidden;
`;

const ClassImg = styled(motion.img)`
  height: 100%;
  width: 100%;
  background-color: teal;
`;

const ClassImgBox = styled.div`
  height: 65%;
  width: 100%;
  overflow: hidden;
`;

const ClassTitleBox = styled.div`
  margin-top: 5px;
  width: 100%;
  height: 22%;
  color: var(--color-black);
  display: flex;
  justify-content: space-between;
  & {
    p:nth-child(2) {
      font-size: 14px;
    }
  }
`;

const ClassInfoBox = styled(ClassTitleBox)`
  height: auto;
  margin-top: 0px;
`;

const ClassInfoP = styled(motion.p)`
  font-weight: var(--weight-thin);
  font-size: var(--size-card-title);
`;

const ClassTitle = styled(motion.p)`
  font-weight: var(--weight-middle);
  font-size: var(--size-card-title);
  padding: 0px 10px;
  box-sizing: border-box;
`;

const ClassInfoTeacherP = styled(ClassInfoP)`
  color: var(--color-gray);
  font-size: var(--size-card-any);
`;
export default function ClassCard({ course }) {
  const [isCardOn, setIsCardOn] = useState(false);

  const navigate = useNavigate();

  return (
    <ClassTab
      onClick={() => {
        navigate(`/course/${course?.courseId}`);
      }}
      onMouseEnter={() => {
        setIsCardOn(true);
      }}
      onMouseLeave={() => {
        setIsCardOn(false);
      }}
    >
      <ClassImgBox>
        <ClassImg
          src={course?.courseImg}
          animate={{
            scale: isCardOn ? 1.2 : 1,
          }}
          transition={{
            ease: "linear",
            duration: 0.2,
          }}
        />
      </ClassImgBox>

      <ClassTitleBox>
        <ClassTitle
        // animate={{
        //   fontWeight: isCardOn
        //     ? "var(--weight-normal)"
        //     : "var(--weight-thin)",
        // }}
        >
          {course?.courseTitle}
        </ClassTitle>
      </ClassTitleBox>

      <ClassInfoBox>
        <ClassInfoTeacherP>
          &nbsp;&nbsp;&nbsp;&nbsp;{course?.courseTeacher}
        </ClassInfoTeacherP>
        <ClassInfoP
          animate={{
            color: isCardOn ? "var(--color-primary)" : "var(--color-text)",
          }}
        >
          별{course?.courseStarRate}&nbsp;&nbsp;&nbsp;
        </ClassInfoP>
      </ClassInfoBox>
    </ClassTab>
  );
}
