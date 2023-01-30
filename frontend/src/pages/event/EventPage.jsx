import { motion } from "framer-motion";
import { useNavigate } from "react-router";
import styled from "styled-components";
import { PROCESS_MAIN_URL } from "../../static";
import { TabTitle, Wrapper } from "../../style/CommonCss";
import { Card } from "../../style/MypageComponentsCss";

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
  const navigate = useNavigate();
  const EventList = () => {
    return [1, 2, 3, 4].map((e, idx) => {
      return (
        <div
          onClick={() => {
            navigate(`${PROCESS_MAIN_URL.EVENT}/${idx}`);
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
              boxShadow:
                "0 0px 0px rgb(0 0 0 / 16%), 0 10px 5px rgb(0 0 0 / 16%)",
            }}
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
