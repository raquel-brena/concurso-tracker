import { createRoot } from "react-dom/client";
import { RouterProvider } from "react-router-dom";

import { router } from "./routes.tsx";
import React from "react";
import AuthProvider from "./infra/contexts/AuthProvider.tsx";
import { Toaster } from "sonner";


createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <AuthProvider>
    <Toaster />
      <RouterProvider router={router} />
    </AuthProvider>
  </React.StrictMode>
);
