
import { Button } from "../../components/buttons/Button";
import { Header } from "../../components/header/Header";

const status = [
    "Previstos",
    "Inscrições abertas",
    "Em andamento",
    "Encerrados"
]

const regioes = [
    "Nacional",
    "Sudeste",
    "Sul",
    "Centro Oeste",
    "Norte",
    "Nordeste"
]

const estados = [
  "AC",
  "AL",
  "AM",
  "AP",
  "BA",
  "CE",
  "DF",
  "ES",
  "GO",
  "MA",
  "MG",
  "MS",
  "MT",
  "PA",
  "PB",
  "PE",
  "PI",
  "PR",
  "RJ",
  "RN",
  "RO",
  "RR",
  "RS",
  "SC",
  "SE",
  "SP",
  "TO",
];

export const Inscricoes = () => { 
    return (
      <div className="w-full flex flex-col h-fit items-center px-10">
        <Header
          title="Histórico de Inscrições"
          subtitle="MEU HISTÓRICO DE INSCRIÇÕES"
          description="Revise e gerencie o histórico de inscrições que você já participou ou está participando de qualquer edital."
        />

        <div className="flex h-44 w-screen bg-[#FCF7F5] min-h-fit stroke-gray-200">
          <div
            className="w-full grid grid-rows-5 
          grid-cols-1 px-10 py-5 h-fit gap-y-3 "
          >
            <div className="grid grid-cols-4 grid-rows-1">
              {status.map((stts) => (
                <button
                  className="bg-white flex-grow border border-collapse py-2 
              text-sm h-fit border-gray-300 hover:bg-gray-50"
                >
                  {stts}
                </button>
              ))}
            </div>

            <div className="grid grid-cols-6 grid-rows-1">
              {regioes.map((regiao) => (
                <button
                  className="bg-white flex-grow border border-collapse py-2 
              text-sm h-fit border-gray-300  hover:bg-gray-50"
                >
                  {regiao}
                </button>
              ))}
            </div>
            <div className="grid grid-cols-[repeat(27,minmax(0,1fr))] grid-rows-1=">
              {estados.map((estado) => (
                <button
                  key={estado}
                  className="bg-white flex-grow border border-collapse py-2 
              text-sm h-fit border-gray-300 hover:bg-gray-50"
                >
                  {estado}
                </button>
              ))}
            </div>
            <div className="grid grid-cols-4 grid-rows-1 gap-4">
              <input
                type="text"
                placeholder="Palavra chave"
                className="border border-gray-300 p-2 rounded"
              />
              <select className="border border-gray-300 p-2 rounded">
                <option value="">Vaga</option>
                {/* Adicione opções aqui */}
              </select>
              <select className="border border-gray-300 p-2 rounded">
                <option value="">Ano</option>
                {/* Adicione opções aqui */}
              </select>
              <select className="border border-gray-300 p-2 rounded">
                <option value="">Instituição</option>
                {/* Adicione opções aqui */}
              </select>
            </div>
            <div className="grid grid-cols-2 grid-rows-1 gap-4">
              <p>Filtros</p>
              <div className="flex justify-end gap-2">
                <Button
                  title="Limpar"
                  type={4}
                  className="bg-white w-fit rounded-sm"
                />
                <Button
                  title="Filtrar"
                  type={3}
                  className="bg-white rounded-sm w-fit"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    );
}