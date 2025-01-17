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
    register }: any) => {

    const { handleVerificarCadastro, handleSignInRequest } = useAuth();
    const navigate = useNavigate();

    const onSubmit = (data: any) => {
        if (showInputPassword === 0) {
            handleVerificarConta(data);
        } else {
            handleLogin(data);
        }
    };

    function handleVerificarConta(form: any) {
        console.log(form);
        handleVerificarCadastro(form.cpf)
            .then((response) => {
                if (response === null) {
                    // setShowInputPassword(2);
                    setShowRegisterForm(true);
                    return response.data;
                } else {
                    setShowInputPassword(1);
                    return response.data;
                }

            })
    }

    function handleLogin(data: any) {
        handleSignInRequest(data.cpf, data.senha)
            .then((user) => {
                if (user) {
                    navigate("/home");
                }
                console.log(user);
            })
    }

    return (
        <form onSubmit={handleSubmit(onSubmit)}
            className="flex md:w-1/2 md:h-full 
md:items-center md:justify-center w-full "
        >
            <BoxContent className="space-y-4  px-10 py-6 min-w-[30rem] md:min-h-fit justify-between w-full h-[50vh] md:h-0 md:w-0">
                <p className="font-semibold text-lg bottom">
                    {!showInputPassword ? "Identifique-se" : "Digite sua senha"}
                </p>
                <div className="flex w-full flex-col space-y-2">

                    <p className="text-base font-medium">Número do CPF</p>
                    {!showInputPassword && (<>  <p className="text-sm text-text_secundary">
                        Digite seu CPF para criar ou acessar sua conta
                    </p></>

                    )}
                    <TextInput  {...register("cpf")} />

                    {showInputPassword === 1 && (
                        <>
                            <p className="text-base font-medium">Senha</p>
                            <p className="text-sm text-text_secundary">
                                Digite sua senha atual
                            </p>
                            <TextInput type={"password"} {...register("senha", { required: true })} />
                        </>)}
                    {showInputPassword === 2 && (
                        <>
                            <p className="text-base font-medium">Senha</p>
                            <p className="text-sm text-text_secundary">
                                Crie uma senha para acessar sua conta
                            </p>
                            <TextInput type={"password"}  {...register("senha", { required: true })} />
                        </>)}

                </div>

                <Button
                    title="Continuar" type={1} className="self-end"
                />

                <a className="text-sm font-medium text-dark_blue self-center">
                    Termo de Uso e Aviso de Privacidade
                </a>
            </BoxContent>
        </form>
    )
}