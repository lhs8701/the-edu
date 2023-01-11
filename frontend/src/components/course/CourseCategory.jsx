import styled from "styled-components";
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

export default function CourseCategory({ courseIdx }) {
  const Category = ({ cate, num }) => {
    return (
      <div key={num}>
        <br />
        <BigCategory>
          &nbsp;&nbsp;&nbsp;&nbsp;{num}.&nbsp;{cate.title}
        </BigCategory>
        <br />
        {cate?.sub?.map((small, idx) => (
          <SmallCategory key={idx}>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{num}-
            {idx}
            .&nbsp;&nbsp;
            {small}
          </SmallCategory>
        ))}
      </div>
    );
  };

  const Categories = ({ courseIdx }) => {
    return courseIdx.map((e, idx) => {
      return <Category key={idx} cate={e} num={idx + 1} />;
    });
  };

  return (
    <Wrapper>
      <Title>강의 커리큘럼</Title>
      <br />
      <br />
      <Categories courseIdx={courseIdx} />
    </Wrapper>
  );
}
