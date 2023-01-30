import { motion } from "framer-motion";
import { useParams } from "react-router";
import { Link } from "react-router-dom";
import styled from "styled-components";
import { PROCESS_MAIN_URL } from "../../static";
import { Wrapper } from "../../style/CommonCss";
import { images } from "../../dummy";

const EventWrapper = styled(Wrapper)`
  width: 80%;
  margin: 0 auto;
`;

const EventCardBox = styled(motion.div)`
  width: 100%;
  min-height: 500px;
  margin-bottom: 5px;
`;

const EventCard = styled.img`
  width: 100%;
  height: 100%;
`;

const EventUploadDate = styled.span`
  color: var(--color-gray);
  font-size: 0.8rem;
`;

const EventPeriod = styled(EventUploadDate)`
  color: red;
`;

const EventInfoTab = styled.div`
  margin-top: 4rem;
  margin-bottom: 20px;
  position: relative;
`;

const EventTitle = styled.h1`
  font-weight: var(--weight-middle);
  font-size: var(--size-mypage-title);
  text-align: center;
`;

const BottomLine = styled.div`
  width: 100%;
  height: 1px;
  background-color: var(--color-box-gray);
  margin-bottom: 30px;
`;

const BackLink = styled(Link)`
  text-decoration: none;
  color: var(--color-gray);
  right: 0;
  position: absolute;
  top: 0;
  background-color: transparent;
  padding: 7px;
  border: 1px solid var(--color-box-gray);
`;

export default function EventDetailPage() {
  const { eventId } = useParams();

  const EventList = () => {
    return images.map((img) => {
      return (
        <EventCardBox>
          <EventCard src={img} />
        </EventCardBox>
      );
    });
  };
  console.log(images);
  return (
    <EventWrapper>
      <EventInfoTab>
        <EventPeriod>남은 날짜</EventPeriod>
        <EventTitle>이벤트 제목{eventId}</EventTitle>
        <EventUploadDate>2022.01.13</EventUploadDate>
        <BackLink to={PROCESS_MAIN_URL.EVENT}>목록으로</BackLink>
      </EventInfoTab>
      <BottomLine />
      <EventList />
    </EventWrapper>
  );
}
