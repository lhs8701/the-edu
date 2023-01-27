import { useQuery } from "react-query";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { queryClient } from "../..";
import { wishCourseApi } from "../../api/myPageApi";
import { getAccessTokenSelector, getMemberIdSelector } from "../../atom";
import { dummyWishList } from "../../dummy";
import {
  MyPageBox,
  MyPageContentBox,
  MyPageTitle,
} from "../../style/MypageComponentsCss";
import ClassCard from "../ClassCard";

const WishListBox = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-row-gap: 40px;
  width: 100%;
  align-items: center;
  justify-items: center;
  margin-bottom: 150px;
`;

export default function WishClass() {
  const memberId = useRecoilValue(getMemberIdSelector);
  const accessToken = useRecoilValue(getAccessTokenSelector);

  const wishCourses = useQuery(
    ["wishCourseList", memberId],
    () => {
      return wishCourseApi(memberId, accessToken);
    },
    {
      enabled: !!memberId,
      onSuccess: (res) => {},
      onError: () => {
        console.error("에러 발생했지롱");
      },
    }
  );

  return (
    <MyPageBox>
      <MyPageTitle>찜한 클래스</MyPageTitle>
      <MyPageContentBox>
        <WishListBox>
          {wishCourses?.data?.map((course) => {
            return <ClassCard key={course.courseId} course={course} />;
          })}
          {wishCourses?.data?.length === 0 && <h1>원하는 강의가 없어요</h1>}
        </WishListBox>
      </MyPageContentBox>
    </MyPageBox>
  );
}
