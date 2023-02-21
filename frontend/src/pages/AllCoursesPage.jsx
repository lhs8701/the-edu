import { useInView } from "framer-motion";
import { useCallback, useEffect, useMemo, useRef, useState } from "react";
import { useInfiniteQuery } from "react-query";

import { Link } from "react-router-dom";
import styled from "styled-components";
import Arcodian from "../components/Arcodian";
import ClassCard from "../components/ClassCard";
import { CATE_VALUE, PROCESS_MAIN_URL } from "../static";
import {
  MyPageBox,
  MyPageContentBox,
  MyPageTitle,
} from "../style/MypageComponentsCss";
import { NavBox } from "../style/SideBarCss";
import { getAllCoursesApi } from "../api/courseApi";
import { BottomDiv } from "../style/CommonCss";

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

export const SideBar = ({ barList }) => {
  return (
    <SideBarBox>
      <NavBox>
        <AllLink to={PROCESS_MAIN_URL.CATEGORIES}>전체보기</AllLink>
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

export function AllCoursesPage() {
  const bottomRef = useRef(null);
  const isInView = useInView(bottomRef);
  const PAGE_SIZE = 10;
  const courseList = useInfiniteQuery(
    ["getAllCoursesList"],
    ({ pageParam = 0 }) => {
      return getAllCoursesApi(pageParam, PAGE_SIZE);
    },
    {
      retry: 1,
      retryDelay: 5000,
      onSuccess: () => {},
      onError: () => {
        console.error("에러 발생했지롱");
      },
      getNextPageParam: (lastPage, allPages) => {
        if (Number(lastPage.totalPage) === 0) {
          return undefined;
        }
        if (lastPage.totalPage === lastPage.page + 1) {
          return undefined;
        }
        return lastPage.page + 1;
      },
    }
  );

  const courses = useMemo(
    () => courseList?.data?.pages.flatMap((page) => page.list),
    [courseList?.data?.pages]
  );

  useEffect(() => {
    if (isInView && courseList.hasNextPage && courseList.isSuccess) {
      courseList.fetchNextPage();
    }
  }, [isInView, courseList.data]);

  const Classes = () => {
    return (
      <MyPageBox>
        <MyPageTitle>강좌 전체보기</MyPageTitle>
        <MyPageContentBox>
          {courseList?.error ? (
            <div>강좌가 없어요.</div>
          ) : (
            <CourseListBox>
              {courseList?.isLoading ? (
                <div>로딩중..</div>
              ) : (
                courses?.map((course) => {
                  return <ClassCard key={course?.courseId} course={course} />;
                })
              )}
            </CourseListBox>
          )}
        </MyPageContentBox>
      </MyPageBox>
    );
  };

  return (
    <>
      <Wrapper>
        <SideBar barList={CATE_VALUE} />
        <Classes />
      </Wrapper>
      <BottomDiv ref={bottomRef} />
    </>
  );
}
