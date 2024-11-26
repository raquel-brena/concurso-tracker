import * as Menubar from "@radix-ui/react-menubar";

interface MenuItemTriggerProps {
  children?: React.ReactNode;
  selected?: boolean;
}

export const MenuItemTrigger = ({
  children,
  selected,
}: MenuItemTriggerProps) => {
  return (
    <div className="flex flex-col justify-between items-center ">
      <Menubar.Trigger
        className="flex items-center justify-between gap-0.5 font-medium
    rounded px-3 py-2 text-sm text-[#444746] leading-none 
    outline-none data-[highlighted]:bg-violet4 data-[state=open]:bg-violet4
    hover:text-red-600 data-[selected]:font-semibold transition-colors"
        data-selected={selected}
      >
        {children}
      </Menubar.Trigger>
      {selected && <div className="w-4/5 bg-red-600 h-[2px] rounded-bl-xl" />}
    </div>
  );
};
