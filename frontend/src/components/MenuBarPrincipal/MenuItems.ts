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
    link: "/adm",
    separator: true,
  },
  {
    label: EnumMenuItems.EDITAIS,
    link: "/editais",
    items: [
      {
        label: "Processos seletivos",
        shortcurt: "",
        separator: true,
        subitems: [
          {
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
        separator: false,
      },
      {
        label: "Gerenciar vagas",
        shortcurt: "",
        separator: false,
      },
      {
        label: "Gerenciar áreas",
        shortcurt: "",
        separator: false,
      },
    ],
  },
  {
    label: EnumMenuItems.ESTATISTICAS,
    link: "/Estatísticas",
    items: [
      {
        label: "",
        shortcurt: "",
        subitems: [],
        separator: false,
      },
    ],
  },
  {
    label: EnumMenuItems.NOTICIAS,
    items: [
      {
        label: "Cadastrar notícia",
        shortcurt: "",
        subitems: [],
        separator: false,
      },
      {
        label: "Visualizar notícias",
        shortcurt: "",
        subitems: [],
        separator: false,
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
