import * as Menubar from "@radix-ui/react-menubar";

interface MenuBarSeparatorProps {
  children?: React.ReactNode;
}

export const MenuBarSeparator = ({ children }: MenuBarSeparatorProps) => {
  return <Menubar.Separator className="m-[5px] h-px bg-violet6" />;
};
