import { useState } from "react";
import { Footer } from "../../components/Footer";
import { Menubar } from "../../components/MenuBarPrincipal/MenuBar";
import {
  MENU_ITEMS,
  EnumMenuItems,
} from "../../components/MenuBarPrincipal/MenuItems";
import { Home } from "./Home";
import { Editais } from "./Editais";
import { Outlet, useNavigate } from "react-router-dom";
import { Noticias } from "./Noticias";
import { Inscricoes } from "./Inscricoes";

function Portal() {
  const [menuItemSelected, setMenuItemSelected] = useState(MENU_ITEMS[0].label);

  const navigate = useNavigate();
  return (
    <div className="flex flex-col items-center w-screen min-h-screen relative overflow-x-hidden ">
      <Menubar
        menuItemSelected={menuItemSelected}
        setMenuItemSelected={setMenuItemSelected}
      />
      <Outlet />
      {/* <RegisterForm/>
      {menuItemSelected === EnumMenuItems.PAGINA_INICIAL && <Home />}
      {menuItemSelected === EnumMenuItems.EDITAIS && <Editais />}
      {menuItemSelected === EnumMenuItems.NOTICIAS && <Noticias />}
      {menuItemSelected === EnumMenuItems.EQUIPE && <Inscricoes />} */}

      <Footer />
    </div>
  );
}
export default Portal;
