import { useForm } from "react-hook-form";
import { Button } from "../../../../../components/buttons/Button";
import { TextInputWithIcon } from "../../../../../components/inputs/TextInputWithIcon";
import { TextInput } from "../../../../../components/inputs/TextInput";
import { CheckboxIcon } from "@radix-ui/react-icons";
import axios from "axios";

type Inputs = {
  titulo: string;
  descricao: string;
  validade: number;
  temporario: boolean;
};

export const DadosGeraisForm = ({ handlePreviousStep } : any) => {
  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm<Inputs>();

  async function criarProcessoSeletivo(data: Inputs) {
    const token = localStorage.getItem("token"); // Recuperar o token do localStorage
 
      console.log(token)
    const response = await axios.post(
      "http://localhost:8081/api/processo/",
      data,
      {
        headers: {
          Authorization: `Bearer ${token}`, // Adicionar o token no cabeçalho
          "Content-Type": "application/json",
        },
      }
    );
    console.log(response);
  }

  return (
    <form
      onSubmit={handleSubmit(criarProcessoSeletivo)}
      className="flex justify-between flex-col w-full h-full 
    space-y-4"
    >
      <div className="flex w-full flex-col h-fit">
        <p className="font-semibold text-lg">Dados Gerais</p>
        <div className="grid grid-cols-1 w-full gap-4 grid-rows-4 py-4">
          <div className="flex flex-col">
            <p
              className="font-semibold text-sm
             text-slate-950"
            >
              Título
            </p>
            <TextInput
              className={`w-1/2 border 
                border-[#888888] py-2 px-3 rounded-md`}
              {...register("titulo", { required: true })}
            />
          </div>
          <div className="flex flex-col">
            <p
              className="font-semibold text-sm
             text-slate-950"
            >
              Descrição
            </p>
            <TextInput
              className={`w-1/2 border 
                border-[#888888] py-2 px-3 rounded-md`}
              {...register("descricao", { required: true })}
            />
          </div>
          <div className="flex flex-col">
            <p
              className="font-semibold text-sm
             text-slate-950"
            >
              Validade em meses
            </p>
            <TextInput
              type="number"
              className={`w-1/2 border 
                border-[#888888] py-2 px-3 rounded-md`}
              {...register("validade", { required: true })}
            />
          </div>
          <div className="flex items-center gap-4">
            <p
              className="font-semibold text-sm
             text-slate-950"
            >
              Temporário?
            </p>
            <input 
            className="size-4 border"
            type="checkbox" {...register("temporario")} />
          </div>
        </div>
      </div>
      <div className="flex w-full justify-between items-center py-4 ">
        <a
          className="font-medium text-sm text-red-500"
          onClick={handlePreviousStep}
        >
          Voltar
        </a>
        <Button className=" flex-end" type="submit" style={1}>
          Salvar
        </Button>
      </div>
    </form>
  );
};
