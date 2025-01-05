import { HomeIcon } from "@radix-ui/react-icons";
import { Separator } from "../separator/separator";

interface HeaderProps {
  title: string;
  subtitle: string;
  description: string;
}

export const Header = ({ title, subtitle, description }: HeaderProps) => {
  return (
    <div className="flex flex-col w-full h-1/4  pt-2 space-y-4">
      <div className="flex text-center items-center text-sm select-none">
        <HomeIcon />
        <p>
          {">"} Editais {">"} Gerenciar processos seletivos
        </p>
      </div>
      <p className="text-2xl text-[#B41313] font-medium">{title}</p>
      <div className="w-screen flex h-px bg-[#F3F3F3]" />
      <p>{description}</p>
      <p className="font-semibold pt-6 uppercase">{subtitle}</p>
      <div className="w-screen flex h-px bg-[#F3F3F3]" />
    </div>
  );
};
