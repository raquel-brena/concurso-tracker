import * as Menubar from "@radix-ui/react-menubar";

interface MenuBarRootProps {
  children?: React.ReactNode;
}

export const MenuBarRoot = ({ children }: MenuBarRootProps) => {
  return (
    <Menubar.Root className="flex bg-white pt-2 shadow-md w-full h-12 z-10">
      {children}
    </Menubar.Root>
  );
};
