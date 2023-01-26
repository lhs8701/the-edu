import React, { Suspense } from "react";

import GlobalStyle from "./style/GlobalStyle";
import { RouterProvider } from "react-router";
import router from "./Router";

function App() {
  return (
    <>
      <GlobalStyle />
      <RouterProvider router={router} />
    </>
  );
}

export default App;
