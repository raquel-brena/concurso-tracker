import { TextInputWithIcon } from "../../../../components/inputs/TextInputWithIcon";
import { Button } from "../../../../components/buttons/Button";

export const DadosAcademicosForm = ({ register }: any) => {
  return (
    <div className="flex justify-between  flex-col   w-full h-full  space-y-4 px-14">
      <div className="flex w-full flex-col h-fit">
        <p className="font-semibold text-lg">Dados Acadêmicos</p>
        <div className="grid grid-cols-2 w-full grid-rows-2">
          <TextInputWithIcon
            icon=""
            title="Nível de formação *"
            register={register}
            input="nivelFormacao"
            type="text"
            disabled={false}
          />
          <TextInputWithIcon
            icon=""
            title="Instituição"
            register={register}
            input="instituicao"
            type="text"
            disabled={false}
          />
          <TextInputWithIcon
            icon=""
            title="Tipo de curso"
            register={register}
            input="tipoCurso"
            type="text"
            disabled={false}
          />
          <TextInputWithIcon
            icon=""
            title="Ano de conclusão"
            register={register}
            input="anoConclusao"
            type="number"
            disabled={false}
          />
        </div>
      </div>
      <div className="flex w-full justify-between items-end">
        <TextInputWithIcon
          icon=""
          title="Anexo do diploma"
          register={register}
          input="anexoDiploma"
          type="text"
          disabled={false}
          className="w-full"
          classNameInput="flex min-w-[35rem]"
        />
        <Button
          type={4}
          className="w-1/2 h-10 mt-4 rounded-none bg-[#383838] "
          onClick={() => {}}
        >
          Adicionar formação
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
  );
};
