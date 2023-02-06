import styled from "styled-components";
import { queryClient } from "../..";
import { Wrapper } from "../../style/CommonCss";
import { Title } from "../../style/CourseCss";

const BigCategory = styled.h1`
  font-weight: var(--weight-middle);
  font-size: var(--size-category-big);
`;
const SmallCategory = styled.h2`
  font-weight: var(--weight-thin);
  font-size: var(--size-category-small);
  line-height: 33px;
`;

export default function CourseCategory({ courseIdx, courseId }) {
  const courseCurriculum = queryClient.getQueryData([
    "courseCurriculum",
    courseId,
  ]);

  const Category = ({ cate, num }) => {
    console.log(cate);
    return (
      <div>
        <br />
        <BigCategory>
          &nbsp;&nbsp;&nbsp;&nbsp;{num}.&nbsp;{cate.title}
        </BigCategory>
        <br />
        {cate?.unitList?.map((small, idx) => {
          return (
            <SmallCategory key={small?.unitId}>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{num}-
              {idx + 1}
              .&nbsp;&nbsp;
              {small?.title}
            </SmallCategory>
          );
        })}
      </div>
    );
  };

  const Categories = ({ courseCurriculum }) => {
    return courseCurriculum.map((curri, idx) => {
      return <Category key={idx} cate={curri} num={idx + 1} />;
    });
  };

  return (
    <Wrapper>
      <Title>강의 커리큘럼</Title>
      <br />
      <br />
      <Categories courseCurriculum={courseCurriculum.data.chapterList} />
    </Wrapper>
  );
}
