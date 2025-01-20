

import { useState } from "react";

import { useNavigate } from "react-router-dom";
import { Step } from "../../../autenticacao/components/Step";
import { Button } from "../../../../components/buttons/Button";
import { DadosGeraisForm } from "./forms/DadosGeraisForm";
import { FormacoesEExperienciasForm } from "./forms/FormacoesEExperienciasForm";
import { CargosEVagasForm } from "./forms/CargosEVagasForm";
import { CalendarioForm } from "./forms/CalendarioForm";
import { useForm } from "react-hook-form";


export const CadastrarEditalForm = () => {
 
  const handlePreviousStep = () => {
    if (step.id === 0) {
      window.location.reload();
      return;
    }

    setStep(registerInputs[step.id - 1]);
  };
  
  const registerInputs = [
    {
      id: 0,
      stepForm: "Dados gerais",
      children: <DadosGeraisForm handlePreviousStep={handlePreviousStep} />,
    },
    {
      id: 1,
      stepForm: "Formações e experiências",
      last: false,
      children: <FormacoesEExperienciasForm />,
    },
    {
      id: 2,
      stepForm: "Cargos e vagas",
      last: false,
      children: <CargosEVagasForm />,
    },
    {
      id: 3,
      stepForm: "Calendário",
      last: true,
      children: <CalendarioForm />,
    },
  ];
  const [step, setStep] = useState(registerInputs[0]);

  const navigate = useNavigate();
  function handleRegister(data: any) {
    console.log(data);
    // data = {
    //   ...data,
    //   perfilId: "aa9961fc-12e5-495c-b6ec-440b82c37302",
    // };

    data = {
      cpf: data.cpf,
      password: data.senha,
    };

    console.log(data);
  }

  const onSubmit = (data: any) => {
    console.log(data);

    if (step.id === registerInputs.length - 2) {
      handleRegister(data);
    } else {
      //dar update
      handleNextStep();
    }
  };

  const handleNextStep = () => {
    if (step.id === registerInputs.length - 1) {
      return;
    }

    setStep(registerInputs[step.id + 1]);
  };


  

  return (

      <div
        // onSubmit={handleSubmit(onSubmit)}
        className="flex flex-col  
      justify-between items-center w-full h-full"
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

       
      </div>

  );
};
