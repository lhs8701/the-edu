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
          <div>이벤트 ID: {eventInfo.id}</div>
          <br />

          <div>이벤트 제목: {eventInfo.title}</div>
          <br />
          <div>작성자: {eventInfo.writer}</div>
          <br />
          <div>내용: {eventInfo.content}</div>
          <EventUploadDate>
            이벤트 날짜: {eventInfo.startDate} ~ {eventInfo.endDate}
          </EventUploadDate>
        </EventInfoTab>
        <EventCardBox>
          사진
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
