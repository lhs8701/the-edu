import React, { Suspense } from "react";
import ReactDOM from "react-dom/client";

import App from "./App.jsx";
import { RecoilRoot } from "recoil";
import { QueryClient, QueryClientProvider } from "react-query";
import GlobalStyle from "./style/GlobalStyle";

const root = ReactDOM.createRoot(document.getElementById("root"));
const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: 0,
      suspense: true,
    },
    mutations: {
      useErrorBoundary: true,
    },
  },
});
root.render(
  <RecoilRoot>
    <QueryClientProvider client={queryClient}>
      <GlobalStyle />
      <App />
    </QueryClientProvider>
  </RecoilRoot>
);
