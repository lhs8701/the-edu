import styled from "styled-components";
import ClassCard from "./ClassCard";

const ListWrapper = styled.div`
  margin-top: 25px;
  margin-bottom: 60px;
`;

const ListTitle = styled.h1`
  font-weight: var(--weight-point);
  font-size: var(--size-rank-title);
  margin-bottom: 30px;
`;

const ClassListBox = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 200px;
`;

export default function ClassRankList({ ranklist }) {
  return (
    <ListWrapper>
      <ListTitle>{ranklist.rankTitle} 클래스 랭킹</ListTitle>
      <ClassListBox>
        {ranklist.rankCourse.map((course) => {
          return <ClassCard key={course.courseId} course={course} />;
        })}
      </ClassListBox>
    </ListWrapper>
  );
}
