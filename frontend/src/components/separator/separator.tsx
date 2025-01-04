import { HomeIcon } from "@radix-ui/react-icons";

type SeparatorProps = {
  title: string;
};

export const Separator = ({ title }: SeparatorProps) => {
  return (
    <div className="flex items-center w-full text-center">
      <div className="flex-grow h-[0.8px] bg-[#333333]"></div>
      <p className="px-4 text-sm whitespace-nowrap text-[#333333] uppercase">
        {title}
      </p>
      <div className="flex-grow h-[0.8px] bg-[#333333]"></div>
    </div>
  );
};
