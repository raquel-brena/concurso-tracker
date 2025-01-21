import { PropsWithChildren, useEffect, useState } from "react";
import axios from "axios";
import { AuthContext } from "./UseAuth";
import {
  registerRequest,
  signInRequest,
  verificarCadastro,
} from "../../modules/autenticacao/service/AuthService";
type AuthProviderProps = PropsWithChildren & {
  isSignedIn?: boolean;
};

export default function AuthProvider({ children }: AuthProviderProps) {
  const [user, setUser] = useState<any>(null);
  const [loading, setLoading] = useState(false);

  // const navigation = useNavigate();
  useEffect(() => {
    setLoading(true);
    const fetchData = async () => {
      const token = localStorage.getItem("token");

      if (token) {
        const response = await getUserByToken(token);
        setHeaders({ Authorization: `Bearer ${token}` });
        setUser(response);
      }
    };
    fetchData();
    setLoading(false);
  }, []);

  useEffect(() => {
    if (user) {
      setLoading(false);
    }
  }, [user]);

  async function getUserByToken(token: string) {
    const response = await axios.get(
      `http://localhost:3000/api/usuario/token/${token}`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
    return response.data;
  }

  async function handleRegisterRequest(data: any) {
    try {
      setLoading(true);
      const response = await registerRequest(data);
      setLoading(false);
      return response;
    } catch (error) {
        setLoading(false);
        return error;
    }
  }

  async function handleUpdateUser(data: any) {
    try {
      setLoading(true); // Ativar loading enquanto a requisição é realizada
  
      // Buscar o ID do usuário pelo CPF
      const cpfUnmasked = data.cpf.replace(/\D/g, ""); // Remover caracteres não numéricos
      handleSignInRequest(cpfUnmasked, data.senha);
      
      const responseUser = await axios.get(`http://localhost:8080/api/auth/cpf/${cpfUnmasked}`);
      
      // Verificar se o usuário foi encontrado
      if (!responseUser.data) {
        setLoading(false);
        throw new Error("Usuário não encontrado.");
      }
  
      const userId = responseUser.data.data; // Obtendo o ID do usuário
  
      const updatedData = {
        userId: userId,
        ...data,
      };

      console.log("Atualizando usuário:", updatedData);


      const token = localStorage.getItem("token");
      const updateResponse = await axios.put(
        `http://localhost:8080/api/usuario`, // Usando o ID do usuário na URL
        updatedData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
  
      // Atualizando o estado do usuário com os novos dados
      if (updateResponse.data) {
        setUser(updateResponse.data);
        setLoading(false);
        return updateResponse.data; // Retorna os dados do usuário atualizados
      }
    } catch (error) {
      setLoading(false); // Desativa o loading em caso de erro
      console.error("Erro ao atualizar usuário:", error);
      throw error; // Lança o erro para tratamento posterior
    }
  }  

  async function handleSignInRequest(cpf: string, password: string) {
    setLoading(true);
    const cpfUnmasked = cpf.replace(/\D/g, "");

    console.log(cpfUnmasked, password);
    const response = await signInRequest(cpf, password);
    console.log(response.token);
    localStorage.setItem("token", response.data.token);

    // setTokenSpotify(data.user.userToken.accessToken);
    // setRefreshToken(data.refreshToken);

    setHeaders(`Bearer ${response.data.token}`);

    setUser(response.user);

    setLoading(false);
    return response.data;
  }

  async function handleVerificarCadastro(cpf: string) {
    try {
      setLoading(true);
      const response = await verificarCadastro(cpf);
      setLoading(false);
      return response;
    } catch (error) {
      setLoading(false);
      return null;
    }
  }

  function handleLogout() {
    // navigation("/");
    setUser(null);
    localStorage.removeItem("token");
    setHeaders(null);
  }

  function setHeaders(headers: any) {
    axios.defaults.headers = headers;
  }
  return (
    <AuthContext.Provider
      value={{
        loading,
        user,
        handleSignInRequest,
        handleRegisterRequest,
        handleVerificarCadastro,
        getUserByToken,
        handleLogout,
        handleUpdateUser,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}
