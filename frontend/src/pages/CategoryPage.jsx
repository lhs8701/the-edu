import { useParams } from "react-router";
import styled from "styled-components";
import ClassCard from "../components/ClassCard";
import SideBar from "../components/SideBar";
import { dummyWishList } from "../dummy";
import { CATEGORY_LIST, CATE_VALUE } from "../static";
import {
  MyPageBox,
  MyPageContentBox,
  MyPageTitle,
} from "../style/MypageComponentsCss";

const MyPageWrapper = styled.div`
  width: 100%;
  min-height: 90vh;
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

export default function CategoryPage() {
  const { categoryId } = useParams();

  const Wishes = ({ dummyWishList }) => {
    return dummyWishList.map((course) => {
      return <ClassCard key={course.courseId} course={course} />;
    });
  };

  const Classes = () => {
    return (
      <MyPageBox>
        <MyPageTitle>{CATE_VALUE[categoryId]}</MyPageTitle>
        <MyPageContentBox>
          <WishListBox>
            <Wishes dummyWishList={dummyWishList} />
          </WishListBox>
        </MyPageContentBox>
      </MyPageBox>
    );
  };

  return (
    <MyPageWrapper>
      <SideBar barList={CATEGORY_LIST} />
      <Classes />
    </MyPageWrapper>
  );
}
