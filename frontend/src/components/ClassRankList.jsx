import styled from "styled-components";
import ClassTab from "./ClassTab";

const ListWrapper = styled.div`
  margin-top: 25px;
  margin-bottom: 25px;
`;

const ListTitle = styled.h1`
  font-weight: var(--weight-point);
  font-size: var(--size-rank-title);
  margin-bottom: 30px;
`;

const ClassListBox = styled.div`
  display: flex;
  width: 100%;
  height: 180px;
`;

export default function ClassRankList(props) {
  return (
    <ListWrapper>
      <ListTitle>랭킹</ListTitle>
      <ClassListBox>
        <ClassTab />
      </ClassListBox>
    </ListWrapper>
  );
}
