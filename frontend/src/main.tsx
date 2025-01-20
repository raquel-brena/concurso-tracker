import { createRoot } from "react-dom/client";
import { BrowserRouter, RouterProvider } from "react-router-dom";
import { router } from "./routes.tsx";
import React from "react";
import AuthProvider from "./infra/contexts/AuthProvider.tsx";
import { Toaster } from "sonner";


createRoot(document.getElementById("root")!).render(
  // <BrowserRouter>
  //   <App />
  // </BrowserRouter>
  <React.StrictMode>
    <AuthProvider>
      <Toaster />
      <RouterProvider
        router={router}
        fallbackElement={<div>Carregando...</div>}
        future={{
          v7_startTransition: true
        }}
      />
    </AuthProvider>
  </React.StrictMode>
);
