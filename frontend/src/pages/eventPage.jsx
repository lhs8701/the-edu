import { motion } from "framer-motion";
import styled from "styled-components";
import { TabTitle, Wrapper } from "../style/CommonCss";
import { Card } from "../style/MypageComponentsCss";

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

const EventDescripton = styled.text``;

const EventPeriod = styled.text`
  color: red;
`;

export default function EventPage() {
  const EventList = () => {
    return [1, 2, 3, 4].map(() => {
      return (
        <div>
          <EventCardBox
            initial={{ opacity: 0.2, y: 10 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.5 }}
            whileHover={{ y: -10, transition: { duration: 0.2 } }}
            whileTap={{ y: 0, transition: { duration: 0.01 } }}
          >
            <EventCard src="https://d33wubrfki0l68.cloudfront.net/594de66469079c21fc54c14db0591305a1198dd6/3f4b1/static/images/wallpapers/bridge-01@2x.png" />
          </EventCardBox>
          <div>
            <EventPeriod>D-13</EventPeriod>

            <br />
            <EventDescripton>이벤트 설명</EventDescripton>
          </div>
        </div>
      );
    });
  };

  return (
    <Wrapper>
      <TabTitle>이벤트</TabTitle>
      <EventBox>
        <EventList />
      </EventBox>
    </Wrapper>
  );
}
