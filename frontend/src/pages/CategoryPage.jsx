import { useCallback, useRef, useState } from "react";
import { useParams } from "react-router";
import { Link } from "react-router-dom";
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
import { NavBox } from "../style/SideBarCss";

const Wrapper = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  position: relative;
  justify-content: space-between;
`;

const CourseListBox = styled.div`
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

const AllLink = styled(Link)`
  width: 100%;
  height: 100%;
  cursor: pointer;
  font-weight: var(--weight-middle);
  display: flex;
  align-items: center;
  font-size: 19px;
  color: ${(props) =>
    props.ison ? "var(--color-primary)" : "var(--color-text)"};
  &:hover {
    color: var(--color-primary);
  }
  padding: 0 20px;
  box-sizing: border-box;
  padding-bottom: 7px;
  margin-top: 5px;
  text-decoration: none;
`;

export default function CategoryPage() {
  const { categoryId, smallCategoryId } = useParams();

  const SideBar = ({ barList }) => {
    return (
      <SideBarBox>
        <NavBox>
          <AllLink to={PROCESS_MAIN_URL.CATEGORIES + "/" + 0 + "/" + 0}>
            전체보기
          </AllLink>
          {barList.map((target) => {
            return (
              <div key={target.id}>
                <Arcodian target={target} />
              </div>
            );
          })}
        </NavBox>
      </SideBarBox>
    );
  };

  const CourseList = ({ dummyWishList }) => {
    return dummyWishList.map((course) => {
      return <ClassCard key={course.courseId} course={course} />;
    });
  };

  const Classes = () => {
    return (
      <MyPageBox>
        <MyPageTitle>
          {Number(categoryId) === 0 && Number(smallCategoryId) === 0 ? (
            <>전체보기</>
          ) : (
            <>
              {CATE_VALUE[categoryId - 1].big}&nbsp;&nbsp;/ &nbsp;
              {CATE_VALUE[categoryId - 1].smallList[smallCategoryId].title}
            </>
          )}
        </MyPageTitle>
        <MyPageContentBox>
          <CourseListBox>
            <CourseList dummyWishList={dummyWishList} />
          </CourseListBox>
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
