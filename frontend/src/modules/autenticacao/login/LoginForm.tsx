import { useNavigate } from "react-router-dom";
import { useAuth } from "../../../infra/contexts/UseAuth";
import { BoxContent } from "../components/BoxContent";
import { TextInput } from "../../../components/inputs/TextInput";
import { Button } from "../../../components/buttons/Button";

export const LoginForm = ({
  showInputPassword,
  setShowInputPassword,
  setShowRegisterForm,
  handleSubmit,
  register,
}: any) => {
  const { handleVerificarCadastro, handleSignInRequest, loading } = useAuth();
  const navigate = useNavigate();

  const onSubmit = (data: any) => {
    if (showInputPassword === 0) {
      handleVerificarConta(data);
    } else if (showInputPassword === 2) {
      setShowRegisterForm(true);
    } else {
      handleLogin(data);
    }
  };

  function handleVerificarConta(form: any) {
    const cpfUnmasked = unmaskCpf(form.cpf);
    console.log(cpfUnmasked);
    handleVerificarCadastro(cpfUnmasked).then((response) => {
      if (response === null) {
        setShowInputPassword(2);
        console.log;
        return response.data;
      } else {
        setShowInputPassword(1);
        return response.data;
      }
    });
  }

  function handleLogin(data: any) {
    handleSignInRequest(data.cpf, data.senha).then((data) => {
      console.log(data);
      if (data.user.admin) {
        navigate("/portal/home");
      } else {
        navigate("/portal/home");
      }
      console.log(data);
    });
  }

  const unmaskCpf = (value: string) => {
    return value.replace(/\D/g, "");
  };

  const maskCPF = (value: string | any) => {
    return value
      .replace(/\D/g, "")
      .replace(/(\d{3})(\d)/, "$1.$2")
      .replace(/(\d{3})(\d)/, "$1.$2")
      .replace(/(\d{3})(\d{1,2})/, "$1-$2")
      .replace(/(-\d{2})\d+?$/, "$1");
  };

  return (
    <form
      onSubmit={handleSubmit(onSubmit)}
      className="flex md:w-1/2 md:h-full 
md:items-center md:justify-center w-full "
    >
      <BoxContent className="space-y-4  px-10 py-6 min-w-[30rem] md:min-h-fit justify-between w-full h-[50vh] md:h-0 md:w-0">
        <p className="font-semibold text-lg bottom">
          {!showInputPassword ? "Identifique-se" : "Digite sua senha"}
        </p>
        <div className="flex w-full flex-col space-y-1">
          <p className="text-base font-medium">Número do CPF</p>
          {!showInputPassword && (
            <>
              {" "}
              <p className="text-sm text-text_secundary">
                Digite seu CPF para criar ou acessar sua conta
              </p>
            </>
          )}
          <TextInput
            {...register("cpf")}
            onChange={(e: any) => {
              e.target.value = maskCPF(e.target.value);
            }}
          />

          {showInputPassword != 0 && (
            <p className={`text-base font-medium`}>
              {showInputPassword === 2 ? "Crie sua senha" : "Senha"}
            </p>
          )}

          {showInputPassword != 0 && (
            <p className="text-sm text-text_secundary">
              {showInputPassword === 2
                ? "Crie uma senha para acessar sua conta"
                : "Digite sua senha atual"}
            </p>
          )}

          {showInputPassword != 0 && (
            <TextInput type="password" {...register("senha")} required={true} />
          )}
        </div>
        <Button style={1} className="self-end" onClick={handleSubmit(onSubmit)}>
          {loading ? "Carregando..." : "Continuar"}
        </Button>
        <a className="text-sm font-medium text-dark_blue self-center">
          Termo de Uso e Aviso de Privacidade
        </a>
      </BoxContent>
    </form>
  );
};
