
import { MenuBarRoot } from "../../components/MenuBarPrincipal/components/MenuBarRoot";

import { useForm } from "react-hook-form";
import { useState } from "react";
import { RegisterForm } from "./register/RegisterForm";
import { LoginForm } from "./login/LoginForm";

type Inputs = {
  cpf: string;
  senha: string;
  nome?: string;
  matricula?: string;
  setor?: string;
  dn?: string;
  estadoCivil?: string;
  sexo?: string;
  peso?: string;
  altura?: string;
}

const AuthPage = () => {

  const [showInputPassword, setShowInputPassword] = useState(0); // 0 = não mostrar, 1 = login, 2 = cadastro
  const [showRegisterForm, setShowRegisterForm] = useState(false);
  const {
      register,
      handleSubmit,
      watch,
      formState: { errors },
  } = useForm<Inputs>()

  return (
    <div className="flex flex-col w-screen h-screen ">
      <MenuBarRoot>
        <div className="w-40"></div>
        <div className="w-px bg-slate-400 m-2" />
      </MenuBarRoot>

      {/* BOLHA */}
      <div className="w-[120%] h-[100%] absolute md:-left-2/4 md:-top-0 -top-2/4 bg-[#FCF7F5] rounded-full -z-10" />

      {showRegisterForm ? (<>
                <RegisterForm

                    handleSubmit={handleSubmit}
                    register={register} />

            </>) : (

                <div className="flex size-full flex-col md:flex-row justify-between md:justify-normal gap-2">
                    {/* ESQUERDA*/}
                    <div className="flex w-1/2 justify-center">
                        <div className="flex flex-col w-full justify-center items-end space-y-2">
                            <p className="font-semibold    uppercase text-sm text-dark_blue ">
                                Nos conheça
                            </p>
                            <div className="flex w-fit justify-center items-center space-x-6">
                                <div className="h-72 w-36 flex relative bg-white shadow-sm" />
                                <div className="h-72 w-36 flex relative bg-white shadow-sm" />
                                <div className="h-72 w-36 flex relative bg-white shadow-sm" />
                            </div>
                        </div>
                    </div>
                    <LoginForm
                        showInputPassword={showInputPassword}
                        setShowInputPassword={setShowInputPassword}
                        setShowRegisterForm={setShowRegisterForm}
                        handleSubmit={handleSubmit}
                        register={register} />
                </div>
            )}
    </div>
  );
};

export default AuthPage;
