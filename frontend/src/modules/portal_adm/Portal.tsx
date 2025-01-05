
import { useState } from "react";
import { ContentButton } from "../../components/buttons/ContentButton";
import { Footer } from "../../components/Footer";
import { Menubar } from "../../components/MenuBarPrincipal/MenuBar";
import { ChamadasContent } from "./components/ChamadasContent";
import { Content } from "./components/Content";
import { NoticiasContent } from "./components/NoticiasContent";
import {
  MENU_ITEMS,
  EnumMenuItems,
} from "../../components/MenuBarPrincipal/MenuItems";
import { Home } from "./Home";
import { Editais } from "./Editais";
import { useNavigate } from "react-router-dom";
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

      {menuItemSelected === EnumMenuItems.PAGINA_INICIAL && <Home />}
      {menuItemSelected === EnumMenuItems.EDITAIS && <Editais />}
      {menuItemSelected === EnumMenuItems.NOTICIAS && <Noticias />}
      {menuItemSelected === EnumMenuItems.EQUIPE && <Inscricoes />}

      <Footer />
    </div>
  );
}
export default Portal;
