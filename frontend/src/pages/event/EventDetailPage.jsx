import { motion } from "framer-motion";
import { useParams } from "react-router";
import { Link } from "react-router-dom";
import styled from "styled-components";
import { PROCESS_MAIN_URL, STATIC_URL } from "../../static";
import { Wrapper } from "../../style/CommonCss";
import { images } from "../../dummy";
import { useQuery } from "react-query";
import { getDetailEventApi } from "../../api/eventApi";

const EventWrapper = styled(Wrapper)`
  width: 80%;
  margin: 0 auto;
`;

const EventCardBox = styled(motion.div)`
  width: 100%;
  min-height: 500px;
  margin-bottom: 5px;
  margin-top: 50px;
`;

const EventCard = styled.img`
  width: 100%;
  height: 100%;
`;

const EventUploadDate = styled.div`
  color: var(--color-gray);
  font-size: 0.8rem;
  margin-top: 20px;
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
  const { data } = useQuery(
    ["eventInfo", eventId],
    () => {
      return getDetailEventApi(eventId);
    },
    {
      enabled: !!eventId,
      onSuccess: (res) => {},
      onError: () => {
        console.error("에러 발생했지롱");
      },
    }
  );

  const EventDetailComponent = ({ eventInfo }) => {
    console.log(eventInfo);
    return (
      <>
        <EventInfoTab>
          <EventPeriod>D - {eventInfo.dday}</EventPeriod>
          <EventTitle>{eventInfo.title}</EventTitle>
          <EventUploadDate>
            {eventInfo.startDate} ~ {eventInfo.endDate}
          </EventUploadDate>
          <BackLink to={PROCESS_MAIN_URL.EVENT}>목록으로</BackLink>
        </EventInfoTab>
        <BottomLine />
        <EventCardBox>
          <EventCard src={STATIC_URL + eventInfo.bannerImage.mediumFilePath} />
        </EventCardBox>
      </>
    );
  };

  return (
    <EventWrapper>
      {data && <EventDetailComponent eventInfo={data?.data} />}
    </EventWrapper>
  );
}
