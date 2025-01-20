import axios from "axios";
import { toast } from 'sonner'

export const signInRequest = async (cpf: string, senha: string) => {
    try {
        const response = await axios.post(
          "http://localhost:8081/api/auth/login",
          {
            cpf,
            senha,
          },
          
        );
        toast.success("Login realizado com sucesso");

        return response.data;
    }
    catch (error) {
        toast.error("Usuário ou senha inválidos");
    }
}

export const registerRequest = async (data: any) => {
    try {
        const response = await axios.post("http://localhost:8081/api/auth/register",
            data
        );
        toast.success(response.data.message);
        return response.data;
    } catch (error: any) {
        if (error.response.status === 409) {
            toast.error("Usuário já cadastrado");
        } else {
            toast.error("Erro ao cadastrar usuário");
        }
            return error;
    }
}

export const verificarCadastro = async (cpf: string) => {
        const response = await axios.get(`http://localhost:8081/api/auth/cpf/${cpf}`); 
        console.log(response);
        return response.data;
}