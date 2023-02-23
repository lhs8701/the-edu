import { Link } from "react-router-dom";
import styled from "styled-components";

export const MyLink = styled(Link)`
  text-decoration: none;
  height: 100%;
  display: flex;
  align-items: center;
  font-size: 19px;
  font-weight: var(--weight-middle);
  color: ${(props) =>
    props.mouse ? "var(--color-primary)" : "var(--color-text)"};
  &:hover {
    color: var(--color-primary);
  }
`;
export const NavBox = styled.ul`
  width: 100%;
  height: 100%;
`;
export const NavTab = styled.li`
  width: 100%;
  height: 45px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  position: relative;
`;
