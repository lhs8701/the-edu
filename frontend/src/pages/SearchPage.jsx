import { useInView } from "framer-motion";
import { useEffect, useMemo, useRef } from "react";
import { useInfiniteQuery } from "react-query";
import { useParams } from "react-router";
import styled from "styled-components";
import ClassCard from "../components/ClassCard";
import {
  MyPageBox,
  MyPageContentBox,
  MyPageTitle,
} from "../style/MypageComponentsCss";
import { searchApi } from "../api/courseApi";

const Wrapper = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  position: relative;
  justify-content: space-between;
`;

const SearchPageBox = styled(MyPageBox)`
  width: 100%;
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

const BottomDiv = styled.div`
  width: 100%;
  height: 1px;
`;

export default function SearchPage() {
  const bottomRef = useRef(null);
  const isInView = useInView(bottomRef);
  const { keyword } = useParams();

  const courseList = useInfiniteQuery(
    ["getSearchList", keyword],
    ({ pageParam = 0 }) => {
      return searchApi(pageParam, keyword);
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

  const CoursesBox = () => {
    return (
      <CourseListBox>
        {courseList.isLoading ? (
          <div>로딩중..</div>
        ) : (
          courses?.map((course) => {
            return <ClassCard key={course.courseId} course={course} />;
          })
        )}
        {courses?.length === 0 && <div>검색 결과가 없어요!</div>}
      </CourseListBox>
    );
  };

  const Classes = () => {
    return (
      <SearchPageBox>
        <MyPageTitle>"{keyword}" &nbsp; 검색 결과</MyPageTitle>
        <MyPageContentBox>
          {courseList.error ? <div>에러</div> : <CoursesBox />}
        </MyPageContentBox>
      </SearchPageBox>
    );
  };

  return (
    <>
      <Wrapper>
        <Classes />
      </Wrapper>
      <BottomDiv ref={bottomRef} />
    </>
  );
}
