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

  async function handleSignInRequest(cpf: string, password: string) {
    setLoading(true);

    console.log(cpf, password);
    const data = await signInRequest(cpf, password);
    console.log(data);
    localStorage.setItem("token", data.user.token);

    // setTokenSpotify(data.user.userToken.accessToken);
    // setRefreshToken(data.refreshToken);

    setHeaders(`Bearer ${data.user.token}`);

    setUser(data.user);

    setLoading(false);
    // navigation("/home");
    return data;
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
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}
