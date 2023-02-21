import { useNavigate } from "react-router";
import styled from "styled-components";

const UnitBox = styled.div`
  display: flex;
  width: 100%;
  align-items: center;
  justify-content: space-between;
  &:hover {
    background-color: var(--color-box-gray);
  }
  margin-bottom: 7px; ;
`;

const ChapterBox = styled.div`
  border: 1px solid black;
  padding: 10px 20px;
  box-sizing: border-box;
  margin-bottom: 10px;
`;

export default function SimpleCurriculumCheck({ curriculumList }) {
  const navigate = useNavigate();

  return curriculumList?.map((chapter, idx) => {
    return (
      <ChapterBox key={chapter.title}>
        <div>
          {idx + 1}. {chapter?.title}
        </div>
        <br />
        {chapter?.units?.map((unit, idx) => {
          return (
            <UnitBox key={unit.unitId}>
              <div>
                {idx + 1}. {unit?.title}
              </div>
              <button
                onClick={() => {
                  navigate(`${unit?.unitId}`);
                }}
              >
                강좌 보기
              </button>
            </UnitBox>
          );
        })}
      </ChapterBox>
    );
  });
}
