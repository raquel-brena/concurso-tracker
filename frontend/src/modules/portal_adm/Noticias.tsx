import { Button } from "../../components/buttons/Button";
import { Header } from "../../components/header/Header";
import noticias2  from "../../assets/noticia2.svg"

export const Noticias = () => {
  return (
    <div className="w-full flex flex-col h-fit items-center px-10">
      <Header
        title="Gerenciar Notícias"
        subtitle="GERENCIAR NOTÍCIAS"
        description="Gerencie todos os concursos cadastrados no sistema, você pode
                visualizar, editar e organizar as informações de cada concurso."
      />
      <div className="flex w-full justify-between  items-center   px-4 ">
        <div className="flex flex-col gap-3 ">
          <p className="text-sm">
            Clique abaixo para cadastrar uma nova notícia.
          </p>
          <Button title="Cadastrar nova notícias" type={3} className="w-fit" />
        </div>
        <img src={noticias2} alt="Logo" className="size-40" />
      </div>

      <div className="w-full  grid grid-cols-2 grid-rows-3 items-center justify ">
        <div className="flex w-[90%] h-28 gap-2 rounded-md">
          <div className="bg-[#F8F8F8] w-[40%] h-full"></div>
          <div className=" w-[60%] h-full flex flex-col p-4">
            <p className="font-bold">Título</p>
            <p>Descrição descrição descrição</p>
          </div>
        </div>

        <div className="flex w-[90%] h-28 gap-2 rounded-md">
          <div className="bg-[#F8F8F8] w-[40%] h-full"></div>
          <div className=" w-[60%] h-full flex flex-col p-4">
            <p className="font-bold">Título</p>
            <p>Descrição descrição descrição</p>
          </div>
        </div>

        <div className="flex w-[90%] h-28 gap-2 rounded-md">
          <div className="bg-[#F8F8F8] w-[40%] h-full"></div>
          <div className=" w-[60%] h-full flex flex-col p-4">
            <p className="font-bold">Título</p>
            <p>Descrição descrição descrição</p>
          </div>
        </div>

        <div className="flex w-[90%] h-28 gap-2 rounded-md">
          <div className="bg-[#F8F8F8] w-[40%] h-full"></div>
          <div className=" w-[60%] h-full flex flex-col p-4">
            <p className="font-bold">Título</p>
            <p>Descrição descrição descrição</p>
          </div>
        </div>
      </div>
    </div>
  );
};
