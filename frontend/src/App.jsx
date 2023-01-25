import React, { Suspense } from "react";
import ReactDOM from "react-dom/client";
import { ReactQueryDevtools } from "react-query/devtools";
import { RecoilRoot } from "recoil";
import { QueryClient, QueryClientProvider } from "react-query";
import GlobalStyle from "./style/GlobalStyle";
import { RouterProvider } from "react-router";
import router from "./Router";

export const queryClient = new QueryClient({
  defaultOptions: {
    mutations: {
      useErrorBoundary: true,
    },
  },
});
function App() {
  return (
    <RecoilRoot>
      <QueryClientProvider client={queryClient}>
        <GlobalStyle />
        <RouterProvider router={router} />
        <ReactQueryDevtools initialIsOpen={false} position="bottom-left" />
      </QueryClientProvider>
    </RecoilRoot>
  );
}

export default App;
