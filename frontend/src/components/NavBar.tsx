import { useState } from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import { MenuItem, Collapse, MenuList } from "@mui/material";

export default function NavBar() {
  const [userType, setUserType] = useState("admin"); // Pode ser 'admin', 'candidato', ou 'anon'

  const menuItensAdmin = [
    { name: "Página Inicial" },
    {
      name: "Editais",
      subMenu: [
        { name: "Editais", subMenu: ["Submenu 1", "Submenu 2"] },
        {
          name: "Concursos",
          subMenu: [
            "Gerenciar concursos",
            "Homologar",
            "Gerenciar recursos",
            "Resultado Preliminar",
            "Resultado Final",
          ],
        },
        { name: "Processo Seletivo" },
        { name: "Gerenciar Cargos" },
        { name: "Gerenciar Áreas" },
      ],
    },
    {
      name: "Estatísticas", submenu: ["Submenu 1", "Submenu 2"],
    },
    { name: "Notícias" },
    { name: "Equipe" },
  ];

  const menuItensCandidato = [
    { name: "Página Inicial" },
    { name: "Editais" },
    { name: "Bancas" },
    { name: "Instituições" },
    { name: "Notícias" },
  ];

  let menuItens: {
    name: string;
    subMenu?: { name: string; subMenu?: string[] }[];
  }[] = [];
  if (userType === "admin") {
    menuItens = menuItensAdmin;
  } else if (userType === "candidato") {
    menuItens = menuItensCandidato;
  } else {
    menuItens = [];
  }

  const renderMenuItems = (items: { name: string; subMenu?: any[] }[]) => {
    return items.map((item, index) => {
      const [open, setOpen] = useState(false);

      const handleClick = () => {
        setOpen(!open); // Alterna entre abrir e fechar o submenu
      };

      return (
        <div key={index} style={{ display: "inline-block", marginRight: 20 }}>
          <MenuItem onClick={handleClick}>
            <Typography
              variant="h6"
              color="default"
              component="div"
              sx={{ color: "#303030", fontSize: "16px" }}
            >
              {item.name}
            </Typography>
          </MenuItem>

          {/* Se o item tem subMenu, renderiza os subitens na vertical */}
          {item.subMenu && (
            <Collapse in={open} timeout="auto" unmountOnExit>
              <MenuList
                sx={{
                  display: "flex",
                  flexDirection: "column",
                  position: "absolute", // Posicionamento absoluto
                  top: "100%", // Coloca os submenus logo abaixo do item
                  zIndex: 10, // Garante que o submenu fique sobre o AppBar
                  backgroundColor: "#FFFFFF", // Define um fundo para o submenu
                  boxShadow: "0px 4px 6px rgba(0, 0, 0, 0.1)", // Sombra para destacar o submenu
                }}
              >
                {renderMenuItems(item.subMenu)}{" "}
                {/* Recursão para renderizar submenus */}
              </MenuList>
            </Collapse>
          )}

          {/* Se o item do subMenu tem um subItem, renderiza os subitens na horizontal */}
          {item.subMenu && item.subMenu.some(sub => sub.subMenu) && (
            <Collapse in={open} timeout="auto" unmountOnExit>
              <MenuList
                sx={{
                  display: "flex",
                  flexDirection: "row", // Subsubmenus serão renderizados na horizontal
                  position: "absolute", // Posicionamento absoluto para evitar aumentar a altura do AppBar
                  top: "100%", // Coloca os subitens na mesma linha
                  left: "100%", // Faz com que os subsubmenus apareçam ao lado do submenu
                  zIndex: 20, // Garante que os subsubmenus fiquem sobrepostos aos submenus
                  backgroundColor: "#FFFFFF",
                  boxShadow: "0px 4px 6px rgba(0, 0, 0, 0.1)",
                }}
              >
                {renderMenuItems(item.subMenu.filter(sub => sub.subMenu))} {/* Renderiza subsubmenus */}
              </MenuList>
            </Collapse>
          )}
        </div>
      );
    });
  };

  return (
    <Box sx={{ flexGrow: 1, width: "100%" }}>
      <AppBar
        position="static"
        sx={{ backgroundColor: "#FFFFFF", boxSizing: "border-box" }}
      >
        <Toolbar sx={{ width: "100%", padding: "0px", display: "flex" }}>
          <IconButton
            edge="start"
            color="default"
            aria-label="menu"
            sx={{ mr: 2 }}
          >
            <MenuIcon />
          </IconButton>

          {/* Mapeia os itens do menu */}
          {renderMenuItems(menuItens)}
        </Toolbar>
      </AppBar>
    </Box>
  );
}
