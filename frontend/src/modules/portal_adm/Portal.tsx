import { Menubar, Separator } from "@radix-ui/react-menubar";
import { Header } from "../../components/header/Header";

function Portal() {
  return (
    <div className="flex flex-col relative w-screen h-screen">
      <Menubar />
      <div className="flex h-1/4 w-full bg-[#FCF7F5]">.</div>
      <Header
        title="Gerenciar processos seletivos"
        subtitle="GERENCIAR NOTÍCIAS"
        description="Gerencie todos os concursos cadastrados no sistema, você pode
        visualizar, editar e organizar as informações de cada concurso."
      />
      <Separator />
    </div>
  );
}
export default Portal;
