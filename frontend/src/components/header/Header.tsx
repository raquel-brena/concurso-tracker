import { HomeIcon } from "@radix-ui/react-icons";
import { Separator } from "../separator/separator";

interface HeaderProps {
  title: string;

}

export const Header = ({ title }: HeaderProps) => {
  return (
    <div className="flex flex-col w-full h-1/4  pt-2 space-y-4">
      <div className="flex text-center items-center text-sm select-none">
        <HomeIcon />
        <p>
          {">"} Editais {">"} Gerenciar processos seletivos
        </p>
      </div>
      <p className="text-2xl text-[#B41313] font-medium">{title}</p>
    </div>
  );
};
