import { motion } from "framer-motion";
import styled from "styled-components";

export const MyPageTitle = styled.h1`
  font-weight: var(--weight-point);
  font-size: var(--size-mypage-title);
  margin-top: 30px;
  margin-bottom: 50px;
  margin-left: 30px;
`;

export const MyPageBox = styled.div`
  width: 80%;
  height: 100%;
`;

export const MyPageContentBox = styled.div`
  width: 100%;
`;

export const Card = styled(motion.div)`
  width: 100%;
  height: 120px;
  background-color: var(--color-background);
  border-radius: var(--size-border-radius);
  overflow: hidden;
  border: 1.7px solid var(--color-text);
  box-shadow: 3px 3px 4px rgba(0, 0, 0, 0.25);
  margin-bottom: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
`;

export const CardInfoBox = styled.div`
  width: 90%;
  height: 80px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

export const CardTitle = styled.h1`
  font-size: 21px;
  font-weight: var(--weight-point);
`;
