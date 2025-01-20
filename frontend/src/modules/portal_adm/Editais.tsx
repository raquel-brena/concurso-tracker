import { Outlet } from "react-router-dom";
import { Header } from "../../components/header/Header";

export const Editais = () => {

  return (
    <div className="w-full flex flex-col h-fit items-center px-10">
      <Header
        title="Gerenciar processos seletivos"
      />
      <Outlet />
     
    </div>
  );
};
