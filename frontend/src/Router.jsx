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
import EventDetailPage from "./pages/event/EventDetailPage";
import CreatorRoot from "./pages/creator/CreatorRoot";
import ResearchBox from "./components/creator/RegistResearch";
import CreatorRequest from "./components/admin/CreatorRequest";
import Comment from "./components/mypage/Comment";
import CreatorInfo from "./components/creator/CreatorInfo";
import CreatorProfit from "./components/creator/CreatorProfit";
import CoursesComment from "./components/creator/CoursesComment";
import Outline from "./components/creator/uploadCourse/Outline";
import CreatorsCourses from "./components/creator/CreatorsCourses";
import CreatorsRegisted from "./components/creator/CreatorsRegisted";
import CourseProfit from "./components/admin/CourseProfit";
import CategoryProfit from "./components/admin/CategoryProfit";
import Users from "./components/admin/Users";
import Creators from "./components/admin/Creators";
import Courses from "./components/admin/course/Courses";
import Revisecourses from "./components/admin/course/Revisecourses";
import CoursesInquires from "./components/creator/CoursesInquires";
import EventList from "./components/admin/event/EventList";
import DetailEvent from "./components/admin/event/DetailEvent";
import SuccessPurchase from "./components/purchase/SuccessPurchase";
import UploadEvent from "./components/admin/event/UploadEvent";
import AdminCoupon from "./components/admin/coupon/AdminCoupon";
import PrivacyTerms from "./components/PrivacyTemrs";
import CouponList from "./components/admin/coupon/CouponList";
import DetailCoupon from "./components/admin/coupon/DetailCoupon";
import DetailCourseInfo from "./components/admin/course/DetailCourseInfo";
import UnitVideo from "./components/admin/course/UnitVideo";
import CourseDetailSetUp from "./components/creator/CourseDetailSetUp";
import FailPurchase from "./components/purchase/FailPurchase";
import EmailCert from "./components/account/EmailCert";
import { AllCoursesPage } from "./pages/AllCoursesPage";

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
        path: "/privacyterms",
        element: <PrivacyTerms />,
        errorElement: <ErrorComponent />,
      },
      {
        path: "category/:categoryId/:smallCategoryId",
        element: <CategoryPage />,
        errorElement: <ErrorComponent />,
      },
      {
        path: "category",
        element: <AllCoursesPage />,
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
            path: "comment",
            element: <Comment />,
          },
          {
            path: "withdraw",
            element: <Withdraw />,
          },
        ],
      },
      {
        path: "purchase/:courseId/:itemId",
        element: <PurchasePage />,
        children: [
          {
            path: ":point/:couponId/success",
            element: <SuccessPurchase />,
          },
          {
            path: ":point/:couponId/fail",
            element: <FailPurchase />,
          },
        ],
        errorElement: <div>dndn</div>,
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
      { path: "certification", element: <EmailCert /> },
    ],
    errorElement: <ErrorComponent />,
  },
  {
    path: "/player/:courseId/:unitId",
    element: <PlayerRoot />,
    errorElement: <ErrorComponent />,
  },
  {
    path: "/admin",
    element: <AdminRoot />,
    children: [
      {
        path: "categoryprofit",
        element: <CategoryProfit />,
      },
      {
        path: "coursesprofit",
        element: <CourseProfit />,
      },
      {
        path: "profit",
        element: <CourseProfit />,
      },
      {
        path: "users",
        element: <Users />,
      },
      {
        path: "leaveusers",
        element: <Users />,
      },
      {
        path: "creatorsrequest",
        element: <CreatorRequest />,
      },
      {
        path: "creators",
        element: <Creators />,
      },
      {
        path: "revisecourses",
        element: <Revisecourses />,
      },
      {
        path: "revisecourses/:courseId",
        element: <DetailCourseInfo />,
      },
      {
        path: "revisecourses/:courseId/:unitId",
        element: <UnitVideo />,
      },
      {
        path: "courses",
        element: <Courses />,
      },
      {
        path: "eventnotice",
        element: <EventList />,
      },
      {
        path: "eventnotice/detailEvent/:eventId",
        element: <DetailEvent />,
      },
      {
        path: "uploadevent",
        element: <UploadEvent />,
      },
      {
        path: "uploadnotice",
        element: <Courses />,
      },
      {
        path: "coupon",
        element: <AdminCoupon />,
      },
      {
        path: "coupon/:couponId",
        element: <DetailCoupon />,
      },
      {
        path: "couponlist",
        element: <CouponList />,
      },
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
        element: <CreatorInfo />,
      },
      {
        path: "profit",
        element: <CreatorProfit />,
      },
      {
        path: "comment",
        element: <CoursesComment />,
      },
      {
        path: "mycourses",
        element: <CreatorsCourses />,
      },
      {
        path: "mycourses/:courseId",
        element: <CourseDetailSetUp />,
      },
      {
        path: "mycourses/:courseId/:unitId",
        element: <UnitVideo />,
      },
      {
        path: "registcourses",
        element: <CreatorsRegisted />,
      },
      {
        path: "inputcourses",
        element: <Outline />,
      },
      {
        path: "inquirecourses",
        element: <CoursesInquires />,
      },
    ],
  },
]);

export default router;
