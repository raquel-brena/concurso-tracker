import { HomeIcon } from "@radix-ui/react-icons";

interface HeaderProps {
  title: string;
  subtitle: string;
  description: string;
}

export const Header = ({ title, subtitle, description }: HeaderProps) => {
  return (
    <div className="flex flex-col w-full h-1/4 px-10 pt-2 space-y-4">
      <div className="flex text-center items-center text-sm select-none">
        <HomeIcon />
        <p>
          {">"} Editais {">"} Gerenciar processos seletivos
        </p>
      </div>
      <p className="text-2xl text-red-500 font-medium">{title}</p>
      <p>{description}</p>
      <p className="font-semibold pt-3 uppercase">{subtitle}</p>
    </div>
  );
};
