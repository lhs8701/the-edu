import { createBrowserRouter } from "react-router-dom";
import Root from "./Root";

import ErrorComponent from "./components/ErrComponents";
import MainPage from "./pages/MainPage";
import SignIn from "./pages/account/SignInPage";
import SignUp from "./pages/account/SignUpPage";
import MyPage from "./pages/MyPage";
import WishClass from "./components/mypage/WishClass";
import Coupon from "./components/mypage/Coupon";
import Deal from "./components/mypage/Deal";
import Withdraw from "./components/mypage/Withdraw";
import CoursePage from "./pages/CoursePage";
import LobbyPage from "./pages/LobbyPage";
import PurchasePage from "./pages/PurchasePage";
import RoadMapPage from "./pages/RoadMapPage";
import EventPage from "./pages/EventPage";
import SearchPage from "./pages/SearchPage";
import NotFound from "./pages/NotFoundPage";
import NotFoundPage from "./pages/NotFoundPage";

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
        errorElement: <ErrorComponent />,
      },
      {
        path: "signup",
        element: <SignUp />,
        errorElement: <ErrorComponent />,
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
    errorElement: <NotFoundPage />,
  },
]);

export default router;
