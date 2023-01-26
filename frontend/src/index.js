import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import { ReactQueryDevtools } from "react-query/devtools";
import { RecoilRoot } from "recoil";
import { QueryClient, QueryClientProvider } from "react-query";
import GlobalStyle from "./style/GlobalStyle";
import { RouterProvider } from "react-router";
import router from "./Router";

const root = ReactDOM.createRoot(document.getElementById("root"));
export const queryClient = new QueryClient({
  defaultOptions: {
    mutations: {
      useErrorBoundary: true,
    },
  },
});
root.render(
  <RecoilRoot>
    <QueryClientProvider client={queryClient}>
      <App />
      <ReactQueryDevtools initialIsOpen={false} position="bottom-left" />
    </QueryClientProvider>
  </RecoilRoot>
);
