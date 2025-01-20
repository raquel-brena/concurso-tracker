import { Content } from "./Content";

export const ChamadasContent = () => {
  return (
    <Content title="Chamadas públicas" className="my-4">
      <div className="w-full min-h-36 flex md:flex-row flex-col p-4 justify-around gap-y-4 md:gap-y-0 ">
        <div className="flex flex-col items-center justify-center gap-2 ">
          <p className="font-medium text-red-600">Abertas</p>
          <button className="border rounded-full px-10 py-1 text-[#303030] text-sm border-[#303030]">
            Acesse aqui
          </button>
        </div>
        <div className="flex flex-col items-center justify-center gap-2 ">
          <p className="font-medium text-red-600">Encerradas</p>
          <button className="border rounded-full px-10 py-1 text-[#303030] text-sm border-[#303030]">
            Acesse aqui
          </button>
        </div>
        <div className="flex flex-col items-center justify-center gap-2 ">
          <p className="font-medium text-red-600">Resultados</p>
          <button className="border rounded-full px-10 py-1 text-[#303030] text-sm border-[#303030]">
            Acesse aqui
          </button>
        </div>
      </div>
    </Content>
  );
};
