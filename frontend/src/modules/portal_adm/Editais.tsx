import { Button } from "../../components/buttons/Button";
import { CardProcessoSeletivo } from "../../components/cards/CardProcessoSeletivo";
import { Header } from "../../components/header/Header";

export const Editais = () => {
  return (
    <div className="w-full flex flex-col h-fit items-center px-10">
      <Header
        title="Gerenciar processos seletivos"
        subtitle="GERENCIAR NOTÍCIAS"
        description="Gerencie todos os concursos cadastrados no sistema, você pode
                visualizar, editar e organizar as informações de cada concurso."
      />
      <div className="flex flex-col gap-2 py-8 w-full px-4 ">
        <p className="text-sm">
          Clique abaixo para cadastrar um novo edital para processo seletivo.
        </p>
        <Button
          title="Cadastrar novo processo seletivo"
          type={3}
          className="w-fit"
        />
      </div>
      <div className="w-[80%] flex flex-col items-center gap-2 mb-2">
        <p className="uppercase  flex self-start">
          GERENCIE PELO{" "}
          <span className="text-red-600 font-semibold mx-1">STATUS</span> DO
          PROCESSO SELETIVO
        </p>
        <div className="flex w-full">
          <Button title="Todos" type={1} className="flex-grow py-4 font-medium" />
          <Button
            title="Em andamento"
            type={4}
            className="flex-grow py-4 font-medium "
          />
          <Button
            title="Finalizado"
            type={4}
            className="flex-grow py-4 font-medium "
          />
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
    </div>
  );
};
