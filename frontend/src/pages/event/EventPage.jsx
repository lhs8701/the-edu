import { motion } from "framer-motion";
import { useQuery } from "react-query";
import { useNavigate } from "react-router";
import styled from "styled-components";
import { getOngoingEventApi } from "../../api/eventApi";
import { PROCESS_MAIN_URL, STATIC_URL } from "../../static";
import { TabTitle, Wrapper } from "../../style/CommonCss";

const EventCardBox = styled(motion.div)`
  width: 100%;
  height: 350px;
  margin-bottom: 10px;
  border-radius: var(--size-border-radius);
  overflow: hidden;
  cursor: pointer;
`;

const EventCard = styled.img`
  width: 100%;
  height: 100%;
`;

const EventBox = styled.div`
  width: 100%;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-column-gap: 40px;
  grid-row-gap: 60px;
`;

const EventDescripton = styled.div``;

const EventPeriod = styled.div`
  color: red;
  margin-bottom: 4px;
`;

export default function EventPage() {
  const navigate = useNavigate();
  const { data } = useQuery(
    ["eventList"],
    () => {
      return getOngoingEventApi();
    },
    {
      onSuccess: (res) => {},
      onError: () => {
        console.error("에러 발생했지롱");
      },
    }
  );

  const EventComponent = ({ eventInfo }) => {
    return (
      <div
        onClick={() => {
          navigate(`${PROCESS_MAIN_URL.EVENT}/${eventInfo.id}`);
        }}
      >
        <EventCardBox
          initial={{ opacity: 0, y: 30 }}
          animate={{
            opacity: 1,
            y: 0,
          }}
          transition={{ duration: 0.3 }}
          whileHover={{
            y: -10,
            transition: { duration: 0.2 },
          }}
          whileTap={{ y: 0, transition: { duration: 0.01 } }}
        >
          <EventCard src={STATIC_URL + eventInfo.bannerImage.mediumFilePath} />
        </EventCardBox>
        <div>
          <EventPeriod>D - {eventInfo.dday}</EventPeriod>
          <EventDescripton>{eventInfo.title}</EventDescripton>
        </div>
      </div>
    );
  };

  const EventList = ({ eventList }) => {
    console.log(eventList);
    return eventList.map((event) => {
      return <EventComponent eventInfo={event} key={event.id} />;
    });
  };

  return (
    <Wrapper>
      <TabTitle>진행 중인 이벤트</TabTitle>
      <EventBox>{data && <EventList eventList={data?.data} />}</EventBox>
    </Wrapper>
  );
}
