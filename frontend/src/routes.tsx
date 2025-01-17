import { createBrowserRouter } from "react-router-dom";
import Login from "./modules/autenticacao/login/Login";
import { ProtectedRoute } from "./modules/autenticacao/components/ProtectedRoute";
import { RegisterForm } from "./modules/autenticacao/register/RegisterForm";
import { Portal } from "@radix-ui/react-menubar";
export const router = createBrowserRouter([
  {
    path: "/",
    element: <Login />,
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
]);
