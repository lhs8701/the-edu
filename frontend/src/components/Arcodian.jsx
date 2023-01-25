import React, { useCallback, useRef, useState } from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import { PROCESS_MAIN_URL } from "../static";
import { MyLink, NavTab } from "../style/SideBarCss";

const OpenSmallCategory = styled.div`
  width: 100%;
  height: 100%;
  cursor: pointer;
  font-weight: var(--weight-middle);
  display: flex;
  align-items: center;
  font-size: 19px;
  color: ${(props) =>
    props.ison ? "var(--color-primary)" : "var(--color-text)"};
  &:hover {
    color: var(--color-primary);
  }
  padding: 0 20px;
  box-sizing: border-box;
  padding-bottom: 7px;
  /* border-bottom: 1px solid rgba(0, 0, 0, 0.2); */
`;

const SmallCateBox = styled.div`
  width: 100%;
  height: 0;
  overflow: hidden;
  transition: height 0.35s ease, background 0.35s ease;
  /* display: ${(props) => (props.ison ? "flex" : "none")}; */
`;

const ContentsWrapper = styled.div`
  width: 100%;
`;

const ArcodianNavTab = styled(NavTab)`
  height: 100%;
  margin: 20px 0;
`;

const SmallCategory = styled(MyLink)`
  padding: 10px 30px;
  font-size: 16px;
  font-weight: var(--weight-thin);
  color: var(--weight-gray);
  &:hover {
    font-weight: var(--weight-middle);
    color: var(--weight-text);
  }
  cursor: pointer;
`;

function Accordion({ target }) {
  const parentRef = useRef(null);
  const childRef = useRef(null);
  const [isCollapse, setIsCollapse] = useState(false);
  const nowId = target.id;
  const handleButtonClick = useCallback(() => {
    if (parentRef.current === null || childRef.current === null) {
      return;
    }
    if (parentRef.current.clientHeight > 0) {
      parentRef.current.style.height = "0";
    } else {
      parentRef.current.style.height = `${childRef.current.clientHeight}px`;
    }
    setIsCollapse(!isCollapse);
  }, [isCollapse]);

  const SmallCategories = ({ target }) => {
    return (
      <SmallCategory
        to={PROCESS_MAIN_URL.CATEGORIES + "/" + nowId + "/" + target.id}
      >
        &nbsp;&nbsp;{target.title}
      </SmallCategory>
    );
  };

  return (
    <ArcodianNavTab key={target.id}>
      <OpenSmallCategory
        ison={isCollapse}
        onClick={() => {
          handleButtonClick();
        }}
      >
        {target.big}
      </OpenSmallCategory>
      <SmallCateBox ref={parentRef} ison={isCollapse}>
        <ContentsWrapper ref={childRef}>
          {target.smallList?.map((small) => {
            return (
              <div key={small.id}>
                <SmallCategories target={small} />
              </div>
            );
          })}
        </ContentsWrapper>
      </SmallCateBox>
    </ArcodianNavTab>
  );
}

export default React.memo(Accordion);
