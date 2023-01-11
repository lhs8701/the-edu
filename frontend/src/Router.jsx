import { createBrowserRouter } from "react-router-dom";
import Root from "./Root";
import ErrorComponent from "./components/ErrComponents";
import MainPage from "./pages/MainPage";
import SignIn from "./components/account/SignIn";
import SignUp from "./components/account/SignUp";
import MyPage from "./pages/MyPage";
import WishClass from "./components/mypage/WishClass";
import Coupon from "./components/mypage/Coupon";
import Withdraw from "./components/mypage/Withdraw";
import CoursePage from "./pages/CoursePage";
import LobbyPage from "./pages/LobbyPage";
import PurchasePage from "./pages/PurchasePage";
import RoadMapPage from "./pages/RoadMapPage";
import EventPage from "./pages/EventPage";
import SearchPage from "./pages/SearchPage";
import NotFoundPage from "./pages/NotFoundPage";
import AccountRoot from "./pages/AccountRoot";
import CategoryPage from "./pages/CategoryPage";
import Revise from "./components/mypage/Revise";
import MyClass from "./components/mypage/MyClass";
import PurchaseHistory from "./components/mypage/PurchaseHistory";

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
        path: "category/:categoryId",
        element: <CategoryPage />,
        errorElement: <ErrorComponent />,
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
            path: "",
            element: <MyClass />,
          },
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
            element: <PurchaseHistory />,
          },
          {
            path: "revise",
            element: <Revise />,
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
  {
    path: "/account",
    element: <AccountRoot />,
    children: [
      {
        path: "login",
        element: <SignIn />,
      },
      {
        path: "signup",
        element: <SignUp />,
      },
    ],
    errorElement: <NotFoundPage />,
  },
]);

export default router;
