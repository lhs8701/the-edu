import { useQuery } from "react-query";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { queryClient } from "../..";
import { wishCourseApi } from "../../api/myPageApi";
import { getAccessTokenSelector, getMemberIdSelector } from "../../atom";
import { dummyWishList } from "../../dummy";
import { AlertP, CenterDiv } from "../../style/CommonCss";
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
      return wishCourseApi(accessToken);
    },
    {
      enabled: !!memberId,
      onSuccess: (res) => {},
      onError: () => {
        console.error("에러 발생했지롱");
      },
    }
  );

  console.log(wishCourses?.data);

  return (
    <MyPageBox>
      <MyPageTitle>찜한 클래스</MyPageTitle>
      <MyPageContentBox>
        <WishListBox>
          {wishCourses?.data?.map((course) => {
            return <ClassCard key={course.courseId} course={course} />;
          })}
        </WishListBox>
        {wishCourses?.data?.length === 0 && (
          <CenterDiv>
            <AlertP>구매가 없어요.</AlertP>
          </CenterDiv>
        )}
      </MyPageContentBox>
    </MyPageBox>
  );
}
