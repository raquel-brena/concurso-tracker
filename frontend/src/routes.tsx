import { createBrowserRouter } from "react-router-dom";
import Login from "./modules/autenticacao/AuthPage";
import { ProtectedRoute } from "./modules/autenticacao/components/ProtectedRoute";
import { RegisterForm } from "./modules/autenticacao/register/RegisterForm";
import AuthPage from "./modules/autenticacao/AuthPage";
import Portal from "./modules/portal_adm/Layout";
import CadastrarEdital from "./modules/portal_adm/editais/CadastrarEdital";
import { Home } from "./modules/portal_adm/Home";
import { Editais } from "./modules/portal_adm/Editais";
import { Noticias } from "./modules/portal_adm/Noticias";
import { Inscricoes } from "./modules/portal_adm/Inscricoes";
import { IndexEditais } from "./modules/portal_adm/editais/IndexEditais";
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
    path: "/portal",
    element: (
      <ProtectedRoute isPrivate>
        <Portal />
      </ProtectedRoute>
    ),
    errorElement: <div>Not Found</div>,
    children: [
      {
        path: "home",
        element: (
          <ProtectedRoute isPrivate>
            <Home />,
          </ProtectedRoute>
        ),
      },
      {
        path: "editais",
        element: <Editais />,
        children: [
          {
            path: "",
            element: (
              <IndexEditais
                headerSubTitle="GERENCIAR PROCESSOS SELETIVOS"
                headerDescription="Gerencie todos os concursos cadastrados no sistema, você pode
                visualizar, editar e organizar as informações de cada concurso."
              />
            ),
          },
          {
            path: "cadastrar/:id?",
            element: (
              <CadastrarEdital
                headerSubTitle="CADASTRAR NOVO PROCESSO SELETIVO"
                headerDescription="Gerencie todos os concursos cadastrados no sistema, você pode
                visualizar, editar e organizar as informações de cada concurso."
              />
            ),
          },
        ],
      },
      {
        path: "noticias",
        element: <Noticias />,
      },
      {
        path: "inscricoes",
        element: <Inscricoes />,
      },
    ],
  },
]);
