import styled from "styled-components";

export const Wrapper = styled.div`
  width: 100%;
  height: 100%;
`;

export const TabTitle = styled.h1`
  margin-top: 4rem;
  margin-bottom: 6rem;
  font-weight: var(--weight-point);
  font-size: var(--size-mypage-title);
  width: 100%;
  text-align: center;
`;

export const MoveBtn = styled.button`
  width: 180px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: var(--color-background);
  font-size: 1rem;
  transition: 0.1s;
  &:hover {
    /* box-shadow: 0 0.5em 0.5em -0.4em var(--color-text); */
    background-color: var(--color-primary);
    font-weight: var(--weight-middle);
  }
`;

export const AlertP = styled.p`
  font-size: 1.5rem;
  font-weight: var(--weight-middle);
`;

export const CenterDiv = styled.div`
  display: flex;
  width: 100%;
  align-items: center;
  justify-content: center;
`;
