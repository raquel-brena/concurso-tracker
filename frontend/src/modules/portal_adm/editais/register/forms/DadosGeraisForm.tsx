import { useForm } from "react-hook-form";
import { Button } from "../../../../../components/buttons/Button";
import { TextInputWithIcon } from "../../../../../components/inputs/TextInputWithIcon";
import { TextInput } from "../../../../../components/inputs/TextInput";
import { CheckboxIcon } from "@radix-ui/react-icons";
import axios from "axios";
import { toast } from "sonner";
import { useNavigate } from "react-router-dom";

type Inputs = {
  titulo: string;
  descricao: string;
  validade: number;
  temporario: boolean;
  edital: any;
};


type DadosGeraisFormProps = {
  handlePreviousStep: () => void;
  handleNextStep: () => void;
  processoSeletivo: any;
  setProcessoSeletivo: any;
}

export const DadosGeraisForm = ({ handlePreviousStep,handleNextStep, processoSeletivo, setProcessoSeletivo } : DadosGeraisFormProps) => {
  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm<Inputs>();
  const navigate = useNavigate(); 
  async function criarProcessoSeletivo(data: Inputs) {
    const formData = {
      titulo: data.titulo,
      descricao: data.descricao,
      validade: data.validade,
      temporario: data.temporario,
    };

    const token = localStorage.getItem("token");
    const response = await axios.post(
      "http://localhost:8080/api/processo/",
      formData,
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      }
    );

    // 2. Envio do Arquivo do Edital
    const file = data.edital[0]; // Arquivo selecionado
    const formDataDocumento = new FormData();
    formDataDocumento.append("file", file);
    formDataDocumento.append("nome", file.name);
    formDataDocumento.append("processoId", response.data.data.id);
    formDataDocumento.append("observacao", "Edital do processo seletivo");

    const responseDocumento = await axios.post(
      "http://localhost:8080/api/documentos/",
      formDataDocumento,
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "multipart/form-data",
        },
      }
    );

    console.log("Documento enviado com sucesso:", responseDocumento.data);
    toast.success("Processo seletivo e edital criados com sucesso!");
    
    setProcessoSeletivo(response.data.data);
    handleNextStep();
    navigate(`/portal/editais/cadastrar/${response.data.data.id}`);
    toast.success("Processo seletivo criado com sucesso!");
    console.log(response.data);
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
          <div className="flex flex-col">
            <p className="font-semibold text-sm text-slate-950">
              Arquivo do edital
            </p>
            <input
              type="file"
              className="w-1/2 border border-[#888888] py-1 px-2 rounded-md placeholder:text-sm"
              {...register("edital", { required: true })}
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
              type="checkbox"
              {...register("temporario")}
            />
          </div>
        </div>
      </div>
      <div className="flex w-full items-end justify-end  py-4 ">
        {/* <a
          className="font-medium text-sm text-red-500"
          onClick={handlePreviousStep}
        >
          Voltar
        </a> */}
        <Button type="submit" style={1}>
          Salvar
        </Button>
      </div>
    </form>
  );
};
