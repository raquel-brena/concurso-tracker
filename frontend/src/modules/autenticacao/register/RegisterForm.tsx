
import { useForm } from "react-hook-form";
import { InputWithIcon } from "../../../components/inputs/TextInputWithIcon";
import { FaRegIdCard } from "react-icons/fa";
import { MdOutlineMailOutline, MdOutlinePersonOutline, MdOutlinePhoneAndroid } from "react-icons/md";
import { Step } from "../components/Step";
export const RegisterForm = () => {
   const {
    register,
    setValue,
    handleSubmit,
    formState: { errors },
  } = useForm<FormData>();

  return (
    <div className="flex flex-col relative w-screen h-screen items-center space-y-5 ">
      <div className="flex bg-white shadow-md w-full h-12" />
      <div className="block w-3/4 ">
        <p className="text-2xl">Seja bem vinda! Vamos completar seu cadastro</p>
        <p className="text-[#6E6D7A]">
          Esses dados ajudarão a tornar suas inscrições em editais mais fáceis!
        </p>
      </div>
      <div className="flex flex-col bg-white shadow-md w-3/4 h-3/4  justify-start pt-4">
        <div className="flex justify-center w-full">
          <Step circle={1} stroke={1} title="Perfil" />
          <Step circle={2} stroke={2} title="Dados pessoais" />
          <Step circle={2} stroke={2} title="Anexos" />
          <Step circle={1} stroke={1} title="Perfil" />
          <Step circle={1} stroke={2} title="Perfil" />
        </div>
        <div className="flex justify-between px-4 pt-8">
          <div className="flex flex-col space-y-4 ">
            <p className="font-semibold font-lg">Dados pessoais</p>
            <InputWithIcon icon={<FaRegIdCard size={24} />} title="cpf" />
            <InputWithIcon icon={<MdOutlinePersonOutline size={26} />} title="Nome" />
            <InputWithIcon icon={<MdOutlineMailOutline size={24} />} title="E-mail" />
            <InputWithIcon icon={<MdOutlinePhoneAndroid size={24} />} title="Celular" />
          </div>
          <div className="flex flex-col space-y-4 ">
            <p className="font-semibold font-lg">Anexos</p>
            <InputWithIcon icon={<FaRegIdCard size={24} />} title="cpf" />
            <InputWithIcon icon={<MdOutlinePersonOutline size={26} />} title="Nome" />
          </div>
        </div>
        <div className="flex justify-between px-4 pt-8">
          <a className="font-medium text-sm text-red-500">Voltar</a>
          <div className="flex gap-4">
            <button className="py-1 px-8 border border-red-500 rounded-full text-red-500 font-medium">
              Pular
            </button>
            <button className="py-1 px-8 bg-red-500 hover:bg-red-600 rounded-full text-white font-medium">
              Avançar
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};


