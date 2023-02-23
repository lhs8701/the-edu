import {
  StatusDisplayUnderBar,
  StatusNavBar,
  StatusNavBox,
  StatusNavTab,
} from "../../style/StatusNavBarCss";

const TAB_STATUS = [
  {
    id: 0,
    title: "전체 보기",
  },
  {
    id: 1,
    title: "덜 봄",
  },
  {
    id: 2,
    title: "다 봄",
  },
];
export default function MyClassNavBar({ isTabStatus, setIsTabStatus }) {
  return (
    <StatusNavBar>
      <StatusNavBox>
        {TAB_STATUS.map((tab) => {
          return (
            <StatusNavTab
              key={tab.id}
              onClick={() => {
                setIsTabStatus(tab.id);
              }}
              ison={[isTabStatus, tab.id]}
            >
              {tab.title}
              {isTabStatus === tab?.id && (
                <StatusDisplayUnderBar layoutId="myClass" />
              )}
            </StatusNavTab>
          );
        })}
      </StatusNavBox>
    </StatusNavBar>
  );
}
