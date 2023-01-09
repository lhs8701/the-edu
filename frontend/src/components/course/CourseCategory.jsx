import styled from "styled-components";

const CategoryWrapper = styled.div`
  width: 100%;
`;

const Title = styled.h1`
  font-size: var(--size-category-title);
`;

const BigCategory = styled.h1`
  font-weight: var(--weight-middle);
  font-size: var(--size-category-big);
`;
const SmallCategory = styled.h2`
  font-weight: var(--weight-thin);
  font-size: var(--size-category-small);
`;

export default function CourseCategory({ courseIdx }) {
  const Category = ({ cate, num }) => {
    return (
      <>
        <BigCategory>
          {num}.{cate.title}
        </BigCategory>
        {cate?.sub?.map((small, idx) => (
          <SmallCategory key={idx}>
            &nbsp;{num}-{small}
          </SmallCategory>
        ))}
      </>
    );
  };

  return (
    <CategoryWrapper>
      <Title>강의 목차</Title>
      {courseIdx.map((e, idx) => {
        return <Category key={idx} cate={e} num={idx + 1} />;
      })}
    </CategoryWrapper>
  );
}
