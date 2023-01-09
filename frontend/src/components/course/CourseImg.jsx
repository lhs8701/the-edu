import styled from "styled-components";

const InfoImg = styled.img`
  width: 100%;
  margin-top: -50px;
  margin-bottom: 60px;
`;

export default function CourseImg({ images }) {
  return (
    <>
      {images.map((img, idx) => {
        return <InfoImg src={img} />;
      })}
    </>
  );
}
