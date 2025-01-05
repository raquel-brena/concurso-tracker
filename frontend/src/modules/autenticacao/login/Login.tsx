import { useNavigate } from "react-router-dom";
import { MenuBarRoot } from "../../../components/MenuBarPrincipal/components/MenuBarRoot";
import { BoxContent } from "../components/BoxContent";
import { TextInput } from "../../../components/inputs/TextInput";
import { Button } from "../../../components/buttons/Button";
const Login = () => {
  return (
    <div className="flex flex-col w-screen h-screen ">
      <MenuBarRoot>
        <div className="w-40"></div>
        <div className="w-px bg-slate-400 m-2" />
      </MenuBarRoot>

      {/* BOLHA */}
      <div className="w-[120%] h-[100%] absolute md:-left-2/4 md:-top-0 -top-2/4 bg-[#FCF7F5] rounded-full -z-10" />

      <div className="flex size-full flex-col md:flex-row justify-between md:justify-normal gap-2">
        {/* ESQUERDA*/}
        <div className="flex w-1/2 justify-center">
          <div className="flex flex-col w-full justify-center items-end space-y-2">
            <p className="font-semibold uppercase text-sm text-red-600 ">
              Nos conheça
            </p>
            <div className="flex w-fit justify-center items-center space-x-6">
              <div className="h-72 w-36 flex relative bg-white shadow-sm" />
              <div className="h-72 w-36 flex relative bg-white shadow-sm" />
              <div className="h-72 w-36 flex relative bg-white shadow-sm" />
            </div>
          </div>
        </div>

        {/* DIREITA*/}
        <div
          className="flex md:w-1/2 md:h-full 
          md:items-center md:justify-center w-full "
        >
          <BoxContent className="space-y-4 px-10 py-6 min-w-[30rem] md:min-h-80 justify-between w-full h-[50vh] md:h-0 md:w-0">
            <p className="font-semibold text-lg bottom">Identifique-se:</p>
            <div className="flex w-full flex-col space-y-2">
              <p className="text-base font-medium">Número do CPF</p>
              <p className="text-sm">
                Digite seu CPF para criar ou acessar sua conta
              </p>
              <TextInput />
            </div>

            <Button title="Continuar" type={1} className="w-fit self-end"/>

            <a className="text-sm font-medium text-red-500 self-center">
              Termo de Uso e Aviso de Privacidade
            </a>
          </BoxContent>
        </div>
      </div>
    </div>
  );
};

export default Login;
