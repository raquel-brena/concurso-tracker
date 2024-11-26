import { HomeIcon } from "@radix-ui/react-icons";

export const Separator = () => {
  return (
    <div className="flex text-center place-items-center justify-between py-4">
      <div className="line-clamp-1 w-1/2  h-[0.8px] bg-[#333333] "></div>
      <p className="px-4 text-sm text-[#333333] uppercase">Titulo</p>
      <div className="line-clamp-1 w-1/2  h-[0.8px] bg-[#333333] "></div>
    </div>
  );
};
