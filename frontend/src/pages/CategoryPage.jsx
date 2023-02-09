import { useInView } from "framer-motion";
import { useCallback, useEffect, useMemo, useRef, useState } from "react";
import { useInfiniteQuery } from "react-query";
import { useParams } from "react-router";
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
import { getCategoryListApi } from "../api/courseApi";

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

const BottomDiv = styled.div`
  width: 100%;
  height: 1px;
`;

export default function CategoryPage() {
  const { categoryId, smallCategoryId } = useParams();
  const bottomRef = useRef(null);
  const isInView = useInView(bottomRef);

  const courseList = useInfiniteQuery(
    [
      "getCategoryList",
      CATE_VALUE[categoryId - 1].smallList[smallCategoryId - 1].title, // 전체보기가 스몰리스트에서 빠졌기때문에 -1을 추가
    ],
    ({ pageParam = 0 }) => {
      return getCategoryListApi(
        pageParam,
        CATE_VALUE[categoryId - 1].smallList[smallCategoryId - 1].title
      );
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

  const Classes = () => {
    console.log(CATE_VALUE[categoryId - 1].smallList[smallCategoryId - 1]);
    return (
      <MyPageBox>
        <MyPageTitle>
          {/* {Number(categoryId) === 0 && Number(smallCategoryId) === 0 ? (
            <>전체보기</>
          ) : (
            <>
              {CATE_VALUE[categoryId - 1].big}&nbsp;&nbsp;/ &nbsp;
              {CATE_VALUE[categoryId - 1].smallList[smallCategoryId].title}
            </> 전체보기를 뺀다면 스몰리스트 인덱스 -1필요
          )} */}
          {CATE_VALUE[categoryId - 1].big}&nbsp;&nbsp;/ &nbsp;
          {CATE_VALUE[categoryId - 1].smallList[smallCategoryId - 1]?.title}
        </MyPageTitle>
        <MyPageContentBox>
          {courseList.error ? (
            <div>강좌가 없어요.</div>
          ) : (
            <CourseListBox>
              {courseList.isLoading ? (
                <div>로딩중..</div>
              ) : (
                courses?.map((course) => {
                  return <ClassCard key={course.courseId} course={course} />;
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
