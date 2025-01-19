import { useNavigate } from "react-router-dom";
import { Button } from "../../../components/buttons/Button";
import { CardProcessoSeletivo } from "../../../components/cards/CardProcessoSeletivo";

export const IndexEditais = () => {
  const navigate = useNavigate();
  return (
    <>
      <div className="flex flex-col gap-2 py-8 w-full px-4 ">
        <p className="text-sm">
          Clique abaixo para cadastrar um novo edital para processo seletivo.
        </p>
        <Button
          style={3}
          className="w-fit"
          onClick={() => {
            navigate("/editais/cadastrar");
          }}
        >
          Cadastrar novo processo seletivo
        </Button>
      </div>
      <div className="w-[80%] flex flex-col items-center gap-2 mb-2">
        <p className="uppercase  flex self-start">
          GERENCIE PELO{" "}
          <span className="text-red-600 font-semibold mx-1">STATUS</span> DO
          PROCESSO SELETIVO
        </p>
        <div className="flex w-full">
          <Button style={1} className="flex-grow py-4 font-medium">
            Todos
          </Button>
          <Button style={4} className="flex-grow py-4 font-medium ">
            Em andamento
          </Button>
          <Button style={4} className="flex-grow py-4 font-medium ">
            Finalizado
          </Button>
        </div>
      </div>
      <div className="flex flex-col w-[90%] h-fit gap-4 ">
        <CardProcessoSeletivo />
        <CardProcessoSeletivo />
        <CardProcessoSeletivo />
        <CardProcessoSeletivo />
        <CardProcessoSeletivo />
        <CardProcessoSeletivo />
      </div>
    </>
  );
};