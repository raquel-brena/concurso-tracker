import { Link } from "react-router-dom";

export enum EnumMenuItems {
  PAGINA_INICIAL = "Página Inicial",
  EDITAIS = "Editais",
  ESTATISTICAS = "Estatísticas",
  NOTICIAS = "Noticias",
  EQUIPE = "Equipe",
}

export const MENU_ITEMS = [
  {
    label: EnumMenuItems.PAGINA_INICIAL,
    link: "/portal/home",
    separator: true,
  },
  {
    label: EnumMenuItems.EDITAIS,
  
    items: [
      {
        label: "Processos seletivos",
        shortcurt: "",
        separator: true,
        subitems: [
          {
            link: "/portal/editais",
            label: "Gerenciar editais",
            shortcurt: "",
          },
          {
            label: "Homologar inscrições",
            shortcurt: "",
          },
          {
            label: "Gerenciar recursos",
            shortcurt: "",
          },
          {
            label: "Resultados preliminares",
            shortcurt: "",
          },
          {
            label: "Resultados finais",
            shortcurt: "",
          },
        ],
      },
      {
        label: "Gerenciar cargos",
        shortcurt: "",
        link: "/portal/editais/cargos",
        separator: false,
      },
      {
        label: "Gerenciar vagas",
        shortcurt: "",
        link: "/portal/editais/vagas",
        separator: false,
      },
      {
        label: "Gerenciar áreas",
        shortcurt: "",
        link: "/portal/editais/areas",
        separator: false,
      },
    ],
  },
  {
    label: EnumMenuItems.ESTATISTICAS,
    link: "/estatísticas",
    items: [
    ],
  },
  {
    label: EnumMenuItems.NOTICIAS,
    items: [
      {
        label: "Cadastrar notícia",
        shortcurt: "",
        separator: false,
        link: "/portal/noticias/cadastrar",
      },
      {
        label: "Visualizar notícias",
        shortcurt: "",
        separator: false,
        link: "/portal/noticias",
      },
    ],
  },
  {
    label: EnumMenuItems.EQUIPE,
    // items: [
    //   {
    //     label: "Cadastrar notícia",
    //     shortcurt: "",
    //     subitems: [],
    //     separator: false,
    //   },
    //   {
    //     label: "Visualizar notícias",
    //     shortcurt: "",
    //     subitems: [],
    //     separator: false,
    //   },
    // ],
  },
];
