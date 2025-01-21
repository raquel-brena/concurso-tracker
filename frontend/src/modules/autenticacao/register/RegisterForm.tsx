import { useForm } from "react-hook-form";
import { FaRegIdCard } from "react-icons/fa";
import {
  MdOutlineMailOutline,
  MdOutlinePersonOutline,
  MdOutlinePhoneAndroid,
} from "react-icons/md";
import { Step } from "../components/Step";
import { Button } from "../../../components/buttons/Button";
import { useAuth } from "../../../infra/contexts/UseAuth";
import { TextInputWithIcon } from "../../../components/inputs/TextInputWithIcon";
import { useState } from "react";
import { CgPhotoscan } from "react-icons/cg";
import { PerfilForm } from "./forms/PerfilForm";
import { DadosPessoaisForm } from "./forms/DadosPessoaisForm";
import { DadosAcademicosForm } from "./forms/DadosAcademicosForm";
import { DadosProfissionaisForm } from "./forms/DadosProfissionaisForm";
import { FinalizarForm } from "./forms/FinalizarForm";
import { useNavigate } from "react-router-dom";
import { toast } from "sonner";

export const RegisterForm = ({ handleSubmit, register, errors }: any) => {
  const registerInputs = [
    {
      id: 0,
      stepForm: "Perfil",
      children: <PerfilForm />,
    },
    {
      id: 1,
      stepForm: "Dados pessoais",
      last: false,
      children: <DadosPessoaisForm register={register} errors={errors} />,
    },
    {
      id: 2,
      stepForm: "Dados acadêmicos",
      last: false,
      children: <DadosAcademicosForm register={register} errors={errors} />,
    },
    {
      id: 3,
      stepForm: "Dados profissionais",
      last: false,
      children: <DadosProfissionaisForm register={register} errors={errors} />,
    },
    {
      id: 4,
      stepForm: "Finalizar",
      last: true,
      children: <FinalizarForm />,
    },
  ];

  const { handleRegisterRequest, handleUpdateUser, loading } = useAuth();
  const [step, setStep] = useState(registerInputs[0]);

  // const navigate = useNavigate();
  // function handleRegister(data: any) {
  //   console.log(data);

  //   handleRegisterRequest(data)
  //     .then((user) => {
  //       if (user.data) {
  //         handleNextStep();
  //       }
  //     })
  //     .catch((error) => {
  //       console.log(error);
  //     });
  // }

  const onSubmit = (data: any) => {
    console.log(data);
  
    if (step.id === registerInputs.length - 2) {
      handleUpdateUser(data);
    } else {
      handleUpdateUser(data);
      handleNextStep();
    }
  };
  

  const handleNextStep = () => {
    if (step.id === registerInputs.length - 1) {
      return;
    }

    setStep(registerInputs[step.id + 1]);
  };

  const handlePreviousStep = () => {
    if (step.id === 0) {
      window.location.reload();
      return;
    }

    setStep(registerInputs[step.id - 1]);
  };

  return (
    <div className="flex flex-col relative w-screen h-screen items-center pt-8 space-y-5 ">
      <div className="block w-3/4 ">
        <p className="text-2xl">Seja bem vinda! Vamos completar seu cadastro</p>
        <p className="text-[#6E6D7A]">
          Esses dados ajudarão a tornar suas inscrições em editais mais fáceis!
        </p>
      </div>
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="flex flex-col shadow-md bg-white w-3/4 h-3/4 min-h-fit  transition-all 
      py-3 justify-between items-center pt-4"
      >
        <div className="flex justify-center w-full">
          {registerInputs.map((inputs) => (
            <Step
              circle={inputs.id < step.id ? 1 : step.id === inputs.id ? 1 : 2}
              stroke={
                inputs.last
                  ? 3
                  : inputs.id < step.id
                  ? 1
                  : step.id === inputs.id
                  ? 1
                  : 2
              }
              title={inputs.stepForm}
            />
          ))}
        </div>
        <div className="flex w-full items-center justify-center pt-8 px-14 ">
          {step.children}
        </div>

        <div className="flex w-full justify-between items-center px-12 py-4 ">
          <a
            className="font-medium text-sm text-red-500"
            onClick={handlePreviousStep}
          >
            Voltar
          </a>
          {!step.last && (
            <div className="flex gap-4">
              {!(step.id === registerInputs.length - 2) && (
                <Button
                  type="button"
                  className="bg-transparent border border-dark_blue text-dark_blue"
                  style={2}
                  onClick={() => {
                    if (step.id === registerInputs.length - 1) {
                      return;
                    } else {
                      handleNextStep();
                    }
                  }}
                >
                  Pular
                </Button>
              )}

              <Button type="submit" style={1}>
                {step.id === registerInputs.length - 2
                  ? "Enviar dados"
                  : "Avançar"}
              </Button>
            </div>
          )}
        </div>
      </form>
    </div>
  );
};
