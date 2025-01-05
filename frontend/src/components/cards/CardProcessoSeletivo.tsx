import { Button } from "./buttons/Button";

export const CardProcessoSeletivo = () => { 
    return (
      <div className="flex w-full h-32 max-h-32 py-2 px-4 shadow-md border-l-8 border-l-yellow-500 justify-between">
        <div className="flex flex-col gap-2 w-1/3 h-full">
          <p className="text-sm">Processo seletivo 01</p>
          <p className="">
            Descrição Descrição Descrição Descrição Descrição Descrição
            Descrição Descrição Descrição Descrição{" "}
          </p>
        </div>
        <div className="grid grid-cols-3 px-4 grid-rows-2 gap-x-8 gap-y-4 flex-grow h-full ">
          <div>
            <p>Estatísticas</p>
          </div>
          <div>
            <p>Estatísticas</p>
          </div>
          <div>
            <p>Estatísticas</p>
          </div>
          <div>
            <p>Estatísticas</p>
          </div>
          <div>
            <p>Estatísticas</p>
          </div>
          <div>
            <p>Estatísticas</p>
          </div>
        </div>
        <div className="flex flex-col h-full items-end justify-center w-fit gap-2">
          <Button
            title="Gerenciar inscrições"
            type={3}
            className="rounded-sm w-36"
          />
          <Button title="Editar Edital" type={1} className="rounded-sm w-36" />
        </div>
      </div>
    );
}