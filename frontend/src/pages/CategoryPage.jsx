import { useCallback, useRef, useState } from "react";
import { useParams } from "react-router";
import styled from "styled-components";
import Arcodian from "../components/Arcodian";
import ClassCard from "../components/ClassCard";
import { dummyWishList } from "../dummy";
import { CATE_VALUE, PROCESS_MAIN_URL } from "../static";
import {
  MyPageBox,
  MyPageContentBox,
  MyPageTitle,
} from "../style/MypageComponentsCss";
import { MyLink, NavBox, NavTab } from "../style/SideBarCss";

const Wrapper = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  position: relative;
  justify-content: space-between;
`;

const WishListBox = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-row-gap: 40px;
  width: 100%;
  align-items: center;
  justify-items: center;
  margin-bottom: 150px;
`;

const SideBarBox = styled.nav`
  width: 17%;
  height: 100%;
  border-radius: 10px;
  background: var(--color-sidebar);
  box-shadow: 0 0px 10px rgb(0 0 0 / 16%), 0 0px 0px rgb(0 0 0 / 16%);
  display: flex;
  justify-content: flex-start;
  align-items: center;
  flex-direction: column;
  padding: 15px 0;
  box-sizing: border-box;
  margin-top: 80px;
`;

export default function CategoryPage() {
  const { categoryId } = useParams();
  const [isSmallTitle, setUsSmallTitle] = useState(0);

  const SideBar = ({ barList }) => {
    return (
      <SideBarBox>
        <NavBox>
          {barList.map((target) => {
            return (
              <div key={target.id}>
                <Arcodian
                  categoryId={categoryId}
                  isSmallTitle={isSmallTitle}
                  setUsSmallTitle={setUsSmallTitle}
                  target={target}
                />
              </div>
            );
          })}
        </NavBox>
      </SideBarBox>
    );
  };

  const Wishes = ({ dummyWishList }) => {
    return dummyWishList.map((course) => {
      return <ClassCard key={course.courseId} course={course} />;
    });
  };

  const Classes = () => {
    return (
      <MyPageBox>
        <MyPageTitle>
          {CATE_VALUE[categoryId].big}&nbsp;&nbsp;/ &nbsp;
          {CATE_VALUE[categoryId].smallList[isSmallTitle].title}
        </MyPageTitle>
        <MyPageContentBox>
          <WishListBox>
            <Wishes dummyWishList={dummyWishList} />
          </WishListBox>
        </MyPageContentBox>
      </MyPageBox>
    );
  };

  return (
    <Wrapper>
      <SideBar barList={CATE_VALUE} />
      <Classes />
    </Wrapper>
  );
}
