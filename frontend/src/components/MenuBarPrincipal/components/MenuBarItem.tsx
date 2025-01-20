import * as Menubar from "@radix-ui/react-menubar";

interface MenuBarItemProps {
  children?: React.ReactNode;
}

export const MenuBarItem = ({ children }: MenuBarItemProps) => {
  return (
    <Menubar.Item
      className="group relative flex h-[25px] select-none items-center rounded 
    px-2.5 text-[13px] leading-none text-violet11 outline-none
    hover:text-slate-600  
    data-[disabled]:pointer-events-none data-[state=open]:bg-violet4 
    data-[highlighted]:bg-gradient-to-br data-[highlighted]:from-violet9 
    data-[highlighted]:to-violet10 data-[disabled]:text-mauve8 
    data-[highlighted]:data-[state=open]:text-violet1 
    data-[highlighted]:text-violet1 data-[state=open]:text-violet11"
    >
      {children}
    </Menubar.Item>
  );
};
