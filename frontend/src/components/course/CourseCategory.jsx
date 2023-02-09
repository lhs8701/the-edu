import styled from "styled-components";
import { queryClient } from "../..";
import { Wrapper } from "../../style/CommonCss";
import { Title } from "../../style/CourseCss";

const BigCategory = styled.h1`
  font-weight: var(--weight-middle);
  font-size: 1.5rem;
`;
const SmallCategory = styled.h2`
  font-weight: var(--weight-thin);
  font-size: 1.3rem;
  line-height: 33px;
  margin: 5px 0;
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
  width: 100%;
`;

const TitleTab = styled.div`
  width: 80%;
`;

export default function CourseCategory({ courseIdx, courseId }) {
  const courseCurriculum = queryClient.getQueryData([
    "courseCurriculum",
    courseId,
  ]);

  const SmallCategories = ({ unit, chapterIdx, unitIdx }) => {
    return (
      <SmallCategory>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{chapterIdx}-{unitIdx + 1}
        .&nbsp;&nbsp;
        <TitleTab>{unit?.title}</TitleTab>
      </SmallCategory>
    );
  };

  const Category = ({ curri, chapterIdx }) => {
    return (
      <div>
        <br />
        <br />
        <br />
        <BigCategory>
          &nbsp;{chapterIdx}.&nbsp;{curri.title}
        </BigCategory>
        <br />
        {curri?.units?.map((unit, idx) => {
          return (
            <SmallCategories
              key={unit?.unitId}
              unit={unit}
              chapterIdx={chapterIdx}
              unitIdx={idx}
            />
          );
        })}
      </div>
    );
  };

  const Categories = ({ courseCurriculum }) => {
    console.log(courseCurriculum);
    return courseCurriculum?.map((curri, idx) => {
      return <Category key={idx} curri={curri} chapterIdx={idx + 1} />;
    });
  };

  return (
    <Wrapper>
      <Title>강의 커리큘럼</Title>
      <br />
      <br />
      {courseCurriculum && (
        <Categories courseCurriculum={courseCurriculum?.data.chapters} />
      )}
    </Wrapper>
  );
}
