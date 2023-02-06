import styled from "styled-components";
import { STATIC_URL } from "../../static";

const InfoImg = styled.img`
  width: 100%;
`;

export default function CourseImg({ images }) {
  return (
    <>
      {images?.map((img, idx) => {
        return <InfoImg key={idx} src={STATIC_URL + img.originalFilePath} />;
      })}
    </>
  );
}
