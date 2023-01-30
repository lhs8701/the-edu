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
import EventPage from "./pages/event/EventPage";
import SearchPage from "./pages/SearchPage";
import NotFoundPage from "./pages/NotFoundPage";
import AccountRoot from "./pages/AccountRoot";
import CategoryPage from "./pages/CategoryPage";
import Revise from "./components/mypage/Revise";
import MyClass from "./components/mypage/MyClass";
import PurchaseHistory from "./components/mypage/PurchaseHistory";
import FindAccount from "./components/account/FindAccount";
import FindPassword from "./components/account/FindPassword";
import KaKaoAuth from "./components/account/KakaoAuth";
import PlayerRoot from "./pages/PlayerRoot";
import MypageErrComponent from "./components/mypage/MypageErrComponent";
import AdminRoot from "./pages/admin/AdminRoot";

import AdminLogin from "./components/admin/AdminLogin";
import EventDetailPage from "./pages/event/EventDetailPage";
import CreatorRoot from "./pages/creator/CreatorRoot";
import ResearchBox from "./components/creator/RegistResearch";
import Board from "./pages/creator/Board";
import Info from "./pages/creator/Info";
import AdminDashboard from "./components/admin/AdminDashboard";
import CreatorRequest from "./components/admin/CreatorRequest";

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
        path: "category/:categoryId/:smallCategoryId",
        element: <CategoryPage />,
        errorElement: <ErrorComponent />,
      },
      {
        path: "course/:courseId",
        element: <CoursePage />,
        errorElement: <ErrorComponent />,
      },
      {
        path: "course/:courseId/lobby",
        element: <LobbyPage />,
      },
      {
        path: "my",
        element: <MyPage />,
        errorElement: <MypageErrComponent />,
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
        path: "purchase/:courseId",
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
        path: "event/:eventId",
        element: <EventDetailPage />,
      },
      {
        path: "search/:keyword",
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
      {
        path: "id",
        element: <FindAccount />,
      },
      {
        path: "password",
        element: <FindPassword />,
      },
      { path: "kauth", element: <KaKaoAuth /> },
    ],
    errorElement: <ErrorComponent />,
  },
  {
    path: "/player",
    element: <PlayerRoot />,
    errorElement: <ErrorComponent />,
  },
  {
    path: "/admin",
    element: <AdminRoot />,
    children: [
      {
        path: "dashboard",
        element: <div>우</div>,
      },
      {
        path: "courses",
        element: <div>우</div>,
      },
      {
        path: "users",
        element: <div>우</div>,
      },
      {
        path: "profit",
        element: <div>우</div>,
      },
      {
        path: "creators",
        element: <CreatorRequest />,
      },
      { path: "dashboard", element: <AdminDashboard /> },
    ],
  },
  {
    path: "/creator",
    element: <CreatorRoot />,
    children: [
      {
        path: "register",
        element: <ResearchBox />,
      },
      {
        path: "info",
        element: <Info />,
      },
      {
        path: "courses",
        element: <Board />,
      },
      {
        path: "profit",
        element: <Board />,
      },
      {
        path: "comment",
        element: <Board />,
      },
      { path: "dashboard", element: <Board /> },
    ],
  },
]);

export default router;
