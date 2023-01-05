import styled from "styled-components";

const ListWrapper = styled.div`
  margin-top: 25px;
  margin-bottom: 25px;
`;

const ListTitle = styled.h1`
  font-weight: var(--weight-point);
  font-size: var(--size-rank-title);
`;

export default function ClassRankList(props) {
  return (
    <ListWrapper>
      <ListTitle>랭킹</ListTitle>
    </ListWrapper>
  );
}
