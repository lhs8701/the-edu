import { createBrowserRouter } from "react-router-dom";
import Root from "./Root";

import ErrorComponent from "./components/ErrComponents";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Root />,
    // children: [
    //   {
    //     path: "",
    //     element: <Home />,
    //     errorElement: <ErrorComponent />,
    //   },
    //   {
    //     path: "about",
    //     element: <About />,
    //   },
    //   {
    //     path: "users/:userId",
    //     element: <User />,
    //     children: [
    //       {
    //         path: "followers",
    //         element: <Followers />,
    //       },
    //     ],
    //   },
    // ],
    errorElement: <ErrorComponent />,
  },
]);

export default router;
