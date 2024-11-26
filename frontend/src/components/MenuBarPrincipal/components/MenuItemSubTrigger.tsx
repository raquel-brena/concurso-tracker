import { ChevronRightIcon } from "@radix-ui/react-icons";
import * as Menubar from "@radix-ui/react-menubar";

interface MenuItemTriggerProps {
  children?: React.ReactNode;
}

export const MenuItemSubTrigger = ({ children }: MenuItemTriggerProps) => {
  return (
    <Menubar.SubTrigger className="group relative flex h-[25px] select-none items-center rounded px-2.5 text-[13px] leading-none text-violet11 outline-none data-[disabled]:pointer-events-none data-[state=open]:bg-violet4 data-[highlighted]:bg-gradient-to-br data-[highlighted]:from-violet9 data-[highlighted]:to-violet10 data-[disabled]:text-mauve8 data-[highlighted]:data-[state=open]:text-violet1 data-[highlighted]:text-violet1 data-[state=open]:text-violet11">
      {children}
    </Menubar.SubTrigger>
  );
};
