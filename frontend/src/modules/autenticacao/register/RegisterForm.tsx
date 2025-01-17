
import { useForm } from "react-hook-form";
import { FaRegIdCard } from "react-icons/fa";
import { MdOutlineMailOutline, MdOutlinePersonOutline, MdOutlinePhoneAndroid } from "react-icons/md";
import { Step } from "../components/Step";
import { Button } from "../../../components/buttons/Button";
import { useAuth } from "../../../infra/contexts/UseAuth";
import { TextInputWithIcon } from "../../../components/inputs/TextInputWithIcon";
import { useState } from "react";
import { CgPhotoscan } from "react-icons/cg";
import { PerfilForm } from "./forms/PerfilForm";
import { DadosPessoaisForm } from "./forms/DadosPessoaisForm";
import { DadosAcademicosForm } from "./forms/DadosAcademicosForm";


export const RegisterForm = ({
  handleSubmit,
  register }: any) => {

    const registerInputs = [
      {
        id: 0,
        stepForm: "Perfil",
        children: <PerfilForm/>
      },
      {
        id: 1,
        stepForm: "Dados pessoais",
        last: false,
        children: <DadosPessoaisForm register={register}/>
      },
      {
        id: 2,
        stepForm: "Dados acadêmicos",
        last: true,
        children: <DadosAcademicosForm register={register}/>
      }
    ]
  
    
  const { handleRegisterRequest } = useAuth();
  const [step, setStep] = useState(registerInputs[0]);

  function handleRegister(data: any) {
    data = {
      ...data,
      perfilId: "aa9961fc-12e5-495c-b6ec-440b82c37302"
    }
    console.log(data)
    handleRegisterRequest(data)
      .then((user) => {
        console.log(user);
      })

  }

  const handleNextStep = () => {
    if (step.id === 2) {
      return;
    }
    setStep(registerInputs[step.id + 1]);
  };

  const handlePreviousStep = () => {
    if (step.id === 0) {
      return;
    }
    
    setStep(registerInputs[step.id - 1]);
  };

  return (
    <div className="flex flex-col relative w-screen h-screen items-center space-y-5 ">

      <div className="block w-3/4 ">
        <p className="text-2xl">Seja bem vinda! Vamos completar seu cadastro</p>
        <p className="text-[#6E6D7A]">
          Esses dados ajudarão a tornar suas inscrições em editais mais fáceis!
        </p>
      </div>
      <form onSubmit={handleSubmit(handleRegister)} className="flex flex-col shadow-md bg-white w-3/4 h-3/4 min-h-fit  
      py-3 justify-between pt-4">
        <div className="flex justify-center w-4/5 ">
          {registerInputs.map((inputs) => (
            <Step
              circle={inputs.id < step.id ? 1 : step.id === inputs.id ? 1 : 2}
              stroke={inputs.last ? 3 : inputs.id < step.id ? 1 : step.id === inputs.id ? 1 : 2}
              title={inputs.stepForm} />
          ))}

        </div>
        <div className="flex w-full pt-8 px-14 ">
          {step.children}
        </div>
        <div className="flex justify-between px-4 pt-8 ">
          <a className="font-medium text-sm text-red-500"
            onClick={
              handlePreviousStep
            }>Voltar</a>
          <div className="flex gap-4">
            {/* <Button title="Pular" className="bg-transparent border border-dark_blue text-dark_blue" /> */}
            <Button title="Avançar" type={1}
              onClick={
                handleNextStep

              } />
          </div>
        </div>
      </form>
    </div>
  );
};

