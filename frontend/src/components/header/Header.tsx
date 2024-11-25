import { HomeIcon } from "@radix-ui/react-icons";

export const Header = () => {
  return (
    <div className="flex flex-col w-full h-1/4 px-10 pt-2 space-y-4">
      <div className="flex text-center items-center text-sm select-none">
        <HomeIcon />
        <p>
          {">"} Editais {">"} Gerenciar processos seletivos
        </p>
      </div>
      <p className="text-2xl text-red-500 font-medium">
        Gerenciar processos seletivos
      </p>
      <p>
        Gerencie todos os concursos cadastrados no sistema, você pode
        visualizar, editar e organizar as informações de cada concurso.
      </p>
      <p className="font-semibold pt-3">GERENCIAR NOTÍCIAS</p>
    </div>
  );
};
