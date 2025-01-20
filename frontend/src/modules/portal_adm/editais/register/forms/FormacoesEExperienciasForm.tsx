import { MdOutlineMailOutline, MdOutlinePersonOutline, MdOutlinePhoneAndroid } from "react-icons/md";
import { MdOutlineDocumentScanner } from "react-icons/md";
import { TextInputWithIcon } from "../../../../../components/inputs/TextInputWithIcon";
import { TextInput } from "../../../../../components/inputs/TextInput";
import { useForm, useWatch } from "react-hook-form";
import axios from "axios";
import { toast } from "sonner";
import { Button } from "../../../../../components/buttons/Button";
import { useEffect, useState } from "react";
import { TrashIcon } from "@radix-ui/react-icons";

type Inputs = {
  experiencia: boolean;
  formacaoAcademica: boolean;
  formacaoAcademicaOuExperiencia: string;
  peso: number;
  limiteTempoOuExperiencia: number;
};


export const FormacoesEExperienciasForm = ({ handlePreviousStep,handleNextStep, processoSeletivo, setProcessoSeletivo  }: any) => {
    const {
      register,
      handleSubmit,
      watch,
      setValue,
      control,
      formState: { errors },
    } = useForm<Inputs>();

    
    const token = localStorage.getItem("token"); 

    const [formacoes, setFormacoes] = useState<
    any[]>([]);
    
    const [criteriosId, setCriteriosId] = useState<any[]>([]);
    async function criarCriterios() {
    
      for (const formacao of formacoes) {
        const data = {
          nome: formacao.nome,
          peso: formacao.pontuacao,
          processoSeletivoId: processoSeletivo.id
      }
      const response = await axios.post(
        `http://localhost:8081/api/criterios/`,
        data,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      ).then((response) => {
        setCriteriosId([...criteriosId, response.data.data.id])});
    }
    await atualizarProcessoSeletivo();
  } 

  async function atualizarProcessoSeletivo() {

    const response = await axios.put(
      `http://localhost:8081/api/processo/${processoSeletivo.id}`,
      criteriosId,
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      }
    );
    if (response.status !== 201) { 
      toast.error(response.data);
    }
    handleNextStep();
      console.log(response.data);
      toast.success("Processo seletivo criado com sucesso!");

}


const formacaoAcademica = useWatch({
  control,
  name: "formacaoAcademica",
});

const experiencia = useWatch({
  control,
  name: "experiencia",
});

useEffect(() => {
  if (formacaoAcademica) {
    setValue("experiencia", false);
  } 
  if (experiencia) {
    setValue("formacaoAcademica", false);
  } 
}, [formacaoAcademica, experiencia]);


const tiposExperiencias = [
  "Estágio",
  "Trabalho Voluntário",
  "Emprego Formal",
  "Emprego Informal",
  "Freelancer",
  "Projeto Acadêmico",
  "Iniciação Científica",
  "Monitoria",
  "Consultoria",
  "Treinamento Técnico",
  "Programa de Trainee",
  "Pesquisa",
  "Empreendedorismo",
  "Cargo Público",
  "Atividade Militar"
];

const tiposFormacoes = [
  "Ensino Médio",
  "Ensino Superior",
  "Pós-Graduação",
  "Mestrado",
  "Doutorado",
  "Pós-Doutorado",
  "Curso Técnico",
  "Curso Profissionalizante",
];


function handleAddFormacao(data: any) {
  setFormacoes([...formacoes, 
    {
      nome: data.formacaoAcademicaOuExperiencia,
      peso: data.peso
    }
  ]);
}

const handleRemoveFormacao = (index: number) => {
  setFormacoes(formacoes.filter((_, i) => i !== index));
};

  return (

     <form
          onSubmit={handleSubmit(handleAddFormacao)}
          className="flex justify-between flex-col w-full h-full 
        space-y-4"
        >
          <div className="flex w-full flex-col h-fit">
            <p className="font-semibold text-lg">Formações e experiências</p>
            <div className="grid grid-cols-1 w-full gap-4 grid-rows-4 py-4">
            <div className="flex items-center gap-8">
               <div className="flex items-center gap-4">
             
                <input 
                className="size-4 border"
                type="radio" 
          
                {...register("formacaoAcademica")} />
                  <p
                  className="font-semibold text-sm
                 text-slate-950"
                >
                 Formação acadêmica
                </p>
               </div>
               <div className="flex items-center gap-4">
               
                <input 
                className="size-4 border"
                type="radio" {...register("experiencia")} />
               </div>
               <p
                  className="font-semibold text-sm
                 text-slate-950"
                >
                  Experiência
                </p>
              </div>
              <div className="flex flex-col">
                <p
                  className="font-semibold text-sm
                 text-slate-950"
                >
                  {watch("formacaoAcademica") ? "Formação acadêmica" : "Experiência"}
                </p>
                {}
                <select
               
                  className={`w-1/2 border 
                    border-[#888888] py-2 px-3 rounded-md`}
                  {...register("formacaoAcademicaOuExperiencia", { required: true })}
                >
                    {formacaoAcademica
                ? tiposFormacoes.map((tipo, index) => (
                    <option key={index} value={tipo}>
                      {tipo}
                    </option>
                  ))
                : tiposExperiencias.map((tipo, index) => (
                    <option key={index} value={tipo}>
                      {tipo}
                    </option>
                  ))}
                </select>
              </div>
              <div className="flex flex-col">
                <p
                  className="font-semibold text-sm
                 text-slate-950"
                >
                  Pontuação
                </p>
                <TextInput
                  className={`w-1/2 border 
                    border-[#888888] py-2 px-3 rounded-md`}
                  {...register("peso", { required: true })}
                />
              </div>
              <div className="flex w-full items-end justify-between gap-4">
              <div className="flex flex-col w-1/2">
                <p
                  className="font-semibold text-sm
                 text-slate-950"
                >
                   {watch("formacaoAcademica") ? "Limite de certificados contabilizados" : "Limite de tempo de experiência"}
                  
                </p>
                <TextInput
                  type="number"
                  className={`w-full border 
                    border-[#888888] py-2 px-3 rounded-md`}
                  {...register("limiteTempoOuExperiencia", { required: true })}
                />
              </div>
              <Button type="submit" className="rounded-none w-1/2 h-10" style={4}>
              Adicionar
        </Button>
              </div>
             
           
            </div>
          
            <div>
       
      </div>
          </div>
          <p className="font-semibold text-lg">
         Dados cadastrados
        </p>
        <table className="w-full shadow-md p-4">
          <thead>
            <tr className="bg-[#F5F5F5]">
              <th className="text-red-500 semibold text-sm p-2">Tipo</th>
              <th className="text-red-500 semibold text-sm p-2">Pontuação</th>
              <th className="text-red-500 semibold text-sm p-2">Limite</th>
              <th className="text-red-500 semibold text-sm p-2">Ações</th>
            </tr>
          </thead>
          <tbody>
            {formacoes.map((formacao, index) => (
              <tr key={index} className="bg-white">
                <td>{formacao.formacaoAcademicaOuExperiencia}</td>
                <td>{formacao.pontuacao}</td>
                <td>{formacao.limiteTempoOuExperiencia}</td>
                <td>
                  <button type="button" onClick={() => handleRemoveFormacao(index)}><TrashIcon/></button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
          <div className="flex w-full items-end justify-end  py-4 ">
        {/* <a
          className="font-medium text-sm text-red-500"
          onClick={handlePreviousStep}
        >
          Voltar
        </a> */}
        <Button type="button" style={1}
        onClick={criarCriterios}>
          Salvar
        </Button>
      </div>
        </form>
  );
};

