import * as Menubar from "@radix-ui/react-menubar";

interface MenuBarContentProps {
  children?: React.ReactNode;
}

export const MenuBarContent = ({ children }: MenuBarContentProps) => {
  return (
    <Menubar.Content
      className="min-w-56  rounded-md bg-white p-[5px] shadow-[0px_10px_38px_-10px_rgba(22,_23,_24,_0.35),_0px_10px_20px_-15px_rgba(22,_23,_24,_0.2)] will-change-[transform,opacity] [animation-duration:_400ms] [animation-timing-function:_cubic-bezier(0.16,_1,_0.3,_1)]"
      align="start"
      sideOffset={5}
      alignOffset={-3}
    >
      {children}
    </Menubar.Content>
  );
};
