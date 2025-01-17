import { HomeIcon } from "@radix-ui/react-icons";
import { FaRegIdCard } from "react-icons/fa";
import { TextInput } from "./TextInput";

type InputProps = {
  title: string;
  placeholder?: string;
  icon: any;
  input: string;
  disabled: boolean | undefined;
  type: string | undefined;
  register: any;
  className?: string;
  classNameInput?: string;
};
export const TextInputWithIcon = ({className, classNameInput, title, placeholder, icon, register, input, type, disabled}: InputProps) => {
  return (
    <div className="flex items-center space-x-2 mt-4" >
      {icon}

      <div className={`flex flex-col ${className}`}>
        <p className="font-medium text-sm text-red-500 uppercase">{title}</p>
        <TextInput 
        disabled={disabled}
        type={type}
        className={`w-72 border border-[#888888] py-2 px-3 rounded-md ${classNameInput} ${disabled ? 'bg-gray-200' : ''}`}
          {...register(input, { required: true })} />
      </div>
    </div>
  );
};
