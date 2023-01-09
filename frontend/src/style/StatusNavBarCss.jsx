import { motion } from "framer-motion";
import styled from "styled-components";

export const StatusNavBar = styled.nav`
  width: 100%;
  height: 35px;
  font-size: var(--size-own-nav);
  font-weight: var(--weight-normal);
  background-color: var(--color-background);
  z-index: 3;
`;

export const StatusNavBox = styled.ul`
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
`;

export const StatusDisplayUnderBar = styled(motion.div)`
  width: 100%;
  height: 2px;
  background-color: var(--color-primary);
  position: absolute;
  bottom: 0px;
`;

export const StatusNavTab = styled.li`
  color: ${(props) =>
    props.ison[0] === props.ison[1]
      ? "var(--color-text)"
      : "var(--color-gray)"};
  font-weight: ${(props) =>
    props.ison[0] === props.ison[1]
      ? "var(--weight-point)"
      : "var(--weight-thin)"};
  height: 100%;
  margin: 0 30px;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
`;
