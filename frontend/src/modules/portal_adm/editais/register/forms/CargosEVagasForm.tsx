import { Button } from "../../../../../components/buttons/Button";
import { TextInputWithIcon } from "../../../../../components/inputs/TextInputWithIcon";

export const CargosEVagasForm = ({ register, errors }: any) => {
  return (
    <div className="flex justify-between w-full h-full  space-y-4 px-14">
      <div className="flex w-full flex-col ">
        <p className="font-semibold text-lg">Dados Acadêmicos</p>
        <div className="grid grid-cols-2 w-full grid-rows-3">
          <TextInputWithIcon
            icon=""
            title="Cargo ou função"
            register={register}
            input="cargo"
            type="text"
            disabled={false}
          />
          {errors.cargo && (
            <span className="text-red-500">{errors.cargo?.message}</span>
          )}
          <TextInputWithIcon
            icon=""
            title="Empresa ou organização"
            register={register}
            input="organizacao"
            type="text"
            disabled={false}
          />
          {errors.organizacao && (
            <span className="text-red-500">{errors.organizacao?.message}</span>
          )}
          <TextInputWithIcon
            icon=""
            title="Tempo de contribuição"
            register={register}
            input="tempoContribuicao"
            type="text"
            disabled={false}
          />
          <TextInputWithIcon
            icon=""
            title="Localização"
            register={register}
            input="localizacao"
            type="text"
            disabled={false}
          />
          <TextInputWithIcon
            icon=""
            title="Tipo de vínculo"
            register={register}
            input="vinculo"
            type="text"
            disabled={false}
          />
          <Button
            style={4}
            className="w-1/2 h-10 mt-4 rounded-none bg-[#383838] "
            onClick={() => {}}
          >
            Adicionar dados profissionais
          </Button>
        </div>

        <div>
          <p className="font-semibold text-lg">
            Formações acadêmicas inseridas (1)
          </p>
          <table className="w-full">
            <tr className="bg-[#F5F5F5]">
              <th className="text-red-500 semibold text-sm p-2">
                Cargo ou função
              </th>
              <th className="text-red-500 semibold text-sm p-2">
                Empresa ou organização
              </th>
              <th className="text-red-500 semibold text-sm p-2">
                Tempo de contribuição
              </th>
              <th className="text-red-500 semibold text-sm p-2 bg-red-100">
                Tipo de Vínculo
              </th>
            </tr>
            <tr>
              <td>Graduação</td>
              <td>Universidade Federal do Ceará</td>
              <td>Presencial</td>
              <td>2020</td>
            </tr>
          </table>
        </div>
      </div>
    </div>
  );
};
