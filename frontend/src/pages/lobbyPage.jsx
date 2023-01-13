import { useParams } from "react-router";
import styled from "styled-components";
import { dummyCourseProgressData } from "../dummy";

const LobbyWrapper = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
`;

const InfoSection = styled.div`
  position: relative;
  width: 31%;
  height: 100%;
`;

const CategorySection = styled.div`
  width: 69%;
`;

const ImgBox = styled.div`
  margin-top: 40px;
  width: 100%;
  height: 200px;
  overflow: hidden;
  border-radius: var(--size-border-radius);
`;

const Img = styled.img`
  width: 100%;
  height: 100%;
`;

const InfoBox = styled.div`
  box-shadow: 0 1px 1px rgb(0 0 0 / 16%), 0 2px 14px rgb(0 0 0 / 16%);
  position: absolute;
  width: 87%;
  height: 48%;
  background-color: #fffbfb;
  bottom: -18%;
  left: 6.5%;
  border-radius: var(--size-border-radius);
  padding: 15px 20px;
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-direction: column;
`;

const Tab = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
`;

const InfoTab = styled(Tab)`
  justify-content: space-between;
`;
const CourseTitle = styled.p`
  font-weight: var(--weight-middle);
  font-size: 18px;
`;
const CourseRate = styled.p``;

const CategoryBox = styled.div`
  width: 85%;
  margin: 0 auto;
  margin-bottom: 15px;
`;

const BigCategoryTab = styled.div`
  width: 100%;
  background-color: var(--color-box-primary);
  font-weight: var(--weight-point);
  font-size: 1.5rem;
  padding: 10px 20px;
  box-sizing: border-box;
  box-shadow: 0 0 3px rgb(0 0 0 / 16%), 0 0px 1px rgb(0 0 0 / 16%);
`;

const SmallCategoryTab = styled.div`
  width: 100%;
  background-color: var(--color-course-background);
  font-weight: var(--weight-middle);
  font-size: 1.1rem;
  padding: 10px 20px;
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: space-between;
  &:hover {
    background-color: #dcdcdc;
  }
`;

const PlayBtn = styled.button``;

export default function LobbyPage() {
  const data = dummyCourseProgressData;

  const playUnit = (link) => {
    window.open(
      "http://localhost:3000/player",
      "the-edu 플레이어",
      "location=no,status=no,scrollbars=no"
    );
  };

  const InFo = () => {
    return (
      <>
        <ImgBox>
          <Img src={data?.img} />
        </ImgBox>
        <InfoBox>
          <InfoTab>
            <CourseTitle>{data?.title}</CourseTitle>
            <CourseRate>{data?.rate}</CourseRate>
          </InfoTab>
          <Tab>프로그래스바</Tab>
          <Tab>{data?.Percent}%</Tab>
        </InfoBox>
      </>
    );
  };

  const Categories = ({ unitList }) => {
    return unitList.map((category, idx) => {
      return <Category category={category} idx={idx} />;
    });
  };

  const Category = ({ category, idx }) => {
    return (
      <CategoryBox>
        <BigCategoryTab>
          {idx + 1}. &nbsp;{category?.big}
        </BigCategoryTab>
        <SmallCategories smallList={category?.small} />
      </CategoryBox>
    );
  };

  const SmallCategories = ({ smallList }) => {
    return smallList.map((small, idx) => {
      return (
        <SmallCategoryTab>
          &nbsp;&nbsp;&nbsp;{idx + 1}. &nbsp;
          {small}
          <PlayBtn onClick={() => playUnit()}>재생</PlayBtn>
        </SmallCategoryTab>
      );
    });
  };

  return (
    <LobbyWrapper>
      <InfoSection>
        <InFo />
      </InfoSection>
      <CategorySection>
        <br />
        <br />
        <br />
        <br />
        <br />
        <Categories unitList={data?.list} />
      </CategorySection>
    </LobbyWrapper>
  );
}
