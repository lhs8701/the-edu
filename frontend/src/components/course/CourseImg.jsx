import styled from "styled-components";

const InfoImg = styled.img`
  width: 100%;
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
