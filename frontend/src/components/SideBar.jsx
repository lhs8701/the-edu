import { Link, useMatch, useParams } from "react-router-dom";
import styled from "styled-components";
import { PROCESS_MAIN_URL } from "../static";

const SideBarBox = styled.nav`
  width: 17%;
  height: 270px;
  border-radius: 10px;
  top: 200px;
  position: sticky;
  position: -webkit-sticky;
  background: var(--color-sidebar);
  box-shadow: 0 1px 1px rgb(0 0 0 / 16%), 0 2px 10px rgb(0 0 0 / 16%);
  display: flex;
  justify-content: flex-start;
  align-items: center;
  flex-direction: column;
  padding: 15px 0;
`;

const MyLink = styled(Link)`
  text-decoration: none;
  height: 100%;
  display: flex;
  align-items: center;
  font-size: 19px;
  font-weight: var(--weight-middle);
  color: ${(props) =>
    props.mouse ? "var(--color-primary)" : "var(--color-text)"};
  &:hover {
    color: var(--color-primary);
  }
`;
const NavBox = styled.ul``;
const NavTab = styled.li`
  height: 45px;
  display: flex;
  align-items: center;
  justify-content: center;
`;

export default function SideBar({ barList }) {
  return (
    <SideBarBox>
      <NavBox>
        {barList.isCategory
          ? barList.list.map((target, idx) => {
              return (
                <NavTab>
                  <MyLink
                    valid={[idx]}
                    to={PROCESS_MAIN_URL.CATEGORIES + "/" + idx}
                  >
                    {target}
                  </MyLink>
                </NavTab>
              );
            })
          : barList.list.map((target, idx) => {
              return (
                <NavTab>
                  <MyLink to={target.url}>{target.name}</MyLink>
                </NavTab>
              );
            })}
      </NavBox>
    </SideBarBox>
  );
}
