import React, { useState } from "react";
import * as Menubar from "@radix-ui/react-menubar";
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

const RADIO_ITEMS = ["Andy", "Benoît", "Luis"];
const CHECK_ITEMS = ["Always Show Bookmarks Bar", "Always Show Full URLs"];

const MenubarDemo = () => {
  const [checkedSelection, setCheckedSelection] = React.useState([
    CHECK_ITEMS[1],
  ]);
  const [radioSelection, setRadioSelection] = React.useState(RADIO_ITEMS[2]);

  return (
    <Menubar.Root className="flex bg-white pt-2 shadow-md w-full h-12">
      <div className="w-40"></div>
      <div className="w-px bg-slate-400 m-2" />
      <Menubar.Menu>
        <MenuItemTrigger>Página Inicial</MenuItemTrigger>
        <Menubar.Portal>
          <MenuBarContent>
            <MenuBarItem>
              New Tab{" "}
              <div className="ml-auto pl-5 text-mauve9 group-data-[disabled]:text-mauve8 group-data-[highlighted]:text-white">
                ⌘ T
              </div>
            </MenuBarItem>
            <MenuBarItem>
              New Window{" "}
              <div className="ml-auto pl-5 text-mauve9 group-data-[disabled]:text-mauve8 group-data-[highlighted]:text-white">
                ⌘ N
              </div>
            </MenuBarItem>
            <MenuBarItem>New Incognito Window</MenuBarItem>
            <MenuBarSeparator />
            <Menubar.Sub>
              <MenuItemSubTrigger>
                Share
                <div className="ml-auto pl-5 text-mauve9 group-data-[disabled]:text-mauve8 group-data-[highlighted]:text-white">
                  <ChevronRightIcon />
                </div>
              </MenuItemSubTrigger>
              <Menubar.Portal>
                <Menubar.SubContent
                  className="min-w-[220px] rounded-md bg-white p-[5px] shadow-[0px_10px_38px_-10px_rgba(22,_23,_24,_0.35),_0px_10px_20px_-15px_rgba(22,_23,_24,_0.2)] will-change-[transform,opacity] [animation-duration:_400ms] [animation-timing-function:_cubic-bezier(0.16,_1,_0.3,_1)]"
                  alignOffset={-5}
                >
                  <MenuBarItem>Email Link</MenuBarItem>
                  <MenuBarItem>Messages</MenuBarItem>
                  <MenuBarItem>Notes</MenuBarItem>
                </Menubar.SubContent>
              </Menubar.Portal>
            </Menubar.Sub>
            <MenuBarSeparator />
            <Menubar.Item className="group relative flex h-[25px] select-none items-center rounded px-2.5 text-[13px] leading-none text-violet11 outline-none data-[disabled]:pointer-events-none data-[highlighted]:bg-gradient-to-br data-[highlighted]:from-violet9 data-[highlighted]:to-violet10 data-[disabled]:text-mauve8 data-[highlighted]:text-violet1">
              Print…{" "}
              <div className="ml-auto pl-5 text-mauve9 group-data-[disabled]:text-mauve8 group-data-[highlighted]:text-white">
                ⌘ P
              </div>
            </Menubar.Item>
          </MenuBarContent>
        </Menubar.Portal>
      </Menubar.Menu>

      <Menubar.Menu>
        <MenuItemTrigger selected>
          Editais <ChevronDownIcon />
        </MenuItemTrigger>
        <Menubar.Portal>
          <MenuBarContent>
            <MenuBarItem>
              Undo{" "}
              <div className="ml-auto pl-5 text-mauve9 group-data-[disabled]:text-mauve8 group-data-[highlighted]:text-white">
                ⌘ Z
              </div>
            </MenuBarItem>
            <MenuBarItem>
              Redo{" "}
              <div className="ml-auto pl-5 text-mauve9 group-data-[disabled]:text-mauve8 group-data-[highlighted]:text-white">
                ⇧ ⌘ Z
              </div>
            </MenuBarItem>
            <MenuBarSeparator />
            <Menubar.Sub>
              <MenuItemSubTrigger>
                Find
                <div className="ml-auto pl-5 text-mauve9 group-data-[disabled]:text-mauve8 group-data-[highlighted]:text-white">
                  <ChevronRightIcon />
                </div>
              </MenuItemSubTrigger>

              <Menubar.Portal>
                <Menubar.SubContent
                  className="min-w-[220px] rounded-md bg-white p-[5px] shadow-[0px_10px_38px_-10px_rgba(22,_23,_24,_0.35),_0px_10px_20px_-15px_rgba(22,_23,_24,_0.2)] will-change-[transform,opacity] [animation-duration:_400ms] [animation-timing-function:_cubic-bezier(0.16,_1,_0.3,_1)]"
                  alignOffset={-5}
                >
                  <Menubar.Item className="group relative flex h-[25px] select-none items-center rounded px-2.5 text-[13px] leading-none text-violet11 outline-none data-[disabled]:pointer-events-none data-[state=open]:bg-violet4 data-[highlighted]:bg-gradient-to-br data-[highlighted]:from-violet9 data-[highlighted]:to-violet10 data-[disabled]:text-mauve8 data-[highlighted]:text-violet1 data-[state=open]:text-violet11">
                    Search the web…
                  </Menubar.Item>
                  <MenuBarSeparator />
                  <MenuBarItem>Find…</MenuBarItem>
                  <MenuBarItem>Find Next</MenuBarItem>
                  <MenuBarItem>Find Previous</MenuBarItem>
                </Menubar.SubContent>
              </Menubar.Portal>
            </Menubar.Sub>
            <MenuBarSeparator />
            <MenuBarItem>Cut</MenuBarItem>
            <MenuBarItem>Copy</MenuBarItem>
            <MenuBarItem>Paste</MenuBarItem>
          </MenuBarContent>
        </Menubar.Portal>
      </Menubar.Menu>

      <Menubar.Menu>
        <MenuItemTrigger>
          Estatísticas
          <ChevronDownIcon />
        </MenuItemTrigger>
        <Menubar.Portal>
          <MenuBarContent>
            {CHECK_ITEMS.map((item) => (
              <Menubar.CheckboxItem
                className="relative flex h-[25px] select-none items-center rounded px-2.5 pl-5 text-[13px] leading-none text-violet11 outline-none data-[disabled]:pointer-events-none data-[highlighted]:bg-gradient-to-br data-[highlighted]:from-violet9 data-[highlighted]:to-violet10 data-[disabled]:text-mauve8 data-[highlighted]:text-violet1"
                key={item}
                checked={checkedSelection.includes(item)}
                onCheckedChange={() =>
                  setCheckedSelection((current) =>
                    current.includes(item)
                      ? current.filter((el) => el !== item)
                      : current.concat(item)
                  )
                }
              >
                <Menubar.ItemIndicator className="absolute left-0 inline-flex w-5 items-center justify-center">
                  <CheckIcon />
                </Menubar.ItemIndicator>
                {item}
              </Menubar.CheckboxItem>
            ))}
            <MenuBarSeparator />
            <Menubar.Item className="group relative flex h-[25px] select-none items-center rounded pl-5 pr-2.5 text-[13px] leading-none text-violet11 outline-none data-[disabled]:pointer-events-none data-[state=open]:bg-violet4 data-[highlighted]:bg-gradient-to-br data-[highlighted]:from-violet9 data-[highlighted]:to-violet10 data-[disabled]:text-mauve8 data-[highlighted]:data-[state=open]:text-violet1 data-[highlighted]:text-violet1 data-[state=open]:text-violet11">
              Reload{" "}
              <div className="ml-auto pl-5 text-mauve9 group-data-[disabled]:text-mauve8 group-data-[highlighted]:text-white">
                ⌘ R
              </div>
            </Menubar.Item>
            <Menubar.Item
              className="group relative flex h-[25px] select-none items-center rounded pl-5 pr-2.5 text-[13px] leading-none text-violet11 outline-none data-[disabled]:pointer-events-none data-[state=open]:bg-violet4 data-[highlighted]:bg-gradient-to-br data-[highlighted]:from-violet9 data-[highlighted]:to-violet10 data-[disabled]:text-mauve8 data-[highlighted]:data-[state=open]:text-violet1 data-[highlighted]:text-violet1 data-[state=open]:text-violet11"
              disabled
            >
              Force Reload{" "}
              <div className="ml-auto pl-5 text-mauve9 group-data-[disabled]:text-mauve8 group-data-[highlighted]:text-white">
                ⇧ ⌘ R
              </div>
            </Menubar.Item>
            <MenuBarSeparator />
            <MenuBarItem>Toggle Fullscreen</MenuBarItem>
            <MenuBarSeparator />
            <MenuBarItem>Hide Sidebar</MenuBarItem>
          </MenuBarContent>
        </Menubar.Portal>
      </Menubar.Menu>

      <Menubar.Menu>
        <MenuItemTrigger>
          Equipe
          <ChevronDownIcon />
        </MenuItemTrigger>
        <Menubar.Portal>
          <MenuBarContent>
            <Menubar.RadioGroup
              value={radioSelection}
              onValueChange={setRadioSelection}
            >
              {RADIO_ITEMS.map((item) => (
                <Menubar.RadioItem
                  className="relative flex h-[25px] select-none items-center rounded pl-5 pr-2.5 text-[13px] leading-none text-violet11 outline-none data-[disabled]:pointer-events-none data-[highlighted]:bg-gradient-to-br data-[highlighted]:from-violet9 data-[highlighted]:to-violet10 data-[disabled]:text-mauve8 data-[highlighted]:text-violet1"
                  key={item}
                  value={item}
                >
                  <Menubar.ItemIndicator className="absolute left-0 inline-flex w-5 items-center justify-center">
                    <DotFilledIcon />
                  </Menubar.ItemIndicator>
                  {item}
                </Menubar.RadioItem>
              ))}
              <MenuBarSeparator />
              <MenuBarItem>Edit…</MenuBarItem>
              <MenuBarSeparator />
              <MenuBarItem>Add Profile…</MenuBarItem>
            </Menubar.RadioGroup>
          </MenuBarContent>
        </Menubar.Portal>
      </Menubar.Menu>
    </Menubar.Root>
  );
};

export default MenubarDemo;
