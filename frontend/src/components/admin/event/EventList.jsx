import { useEffect, useState } from "react";
import { useQuery } from "react-query";
import { useNavigate } from "react-router";
import { useRecoilValue } from "recoil";
import {
  deleteEventApi,
  getClosedEventApi,
  getDetailEventApi,
  getOngoingEventApi,
} from "../../../api/eventApi";
import { getAdminAccessTokenSelector } from "../../../atom";
import { ADMIN_BAR_LIST } from "../../../static";
import DashboardTitleTab from "../../dashboard/DashboardTitleTab";
import { EventTable } from "../../BasicTable";

export default function EventList() {
  const [eventList, setEventList] = useState();
  const adminAccessToken = useRecoilValue(getAdminAccessTokenSelector);
  const navigate = useNavigate();

  const eventListTableCells = [
    { name: "id", id: 0 },
    { name: "제목", id: 1 },
    { name: "시작날짜", id: 2 },
    { name: "종료날짜", id: 3 },
    { name: "", id: 4 }, //삭제버튼을 위한 필드
  ];

  const onGoingList = useQuery(
    ["ongoinEventList"],
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

  const closedList = useQuery(
    ["closdeEventList"],
    () => {
      return getClosedEventApi();
    },
    {
      onSuccess: (res) => {},
      onError: () => {
        console.error("에러 발생했지롱");
      },
    }
  );

  const eventListFilter = (eventList) => {
    const filterList = eventList.filter((event) => {
      return {
        title: event.title,
        startDate: event.startDate,
        endDate: event.endDate,
        id: event.id,
      };
    });

    return filterList;
  };

  const refetchList = () => {
    onGoingList.refetch();
    closedList.refetch();
  };

  const makeEventList = () => {
    if (onGoingList.isSuccess && closedList.isSuccess) {
      setEventList(
        eventListFilter([
          ...closedList.data.data,
          ...onGoingList.data.data,
        ]).sort((a, b) => {
          return a.id - b.id;
        })
      );
    }
  };

  const deleteEvent = (eventId) => {
    deleteEventApi(eventId, adminAccessToken)
      .then(() => {
        refetchList();
      })
      .catch((err) => {
        alert(err);
      });
  };

  useEffect(makeEventList, [onGoingList?.data?.data, closedList?.data?.data]);
  return (
    <div>
      <DashboardTitleTab title={ADMIN_BAR_LIST.list[4].list[0].name} />
      <EventTable
        cells={eventListTableCells}
        rows={eventList}
        deleteFun={deleteEvent}
        navigate={navigate}
      />
    </div>
  );
}
