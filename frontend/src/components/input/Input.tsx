import { HomeIcon } from "@radix-ui/react-icons";
import { FaRegIdCard } from "react-icons/fa";

type InputProps = { 
  title: string;
  placeholder?: string;
  icon: any;
}
export const Input = ({title, placeholder, icon} : InputProps) => {
  return (
    <div className="flex items-center space-x-2 mt-4">
      {icon}
           
              <div className="flex flex-col">
              <p className="font-medium text-sm text-red-500 uppercase">{title}</p>
              <input className="max-w-72 border border-[#888888] py-2 px-3 rounded-md" />
              </div>
            </div>
  );
};
