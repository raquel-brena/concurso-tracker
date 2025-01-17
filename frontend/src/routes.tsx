import { createBrowserRouter } from "react-router-dom";
import Login from "./modules/autenticacao/AuthPage";
import { ProtectedRoute } from "./modules/autenticacao/components/ProtectedRoute";
import { RegisterForm } from "./modules/autenticacao/register/RegisterForm";
import { Portal } from "@radix-ui/react-menubar";
import AuthPage from "./modules/autenticacao/AuthPage";
export const router = createBrowserRouter([
  {
    path: "/",
    element: <AuthPage />,
    errorElement: <div>Not Found</div>,
  },
  {
    path: "/register",
    element: (
      <ProtectedRoute>
        {/* <ContainerCentral> */}
        <RegisterForm />
        {/* </ContainerCentral> */}
      </ProtectedRoute>
    ),
    errorElement: <div>Not Found</div>,
  },
  {
    path: "/adm",
    element: (
      <ProtectedRoute>
        {/* <ContainerCentral> */}
        <Portal />
        {/* </ContainerCentral> */}
      </ProtectedRoute>
    ),
    errorElement: <div>Not Found</div>,
  },
  {
    path: "/editais",
    element: (
      <ProtectedRoute isPrivate>
        {/* <ContainerCentral> */}
        <Portal />
        {/* </ContainerCentral> */}
      </ProtectedRoute>
    ),
    errorElement: <div>Not Found</div>,
  },
]);
