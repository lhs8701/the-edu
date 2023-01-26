import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { queryClient } from "../..";
import { getMemberIdSelector } from "../../atom";
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
  const wishCourses = queryClient.getQueryData(["wishCourseList", memberId]);

  return (
    <MyPageBox>
      <MyPageTitle>찜한 클래스</MyPageTitle>
      <MyPageContentBox>
        <WishListBox>
          {wishCourses.map((course) => {
            return <ClassCard key={course.courseId} course={course} />;
          })}
        </WishListBox>
      </MyPageContentBox>
    </MyPageBox>
  );
}
