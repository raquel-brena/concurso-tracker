import { TextInputWithIcon } from "../../../../components/inputs/TextInputWithIcon";
import { Button } from "../../../../components/buttons/Button";
import cadastro_completo from "../../../../assets/cadastro_completo.svg";
export const FinalizarForm = () => {
  return (
    <div className="flex justify-center w-full h-full space-y-4 px-14 ">
      <img src={cadastro_completo} alt="Logo" className="" />
      <div className="flex w-1/3 flex-col gap-4 pt-6 ">
        <p className="text-2xl">
          Cadastro completo, agora é só ficar atenta nos editais!
        </p>
        <p className=" font-light">
          Mantenha seus dados sempre atualizados e fique à vontade para
          gerenciá-los no seu perfil quando quiser.
        </p>
        <Button style={3} className="self-end" onClick={()=> window.location.reload()}> Fazer login </Button>
      </div>
    </div>
  );
};
