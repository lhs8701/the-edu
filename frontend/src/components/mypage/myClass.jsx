import { motion } from "framer-motion";
import { useState } from "react";
import styled from "styled-components";
import {
  MyPageBox,
  MyPageContentBox,
  MyPageTitle,
} from "../../style/MypageComponentsCss";
import MyClassNavBar from "./MyClassNavBar";

const MyListBox = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-row-gap: 40px;
  width: 100%;
  align-items: center;
  justify-items: center;
  margin-bottom: 50px;
`;

export default function MyClass() {
  const [isTabStatus, setIsTabStatus] = useState(0);

  return (
    <MyPageBox>
      <MyPageTitle>나의 클래스</MyPageTitle>
      <MyPageContentBox>
        <MyClassNavBar
          isTabStatus={isTabStatus}
          setIsTabStatus={setIsTabStatus}
        />
      </MyPageContentBox>
    </MyPageBox>
  );
}
