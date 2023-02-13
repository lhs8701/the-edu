import { useQuery } from "react-query";
import { useParams } from "react-router";
import styled from "styled-components";
import { getDetailEventApi } from "../../../api/eventApi";
import { STATIC_URL } from "../../../static";
import { Wrapper } from "../../../style/CommonCss";
import DashboardTitleTab from "../../dashboard/DashboardTitleTab";

const EventCardBox = styled.div`
  width: 500px;
  min-height: 500px;
  margin-bottom: 5px;
  margin-top: 30px;
`;

const EventCard = styled.img`
  width: 100%;
  height: 100%;
`;

const EventUploadDate = styled.div`
  color: var(--color-gray);
  font-size: 0.9rem;
  margin-top: 20px;
`;

const BoldTab = styled.span`
  font-weight: var(--weight-point);
  color: var(--color-text);
`;

const EventInfoTab = styled.div`
  margin-top: 4rem;
  margin-bottom: 20px;
`;

export default function DetailEvent() {
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
          <div>
            <BoldTab>이벤트 ID : </BoldTab>
            {eventInfo.id}
          </div>
          <br />

          <div>
            <BoldTab>이벤트 제목 : </BoldTab> {eventInfo.title}
          </div>
          <br />
          <div>
            <BoldTab>작성자 : </BoldTab> {eventInfo.writer}
          </div>
          <br />
          <div>
            <BoldTab>내용 : </BoldTab>
            {eventInfo.content}
          </div>
          <EventUploadDate>
            <BoldTab>이벤트 날짜 : </BoldTab>
            {eventInfo.startDate} ~ {eventInfo.endDate}
          </EventUploadDate>
        </EventInfoTab>
        <EventCardBox>
          <BoldTab>사진</BoldTab>
          <EventCard
            src={STATIC_URL + eventInfo.bannerImage.originalFilePath}
          />
        </EventCardBox>
      </>
    );
  };

  return (
    <div>
      <DashboardTitleTab title="상세 이벤트" />
      {data && <EventDetailComponent eventInfo={data?.data} />}
    </div>
  );
}
