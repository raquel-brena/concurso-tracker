import { createContext, useContext, useState } from "react";

type AuthContextType = {
    user: any;
    // posts: any[];
    loading: boolean;
    // handleLogin: (cpf: string, password: string) => Promise<void>;
    handleSignInRequest: (cpf: string, senha: string) => Promise<any>;
    handleRegisterRequest: (data: any) => Promise<any>;
    handleVerificarCadastro: (cpf: string) => Promise<any>;
    handleLogout: () => void;
    getUserByToken: (token: string) => Promise<any>;
    handleUpdateUser: (data: any) => Promise<any>; 
    // handleRegister: (data: UserRegister) => Promise<void>;
    // handleLogout: () => void;
    // setPosts: (posts: any[]) => void;
    // tokenSpotify: string | null;
};

export const AuthContext = createContext<AuthContextType | undefined>({
    user: null,
    loading: true,
    handleSignInRequest: async () => { },
    handleRegisterRequest: async () => { },
    handleVerificarCadastro: async () => { },
    getUserByToken: async () => { },
    handleLogout: () => { },
    handleUpdateUser: async () => { },
} as AuthContextType);

export function useAuth() {
    const context = useContext(AuthContext);

    if (context === undefined) {
        throw new Error("UseAuth must be used within a AuthProvider");
    }

    return context;
}