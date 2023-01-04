import { createBrowserRouter } from "react-router-dom";
import Root from "./Root";

import ErrorComponent from "./components/ErrComponents";
import MainPage from "./pages/mainPage";
import SignIn from "./pages/account/signInPage";
import SignUp from "./pages/account/signUpPage";
import MyPage from "./pages/myPage";
import WishClass from "./components/mypage/wishClass";
import Coupon from "./components/mypage/coupon";
import Deal from "./components/mypage/deal";
import Withdraw from "./components/mypage/withdraw";
import CoursePage from "./pages/coursePage";
import LobbyPage from "./pages/lobbyPage";
import PurchasePage from "./pages/purchasePage";
import RoadMapPage from "./pages/roadMapPage";
import EventPage from "./pages/eventPage";
import SearchPage from "./pages/searchPage";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Root />,
    children: [
      {
        path: "",
        element: <MainPage />,
        errorElement: <ErrorComponent />,
      },
      {
        path: "login",
        element: <SignIn />,
      },
      {
        path: "signup",
        element: <SignUp />,
      },
      {
        path: "course/:courseId",
        element: <CoursePage />,
      },
      {
        path: "course/:courseId",
        element: <CoursePage />,
      },
      {
        path: "course/:courseId/lobby",
        element: <LobbyPage />,
      },
      {
        path: "my",
        element: <MyPage />,
        children: [
          {
            path: "wish",
            element: <WishClass />,
          },
          {
            path: "coupon",
            element: <Coupon />,
          },
          {
            path: "deal",
            element: <Deal />,
          },
          {
            path: "withdraw",
            element: <Withdraw />,
          },
        ],
      },
      {
        path: "purchase",
        element: <PurchasePage />,
      },
      {
        path: "roadmap",
        element: <RoadMapPage />,
      },
      {
        path: "event",
        element: <EventPage />,
      },
      {
        path: "search",
        element: <SearchPage />,
      },
    ],
    errorElement: <ErrorComponent />,
  },
]);

export default router;
