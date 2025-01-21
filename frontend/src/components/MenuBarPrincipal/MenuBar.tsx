import React, { useState } from "react";
import * as mb from "@radix-ui/react-menubar";
import {
  CheckIcon,
  ChevronDownIcon,
  ChevronRightIcon,
  DotFilledIcon,
} from "@radix-ui/react-icons";
import { MenuBarContent } from "./components/MenuBarContent";
import { MenuBarItem } from "./components/MenuBarItem";
import { MenuBarSeparator } from "./components/MenuBarSeparator";
import { MenuItemSubTrigger } from "./components/MenuItemSubTrigger";
import { MenuItemTrigger } from "./components/MenuItemTrigger copy";
import { MENU_ITEMS, EnumMenuItems } from "./MenuItems";
import { useNavigate } from "react-router-dom";

type MenuBarProps = {
  menuItemSelected: EnumMenuItems;
  setMenuItemSelected: (value: EnumMenuItems) => void;
};

export const Menubar = ({
  menuItemSelected,
  setMenuItemSelected,
}: MenuBarProps) => {
  const navigate = useNavigate();
  return (
    <mb.Root className="flex bg-white pt-2 shadow-md w-full h-12">
      <div className="w-40"></div>
      <div className="w-px bg-slate-100 m-2" />
      {MENU_ITEMS.map((menu) => (
        <mb.Menu key={menu.label}>
          <MenuItemTrigger
            selected={menuItemSelected === menu.label}
            onClick={() => {
              if (!menu.items && menu.link) {
                navigate(menu.link);
              }
              setMenuItemSelected(menu.label);
            }}
          >
            {menu.label} {menu.items && <ChevronDownIcon />}
          </MenuItemTrigger>
          {menu.items && (
            <mb.Portal>
              <MenuBarContent>
                {menu.items.map((item) => (
                  <React.Fragment key={item.label}>
                    {item.subitems ? (
                      <mb.Sub>
                        <MenuItemSubTrigger>
                          {item.label}
                          <div className="ml-auto pl-5 text-mauve9 group-data-[disabled]:text-mauve8 group-data-[highlighted]:text-white">
                            <ChevronRightIcon />
                          </div>
                        </MenuItemSubTrigger>
                        <mb.Portal>
                          <mb.SubContent
                            className="min-w-[220px] rounded-md bg-white p-[5px] shadow-[0px_10px_38px_-10px_rgba(22,_23,_24,_0.35),_0px_10px_20px_-15px_rgba(22,_23,_24,_0.2)] will-change-[transform,opacity] [animation-duration:_400ms] [animation-timing-function:_cubic-bezier(0.16,_1,_0.3,_1)]"
                            alignOffset={-5}
                          >
                            {item.subitems.map((subitem) => (
                              <MenuBarItem
                                key={subitem.label}
                                onClick={() => {
                                  if (subitem.link) {
                                    navigate(subitem.link);
                                  }
                                }}
                              >
                                {subitem.label}
                              </MenuBarItem>
                            ))}
                          </mb.SubContent>
                        </mb.Portal>
                      </mb.Sub>
                    ) : (
                      <MenuBarItem
                        onClick={() => {
                          console.log("Item Clicado:", item); // Apenas exibe o item clicado
                          if (item.link) {
                            navigate(item.link); // Navega para o link, se existir
                          }
                        }}
                      >
                        {item.label}
                        <div className="ml-auto pl-5 text-mauve9 group-data-[disabled]:text-mauve8 group-data-[highlighted]:text-white">
                          {item.shortcurt} ⌘ N
                        </div>
                      </MenuBarItem>
                    )}
                    {item.separator && <MenuBarSeparator />}
                  </React.Fragment>
                ))}
              </MenuBarContent>
            </mb.Portal>
          )}
        </mb.Menu>
      ))}
    </mb.Root>
  );
};
